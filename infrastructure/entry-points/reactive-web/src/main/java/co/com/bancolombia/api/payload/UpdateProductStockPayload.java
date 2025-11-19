package co.com.bancolombia.api.payload;

import co.com.bancolombia.model.product.Product;
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

    public static UpdateProductStockPayload from(Product product) {
        return UpdateProductStockPayload.builder()
                .slug(product.getSlug())
                .stock(product.getStock())
                .build();
    }

    public UpdateProductStockPayload.Response response() {
        return new Response(this);
    }
}
