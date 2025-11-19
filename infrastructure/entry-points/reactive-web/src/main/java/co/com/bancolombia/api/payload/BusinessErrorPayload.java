package co.com.bancolombia.api.payload;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BusinessErrorPayload {
    Integer status;
    String reason;
    String code;
    String message;
    @Builder.Default
    Boolean success = false;
}
