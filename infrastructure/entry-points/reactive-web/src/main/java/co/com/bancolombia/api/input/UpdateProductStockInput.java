package co.com.bancolombia.api.input;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder(toBuilder = true)
public class UpdateProductStockInput {
    @NotNull
    Long stock;
}
