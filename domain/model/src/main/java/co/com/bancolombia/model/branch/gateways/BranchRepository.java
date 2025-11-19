package co.com.bancolombia.model.branch.gateways;

import co.com.bancolombia.model.branch.Branch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchRepository {
    Mono<Branch> save(Branch franchise);
    Mono<Boolean> existsBySlugAndFranchiseId(String slug, String franchiseId);
    Mono<Boolean> existsBySlug(String slug);
    Flux<Branch> findByFranchiseId(String franchiseId);
    Mono<Branch> findBySlugAndFranchiseId(String slug, String franchiseId);
}
