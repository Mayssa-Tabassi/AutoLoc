package tn.esprit.autoloc_api.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.PastOrPresent;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Données reçues par l'API pour créer ou modifier un contrat.
 * L'identifiant n'est jamais fourni par le client.
 */
public record ContratRequestDTO(

        @NotNull(message = "La date de signature est obligatoire")
        @PastOrPresent(message = "La date de signature ne peut pas être dans le futur")
        LocalDate dateSignature,

        @NotNull(message = "Le montant total est obligatoire")
        @PositiveOrZero(message = "Le montant total ne peut pas être négatif")
        @Digits(integer = 10, fraction = 2,
                message = "Le montant total doit avoir au plus 10 chiffres entiers et 2 décimales")
        BigDecimal montantTotal,

        @NotNull(message = "Le statut de validité du contrat est obligatoire")
        Boolean valide,

        @NotNull(message = "L'identifiant de la réservation est obligatoire")
        @Positive(message = "L'identifiant de la réservation doit être strictement positif")
        Long reservationId
) {
}
