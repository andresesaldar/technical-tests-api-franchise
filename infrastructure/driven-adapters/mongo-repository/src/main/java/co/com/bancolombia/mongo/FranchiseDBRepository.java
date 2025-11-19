package co.com.bancolombia.mongo;

import co.com.bancolombia.model.franchise.Franchise;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import reactor.core.publisher.Mono;

public interface FranchiseDBRepository extends ReactiveMongoRepository<Franchise, String>, ReactiveQueryByExampleExecutor<Franchise> {
    Mono<Boolean> existsBySlug(String slug);
    Mono<Franchise> findBySlug(String slug);
}
