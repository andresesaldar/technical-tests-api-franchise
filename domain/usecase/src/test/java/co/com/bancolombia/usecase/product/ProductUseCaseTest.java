package co.com.bancolombia.usecase.product;

import co.com.bancolombia.error.InvalidParamError;
import co.com.bancolombia.error.InvalidParamException;
import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.branch.gateways.BranchRepository;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateways.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class ProductUseCaseTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    BranchRepository branchRepository;

    @Mock
    FranchiseRepository franchiseRepository;

    @InjectMocks
    ProductUseCase productUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldUpdateProductStock() {
        final Long stock = 50L;
        final String franchiseSlug = "test-franchise";
        final String branchSlug = "test-branch";
        final String slug = "test-product";

        final Product product = Product.builder()
                .slug(slug)
                .stock(20L)
                .build();

        final String franchiseId = "franchise-id";
        final String branchId = "branch-id";
        final Franchise franchise = Franchise.builder()
                .id(franchiseId)
                .slug(franchiseSlug)
                .build();
        final Branch branch = Branch.builder()
                .id(branchId)
                .slug(branchSlug)
                .build();

        when(franchiseRepository.findBySlug(anyString()))
                .thenReturn(Mono.just(franchise));

        when(branchRepository.findBySlugAndFranchiseId(anyString(), anyString()))
                .thenReturn(Mono.just(branch));

        when(productRepository.findBySlugAndBranchId(anyString(), anyString()))
                .thenReturn(Mono.just(product));

        when(productRepository.save(any(Product.class)))
                .thenAnswer(p -> Mono.just((Product) p.getArgument(0)));

        Mono<Product> result = productUseCase.updateProductStock(franchiseSlug, branchSlug, slug, stock);

        StepVerifier.create(result)
                .expectNextMatches(p -> p.getStock().equals(stock))
                .verifyComplete();
    }

    @Test
    void shouldThrowInvalidParamError_whenUpdateProductStock_andProductNotFound() {
        final Long stock = 50L;
        final String franchiseSlug = "test-franchise";
        final String branchSlug = "test-branch";
        final String slug = "test-product";
        final String franchiseId = "franchise-id";
        final String branchId = "branch-id";
        final Franchise franchise = Franchise.builder()
                .id(franchiseId)
                .slug(franchiseSlug)
                .build();
        final Branch branch = Branch.builder()
                .id(branchId)
                .slug(branchSlug)
                .build();

        when(franchiseRepository.findBySlug(anyString()))
                .thenReturn(Mono.just(franchise));

        when(branchRepository.findBySlugAndFranchiseId(anyString(), anyString()))
                .thenReturn(Mono.just(branch));

        when(productRepository.findBySlugAndBranchId(anyString(), anyString()))
                .thenReturn(Mono.empty());

        Mono<Product> result = productUseCase.updateProductStock(franchiseSlug, branchSlug, slug, stock);

        StepVerifier.create(result)
                .expectErrorMatches(e -> e instanceof InvalidParamException &&
                        e.getMessage().equals(InvalidParamError.INVALID_PRODUCT_SLUG.getMessage()))
                .verify();
    }

    @Test
    void shouldCreate() {
        final Product product = Product.builder()
                .name("Test Product")
                .build();
        final String franchiseSlug = "test-franchise";
        final String franchiseId = "franchise-id";
        final String branchSlug = "test-branch";
        final String branchId = "branch-id";
        final Franchise franchise = Franchise.builder()
                .id(franchiseId)
                .slug(franchiseSlug)
                .build();
        final Branch branch = Branch.builder()
                .id(branchId)
                .slug(branchSlug)
                .build();

        when(franchiseRepository.findBySlug(anyString()))
                .thenReturn(Mono.just(franchise));

        when(branchRepository.findBySlugAndFranchiseId(anyString(), anyString()))
                .thenReturn(Mono.just(branch));

        when(productRepository.existsBySlugAndBranchId(anyString(), anyString()))
                .thenReturn(Mono.just(false));

        when(productRepository.save(any(Product.class)))
                .thenAnswer(p -> Mono.just((Product) p.getArgument(0)));

        final String expectedSlug = "test-product";

        Mono<Product> result = productUseCase.create(franchiseSlug, branchSlug, product);

        StepVerifier.create(result)
                .expectNextMatches(p -> p.getSlug().equals(expectedSlug))
                .verifyComplete();
    }

    @Test
    void shouldThrowInvalidParamError_whenCreate_withUnexistentFranchise() {
        final Product product = Product.builder()
                .name("Test Product")
                .build();
        final String franchiseSlug = "test-franchise";
        final String franchiseId = "franchise-id";
        final String branchSlug = "test-branch";
        final Franchise franchise = Franchise.builder()
                .id(franchiseId)
                .slug(franchiseSlug)
                .build();

        when(franchiseRepository.findBySlug(anyString()))
                .thenReturn(Mono.empty());

        Mono<Product> result = productUseCase.create(franchiseSlug, branchSlug, product);

        StepVerifier.create(result)
                .expectErrorMatches(e -> e instanceof InvalidParamException
                    && e.getMessage().equals(InvalidParamError.INVALID_FRANCHISE_SLUG.getMessage()))
                .verify();
    }

    @Test
    void shouldThrowInvalidParamError_whenCreate_withUnexistentBranch() {
        final Product product = Product.builder()
                .name("Test Product")
                .build();
        final String franchiseSlug = "test-franchise";
        final String franchiseId = "franchise-id";
        final String branchSlug = "test-branch";
        final Franchise franchise = Franchise.builder()
                .id(franchiseId)
                .slug(franchiseSlug)
                .build();

        when(franchiseRepository.findBySlug(anyString()))
                .thenReturn(Mono.just(franchise));

        when(branchRepository.findBySlugAndFranchiseId(anyString(), anyString()))
                .thenReturn(Mono.empty());

        Mono<Product> result = productUseCase.create(franchiseSlug, branchSlug, product);

        StepVerifier.create(result)
                .expectErrorMatches(e -> e instanceof InvalidParamException
                        && e.getMessage().equals(InvalidParamError.INVALID_BRANCH_SLUG.getMessage()))
                .verify();
    }

    @Test
    void shouldThrowInvalidParamError_whenCreate_withExistentSlug() {
        final Product product = Product.builder()
                .name("Test Product")
                .build();
        final String franchiseSlug = "test-franchise";
        final String franchiseId = "franchise-id";
        final String branchSlug = "test-branch";
        final String branchId = "branch-id";
        final Franchise franchise = Franchise.builder()
                .id(franchiseId)
                .slug(franchiseSlug)
                .build();
        final Branch branch = Branch.builder()
                .id(branchId)
                .slug(branchSlug)
                .build();

        when(franchiseRepository.findBySlug(anyString()))
                .thenReturn(Mono.just(franchise));

        when(branchRepository.findBySlugAndFranchiseId(anyString(), anyString()))
                .thenReturn(Mono.just(branch));

        when(productRepository.existsBySlugAndBranchId(anyString(), anyString()))
                .thenReturn(Mono.just(true));

        Mono<Product> result = productUseCase.create(franchiseSlug, branchSlug, product);

        StepVerifier.create(result)
                .expectErrorMatches(e -> e instanceof InvalidParamException
                        && e.getMessage().equals(InvalidParamError.INVALID_PRODUCT_SLUG.getMessage()))
                .verify();
    }

    @Test
    void shouldGetProductsAvailability() {
        final Product product = Product.builder()
                .name("Test Product")
                .branchSlug("test-branch")
                .build();
        final Integer page = 0;
        final Integer pageSize = 10;
        final String franchiseSlug = "test-franchise";
        final String franchiseId = "franchise-id";
        final Franchise franchise = Franchise.builder()
                .id(franchiseId)
                .slug(franchiseSlug)
                .build();

        when(franchiseRepository.findBySlug(anyString()))
                .thenReturn(Mono.just(franchise));

        when(productRepository.findProductsWithBranchByFranchiseIdSortByStockDesc(anyString(), anyInt(), anyInt()))
                .thenReturn(Flux.just(product));

        Flux<Product> result = productUseCase.getProductsAvailability(franchiseSlug, page, pageSize);

        StepVerifier.create(result)
                .expectNext(product)
                .verifyComplete();
    }
}