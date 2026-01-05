package com.demo.web.session;

import java.io.Serializable;

public class PendingAddToCart implements Serializable {
  
	private static final long serialVersionUID = 1L;
	
	private final int productId;
    private final int qty;

    public PendingAddToCart(int productId, int qty) {
        this.productId = productId;
        this.qty = qty;
    }
    public int getProductId() { return productId; }
    public int getQty() { return qty; }
}
