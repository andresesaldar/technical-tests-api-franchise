package co.com.bancolombia.mongo;

import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateways.ProductRepository;
import co.com.bancolombia.mongo.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class ProductRepositoryAdapter
        extends AdapterOperations<Product, Product, String, ProductDBRepository>
        implements ProductRepository {

    private final ProductDBRepository repository;

    public ProductRepositoryAdapter(ProductDBRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Product.class));
        this.repository = repository;
    }

    @Override
    public Mono<Product> findBySlug(String slug) {
        return repository.findBySlug(slug);
    }

    @Override
    public Mono<Boolean> existsBySlugAndBranchId(String slug, String branchId) {
        return repository.existsBySlugAndBranchId(slug, branchId);
    }

    @Override
    public Mono<Product> findBySlugAndBranchId(String slug, String branchId) {
        return repository.findBySlugAndBranchId(slug, branchId);
    }

    @Override
    public Mono<Void> delete(Product product) {
        return repository.delete(product);
    }
}
