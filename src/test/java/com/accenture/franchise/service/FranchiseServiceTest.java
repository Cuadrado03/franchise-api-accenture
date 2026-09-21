package com.accenture.franchise.service;

import com.accenture.franchise.domain.Franchise;
import com.accenture.franchise.dto.CreateFranchiseRequest;
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
class FranchiseServiceTest {

    @Mock
    private FranchiseRepository franchiseRepository;

    @InjectMocks
    private FranchiseService franchiseService;

    private Franchise franchise;
    private CreateFranchiseRequest request;

    @BeforeEach
    void setUp() {
        franchise = new Franchise(1L, "Test Franchise");
        request = new CreateFranchiseRequest("Test Franchise");
    }

    @Test
    void testCreateFranchise() {
        when(franchiseRepository.save(any(Franchise.class)))
                .thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseService.createFranchise(request))
                .expectNext(franchise)
                .verifyComplete();
    }

    @Test
    void testGetAllFranchises() {
        when(franchiseRepository.findAll())
                .thenReturn(Flux.just(franchise));

        StepVerifier.create(franchiseService.getAllFranchises())
                .expectNext(franchise)
                .verifyComplete();
    }

    @Test
    void testGetFranchiseById() {
        when(franchiseRepository.findById(1L))
                .thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseService.getFranchiseById(1L))
                .expectNext(franchise)
                .verifyComplete();
    }

    @Test
    void testUpdateFranchiseName() {
        franchise.setName("Updated Franchise");
        when(franchiseRepository.findById(1L))
                .thenReturn(Mono.just(new Franchise(1L, "Test Franchise")));
        when(franchiseRepository.save(any(Franchise.class)))
                .thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseService.updateFranchiseName(1L, "Updated Franchise"))
                .expectNext(franchise)
                .verifyComplete();
    }

    @Test
    void testDeleteFranchise() {
        when(franchiseRepository.findById(1L))
                .thenReturn(Mono.just(franchise));
        when(franchiseRepository.deleteById(1L))
                .thenReturn(Mono.empty());

        StepVerifier.create(franchiseService.deleteFranchise(1L))
                .verifyComplete();
    }
}