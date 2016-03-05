package com.epam.preprod.bohdanov.utils.captcha;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import nl.captcha.Captcha;
import nl.captcha.text.producer.TextProducer;

public class CaptchaContainer {
    private static final Logger LOG = Logger.getLogger(CaptchaContainer.class);
    private final int CAPTCHA_VALUE_RANGE = 999999;
    private long maxAge;
    private Map<String, UserCaptcha> container;
    private final Object monitor = new Object();

    public CaptchaContainer(long maxAge) {
        container = new ConcurrentHashMap<String, UserCaptcha>();
        this.maxAge = maxAge;
        new Thread(new CaptchaRemover(maxAge)).start();
    }

    public class CaptchaRemover implements Runnable {
        private long timeToSleep;

        public CaptchaRemover(long timeToSleep) {
            this.timeToSleep = timeToSleep;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    LOG.info("Sleep about " + timeToSleep);
                    Thread.sleep(timeToSleep);
                } catch (InterruptedException e) {
                    LOG.error("Remover error" + e.getMessage());
                }
                removeOldCaptcha();
            }
        }

        private void removeOldCaptcha() {
            timeToSleep = maxAge;
            LOG.info("Start removing");
            while (container.isEmpty()) {
                synchronized (monitor) {
                    try {
                        LOG.info("Empty container.. i go sleep");
                        monitor.wait();
                    } catch (InterruptedException e) {
                        LOG.error(e.getMessage());
                    }
                }
            }
            long currentTime = System.currentTimeMillis();
            for (String elem : container.keySet()) {
                UserCaptcha currentCaptcha = container.get(elem);
                if (currentCaptcha.isAlive()) {
                    if (currentCaptcha.time - currentTime < timeToSleep) {
                        timeToSleep = currentCaptcha.time - currentTime;
                    }
                } else {
                    container.remove(elem);
                }
            }
            LOG.info("End removing");
        }

    }

    public class UserCaptcha {
        private int value;
        private long time;

        public UserCaptcha() {
            time = System.currentTimeMillis() + maxAge;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public void invalidateCaptcha() {
            this.time = 0;
        }

        public boolean isAlive() {
            long currentTime = System.currentTimeMillis();
            return time > currentTime;
        }

        public void outCaptchaImage(OutputStream out) {
            Captcha captcha = new Captcha.Builder(200, 50).addText(new TextProducer() {
                public String getText() {
                    return String.valueOf(value);
                }
            }).build();
            try {
                ImageIO.write(captcha.getImage(), "jpg", out);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String generateCaptcha() {
        UserCaptcha captcha = new UserCaptcha();
        Random r = ThreadLocalRandom.current();
        String key = String.valueOf(r.nextInt());
        captcha.setValue(r.nextInt(CAPTCHA_VALUE_RANGE));
        container.put(key, captcha);
        synchronized (monitor) {

            monitor.notifyAll();
        }
        return key;
    }

    public UserCaptcha getCaptcha(String key) {
        return container.get(key);
    }

}
