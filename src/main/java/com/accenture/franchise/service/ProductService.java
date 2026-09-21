package com.accenture.franchise.service;

import com.accenture.franchise.domain.Product;
import com.accenture.franchise.dto.CreateProductRequest;
import com.accenture.franchise.exception.ResourceNotFoundException;
import com.accenture.franchise.repository.BranchRepository;
import com.accenture.franchise.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;

    public Mono<Product> createProduct(Long branchId, CreateProductRequest request) {
        return branchRepository.findById(branchId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Sucursal no encontrada")))
                .flatMap(branch -> {
                    Product product = new Product();
                    product.setBranchId(branchId);
                    product.setName(request.getName());
                    product.setStock(request.getStock());
                    return productRepository.save(product);
                });
    }

    public Flux<Product> getProductsByBranch(Long branchId) {
        return productRepository.findByBranchId(branchId);
    }

    public Mono<Product> getProductById(Long id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Producto no encontrado")));
    }

    public Mono<Product> updateProductStock(Long id, Integer stock) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Producto no encontrado")))
                .flatMap(product -> {
                    product.setStock(stock);
                    return productRepository.save(product);
                });
    }

    public Mono<Product> updateProductName(Long id, String name) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Producto no encontrado")))
                .flatMap(product -> {
                    product.setName(name);
                    return productRepository.save(product);
                });
    }

    public Mono<Void> deleteProduct(Long id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Producto no encontrado")))
                .flatMap(product -> productRepository.deleteById(id));
    }

    public Mono<Product> getProductWithMaxStock(Long branchId) {
        return productRepository.findFirstByBranchIdOrderByStockDesc(branchId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No hay productos en esta sucursal")));
    }
}