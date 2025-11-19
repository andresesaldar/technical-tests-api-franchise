package co.com.bancolombia.api.payload;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ErrorPayload {
    Integer status;
    String reason;
    @Builder.Default
    Boolean success = false;
}
