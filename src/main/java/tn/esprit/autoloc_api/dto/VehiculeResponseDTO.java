package tn.esprit.autoloc_api.dto;

import tn.esprit.autoloc_api.domain.CategorieVehicule;
import tn.esprit.autoloc_api.domain.StatutVehicule;

import java.math.BigDecimal;
import java.util.Set;

public record VehiculeResponseDTO(Long idVehicule, String immatriculation, String marque,
                                  String modele, CategorieVehicule categorie,
                                  BigDecimal tarifJournalier, StatutVehicule statut,
                                  Long agenceId, Set<Long> equipementIds) {
}
