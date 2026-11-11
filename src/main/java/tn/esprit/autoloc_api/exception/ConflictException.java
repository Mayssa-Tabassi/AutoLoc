package tn.esprit.autoloc_api.exception;

/** Levée lorsqu'une opération viole une règle d'unicité métier (HTTP 409). */
public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
