package com.accenture.franchise.controller;

import com.accenture.franchise.domain.Branch;
import com.accenture.franchise.dto.CreateBranchRequest;
import com.accenture.franchise.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/franchises/{franchiseId}/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    public Mono<ResponseEntity<Branch>> createBranch(
            @PathVariable Long franchiseId,
            @RequestBody CreateBranchRequest request) {
        return branchService.createBranch(franchiseId, request)
                .map(branch -> ResponseEntity.status(HttpStatus.CREATED).body(branch));
    }

    @GetMapping
    public Flux<Branch> getBranchesByFranchise(@PathVariable Long franchiseId) {
        return branchService.getBranchesByFranchise(franchiseId);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Branch>> getBranchById(@PathVariable Long id) {
        return branchService.getBranchById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Branch>> updateBranchName(
            @PathVariable Long id,
            @RequestParam String name) {
        return branchService.updateBranchName(id, name)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteBranch(@PathVariable Long id) {
        return branchService.deleteBranch(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .onErrorReturn(ResponseEntity.notFound().build());
    }
}