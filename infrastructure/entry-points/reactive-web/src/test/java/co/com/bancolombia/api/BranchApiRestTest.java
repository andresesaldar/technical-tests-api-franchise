package co.com.bancolombia.api;

import co.com.bancolombia.api.input.CreateBranchInput;
import co.com.bancolombia.api.input.UpdateBranchNameInput;
import co.com.bancolombia.api.mapper.BranchMapper;
import co.com.bancolombia.api.mapper.ProductMapper;
import co.com.bancolombia.api.payload.BranchPayload;
import co.com.bancolombia.api.payload.ProductAvailabilityPayload;
import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.usecase.branch.BranchUseCase;
import co.com.bancolombia.usecase.product.ProductUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class BranchApiRestTest {

    @Mock
    BranchUseCase branchUseCase;

    BranchMapper branchMapper;

    @Mock
    ProductUseCase productUseCase;

    ProductMapper productMapper;

    @InjectMocks
    BranchApiRest branchApiRest;

    @BeforeEach
    void setUp() {
        branchMapper = Mappers.getMapper(BranchMapper.class);
        productMapper = Mappers.getMapper(ProductMapper.class);
        MockitoAnnotations.openMocks(this);
        branchApiRest = new BranchApiRest(branchUseCase, branchMapper, productUseCase, productMapper);
    }

    @Test
    void shouldCreate() {
        final String franchiseSlug = "test-franchise";
        final CreateBranchInput input = CreateBranchInput.builder()
                .name("Test Branch")
                .build();
        final Branch createdBranch = Branch.builder()
                .name("Test Branch")
                .slug("test-branch")
                .build();

        when(branchUseCase.create(anyString(), any(Branch.class)))
                .thenReturn(Mono.just(createdBranch));

        final Mono<BranchPayload.Response> result = branchApiRest.create(franchiseSlug, input);

        StepVerifier.create(result)
                    .expectNextMatches(branch ->
                        input.getName().equals(branch.getData().getName()) &&
                        "test-branch".equals(branch.getData().getSlug()))
                    .verifyComplete();
    }

    @Test
    void shouldUpdateName() {
        final String franchiseSlug = "test-franchise";
        final String slug = "old-branch";
        final UpdateBranchNameInput input = UpdateBranchNameInput.builder()
                .name("Updated Branch Name")
                .build();
        final Branch updatedBranch = Branch.builder()
                .name("Updated Branch Name")
                .slug("updated-branch-name")
                .build();

        when(branchUseCase.updateName(anyString(), anyString(), anyString()))
                .thenReturn(Mono.just(updatedBranch));

        final Mono<BranchPayload.Response> result = branchApiRest.updateName(franchiseSlug, slug, input);

        StepVerifier.create(result)
                    .expectNextMatches(branch ->
                        input.getName().equals(branch.getData().getName()) &&
                        "updated-branch-name".equals(branch.getData().getSlug()))
                    .verifyComplete();
    }

    @Test
    void shouldGetProductsAvailability() {
        final String franchiseSlug = "test-franchise";
        final Product product1 = Product.builder()
                .slug("product-1")
                .name("Product 1")
                .stock(100L)
                .branchId("branch-1")
                .build();
        final Product product2 = Product.builder()
                .slug("product-2")
                .name("Product 2")
                .stock(50L)
                .branchId("branch-2")
                .build();

        when(productUseCase.getProductsAvailability(anyString(), anyInt(), anyInt()))
                .thenReturn(Flux.just(product1, product2));

        final Mono<ProductAvailabilityPayload.ListResponse> result =
                branchApiRest.getProductsAvailability(franchiseSlug, 0);

        StepVerifier.create(result)
                    .expectNextMatches(response ->
                        response.getData().size() == 2 &&
                        response.getData().getFirst().getName().equals("Product 1") &&
                        response.getData().get(1).getName().equals("Product 2"))
                    .verifyComplete();
    }

}