package co.com.bancolombia.usecase.franchise;

import co.com.bancolombia.error.InvalidParamError;
import co.com.bancolombia.error.InvalidParamException;
import co.com.bancolombia.model.branch.gateways.BranchRepository;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class FranchiseUseCaseTest {
    @Mock
    FranchiseRepository franchiseRepository;

    @Mock
    BranchRepository branchRepository;

    @InjectMocks
    FranchiseUseCase franchiseUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreate() {
        final Franchise franchise = Franchise.builder()
                .name("Test Franchise")
                .build();

        when(franchiseRepository.existsBySlug(anyString()))
                .thenReturn(Mono.just(false));

        when(franchiseRepository.save(any(Franchise.class)))
                .thenAnswer(p -> Mono.just((Franchise) p.getArgument(0)));

        final String expectedSlug = "test-franchise";

        Mono<Franchise> result = franchiseUseCase.create(franchise);

        StepVerifier.create(result)
                .expectNextMatches(f -> f.getSlug().equals(expectedSlug))
                .verifyComplete();
    }

    @Test
    void shouldThrowInvalidParamError_whenCreate_withExistentSlug() {
        final Franchise franchise = Franchise.builder()
                .name("Test Franchise")
                .build();

        when(franchiseRepository.existsBySlug(anyString()))
                .thenReturn(Mono.just(true));

        Mono<Franchise> result = franchiseUseCase.create(franchise);

        StepVerifier.create(result)
                .expectErrorMatches(
                        throwable -> throwable instanceof InvalidParamException &&
                                throwable.getMessage().equals(InvalidParamError.INVALID_FRANCHISE_SLUG.getMessage())
                )
                .verify();
    }

    @Test
    void shouldUpdateName() {
        final String slug = "old-franchise";
        final String newName = "Updated Franchise Name";
        final Franchise existingFranchise = Franchise.builder()
                .slug(slug)
                .name("Old Franchise")
                .build();

        when(franchiseRepository.findBySlug(slug))
                .thenReturn(Mono.just(existingFranchise));

        when(franchiseRepository.existsBySlug("updated-franchise-name"))
                .thenReturn(Mono.just(false));

        when(franchiseRepository.save(any(Franchise.class)))
                .thenAnswer(p -> Mono.just((Franchise) p.getArgument(0)));

        Mono<Franchise> result = franchiseUseCase.updateName(slug, newName);

        StepVerifier.create(result)
                .expectNextMatches(f ->
                        f.getName().equals(newName) &&
                        f.getSlug().equals("updated-franchise-name"))
                .verifyComplete();
    }

    @Test
    void shouldThrowInvalidParamError_whenUpdateName_withNonExistentSlug() {
        final String slug = "non-existent-franchise";
        final String newName = "Updated Name";

        when(franchiseRepository.findBySlug(slug))
                .thenReturn(Mono.empty());

        Mono<Franchise> result = franchiseUseCase.updateName(slug, newName);

        StepVerifier.create(result)
                .expectErrorMatches(
                        throwable -> throwable instanceof InvalidParamException &&
                                throwable.getMessage().equals(InvalidParamError.INVALID_FRANCHISE_SLUG.getMessage())
                )
                .verify();
    }

    @Test
    void shouldThrowInvalidParamError_whenUpdateName_withExistentNewSlug() {
        final String slug = "old-franchise";
        final String newName = "Existing Franchise";
        final Franchise existingFranchise = Franchise.builder()
                .slug(slug)
                .name("Old Franchise")
                .build();

        when(franchiseRepository.findBySlug(slug))
                .thenReturn(Mono.just(existingFranchise));

        when(franchiseRepository.existsBySlug("existing-franchise"))
                .thenReturn(Mono.just(true));

        Mono<Franchise> result = franchiseUseCase.updateName(slug, newName);

        StepVerifier.create(result)
                .expectErrorMatches(
                        throwable -> throwable instanceof InvalidParamException &&
                                throwable.getMessage().equals(InvalidParamError.INVALID_FRANCHISE_SLUG.getMessage())
                )
                .verify();
    }

}