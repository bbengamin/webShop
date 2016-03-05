package com.epam.preprod.bohdanov.controller.filter.wrapper;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class GZipServletResponseWrapper extends HttpServletResponseWrapper {
    private GZipServletOutputStream gzipOutputStream = null;
    private PrintWriter printWriter = null;

    public GZipServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public void close() throws IOException {
        if (this.printWriter != null) {
            this.printWriter.close();
        }

        if (this.gzipOutputStream != null) {
            this.gzipOutputStream.close();
        }
    }

    @Override
    public void flushBuffer() throws IOException {

        if (this.printWriter != null) {
            this.printWriter.flush();
        }

        IOException streamException = null;
        try {
            if (this.gzipOutputStream != null) {
                this.gzipOutputStream.flush();
            }
        } catch (IOException e) {
            streamException = e;
        }

        IOException superFlushException = null;
        try {
            super.flushBuffer();
        } catch (IOException e) {
            superFlushException = e;
        }

        if (streamException != null)
            throw streamException;
        if (superFlushException != null)
            throw superFlushException;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (this.printWriter != null) {
            throw new IllegalStateException("PrintWriterare are already obtained - cannot get OutputStream");
        }
        if (this.gzipOutputStream == null) {
            this.gzipOutputStream = new GZipServletOutputStream(getResponse().getOutputStream());
        }
        return this.gzipOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (this.printWriter == null && this.gzipOutputStream != null) {
            throw new IllegalStateException("OutputStream are already obtained - cannot get PrintWriter");
        }
        if (this.printWriter == null) {
            this.gzipOutputStream = new GZipServletOutputStream(getResponse().getOutputStream());
            this.printWriter = new PrintWriter(
                    new OutputStreamWriter(this.gzipOutputStream, getResponse().getCharacterEncoding()));
        }
        return this.printWriter;
    }

    @Override
    public void setContentLength(int len) {
    }

}
