package tn.esprit.autoloc_api.dto;

import java.time.LocalDate;

public record ClientResponseDTO(Long idClient, String nom, String prenom, String email,
                                String telephone, String numPermis, LocalDate dateInscription) {
}
