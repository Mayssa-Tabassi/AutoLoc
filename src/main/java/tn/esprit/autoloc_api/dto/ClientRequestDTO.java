package tn.esprit.autoloc_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ClientRequestDTO(
        @NotBlank(message = "Le nom du client est obligatoire")
        @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
        String nom,

        @Size(max = 100, message = "Le prénom ne doit pas dépasser 100 caractères")
        String prenom,

        @NotBlank(message = "L'email du client est obligatoire")
        @Email(message = "Le format de l'email est invalide")
        String email,

        @Pattern(regexp = "^[0-9+ ]{8,15}$",
                message = "Le téléphone doit contenir 8 à 15 chiffres (+ et espaces autorisés)")
        String telephone,

        @Size(max = 30, message = "Le numéro de permis ne doit pas dépasser 30 caractères")
        String numPermis,

        @PastOrPresent(message = "La date d'inscription ne peut pas être dans le futur")
        LocalDate dateInscription
) {
}
