package tn.esprit.autoloc_api.dto;

public record AgenceResponseDTO(Long idAgence, String nom, String ville,
                                String adresse, String telephone) {
}
