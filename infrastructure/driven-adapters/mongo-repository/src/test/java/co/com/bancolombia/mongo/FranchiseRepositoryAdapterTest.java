package co.com.bancolombia.mongo;

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
}