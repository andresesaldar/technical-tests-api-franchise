package co.com.bancolombia.api.payload;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class ProductPayload {
    String slug;
    String name;
    Long stock;

    @ApiResponse
    @Tag(name = "ResponsePayload<FranchisePayload>")
    public static class Response extends ResponsePayload<ProductPayload> {
        public Response(ProductPayload data) {
            super(data);
        }
    }

    public ProductPayload.Response response() {
        return new ProductPayload.Response(this);
    }
}
