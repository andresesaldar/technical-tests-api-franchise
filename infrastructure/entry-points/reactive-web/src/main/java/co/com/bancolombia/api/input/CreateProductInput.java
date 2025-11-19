package co.com.bancolombia.api.input;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder(toBuilder = true)
public class CreateProductInput {
    @NotEmpty
    String name;

    @Builder.Default
    Long stock = 0L;

    public CreateProductInput(String name, Long stock) {
        this.name = name.trim();
        this.stock = stock;
    }
}
