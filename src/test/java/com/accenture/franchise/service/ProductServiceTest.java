package com.accenture.franchise.service;

import com.accenture.franchise.domain.Branch;
import com.accenture.franchise.domain.Product;
import com.accenture.franchise.dto.CreateProductRequest;
import com.accenture.franchise.repository.BranchRepository;
import com.accenture.franchise.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private Branch branch;
    private CreateProductRequest request;

    @BeforeEach
    void setUp() {
        branch = new Branch(1L, 1L, "Test Branch");
        product = new Product(1L, 1L, "Test Product", 100);
        request = new CreateProductRequest("Test Product", 100);
    }

    @Test
    void testCreateProduct() {
        when(branchRepository.findById(1L))
                .thenReturn(Mono.just(branch));
        when(productRepository.save(any(Product.class)))
                .thenReturn(Mono.just(product));

        StepVerifier.create(productService.createProduct(1L, request))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void testGetProductsByBranch() {
        when(productRepository.findByBranchId(1L))
                .thenReturn(Flux.just(product));

        StepVerifier.create(productService.getProductsByBranch(1L))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void testGetProductById() {
        when(productRepository.findById(1L))
                .thenReturn(Mono.just(product));

        StepVerifier.create(productService.getProductById(1L))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void testUpdateProductStock() {
        product.setStock(200);
        when(productRepository.findById(1L))
                .thenReturn(Mono.just(new Product(1L, 1L, "Test Product", 100)));
        when(productRepository.save(any(Product.class)))
                .thenReturn(Mono.just(product));

        StepVerifier.create(productService.updateProductStock(1L, 200))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void testUpdateProductName() {
        product.setName("Updated Product");
        when(productRepository.findById(1L))
                .thenReturn(Mono.just(new Product(1L, 1L, "Test Product", 100)));
        when(productRepository.save(any(Product.class)))
                .thenReturn(Mono.just(product));

        StepVerifier.create(productService.updateProductName(1L, "Updated Product"))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void testGetProductWithMaxStock() {
        when(productRepository.findFirstByBranchIdOrderByStockDesc(1L))
                .thenReturn(Mono.just(product));

        StepVerifier.create(productService.getProductWithMaxStock(1L))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void testDeleteProduct() {
        when(productRepository.findById(1L))
                .thenReturn(Mono.just(product));
        when(productRepository.deleteById(1L))
                .thenReturn(Mono.empty());

        StepVerifier.create(productService.deleteProduct(1L))
                .verifyComplete();
    }
}