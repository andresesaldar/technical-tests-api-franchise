package co.com.bancolombia.mongo;

import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import co.com.bancolombia.mongo.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class FranchiseRepositoryAdapter
        extends AdapterOperations<Franchise, Franchise, String, FranchiseDBRepository>
        implements FranchiseRepository {

    final FranchiseDBRepository repository;

    public FranchiseRepositoryAdapter(FranchiseDBRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Franchise.class));
        this.repository = repository;
    }

    @Override
    public Mono<Boolean> existsBySlug(String slug) {
        return repository.existsBySlug(slug);
    }

    @Override
    public Mono<Franchise> findBySlug(String slug) {
        return repository.findBySlug(slug);
    }
}
