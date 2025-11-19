package co.com.bancolombia.api;

import co.com.bancolombia.api.input.CreateBranchInput;
import co.com.bancolombia.api.input.CreateFranchiseInput;
import co.com.bancolombia.api.mapper.BranchMapper;
import co.com.bancolombia.api.mapper.FranchiseMapper;
import co.com.bancolombia.api.payload.FranchisePayload;
import co.com.bancolombia.api.route.API_REST_ROUTES;
import co.com.bancolombia.usecase.branch.BranchUseCase;
import co.com.bancolombia.usecase.franchise.FranchiseUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = API_REST_ROUTES.API + API_REST_ROUTES.FRANCHISES, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Franchise API REST")
public class FranchiseApiRest {
    private final FranchiseUseCase franchiseUseCase;
    private final BranchUseCase branchUseCase;
    private final FranchiseMapper franchiseMapper;
    private final BranchMapper branchMapper;

    @PostMapping
    @Operation(summary = "Create franchise")
    public Mono<FranchisePayload.Response> create(
            @RequestBody @Valid CreateFranchiseInput input
            ) {
        return franchiseUseCase.create(franchiseMapper.toFranchise(input))
                .map(franchiseMapper::toPayload)
                .map(FranchisePayload::response);
    }

    @PostMapping(API_REST_ROUTES.SLUG_PARAM + API_REST_ROUTES.BRANCH)
    @Operation(summary = "Add branch to franchise")
    public Mono<FranchisePayload.Response> createBranch(
            @PathVariable("slug") String slug,
            @RequestBody @Valid CreateBranchInput input
    ) {
        return branchUseCase.create(slug, branchMapper.toBranch(input))
                .flatMap(ignored -> franchiseUseCase.getBySlug(slug))
                .map(franchiseMapper::toPayload)
                .map(FranchisePayload::response);
    }
}
