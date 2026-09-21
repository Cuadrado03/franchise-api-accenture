package com.accenture.franchise.repository;

import com.accenture.franchise.domain.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
    Flux<Product> findByBranchId(Long branchId);
    Mono<Product> findFirstByBranchIdOrderByStockDesc(Long branchId);
}