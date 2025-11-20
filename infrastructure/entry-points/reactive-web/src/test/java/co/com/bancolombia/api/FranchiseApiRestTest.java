package co.com.bancolombia.api;

import co.com.bancolombia.api.input.CreateFranchiseInput;
import co.com.bancolombia.api.input.UpdateFranchiseNameInput;
import co.com.bancolombia.api.mapper.FranchiseMapper;
import co.com.bancolombia.api.payload.FranchisePayload;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.usecase.franchise.FranchiseUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class FranchiseApiRestTest {

    @Mock
    FranchiseUseCase franchiseUseCase;

    FranchiseMapper franchiseMapper;

    @InjectMocks
    FranchiseApiRest franchiseApiRest;

    @BeforeEach
    void setUp() {
        franchiseMapper = Mappers.getMapper(FranchiseMapper.class);
        MockitoAnnotations.openMocks(this);
        franchiseApiRest = new FranchiseApiRest(franchiseUseCase, franchiseMapper);
    }

    @Test
    void shouldCreate() {
        final CreateFranchiseInput input = CreateFranchiseInput.builder()
                .name("Test Franchise")
                .build();
        final Franchise createdFranchise = Franchise.builder()
                .name("Test Franchise")
                .slug("test-franchise")
                .build();

        when(franchiseUseCase.create(any(Franchise.class)))
                .thenReturn(Mono.just(createdFranchise));

        final Mono<FranchisePayload.Response> result = franchiseApiRest.create(input);

        StepVerifier.create(result)
                    .expectNextMatches(franchise ->
                        input.getName().equals(franchise.getData().getName()) &&
                        "test-franchise".equals(franchise.getData().getSlug()))
                    .verifyComplete();
    }

    @Test
    void shouldUpdateName() {
        final String slug = "old-franchise";
        final UpdateFranchiseNameInput input = UpdateFranchiseNameInput.builder()
                .name("Updated Franchise Name")
                .build();
        final Franchise updatedFranchise = Franchise.builder()
                .name("Updated Franchise Name")
                .slug("updated-franchise-name")
                .build();

        when(franchiseUseCase.updateName(anyString(), anyString()))
                .thenReturn(Mono.just(updatedFranchise));

        final Mono<FranchisePayload.Response> result = franchiseApiRest.updateName(slug, input);

        StepVerifier.create(result)
                    .expectNextMatches(franchise ->
                        input.getName().equals(franchise.getData().getName()) &&
                        "updated-franchise-name".equals(franchise.getData().getSlug()))
                    .verifyComplete();
    }

}

