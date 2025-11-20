package co.com.bancolombia.api.input;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder(toBuilder = true)
public class UpdateProductNameInput {
    @NotEmpty
    String name;

    public UpdateProductNameInput(String name) {
        this.name = name.trim();
    }
}

