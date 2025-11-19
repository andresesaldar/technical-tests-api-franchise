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
import reactor.core.publisher.Flux;
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
    void shouldGetBySlug() {
        final String slug = "test-franchise";
        final String franchiseId = "franchise-123";
        final Franchise franchise = Franchise.builder()
                .slug(slug)
                .id(franchiseId)
                .build();

        when(branchRepository.findByFranchiseId(anyString()))
                .thenReturn(Flux.empty());

        when(franchiseRepository.findBySlug(anyString()))
                .thenReturn(Mono.just(franchise));

        Mono<Franchise> result = franchiseUseCase.getBySlug(slug);

        StepVerifier.create(result)
                .expectNext(franchise)
                .verifyComplete();
    }

}