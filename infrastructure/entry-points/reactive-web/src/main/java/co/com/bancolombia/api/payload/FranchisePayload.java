package co.com.bancolombia.api.payload;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.List;

@Value
@Builder
@Jacksonized
public class FranchisePayload {
    String slug;
    String name;
    @Builder.Default
    List<BranchPayload> branches = new ArrayList<>();

    @ApiResponse
    @Tag(name = "ResponsePayload<FranchisePayload>")
    public static class Response extends ResponsePayload<FranchisePayload> {
        public Response(FranchisePayload data) {
            super(data);
        }
    }

    public FranchisePayload.Response response() {
        return new FranchisePayload.Response(this);
    }
}
