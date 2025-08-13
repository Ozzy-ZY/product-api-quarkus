package org.product.data;

import org.product.data.model.Product;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryImpl implements Repository{
    private static volatile Repository instance;
    private final Map<Integer, Product> products = new HashMap<>();
    private static int nextId;
    private RepositoryImpl() {
        products.put(1, new Product(1, "Product 1", "Description 1", 100.0));
        products.put(2, new Product(2, "Product 2", "Description 2", 200.0));
        products.put(3, new Product(3, "Product 3", "Description 3", 300.0));
        products.put(4, new Product(4, "Product 4", "Description 4", 400.0));
        products.put(5, new Product(5, "Product 5", "Description 5", 500.0));
        nextId = 6;
    }
    public static Repository getInstance() {
        if (instance == null) {
            synchronized (RepositoryImpl.class) {
                if (instance == null) {
                    instance = new RepositoryImpl();
                }
            }
        }
        return instance;
    }
    @Override
    public Collection<Product> getProducts() {
        return products.values();
    }

    @Override
    public Product getProduct(int id) {
        return products.get(id);
    }

    @Override
    public void addProduct(Product product) {
        product.setId(nextId++);
        products.put(product.getId(), product);
    }

    @Override
    public void removeProduct(int id) {
        products.remove(id);
    }

    @Override
    public void updateProduct(Product product) {
        products.put(product.getId(), product);
    }
}
