package co.com.bancolombia.mongo;

import co.com.bancolombia.model.franchise.Franchise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class FranchiseRepositoryAdapterTest {
    @Mock
    private FranchiseDBRepository repository;

    @InjectMocks
    private FranchiseRepositoryAdapter adapter;

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
    void shouldFindBySlug() {
        final String slug = "test-slug";
        final Franchise franchise = Franchise.builder()
                .slug(slug)
                .build();

        when(repository.findBySlug(anyString()))
                .thenReturn(Mono.just(franchise));

        StepVerifier.create(adapter.findBySlug(slug))
                .expectNext(franchise)
                .verifyComplete();
    }
}