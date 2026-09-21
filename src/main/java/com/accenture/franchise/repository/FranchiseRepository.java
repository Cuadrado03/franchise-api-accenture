package com.accenture.franchise.repository;

import com.accenture.franchise.domain.Franchise;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface FranchiseRepository extends ReactiveCrudRepository<Franchise, Long> {
    Mono<Franchise> findByName(String name);
}