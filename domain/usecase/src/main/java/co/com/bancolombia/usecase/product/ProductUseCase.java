package co.com.bancolombia.usecase.product;

import co.com.bancolombia.error.InvalidParamError;
import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.branch.gateways.BranchRepository;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductUseCase {
    private final ProductRepository productRepository;
    private final FranchiseRepository franchiseRepository;
    private final BranchRepository branchRepository;

    public Mono<Product> updateProductStock(
            String franchiseSlug, String branchSlug, String slug, Long stock) {
        return validateFranchiseAndBranch(franchiseSlug, branchSlug)
                .flatMap(branch -> productRepository.findBySlugAndBranchId(slug, branch.getId()))
                .switchIfEmpty(Mono.error(InvalidParamError.INVALID_PRODUCT_SLUG.exception()))
                .flatMap(product -> {
                    product.setStock(stock);
                    return productRepository.save(product);
                });

    }

    public Mono<Product> create(String franchiseSlug, String branchSlug, Product product) {
        final String slug = product.getName().toLowerCase().replace(" ", "-");

        product.setSlug(slug);

        return validateFranchiseAndBranch(franchiseSlug, branchSlug)
                .flatMap(branch -> productRepository.existsBySlugAndBranchId(slug, branch.getId())
                    .filter(Boolean.FALSE::equals)
                    .switchIfEmpty(Mono.error(InvalidParamError.INVALID_PRODUCT_SLUG.exception()))
                    .flatMap(ignored -> {
                        product.setBranchId(branch.getId());
                        return productRepository.save(product);
                    }));
    }

    public Mono<Void> delete(String franchiseSlug, String branchSlug, String slug) {

        return validateFranchiseAndBranch(franchiseSlug, branchSlug)
                .flatMap(branch -> productRepository.findBySlugAndBranchId(slug, branch.getId())
                        .switchIfEmpty(Mono.error(InvalidParamError.INVALID_PRODUCT_SLUG.exception()))
                        .flatMap(productRepository::delete));
    }

    public Flux<Product> getProductsAvailability(String franchiseSlug, Integer page, Integer pageSize) {
        return franchiseRepository.findBySlug(franchiseSlug)
                .switchIfEmpty(Mono.error(InvalidParamError.INVALID_FRANCHISE_SLUG.exception()))
                .flatMapMany(franchise ->
                        productRepository.findProductsWithBranchByFranchiseIdSortByStockDesc(franchise.getId(), page, pageSize));
    }

    private Mono<Branch> validateFranchiseAndBranch(String franchiseSlug, String slug) {
        return franchiseRepository.findBySlug(franchiseSlug)
                .switchIfEmpty(Mono.error(InvalidParamError.INVALID_FRANCHISE_SLUG.exception()))
                .flatMap(franchise -> branchRepository.findBySlugAndFranchiseId(slug, franchise.getId()))
                .switchIfEmpty(Mono.error(InvalidParamError.INVALID_BRANCH_SLUG.exception()));
    }
}
