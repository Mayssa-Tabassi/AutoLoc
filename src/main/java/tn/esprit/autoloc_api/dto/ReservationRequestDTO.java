package tn.esprit.autoloc_api.dto;

import tn.esprit.autoloc_api.domain.StatutReservation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record ReservationRequestDTO(
        @NotNull(message = "La date de début est obligatoire")
        LocalDate dateDebut,

        @NotNull(message = "La date de fin est obligatoire")
        LocalDate dateFin,

        @NotNull(message = "Le statut est obligatoire (EN_ATTENTE, CONFIRMEE, ANNULEE ou TERMINEE)")
        StatutReservation statut,

        @NotNull(message = "L'identifiant du véhicule est obligatoire")
        @Positive(message = "L'identifiant du véhicule doit être strictement positif")
        Long vehiculeId,

        @NotNull(message = "L'identifiant du client est obligatoire")
        @Positive(message = "L'identifiant du client doit être strictement positif")
        Long clientId
) {
}
