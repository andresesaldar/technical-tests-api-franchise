package co.com.bancolombia.error;

import lombok.Getter;

@Getter
public class InvalidParamException extends Exception {
    private final int status;
    private final InvalidParamError customError;
    private final String reason;

    public InvalidParamException(InvalidParamError customError) {
        super(customError.getMessage());
        this.status = 400;
        this.customError = customError;
        this.reason = customError.getMessage();
    }
}
