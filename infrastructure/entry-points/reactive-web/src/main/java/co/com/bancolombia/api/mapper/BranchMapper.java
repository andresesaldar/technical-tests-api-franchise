package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.input.CreateBranchInput;
import co.com.bancolombia.api.payload.BranchPayload;
import co.com.bancolombia.model.branch.Branch;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BranchMapper {
    Branch toBranch(CreateBranchInput input);
    BranchPayload toPayload(Branch branch);
}
