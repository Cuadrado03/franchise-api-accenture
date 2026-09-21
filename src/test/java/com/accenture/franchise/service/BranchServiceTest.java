package com.accenture.franchise.service;

import com.accenture.franchise.domain.Branch;
import com.accenture.franchise.domain.Franchise;
import com.accenture.franchise.dto.CreateBranchRequest;
import com.accenture.franchise.repository.BranchRepository;
import com.accenture.franchise.repository.FranchiseRepository;
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
class BranchServiceTest {

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private FranchiseRepository franchiseRepository;

    @InjectMocks
    private BranchService branchService;

    private Branch branch;
    private Franchise franchise;
    private CreateBranchRequest request;

    @BeforeEach
    void setUp() {
        franchise = new Franchise(1L, "Test Franchise");
        branch = new Branch(1L, 1L, "Test Branch");
        request = new CreateBranchRequest("Test Branch");
    }

    @Test
    void testCreateBranch() {
        when(franchiseRepository.findById(1L))
                .thenReturn(Mono.just(franchise));
        when(branchRepository.save(any(Branch.class)))
                .thenReturn(Mono.just(branch));

        StepVerifier.create(branchService.createBranch(1L, request))
                .expectNext(branch)
                .verifyComplete();
    }

    @Test
    void testGetBranchesByFranchise() {
        when(branchRepository.findByFranchiseId(1L))
                .thenReturn(Flux.just(branch));

        StepVerifier.create(branchService.getBranchesByFranchise(1L))
                .expectNext(branch)
                .verifyComplete();
    }

    @Test
    void testGetBranchById() {
        when(branchRepository.findById(1L))
                .thenReturn(Mono.just(branch));

        StepVerifier.create(branchService.getBranchById(1L))
                .expectNext(branch)
                .verifyComplete();
    }

    @Test
    void testUpdateBranchName() {
        branch.setName("Updated Branch");
        when(branchRepository.findById(1L))
                .thenReturn(Mono.just(new Branch(1L, 1L, "Test Branch")));
        when(branchRepository.save(any(Branch.class)))
                .thenReturn(Mono.just(branch));

        StepVerifier.create(branchService.updateBranchName(1L, "Updated Branch"))
                .expectNext(branch)
                .verifyComplete();
    }

    @Test
    void testDeleteBranch() {
        when(branchRepository.findById(1L))
                .thenReturn(Mono.just(branch));
        when(branchRepository.deleteById(1L))
                .thenReturn(Mono.empty());

        StepVerifier.create(branchService.deleteBranch(1L))
                .verifyComplete();
    }
}