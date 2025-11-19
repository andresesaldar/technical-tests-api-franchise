package co.com.bancolombia.mongo;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.franchise.Franchise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class BranchRepositoryAdapterTest {
    @Mock
    private BranchDBRepository repository;

    @InjectMocks
    private BranchRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldExistBySlug() {
        final String slug = "test-slug";

        when(repository.existsBySlug(anyString()))
                .thenReturn(Mono.just(true));

        StepVerifier.create(adapter.existsBySlug(slug))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void shouldFindByFranchiseId() {
        final String franchiseId = "franchise-123";
        final Branch branch = Branch.builder()
                .franchiseId(franchiseId)
                .build();

        when(repository.findByFranchiseId(anyString()))
                .thenReturn(Flux.just(branch));

        StepVerifier.create(adapter.findByFranchiseId(franchiseId))
                .expectNext(branch)
                .verifyComplete();
    }

    @Test
    void shouldExistBySlugAndFranchiseId() {
        final String slug = "test-slug";
        final String franchiseId = "franchise-123";

        when(repository.existsBySlugAndFranchiseId(anyString(), anyString()))
                .thenReturn(Mono.just(true));

        StepVerifier.create(adapter.existsBySlugAndFranchiseId(slug, franchiseId))
                .expectNext(true)
                .verifyComplete();
    }

}