package co.com.bancolombia.mongo;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.branch.gateways.BranchRepository;
import co.com.bancolombia.mongo.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class BranchRepositoryAdapter
        extends AdapterOperations<Branch, Branch, String, BranchDBRepository>
        implements BranchRepository {

    final BranchDBRepository repository;

    public BranchRepositoryAdapter(BranchDBRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Branch.class));
        this.repository = repository;
    }

    @Override
    public Mono<Boolean> existsBySlug(String slug) {
        return repository.existsBySlug(slug);
    }

    @Override
    public Flux<Branch> findByFranchiseId(String franchiseId) {
        return repository.findByFranchiseId(franchiseId);
    }

    @Override
    public Mono<Branch> findBySlugAndFranchiseId(String slug, String franchiseId) {
        return repository.findBySlugAndFranchiseId(slug, franchiseId);
    }

    @Override
    public Mono<Boolean> existsBySlugAndFranchiseId(String slug, String franchiseId) {
        return repository.existsBySlugAndFranchiseId(slug, franchiseId);
    }
}
