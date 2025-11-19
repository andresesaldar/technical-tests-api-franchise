package co.com.bancolombia.api.payload;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class UpdateProductStockPayload {
    String slug;
    Long stock;

    public static class Response extends ResponsePayload<UpdateProductStockPayload> {
        public Response(UpdateProductStockPayload data) {
            super(data);
        }
    }

    public UpdateProductStockPayload.Response response() {
        return new Response(this);
    }
}
