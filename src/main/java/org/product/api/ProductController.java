package org.product.api;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestResponse;
import org.product.data.Repository;
import org.product.data.RepositoryImpl;
import org.product.data.model.Product;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;


import java.net.URI;
import java.util.Collection;
@Path("/product")
public class ProductController {
    private final Repository repository;

    public ProductController() {
        this.repository = RepositoryImpl.getInstance();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<Collection<Product>> getProducts() {
        return ResponseBuilder
                .ok(repository.getProducts())
                .build();
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<Product> getProduct(@PathParam("id") int id) {
        if (id <= 0) {
            return RestResponse.status(400);
        }
        var product = repository.getProduct(id);
        if (product == null) {
            return RestResponse.status(404);
        }
        return ResponseBuilder
                .ok(product)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse addProduct(Product product) {
        if (!Product.isValid(product)) {
            return RestResponse.status(400);
        }
        repository.addProduct(product);
        return RestResponse.created(URI.create("/product/" + product.getId()));
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse updateProduct(@QueryParam("id") int id,Product product){
        if(repository.getProduct(id) == null){
            return  RestResponse.status(404);
        }
        if (!Product.isValid(product)) {
            return RestResponse.status(400,"Product is not valid");
        }
        product.setId(id);
        repository.updateProduct(product);
        return ResponseBuilder.ok()
                .header("Location", "/product/" + id)
                .build();
    }

    @DELETE
    public RestResponse deleteProduct(@QueryParam("id") int id){
        repository.removeProduct(id);
        return ResponseBuilder.noContent().build();
    }

}