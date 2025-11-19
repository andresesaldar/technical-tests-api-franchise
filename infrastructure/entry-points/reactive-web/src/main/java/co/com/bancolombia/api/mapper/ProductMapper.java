package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.payload.UpdateProductStockPayload;
import co.com.bancolombia.model.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
    UpdateProductStockPayload toUpdateProductStockPayload(Product product);
}
