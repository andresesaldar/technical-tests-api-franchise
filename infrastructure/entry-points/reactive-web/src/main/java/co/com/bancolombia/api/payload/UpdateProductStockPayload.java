package co.com.bancolombia.api.payload;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class UpdateProductStockPayload {
    String slug;
    Long stock;

    @ApiResponse
    @Tag(name = "ResponsePayload<UpdateProductStockPayload>")
    public static class Response extends ResponsePayload<UpdateProductStockPayload> {
        public Response(UpdateProductStockPayload data) {
            super(data);
        }
    }

    public UpdateProductStockPayload.Response response() {
        return new Response(this);
    }
}
