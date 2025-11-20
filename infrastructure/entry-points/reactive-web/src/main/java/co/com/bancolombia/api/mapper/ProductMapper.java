package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.input.CreateProductInput;
import co.com.bancolombia.api.payload.ProductAvailabilityPayload;
import co.com.bancolombia.api.payload.ProductPayload;
import co.com.bancolombia.api.payload.UpdateProductStockPayload;
import co.com.bancolombia.model.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
    UpdateProductStockPayload toUpdateProductStockPayload(Product product);
    ProductPayload toPayload(Product product);
    Product toProduct(CreateProductInput input);

    @Mapping(target = "branch", source = "branchSlug")
    ProductAvailabilityPayload toAvailabilityPayload(Product product);
}
