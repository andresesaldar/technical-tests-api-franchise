package co.com.bancolombia.api.payload;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class ProductAvailabilityPayload {
    String slug;
    String name;
    Long stock;
    String branch;

    @ApiResponse
    @Tag(name = "ResponsePayload<List<ProductAvailabilityPayload>>")
    public static class ListResponse extends  ResponsePayload<List<ProductAvailabilityPayload>> {
        public ListResponse(List<ProductAvailabilityPayload> data) {
            super(data);
        }
    }
}
