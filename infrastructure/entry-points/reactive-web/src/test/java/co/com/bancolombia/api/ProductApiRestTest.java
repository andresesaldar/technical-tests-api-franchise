package co.com.bancolombia.api;

import co.com.bancolombia.api.input.CreateProductInput;
import co.com.bancolombia.api.input.UpdateProductNameInput;
import co.com.bancolombia.api.input.UpdateProductStockInput;
import co.com.bancolombia.api.mapper.ProductMapper;
import co.com.bancolombia.api.payload.ProductPayload;
import co.com.bancolombia.api.payload.ResponsePayload;
import co.com.bancolombia.api.payload.UpdateProductStockPayload;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.usecase.product.ProductUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class ProductApiRestTest {

    @Mock
    ProductUseCase productUseCase;

    ProductMapper productMapper;

    @InjectMocks
    ProductApiRest productApiRest;

    @BeforeEach
    void setUp() {
        productMapper = Mappers.getMapper(ProductMapper.class);
        MockitoAnnotations.openMocks(this);
        productApiRest = new ProductApiRest(productUseCase, productMapper);
    }

    @Test
    void shouldUpdateProductStock() {
        final String franchiseSlug = "test-franchise";
        final String branchSlug = "test-branch";
        final String productSlug = "test-product";
        final UpdateProductStockInput input = UpdateProductStockInput.builder()
                .stock(150L)
                .build();
        final Product updatedProduct = Product.builder()
                .slug(productSlug)
                .name("Test Product")
                .stock(150L)
                .build();

        when(productUseCase.updateProductStock(anyString(), anyString(), anyString(), anyLong()))
                .thenReturn(Mono.just(updatedProduct));

        final Mono<UpdateProductStockPayload.Response> result =
                productApiRest.updateProductStock(franchiseSlug, branchSlug, productSlug, input);

        StepVerifier.create(result)
                    .expectNextMatches(response ->
                        response.getData().getSlug().equals(productSlug) &&
                        response.getData().getStock().equals(150L))
                    .verifyComplete();
    }

    @Test
    void shouldCreate() {
        final String franchiseSlug = "test-franchise";
        final String branchSlug = "test-branch";
        final CreateProductInput input = CreateProductInput.builder()
                .name("Test Product")
                .stock(100L)
                .build();
        final Product createdProduct = Product.builder()
                .name("Test Product")
                .slug("test-product")
                .stock(100L)
                .build();

        when(productUseCase.create(anyString(), anyString(), any(Product.class)))
                .thenReturn(Mono.just(createdProduct));

        final Mono<ProductPayload.Response> result =
                productApiRest.create(franchiseSlug, branchSlug, input);

        StepVerifier.create(result)
                    .expectNextMatches(response ->
                        response.getData().getName().equals(input.getName()) &&
                        response.getData().getSlug().equals("test-product") &&
                        response.getData().getStock().equals(100L))
                    .verifyComplete();
    }
    @Test
    void shouldUpdateName() {
        final String franchiseSlug = "test-franchise";
        final String branchSlug = "test-branch";
        final String slug = "old-product";
        final UpdateProductNameInput input = UpdateProductNameInput.builder()
                .name("Updated Product Name")
                .build();
        final Product updatedProduct = Product.builder()
                .name("Updated Product Name")
                .slug("updated-product-name")
                .build();

        when(productUseCase.updateName(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Mono.just(updatedProduct));

        final Mono<ProductPayload.Response> result =
                productApiRest.updateName(franchiseSlug, branchSlug, slug, input);

        StepVerifier.create(result)
                    .expectNextMatches(response ->
                        input.getName().equals(response.getData().getName()) &&
                        "updated-product-name".equals(response.getData().getSlug()))
                    .verifyComplete();
    }


    @Test
    void shouldDelete() {
        final String franchiseSlug = "test-franchise";
        final String branchSlug = "test-branch";
        final String productSlug = "test-product";

        when(productUseCase.delete(anyString(), anyString(), anyString()))
                .thenReturn(Mono.empty());

        final Mono<ResponsePayload.OkResponse> result =
                productApiRest.delete(franchiseSlug, branchSlug, productSlug);

        StepVerifier.create(result)
                    .expectNextMatches(response -> response.getData().equals(true))
                    .verifyComplete();
    }

}

