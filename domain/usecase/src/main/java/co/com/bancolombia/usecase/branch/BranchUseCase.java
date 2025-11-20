package co.com.bancolombia.usecase.branch;

import co.com.bancolombia.error.InvalidParamError;
import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.branch.gateways.BranchRepository;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BranchUseCase {
    private final BranchRepository branchRepository;
    private final FranchiseRepository franchiseRepository;

    public Mono<Branch> create(String franchiseSlug, Branch branch) {
        final String slug = branch.getName().toLowerCase().replace(" ", "-");

        branch.setSlug(slug);

        return franchiseRepository.findBySlug(franchiseSlug)
                .switchIfEmpty(Mono.error(InvalidParamError.INVALID_FRANCHISE_SLUG.exception()))
                .flatMap(franchise -> branchRepository.existsBySlugAndFranchiseId(slug, franchise.getId())
                    .filter(Boolean.FALSE::equals)
                    .switchIfEmpty(Mono.error(InvalidParamError.INVALID_BRANCH_SLUG.exception()))
                    .flatMap(ignored -> {
                        branch.setFranchiseId(franchise.getId());
                        return branchRepository.save(branch);
                    }));
    }

    public Mono<Branch> updateName(String franchiseSlug, String slug, String newName) {
        final String newSlug = newName.toLowerCase().replace(" ", "-");

        return validateFranchiseAndBranch(franchiseSlug, slug)
                .flatMap(branch -> branchRepository.existsBySlugAndFranchiseId(newSlug, branch.getFranchiseId())
                    .filter(Boolean.FALSE::equals)
                    .switchIfEmpty(Mono.error(InvalidParamError.INVALID_BRANCH_SLUG.exception()))
                    .flatMap(ignored -> {
                        branch.setName(newName);
                        branch.setSlug(newSlug);
                        return branchRepository.save(branch);
                    }));
    }

    private Mono<Branch> validateFranchiseAndBranch(String franchiseSlug, String slug) {
        return franchiseRepository.findBySlug(franchiseSlug)
                .switchIfEmpty(Mono.error(InvalidParamError.INVALID_FRANCHISE_SLUG.exception()))
                .flatMap(franchise -> branchRepository.findBySlugAndFranchiseId(slug, franchise.getId()))
                .switchIfEmpty(Mono.error(InvalidParamError.INVALID_BRANCH_SLUG.exception()));
    }
}
