package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.input.CreateFranchiseInput;
import co.com.bancolombia.api.payload.FranchisePayload;
import co.com.bancolombia.model.franchise.Franchise;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FranchiseMapper {
    Franchise toFranchise(CreateFranchiseInput input);
    FranchisePayload toPayload(Franchise franchise);
}
