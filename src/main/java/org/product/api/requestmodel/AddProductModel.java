package org.product.api.requestmodel;

public record AddProductModel(String idempotencyKey, String name, String description, double price) {
}
