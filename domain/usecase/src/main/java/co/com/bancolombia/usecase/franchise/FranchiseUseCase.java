package co.com.bancolombia.usecase.franchise;

import co.com.bancolombia.error.InvalidParamError;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FranchiseUseCase {
    private final FranchiseRepository franchiseRepository;

    public Mono<Franchise> create(Franchise franchise) {
        final String slug = franchise.getName().toLowerCase().replace(" ", "-");

        franchise.setSlug(slug);

        return franchiseRepository.existsBySlug(slug)
                .filter(Boolean.FALSE::equals)
                .switchIfEmpty(Mono.error(InvalidParamError.INVALID_FRANCHISE_SLUG.exception()))
                .flatMap(ignore -> franchiseRepository.save(franchise));
    }
}
