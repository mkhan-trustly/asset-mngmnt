package se.work.asset.demo.command.exception;

public class NoAssetFoundException extends RuntimeException {

    public NoAssetFoundException(String message) {
        super(message);
    }
}
