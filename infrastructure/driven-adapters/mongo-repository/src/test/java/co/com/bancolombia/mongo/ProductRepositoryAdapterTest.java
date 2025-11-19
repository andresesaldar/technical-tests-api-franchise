package co.com.bancolombia.mongo;

import co.com.bancolombia.model.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class ProductRepositoryAdapterTest {
    @Mock
    private ProductDBRepository repository;

    @InjectMocks
    private ProductRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindBySlug() {
        final String slug = "test-slug";
        final Product product = Product.builder()
                .slug(slug)
                .build();

        when(repository.findBySlug(anyString()))
                .thenReturn(Mono.just(product));

        StepVerifier.create(adapter.findBySlug(slug))
                .expectNextMatches(p -> p.getSlug().equals(slug))
                .verifyComplete();
    }

    @Test
    void shouldExistsBySlugAndBranchId() {
        final String slug = "test-slug";
        final String branchId = "branch-123";

        when(repository.existsBySlugAndBranchId(anyString(), anyString()))
                .thenReturn(Mono.just(true));

        StepVerifier.create(adapter.existsBySlugAndBranchId(slug, branchId))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void shouldDelete() {
        final String slug = "test-slug";
        final String branchId = "branch-123";
        final Product product = Product.builder()
                .slug(slug)
                .branchId(branchId)
                .build();

        when(repository.delete(product))
                .thenReturn(Mono.empty());

        StepVerifier.create(adapter.delete(product))
                .verifyComplete();
    }

}