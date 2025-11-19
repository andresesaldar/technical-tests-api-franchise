package co.com.bancolombia.usecase.franchise;

import co.com.bancolombia.error.InvalidParamError;
import co.com.bancolombia.model.branch.gateways.BranchRepository;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FranchiseUseCase {
    private final FranchiseRepository franchiseRepository;
    private final BranchRepository branchRepository;

    public Mono<Franchise> create(Franchise franchise) {
        final String slug = franchise.getName().toLowerCase().replace(" ", "-");

        franchise.setSlug(slug);

        return franchiseRepository.existsBySlug(slug)
                .filter(Boolean.FALSE::equals)
                .switchIfEmpty(Mono.error(InvalidParamError.INVALID_FRANCHISE_SLUG.exception()))
                .flatMap(ignore -> franchiseRepository.save(franchise));
    }

    public Mono<Franchise> getBySlug(String slug) {
        return franchiseRepository.findBySlug(slug)
            .switchIfEmpty(Mono.error(InvalidParamError.INVALID_FRANCHISE_SLUG.exception()))
            .flatMap(franchise -> branchRepository.findByFranchiseId(franchise.getId())
                .collectList()
                .map(branches -> {
                    franchise.setBranches(branches);
                    return franchise;
                }));
    }
}
