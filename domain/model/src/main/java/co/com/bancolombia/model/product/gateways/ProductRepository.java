package co.com.bancolombia.model.product.gateways;

import co.com.bancolombia.model.product.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Product> findBySlug(String slug);
    Mono<Product> save(Product product);
    Mono<Boolean> existsBySlugAndBranchId(String slug, String branchId);
    Mono<Product> findBySlugAndBranchId(String slug, String branchId);
    Mono<Void> delete(Product product);
    Flux<Product> findProductsWithBranchByFranchiseIdSortByStockDesc(String franchiseId, Integer page, Integer pageSize);
}
