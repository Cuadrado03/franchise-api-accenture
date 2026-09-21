package com.accenture.franchise.service;

import com.accenture.franchise.domain.Franchise;
import com.accenture.franchise.dto.CreateFranchiseRequest;
import com.accenture.franchise.exception.ResourceNotFoundException;
import com.accenture.franchise.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FranchiseService {

    private final FranchiseRepository franchiseRepository;

    public Mono<Franchise> createFranchise(CreateFranchiseRequest request) {
        Franchise franchise = new Franchise();
        franchise.setName(request.getName());
        return franchiseRepository.save(franchise);
    }

    public Flux<Franchise> getAllFranchises() {
        return franchiseRepository.findAll();
    }

    public Mono<Franchise> getFranchiseById(Long id) {
        return franchiseRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Franquicia no encontrada")));
    }

    public Mono<Franchise> updateFranchiseName(Long id, String name) {
        return franchiseRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Franquicia no encontrada")))
                .flatMap(franchise -> {
                    franchise.setName(name);
                    return franchiseRepository.save(franchise);
                });
    }

    public Mono<Void> deleteFranchise(Long id) {
        return franchiseRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Franquicia no encontrada")))
                .flatMap(franchise -> franchiseRepository.deleteById(id));
    }
}