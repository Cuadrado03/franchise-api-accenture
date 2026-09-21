package com.accenture.franchise.controller;

import com.accenture.franchise.domain.Product;
import com.accenture.franchise.dto.CreateProductRequest;
import com.accenture.franchise.dto.UpdateStockRequest;
import com.accenture.franchise.service.ProductService;
import com.accenture.franchise.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/branches/{branchId}/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public Mono<ResponseEntity<Product>> createProduct(
            @PathVariable Long branchId,
            @RequestBody CreateProductRequest request) {
        return productService.createProduct(branchId, request)
                .map(product -> ResponseEntity.status(HttpStatus.CREATED).body(product));
    }

    @GetMapping
    public Flux<Product> getProductsByBranch(@PathVariable Long branchId) {
        return productService.getProductsByBranch(branchId);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Product>> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/stock")
    public Mono<ResponseEntity<Product>> updateProductStock(
            @PathVariable Long id,
            @RequestBody UpdateStockRequest request) {
        return productService.updateProductStock(id, request.getStock())
                .map(ResponseEntity::ok)
                .onErrorResume(ex -> {
                    if (ex instanceof ResourceNotFoundException) {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                    return Mono.error(ex);
                });
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Product>> updateProductName(
            @PathVariable Long id,
            @RequestParam String name) {
        return productService.updateProductName(id, name)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .onErrorReturn(ResponseEntity.notFound().build());
    }

}