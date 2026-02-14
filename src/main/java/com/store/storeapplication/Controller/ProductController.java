package com.store.storeapplication.Controller;

import com.store.storeapplication.Entity.Product;
import com.store.storeapplication.Service.ProductService;
import com.store.storeapplication.Comman.BusinessCode;
import com.store.storeapplication.DTO.ApiRequest;
import com.store.storeapplication.DTO.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                .body(new ApiResponse<List<Product>>(true, "Fetched all product", BusinessCode.SUCCESS,
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
                    .body(new ApiResponse<Product>(false, "name cannot be empty", BusinessCode.VALIDATION_ERROR, null));

        Product dbproduct = productService.getProductById(id);

        if (dbproduct == null)
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<Product>(false, "Id not found", BusinessCode.NOT_FOUND, null));

        dbproduct.setCategory(newProduct.getCategory());
        dbproduct.setName(newProduct.getName());
        dbproduct.setPrice(newProduct.getPrice());
        dbproduct.setDescription(newProduct.getDescription());
        dbproduct = productService.updateProduct(dbproduct);
        return ResponseEntity.ok()
                .body(new ApiResponse<Product>(true, "Modified Successfully", BusinessCode.SUCCESS, dbproduct));

    }

    @DeleteMapping("delete/{Id}")
    public ResponseEntity<ApiResponse<Void>> deleteMapping(@PathVariable Long Id) {
        productService.deleteProduct(Id);

        return ResponseEntity.ok(new ApiResponse<>(true, "Deleted successfully", BusinessCode.SUCCESS, null));
    }
}
