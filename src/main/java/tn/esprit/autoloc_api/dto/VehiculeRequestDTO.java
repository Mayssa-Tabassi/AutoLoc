package tn.esprit.autoloc_api.dto;

import tn.esprit.autoloc_api.domain.CategorieVehicule;
import tn.esprit.autoloc_api.domain.StatutVehicule;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Set;

public record VehiculeRequestDTO(
        @NotBlank(message = "L'immatriculation est obligatoire")
        @Size(max = 20, message = "L'immatriculation ne doit pas dépasser 20 caractères")
        String immatriculation,

        @NotBlank(message = "La marque est obligatoire")
        @Size(max = 50, message = "La marque ne doit pas dépasser 50 caractères")
        String marque,

        @NotBlank(message = "Le modèle est obligatoire")
        @Size(max = 50, message = "Le modèle ne doit pas dépasser 50 caractères")
        String modele,

        @NotNull(message = "La catégorie est obligatoire (CITADINE, BERLINE, SUV ou UTILITAIRE)")
        CategorieVehicule categorie,

        @NotNull(message = "Le tarif journalier est obligatoire")
        @PositiveOrZero(message = "Le tarif journalier ne peut pas être négatif")
        @Digits(integer = 8, fraction = 2,
                message = "Le tarif doit avoir au plus 8 chiffres entiers et 2 décimales")
        BigDecimal tarifJournalier,

        @NotNull(message = "Le statut est obligatoire (DISPONIBLE, LOUE ou MAINTENANCE)")
        StatutVehicule statut,

        @Positive(message = "L'identifiant de l'agence doit être strictement positif")
        Long agenceId,

        Set<@NotNull(message = "Un identifiant d'équipement ne peut pas être nul")
            @Positive(message = "Les identifiants d'équipement doivent être strictement positifs") Long> equipementIds
) {
}
