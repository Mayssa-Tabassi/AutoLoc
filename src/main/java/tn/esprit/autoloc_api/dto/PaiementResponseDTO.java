package tn.esprit.autoloc_api.dto;

import tn.esprit.autoloc_api.domain.ModePaiement;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Données renvoyées par l'API pour un paiement.
 * Les paiements sont en lecture seule : il n'existe donc pas de DTO de requête.
 */
public record PaiementResponseDTO(
        Long idPaiement,
        BigDecimal montant,
        LocalDate datePaiement,
        ModePaiement modePaiement,
        Long contratId
) {
}
