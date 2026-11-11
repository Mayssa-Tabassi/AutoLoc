package tn.esprit.autoloc_api.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Gestion centralisée des erreurs pour tous les contrôleurs.
 * Chaque handler renvoie le même format {@link ApiError}.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** 404 : ressource métier introuvable. */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(
            ResourceNotFoundException ex, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), request, Map.of());
    }

    /** 404 : URL inexistante. */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiError> handleNoResource(
            NoResourceFoundException ex, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND,
                "Ressource introuvable : " + request.getRequestURI(), request, Map.of());
    }

    /** 400 : échec des contraintes de validation (@Valid sur un DTO). */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fe ->
                errors.putIfAbsent(fe.getField(), fe.getDefaultMessage()));
        ex.getBindingResult().getGlobalErrors().forEach(ge ->
                errors.putIfAbsent(ge.getObjectName(), ge.getDefaultMessage()));

        return build(HttpStatus.BAD_REQUEST,
                "Les données envoyées sont invalides", request, errors);
    }

    /** 400 : JSON mal formé, date illisible, valeur d'énumération inconnue... */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleUnreadable(
            HttpMessageNotReadableException ex, HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST,
                "Corps de la requête illisible : JSON mal formé, date au format "
                        + "incorrect (attendu yyyy-MM-dd) ou valeur non autorisée",
                request, Map.of());
    }

    /** 400 : paramètre d'URL du mauvais type (ex. /contrats/abc). */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        String message = "Le paramètre '" + ex.getName()
                + "' a une valeur incorrecte : " + ex.getValue();
        return build(HttpStatus.BAD_REQUEST, message, request, Map.of());
    }

    /** 400 : argument refusé par le code métier. */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(
            IllegalArgumentException ex, HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), request, Map.of());
    }

    /** 405 : méthode HTTP non supportée sur cette URL. */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        return build(HttpStatus.METHOD_NOT_ALLOWED,
                "Méthode HTTP non supportée : " + ex.getMethod(), request, Map.of());
    }

    /** 409 : règle d'unicité métier violée. */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiError> handleConflict(
            ConflictException ex, HttpServletRequest request) {
        return build(HttpStatus.CONFLICT, ex.getMessage(), request, Map.of());
    }

    /** 409 : contrainte de base de données violée (unicité, clé étrangère). */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrity(
            DataIntegrityViolationException ex, HttpServletRequest request) {
        log.warn("Violation d'intégrité des données", ex);
        return build(HttpStatus.CONFLICT,
                "Opération impossible : elle viole une contrainte d'intégrité "
                        + "(doublon ou ressource encore référencée)",
                request, Map.of());
    }

    /** 500 : toute erreur non prévue. Le détail technique reste dans les logs. */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpected(
            Exception ex, HttpServletRequest request) {
        log.error("Erreur inattendue sur {}", request.getRequestURI(), ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR,
                "Une erreur interne est survenue", request, Map.of());
    }

    private ResponseEntity<ApiError> build(HttpStatus status, String message,
                                           HttpServletRequest request,
                                           Map<String, String> errors) {
        ApiError body = new ApiError(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI(),
                errors);
        return ResponseEntity.status(status).body(body);
    }
}
