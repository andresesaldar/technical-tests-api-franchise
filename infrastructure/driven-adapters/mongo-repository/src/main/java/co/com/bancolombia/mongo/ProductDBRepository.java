package co.com.bancolombia.mongo;

import co.com.bancolombia.model.product.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import reactor.core.publisher.Mono;

public interface ProductDBRepository extends ReactiveMongoRepository<Product, String>, ReactiveQueryByExampleExecutor<Product> {
    Mono<Product> findBySlug(String slug);
    Mono<Boolean> existsBySlugAndBranchId(String slug, String branchId);
    Mono<Product> findBySlugAndBranchId(String slug, String branchId);
}
