package co.com.bancolombia.error;

public interface CommonError<T extends Exception> {
    String getCode();
    String getMessage();
    T exception();
}
