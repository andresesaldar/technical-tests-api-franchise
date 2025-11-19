package co.com.bancolombia.mongo;

import co.com.bancolombia.model.branch.Branch;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchDBRepository extends ReactiveMongoRepository<Branch, String>, ReactiveQueryByExampleExecutor<Branch> {
    Mono<Boolean> existsBySlug(String slug);
    Mono<Boolean> existsBySlugAndFranchiseId(String slug, String franchiseId);
    Flux<Branch> findByFranchiseId(String franchiseId);
    Mono<Branch> findBySlugAndFranchiseId(String slug, String franchiseId);
}
