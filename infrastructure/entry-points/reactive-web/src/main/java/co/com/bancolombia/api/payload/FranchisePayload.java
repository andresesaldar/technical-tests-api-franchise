package co.com.bancolombia.api.payload;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class FranchisePayload {
    String slug;
    String name;

    public static class Response extends ResponsePayload<FranchisePayload> {
        public Response(FranchisePayload data) {
            super(data);
        }
    }

    public FranchisePayload.Response response() {
        return new FranchisePayload.Response(this);
    }
}
