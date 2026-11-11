package tn.esprit.autoloc_api.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc_api.domain.Paiement;
import tn.esprit.autoloc_api.dto.PaiementResponseDTO;

import java.util.List;

@Component
public class PaiementMapper {

    public PaiementResponseDTO toResponse(Paiement paiement) {
        return new PaiementResponseDTO(
                paiement.getIdPaiement(),
                paiement.getMontant(),
                paiement.getDatePaiement(),
                paiement.getModePaiement(),
                paiement.getContrat() == null
                        ? null
                        : paiement.getContrat().getIdContrat());
    }

    public List<PaiementResponseDTO> toResponseList(List<Paiement> paiements) {
        return paiements.stream().map(this::toResponse).toList();
    }
}
