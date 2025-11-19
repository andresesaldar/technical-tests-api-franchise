package co.com.bancolombia.api.payload;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class BranchPayload {
    String slug;
    String name;

    @ApiResponse
    @Tag(name = "ResponsePayload<BranchPayload>")
    public static class Response extends ResponsePayload<BranchPayload> {
        public Response(BranchPayload data) {
            super(data);
        }
    }

    public BranchPayload.Response response() {
        return new BranchPayload.Response(this);
    }
}
