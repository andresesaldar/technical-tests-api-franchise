package co.com.bancolombia.api;

import co.com.bancolombia.api.input.CreateBranchInput;
import co.com.bancolombia.api.mapper.BranchMapper;
import co.com.bancolombia.api.mapper.ProductMapper;
import co.com.bancolombia.api.payload.BranchPayload;
import co.com.bancolombia.api.payload.ProductAvailabilityPayload;
import co.com.bancolombia.api.route.API_REST_ROUTES;
import co.com.bancolombia.usecase.branch.BranchUseCase;
import co.com.bancolombia.usecase.product.ProductUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(
        value = API_REST_ROUTES.API + API_REST_ROUTES.FRANCHISES
                + API_REST_ROUTES.FRANCHISE_SLUG_PARAM + API_REST_ROUTES.BRANCHES,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Branch API REST")
public class BranchApiRest {
    private final BranchUseCase branchUseCase;
    private final BranchMapper branchMapper;
    private final ProductUseCase productUseCase;
    private final ProductMapper productMapper;

    private static final Integer PAGE_SIZE = 50;

    @PostMapping
    @Operation(summary = "Add branch to franchise")
    public Mono<BranchPayload.Response> create(
            @PathVariable("franchiseSlug") String franchiseSlug,
            @RequestBody @Valid CreateBranchInput input
    ) {
        return branchUseCase.create(franchiseSlug, branchMapper.toBranch(input))
                .map(branchMapper::toPayload)
                .map(BranchPayload::response);
    }

    @GetMapping(API_REST_ROUTES.AVAILABILITY)
    @Operation(summary = "Get products availability")
    public Mono<ProductAvailabilityPayload.ListResponse> getProductsAvailability(
            @PathVariable("franchiseSlug") String franchiseSlug,
            @RequestParam(value = "page", defaultValue = "0") Integer page
    ) {

        return productUseCase.getProductsAvailability(franchiseSlug, page, PAGE_SIZE)
                .map(productMapper::toAvailabilityPayload)
                .collectList()
                .map(ProductAvailabilityPayload.ListResponse::new);
    }
}
