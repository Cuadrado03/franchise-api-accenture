package com.accenture.franchise.controller;

import com.accenture.franchise.domain.Franchise;
import com.accenture.franchise.domain.Product;
import com.accenture.franchise.dto.CreateFranchiseRequest;
import com.accenture.franchise.exception.ResourceNotFoundException;
import com.accenture.franchise.service.FranchiseService;
import com.accenture.franchise.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/franchises")
@RequiredArgsConstructor
public class FranchiseController {

    private final FranchiseService franchiseService;
    private final ProductService productService;

    @PostMapping
    public Mono<ResponseEntity<Franchise>> createFranchise(@RequestBody CreateFranchiseRequest request) {
        return franchiseService.createFranchise(request)
                .map(franchise -> ResponseEntity.status(HttpStatus.CREATED).body(franchise));
    }

    @GetMapping
    public Flux<Franchise> getAllFranchises() {
        return franchiseService.getAllFranchises();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Franchise>> getFranchiseById(@PathVariable Long id) {
        return franchiseService.getFranchiseById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Franchise>> updateFranchiseName(
            @PathVariable Long id,
            @RequestParam String name) {
        return franchiseService.updateFranchiseName(id, name)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteFranchise(@PathVariable Long id) {
        return franchiseService.deleteFranchise(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .onErrorReturn(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/products/max-stock")
    public Mono<ResponseEntity<Product>> getProductWithMaxStock(@PathVariable Long id) {
        return productService.getProductWithMaxStock(id)
                .map(ResponseEntity::ok)
                .onErrorResume(ex -> {
                    if (ex instanceof ResourceNotFoundException) {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                    return Mono.error(ex);
                });
    }
}