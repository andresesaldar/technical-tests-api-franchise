package co.com.bancolombia.api;

import co.com.bancolombia.api.input.CreateFranchiseInput;
import co.com.bancolombia.api.mapper.FranchiseMapper;
import co.com.bancolombia.api.payload.FranchisePayload;
import co.com.bancolombia.api.route.API_REST_ROUTES;
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
    private final FranchiseMapper franchiseMapper;

    @PostMapping
    @Operation(summary = "Create franchise")
    public Mono<FranchisePayload.Response> create(
            @RequestBody @Valid CreateFranchiseInput input
            ) {
        return franchiseUseCase.create(franchiseMapper.toFranchise(input))
                .map(franchiseMapper::toPayload)
                .map(FranchisePayload::response);
    }
}
