package tn.esprit.autoloc_api.exception;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Format unique des erreurs renvoyées par l'API.
 *
 * @param errors détail par champ pour les erreurs de validation (vide sinon)
 */
public record ApiError(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> errors
) {
}
