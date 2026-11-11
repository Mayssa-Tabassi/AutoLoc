package tn.esprit.autoloc_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AgenceRequestDTO(
        @NotBlank(message = "Le nom de l'agence est obligatoire")
        @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
        String nom,

        @Size(max = 100, message = "La ville ne doit pas dépasser 100 caractères")
        String ville,

        @Size(max = 255, message = "L'adresse ne doit pas dépasser 255 caractères")
        String adresse,

        @Pattern(regexp = "^[0-9+ ]{8,15}$",
                message = "Le téléphone doit contenir 8 à 15 chiffres (+ et espaces autorisés)")
        String telephone
) {
}
