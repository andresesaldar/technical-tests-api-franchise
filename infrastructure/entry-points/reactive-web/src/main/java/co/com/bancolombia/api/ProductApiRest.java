package co.com.bancolombia.api;
import co.com.bancolombia.api.input.UpdateProductStockInput;
import co.com.bancolombia.api.mapper.ProductMapper;
import co.com.bancolombia.api.route.API_REST_ROUTES;
import co.com.bancolombia.usecase.product.ProductUseCase;
import co.com.bancolombia.api.payload.UpdateProductStockPayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = API_REST_ROUTES.API + API_REST_ROUTES.PRODUCTS, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Product API REST")
public class ProductApiRest {
    private final ProductUseCase productUseCase;
    private final ProductMapper productMapper;

    @PutMapping(path = API_REST_ROUTES.SLUG_PARAM + API_REST_ROUTES.STOCK)
    @Operation(summary = "Update product stock by slug")
    public Mono<UpdateProductStockPayload.Response> updateProductStock(
            @PathVariable("slug") String slug,
            @RequestBody @Valid UpdateProductStockInput input
            ) {
        return productUseCase.updateProductStock(slug, input.getStock())
                .map(productMapper::toUpdateProductStockPayload)
                .map(UpdateProductStockPayload::response);
    }
}
