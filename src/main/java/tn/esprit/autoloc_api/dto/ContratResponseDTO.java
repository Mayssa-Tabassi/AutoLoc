package tn.esprit.autoloc_api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Données renvoyées par l'API pour un contrat.
 * Les associations sont réduites à des identifiants : pas de boucle
 * Contrat -> Paiement -> Contrat et pas d'entités JPA exposées.
 */
public record ContratResponseDTO(
        Long idContrat,
        LocalDate dateSignature,
        BigDecimal montantTotal,
        Boolean valide,
        Long reservationId,
        List<Long> paiementIds
) {
}
