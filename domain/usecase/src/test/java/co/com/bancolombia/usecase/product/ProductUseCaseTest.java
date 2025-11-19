package co.com.bancolombia.usecase.product;

import co.com.bancolombia.error.InvalidParamError;
import co.com.bancolombia.error.InvalidParamException;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateways.ProductRepository;
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

class ProductUseCaseTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductUseCase productUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldUpdateProductStock() {
        final Long stock = 50L;
        final String slug = "test-product";

        final Product product = Product.builder()
                .slug(slug)
                .stock(20L)
                .build();

        when(productRepository.findBySlug(anyString()))
                .thenReturn(Mono.just(product));

        when(productRepository.save(any(Product.class)))
                .thenAnswer(p -> Mono.just((Product) p.getArgument(0)));

        Mono<Product> result = productUseCase.updateProductStock(slug, stock);

        StepVerifier.create(result)
                .expectNextMatches(p -> p.getStock().equals(stock))
                .verifyComplete();
    }

    @Test
    void shouldThrowInvalidParamError_whenUpdateProductStock_andProductNotFound() {
        final Long stock = 50L;
        final String slug = "test-product";

        when(productRepository.findBySlug(anyString()))
                .thenReturn(Mono.empty());

        Mono<Product> result = productUseCase.updateProductStock(slug, stock);

        StepVerifier.create(result)
                .expectErrorMatches(e -> e instanceof InvalidParamException &&
                        e.getMessage().equals(InvalidParamError.INVALID_PRODUCT_SLUG.getMessage()))
                .verify();
    }
}