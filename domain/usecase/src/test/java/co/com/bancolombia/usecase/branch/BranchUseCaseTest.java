package co.com.bancolombia.usecase.branch;

import co.com.bancolombia.error.InvalidParamError;
import co.com.bancolombia.error.InvalidParamException;
import co.com.bancolombia.model.branch.Branch;
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

class BranchUseCaseTest {
    @Mock
    BranchRepository branchRepository;

    @Mock
    FranchiseRepository franchiseRepository;

    @InjectMocks
    BranchUseCase branchUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreate() {
        final Branch branch = Branch.builder()
                .name("Test Branch")
                .build();
        final String franchiseSlug = "test-franchise";
        final String franchiseId = "franchise-id";
        final Franchise franchise = Franchise.builder()
                .id(franchiseId)
                .slug(franchiseSlug)
                .build();

        when(franchiseRepository.findBySlug(anyString()))
                .thenReturn(Mono.just(franchise));

        when(branchRepository.existsBySlugAndFranchiseId(anyString(), anyString()))
                .thenReturn(Mono.just(false));

        when(branchRepository.save(any(Branch.class)))
                .thenAnswer(p -> Mono.just((Branch) p.getArgument(0)));

        final String expectedSlug = "test-branch";

        Mono<Branch> result = branchUseCase.create(franchiseSlug, branch);

        StepVerifier.create(result)
                .expectNextMatches(f -> f.getSlug().equals(expectedSlug))
                .verifyComplete();
    }

    @Test
    void shouldThrowInvalidParamError_whenCreate_withUnexistentFranchise() {
        final Branch branch = Branch.builder()
                .name("Test Branch")
                .build();
        final String franchiseSlug = "test-franchise";

        when(franchiseRepository.findBySlug(anyString()))
                .thenReturn(Mono.empty());

        Mono<Branch> result = branchUseCase.create(franchiseSlug, branch);

        StepVerifier.create(result)
                .expectErrorMatches(
                        throwable -> throwable instanceof InvalidParamException &&
                                throwable.getMessage().equals(InvalidParamError.INVALID_FRANCHISE_SLUG.getMessage())
                )
                .verify();
    }

    @Test
    void shouldThrowInvalidParamError_whenCreate_withExistentSlug() {
        final Branch branch = Branch.builder()
                .name("Test Branch")
                .build();
        final String franchiseSlug = "test-franchise";
        final String franchiseId = "franchise-id";
        final Franchise franchise = Franchise.builder()
                .id(franchiseId)
                .slug(franchiseSlug)
                .build();

        when(franchiseRepository.findBySlug(anyString()))
                .thenReturn(Mono.just(franchise));

        when(branchRepository.existsBySlugAndFranchiseId(anyString(), anyString()))
                .thenReturn(Mono.just(true));

        Mono<Branch> result = branchUseCase.create(franchiseSlug, branch);

        StepVerifier.create(result)
                .expectErrorMatches(
                        throwable -> throwable instanceof InvalidParamException &&
                                throwable.getMessage().equals(InvalidParamError.INVALID_BRANCH_SLUG.getMessage())
                )
                .verify();
    }

    @Test
    void shouldUpdateName() {
        final String franchiseSlug = "test-franchise";
        final String franchiseId = "franchise-id";
        final String slug = "old-branch";
        final String newName = "Updated Branch Name";
        final Franchise franchise = Franchise.builder()
                .id(franchiseId)
                .slug(franchiseSlug)
                .build();
        final Branch existingBranch = Branch.builder()
                .slug(slug)
                .name("Old Branch")
                .franchiseId(franchiseId)
                .build();

        when(franchiseRepository.findBySlug(franchiseSlug))
                .thenReturn(Mono.just(franchise));

        when(branchRepository.findBySlugAndFranchiseId(slug, franchiseId))
                .thenReturn(Mono.just(existingBranch));

        when(branchRepository.existsBySlugAndFranchiseId("updated-branch-name", franchiseId))
                .thenReturn(Mono.just(false));

        when(branchRepository.save(any(Branch.class)))
                .thenAnswer(p -> Mono.just((Branch) p.getArgument(0)));

        Mono<Branch> result = branchUseCase.updateName(franchiseSlug, slug, newName);

        StepVerifier.create(result)
                .expectNextMatches(b ->
                        b.getName().equals(newName) &&
                        b.getSlug().equals("updated-branch-name"))
                .verifyComplete();
    }

    @Test
    void shouldThrowInvalidParamError_whenUpdateName_withNonExistentFranchise() {
        final String franchiseSlug = "non-existent-franchise";
        final String slug = "branch-slug";
        final String newName = "Updated Name";

        when(franchiseRepository.findBySlug(franchiseSlug))
                .thenReturn(Mono.empty());

        Mono<Branch> result = branchUseCase.updateName(franchiseSlug, slug, newName);

        StepVerifier.create(result)
                .expectErrorMatches(
                        throwable -> throwable instanceof InvalidParamException &&
                                throwable.getMessage().equals(InvalidParamError.INVALID_FRANCHISE_SLUG.getMessage())
                )
                .verify();
    }

    @Test
    void shouldThrowInvalidParamError_whenUpdateName_withNonExistentBranch() {
        final String franchiseSlug = "test-franchise";
        final String franchiseId = "franchise-id";
        final String slug = "non-existent-branch";
        final String newName = "Updated Name";
        final Franchise franchise = Franchise.builder()
                .id(franchiseId)
                .slug(franchiseSlug)
                .build();

        when(franchiseRepository.findBySlug(franchiseSlug))
                .thenReturn(Mono.just(franchise));

        when(branchRepository.findBySlugAndFranchiseId(slug, franchiseId))
                .thenReturn(Mono.empty());

        Mono<Branch> result = branchUseCase.updateName(franchiseSlug, slug, newName);

        StepVerifier.create(result)
                .expectErrorMatches(
                        throwable -> throwable instanceof InvalidParamException &&
                                throwable.getMessage().equals(InvalidParamError.INVALID_BRANCH_SLUG.getMessage())
                )
                .verify();
    }

    @Test
    void shouldThrowInvalidParamError_whenUpdateName_withExistentNewSlug() {
        final String franchiseSlug = "test-franchise";
        final String franchiseId = "franchise-id";
        final String slug = "old-branch";
        final String newName = "Existing Branch";
        final Franchise franchise = Franchise.builder()
                .id(franchiseId)
                .slug(franchiseSlug)
                .build();
        final Branch existingBranch = Branch.builder()
                .slug(slug)
                .name("Old Branch")
                .franchiseId(franchiseId)
                .build();

        when(franchiseRepository.findBySlug(franchiseSlug))
                .thenReturn(Mono.just(franchise));

        when(branchRepository.findBySlugAndFranchiseId(slug, franchiseId))
                .thenReturn(Mono.just(existingBranch));

        when(branchRepository.existsBySlugAndFranchiseId("existing-branch", franchiseId))
                .thenReturn(Mono.just(true));

        Mono<Branch> result = branchUseCase.updateName(franchiseSlug, slug, newName);

        StepVerifier.create(result)
                .expectErrorMatches(
                        throwable -> throwable instanceof InvalidParamException &&
                                throwable.getMessage().equals(InvalidParamError.INVALID_BRANCH_SLUG.getMessage())
                )
                .verify();
    }
}