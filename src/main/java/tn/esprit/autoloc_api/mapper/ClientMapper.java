package tn.esprit.autoloc_api.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc_api.domain.Client;
import tn.esprit.autoloc_api.dto.ClientRequestDTO;
import tn.esprit.autoloc_api.dto.ClientResponseDTO;

import java.util.List;

@Component
public class ClientMapper {

    public Client toEntity(ClientRequestDTO dto) {
        Client client = new Client();
        updateEntity(dto, client);
        return client;
    }

    public void updateEntity(ClientRequestDTO dto, Client client) {
        client.setNom(dto.nom());
        client.setPrenom(dto.prenom());
        client.setEmail(dto.email());
        client.setTelephone(dto.telephone());
        client.setNumPermis(dto.numPermis());
        client.setDateInscription(dto.dateInscription());
    }

    public ClientResponseDTO toResponse(Client c) {
        return new ClientResponseDTO(c.getIdClient(), c.getNom(), c.getPrenom(),
                c.getEmail(), c.getTelephone(), c.getNumPermis(), c.getDateInscription());
    }

    public List<ClientResponseDTO> toResponseList(List<Client> clients) {
        return clients.stream().map(this::toResponse).toList();
    }
}
