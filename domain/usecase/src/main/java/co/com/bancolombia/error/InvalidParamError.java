package co.com.bancolombia.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum InvalidParamError implements CommonError<InvalidParamException> {
    INVALID_PRODUCT_SLUG("p0001", "Invalid product slug"),
    INVALID_FRANCHISE_SLUG("f0001", "Invalid franchise slug"),
    INVALID_BRANCH_SLUG("b0001", "Invalid branch slug");
    private final String code;
    private final String message;

    @Override
    public InvalidParamException exception() {
        return new InvalidParamException(this);
    }
}
