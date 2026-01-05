package com.demo.security;

public final class SessionKeys {
	private SessionKeys() {}

    public static final String AUTH_USER_ID = "AUTH_USER_ID";
    public static final String AUTH_ROLE    = "AUTH_ROLE";
    public static final String ADMIN_PRODUCTS_LIST_URL = "ADMIN_PRODUCTS_LIST_URL";
    
    public static final String USER_PRODUCTS_LIST_URL  = "USER_PRODUCTS_LIST_URL";

    // flash
    public static final String FLASH_ERR = "FLASH_ERR";
    public static final String FLASH_OK  = "FLASH_OK";
    
    // lưu URL GET để quay lại sau login
    public static final String AFTER_LOGIN_URL = "AFTER_LOGIN_URL";

    // lưu pending action cho POST add-to-cart
    public static final String PENDING_ADD_TO_CART = "PENDING_ADD_TO_CART";
    
    // lưu pending action cho POST place order
    public static final String PENDING_PLACE_ORDER = "PENDING_PLACE_ORDER";
}
