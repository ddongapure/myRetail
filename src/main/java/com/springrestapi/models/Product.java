package com.springrestapi.models;

/**
 * Created by ddongapu on 8/20/2018.
 */

import org.springframework.data.annotation.Id;

import java.util.Map;

public class Product {

    @Id
    public String productId;

    public String productName;

    public Map<String, String> current_price;

    public Product() {}

    public Product(String productId, String productName, Map<String,String> current_price) {
        this.productId = productId;
        this.productName = productName;
        this.current_price = current_price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String title) {
        this.productName = title;
    }

    public Map<String, String> getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(Map<String, String> current_price) {
        this.current_price = current_price;
    }
}
