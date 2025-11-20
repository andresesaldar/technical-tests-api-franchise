package co.com.bancolombia.api;
import co.com.bancolombia.api.input.CreateProductInput;
import co.com.bancolombia.api.input.UpdateProductNameInput;
import co.com.bancolombia.api.input.UpdateProductStockInput;
import co.com.bancolombia.api.mapper.ProductMapper;
import co.com.bancolombia.api.payload.ProductPayload;
import co.com.bancolombia.api.payload.ResponsePayload;
import co.com.bancolombia.api.route.API_REST_ROUTES;
import co.com.bancolombia.usecase.product.ProductUseCase;
import co.com.bancolombia.api.payload.UpdateProductStockPayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(
        value = API_REST_ROUTES.API + API_REST_ROUTES.FRANCHISES
                + API_REST_ROUTES.FRANCHISE_SLUG_PARAM + API_REST_ROUTES.BRANCHES
                + API_REST_ROUTES.BRANCH_SLUG_PARAM + API_REST_ROUTES.PRODUCTS,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Product API REST")
public class ProductApiRest {
    private final ProductUseCase productUseCase;
    private final ProductMapper productMapper;

    @PatchMapping(path = API_REST_ROUTES.SLUG_PARAM + API_REST_ROUTES.STOCK)
    @Operation(summary = "Update product stock by slug")
    public Mono<UpdateProductStockPayload.Response> updateProductStock(
            @PathVariable("franchiseSlug") String franchiseSlug,
            @PathVariable("branchSlug") String branchSlug,
            @PathVariable("slug") String slug,
            @RequestBody @Valid UpdateProductStockInput input
            ) {
        return productUseCase.updateProductStock(franchiseSlug, branchSlug, slug, input.getStock())
                .map(productMapper::toUpdateProductStockPayload)
                .map(UpdateProductStockPayload::response);
    }

    @PostMapping
    @Operation(summary = "Add product to branch")
    public Mono<ProductPayload.Response> create(
            @PathVariable("franchiseSlug") String franchiseSlug,
            @PathVariable("branchSlug") String branchSlug,
            @RequestBody @Valid CreateProductInput input
    ) {
        return productUseCase.create(franchiseSlug, branchSlug, productMapper.toProduct(input))
                .map(productMapper::toPayload)
                .map(ProductPayload::response);
    }

    @PatchMapping(path = API_REST_ROUTES.SLUG_PARAM + API_REST_ROUTES.NAME)
    @Operation(summary = "Update product name")
    public Mono<ProductPayload.Response> updateName(
            @PathVariable("franchiseSlug") String franchiseSlug,
            @PathVariable("branchSlug") String branchSlug,
            @PathVariable("slug") String slug,
            @RequestBody @Valid UpdateProductNameInput input
    ) {
        return productUseCase.updateName(franchiseSlug, branchSlug, slug, input.getName())
                .map(productMapper::toPayload)
                .map(ProductPayload::response);
    }


    @DeleteMapping(path = API_REST_ROUTES.SLUG_PARAM)
    @Operation(summary = "Delete product from branch")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<ResponsePayload.OkResponse> delete(
            @PathVariable("franchiseSlug") String franchiseSlug,
            @PathVariable("branchSlug") String branchSlug,
            @PathVariable("slug") String slug
    ) {
        return productUseCase.delete(franchiseSlug, branchSlug, slug)
                .then(Mono.just(new ResponsePayload.OkResponse()));
    }
}
