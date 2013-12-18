package fengfei.fir.service;

public class ExifException extends RuntimeException {

    public ExifException(String message) {
        super(message);
    }

    public ExifException(String formatMessage, Object... args) {
        super(String.format(formatMessage, args));
    }

    public ExifException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExifException(String formatMessage, Throwable cause, Object... args) {
        super(String.format(formatMessage, args), cause);
    }

    public ExifException(Throwable cause) {
        super(cause);
    }

    protected ExifException(
        String message,
        Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Throwable fillInStackTrace() {
        return null;
    }
}
