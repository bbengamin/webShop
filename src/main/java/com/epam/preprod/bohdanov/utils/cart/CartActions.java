package com.epam.preprod.bohdanov.utils.cart;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.epam.preprod.bohdanov.model.bean.CartBean;
import com.epam.preprod.bohdanov.service.ItemService;
import com.epam.preprod.bohdanov.utils.PriceUtils;

public class CartActions {
    private static final String PRODUCT_ID = "product_id";
    private static final String QUANTITY = "quantity";
    private static final String RESPONSE_PRODUCT_QUANTITY = "productQuantity";
    private static final String RESPONSE_PRODUCT_TOTAL = "productTotal";
    private static final String RESPONSE_TOTAL = "total";
    private static final String RESPONSE_COUNT = "count";
    private static final String ACTION_REMOVE = "remove";
    private static final String ACTION_UPDATE = "update";
    private static final String ACTION_ADD = "add";
    private Map<String, Action> actions;

    public CartActions(ItemService itemService) {
        PriceUtils priceUtils = new PriceUtils();
        actions = new HashMap<>();
        actions.put(ACTION_ADD, new Action() {

            @Override
            public JSONObject updateCart(HttpServletRequest request, CartBean cart) {
                String id = request.getParameter(PRODUCT_ID);
                cart.addProduct(itemService.getProductById(id));
                JSONObject answer = new JSONObject();
                answer.put(RESPONSE_COUNT, cart.getTotalCountOfProducts());
                answer.put(RESPONSE_TOTAL,
                        priceUtils.getLocalizedBigDecimalValue(cart.getTotal(), Locale.getDefault()));
                return answer;
            }
        });
        actions.put(ACTION_UPDATE, new Action() {

            @Override
            public JSONObject updateCart(HttpServletRequest request, CartBean cart) {
                String id = request.getParameter(PRODUCT_ID);
                String quantity = request.getParameter(QUANTITY);
                JSONObject answer = new JSONObject();

                if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(quantity)) {
                    cart.updateProductCount(id, Integer.parseInt(quantity));
                }
                answer.put(RESPONSE_COUNT, cart.getTotalCountOfProducts());
                answer.put(RESPONSE_TOTAL,
                        priceUtils.getLocalizedBigDecimalValue(cart.getTotal(), Locale.getDefault()));
                answer.put(RESPONSE_PRODUCT_TOTAL,
                        priceUtils.getLocalizedBigDecimalValue(cart.getTotalOfProductById(id), Locale.getDefault()));
                answer.put(RESPONSE_PRODUCT_QUANTITY, cart.getTotalCountOfProductById(id));

                return answer;
            }
        });
        actions.put(ACTION_REMOVE, new Action() {

            @Override
            public JSONObject updateCart(HttpServletRequest request, CartBean cart) {
                String id = request.getParameter(PRODUCT_ID);
                JSONObject answer = new JSONObject();

                if (StringUtils.isNotBlank(id)) {
                    cart.removeProduct(id);
                }
                answer.put(RESPONSE_COUNT, cart.getTotalCountOfProducts());
                answer.put(RESPONSE_TOTAL,
                        priceUtils.getLocalizedBigDecimalValue(cart.getTotal(), Locale.getDefault()));
                return answer;
            }
        });
    }

    public Action getAction(String key) {
        return actions.get(key);
    }

}
