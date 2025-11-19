package co.com.bancolombia.usecase.product;

import co.com.bancolombia.error.InvalidParamError;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductUseCase {
    private final ProductRepository productRepository;

    public Mono<Product> updateProductStock(String slug, Long stock) {
        return productRepository.findBySlug(slug)
            .switchIfEmpty(Mono.error(InvalidParamError.INVALID_PRODUCT_SLUG.exception()))
            .flatMap(product -> {
                product.setStock(stock);
                return productRepository.save(product);
            });
    }
}
