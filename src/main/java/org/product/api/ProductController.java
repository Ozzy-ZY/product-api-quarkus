package org.product.api;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.vertx.core.http.HttpServerRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestResponse;
import org.product.data.Repository;
import org.product.data.RepositoryImpl;
import org.product.data.model.Product;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;


import java.util.Arrays;
import java.util.Collection;

@Path("/product")
public class ProductController {
    private final Repository repository;

    public ProductController() {
        this.repository = RepositoryImpl.getInstance();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Product> getProducts() {
        return repository.getProducts();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse addProduct(Product product) {
        if (!Product.isValid(product)) {
            return RestResponse.status(400);
        }
        repository.addProduct(product);
        return ResponseBuilder.ok()
                .header("Location", "/product/" + product.getId())
                .build();
    }
}