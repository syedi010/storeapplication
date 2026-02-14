package com.store.storeapplication.Controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.storeapplication.Comman.BusinessCode;
import com.store.storeapplication.DTO.ApiRequest;
import com.store.storeapplication.DTO.ApiResponse;
import com.store.storeapplication.DTO.fetchIdenticals;
import com.store.storeapplication.Entity.Product;
import com.store.storeapplication.Service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // GET all products
    @GetMapping("/getAllProducts")

    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {

        return ResponseEntity.ok()
                .body(new ApiResponse<>(true, "Fetched all product", BusinessCode.SUCCESS,
                        productService.getAllProducts()));
    }

    // POST add product
    @PostMapping("/addProduct")
    public ResponseEntity<ApiResponse<Product>> addProduct(@RequestBody ApiRequest<Product> product) {

        Product p = product.getData();
        if (p.getName() == null || p.getName().isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false, "Product name cannot be empty", BusinessCode.VALIDATION_ERROR, null));
        }
        Product savedProduct = productService.saveProduct(p);
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Product added successfully", BusinessCode.SUCCESS, savedProduct));
    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@RequestBody ApiRequest<Product> product,
            @PathVariable Long id) {

        Product newProduct = product.getData();

        if (newProduct.getName() == null || newProduct.getName().isEmpty())
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "name cannot be empty", BusinessCode.VALIDATION_ERROR, null));

        Product dbproduct = productService.getProductById(id);

        if (dbproduct == null)
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Id not found", BusinessCode.NOT_FOUND, null));

        dbproduct.setCategory(newProduct.getCategory());
        dbproduct.setName(newProduct.getName());
        dbproduct.setPrice(newProduct.getPrice());
        dbproduct.setDescription(newProduct.getDescription());
        dbproduct = productService.updateProduct(dbproduct);
        return ResponseEntity.ok()
                .body(new ApiResponse<>(true, "Modified Successfully", BusinessCode.SUCCESS, dbproduct));

    }

    @DeleteMapping("delete/{Id}")
    public ResponseEntity<ApiResponse<Void>> deleteMapping(@PathVariable Long Id) {
        productService.deleteProduct(Id);

        return ResponseEntity.ok(new ApiResponse<>(true, "Deleted successfully", BusinessCode.SUCCESS, null));
    }

    @GetMapping("/fetchIdenticalProduct")
    public ResponseEntity<fetchIdenticals<Set<String>>> fetchIdentical()
    {
        Set<String> uniqueProduct=productService.getAllProducts().stream().map(Product::getName).collect(Collectors.toSet());

        return ResponseEntity.ok(new fetchIdenticals<>(true,"Unique products is fetched",0,uniqueProduct,uniqueProduct.size()));
    }
}
