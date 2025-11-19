package co.com.bancolombia.api;

import co.com.bancolombia.api.input.CreateBranchInput;
import co.com.bancolombia.api.mapper.BranchMapper;
import co.com.bancolombia.api.payload.BranchPayload;
import co.com.bancolombia.api.route.API_REST_ROUTES;
import co.com.bancolombia.usecase.branch.BranchUseCase;
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
}
