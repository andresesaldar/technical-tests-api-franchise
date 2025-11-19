package co.com.bancolombia.api.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResponsePayload<T> {
    T data;
    @Builder.Default
    Boolean success = true;

    public ResponsePayload(T data) {
        this.data = data;
        this.success = true;
    }

    public static class OkResponse extends ResponsePayload<Boolean> {

        public OkResponse() {
            super(true);
        }
    }
}
