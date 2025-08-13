package org.product.data;

import org.product.data.model.Product;

import java.util.Collection;

public interface Repository {
    Collection<Product> getProducts();
    public Product getProduct(int id);
    public void addProduct(Product product);
    public void removeProduct(int id);
    public void updateProduct(Product product);
}
