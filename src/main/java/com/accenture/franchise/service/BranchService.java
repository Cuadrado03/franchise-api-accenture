package com.accenture.franchise.service;

import com.accenture.franchise.domain.Branch;
import com.accenture.franchise.dto.CreateBranchRequest;
import com.accenture.franchise.exception.ResourceNotFoundException;
import com.accenture.franchise.repository.BranchRepository;
import com.accenture.franchise.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;
    private final FranchiseRepository franchiseRepository;

    public Mono<Branch> createBranch(Long franchiseId, CreateBranchRequest request) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Franquicia no encontrada")))
                .flatMap(franchise -> {
                    Branch branch = new Branch();
                    branch.setFranchiseId(franchiseId);
                    branch.setName(request.getName());
                    return branchRepository.save(branch);
                });
    }

    public Flux<Branch> getBranchesByFranchise(Long franchiseId) {
        return branchRepository.findByFranchiseId(franchiseId);
    }

    public Mono<Branch> getBranchById(Long id) {
        return branchRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Sucursal no encontrada")));
    }

    public Mono<Branch> updateBranchName(Long id, String name) {
        return branchRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Sucursal no encontrada")))
                .flatMap(branch -> {
                    branch.setName(name);
                    return branchRepository.save(branch);
                });
    }

    public Mono<Void> deleteBranch(Long id) {
        return branchRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Sucursal no encontrada")))
                .flatMap(branch -> branchRepository.deleteById(id));
    }
}