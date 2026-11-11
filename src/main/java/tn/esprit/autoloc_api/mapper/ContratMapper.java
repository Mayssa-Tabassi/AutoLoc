package tn.esprit.autoloc_api.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc_api.domain.Contrat;
import tn.esprit.autoloc_api.domain.Paiement;
import tn.esprit.autoloc_api.dto.ContratRequestDTO;
import tn.esprit.autoloc_api.dto.ContratResponseDTO;

import java.util.List;
import java.util.Set;

@Component
public class ContratMapper {

    /** Construit une nouvelle entité (sans réservation : le service la résout). */
    public Contrat toEntity(ContratRequestDTO dto) {
        Contrat contrat = new Contrat();
        updateEntity(dto, contrat);
        return contrat;
    }

    /** Copie les champs simples du DTO vers l'entité existante. */
    public void updateEntity(ContratRequestDTO dto, Contrat contrat) {
        contrat.setDateSignature(dto.dateSignature());
        contrat.setMontantTotal(dto.montantTotal());
        contrat.setValide(dto.valide());
    }

    public ContratResponseDTO toResponse(Contrat contrat) {
        Set<Paiement> paiements = contrat.getPaiements();
        List<Long> paiementIds = paiements == null
                ? List.of()
                : paiements.stream().map(Paiement::getIdPaiement).toList();

        return new ContratResponseDTO(
                contrat.getIdContrat(),
                contrat.getDateSignature(),
                contrat.getMontantTotal(),
                contrat.getValide(),
                contrat.getReservation() == null
                        ? null
                        : contrat.getReservation().getIdReservation(),
                paiementIds);
    }

    public List<ContratResponseDTO> toResponseList(Iterable<Contrat> contrats) {
        java.util.ArrayList<ContratResponseDTO> result = new java.util.ArrayList<>();
        contrats.forEach(c -> result.add(toResponse(c)));
        return result;
    }
}
