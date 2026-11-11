
package tn.esprit.autoloc_api.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc_api.domain.Client;
import tn.esprit.autoloc_api.dto.ClientRequestDTO;
import tn.esprit.autoloc_api.dto.ClientResponseDTO;
import tn.esprit.autoloc_api.exception.ResourceNotFoundException;
import tn.esprit.autoloc_api.mapper.ClientMapper;
import tn.esprit.autoloc_api.repository.IClientRepository;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;
    private final ClientMapper clientMapper;

    // CREATE
    @Override
    @Transactional
    public ClientResponseDTO create(ClientRequestDTO dto) {

        Client client = clientMapper.toEntity(dto);

        return clientMapper.toResponse(
                clientRepository.save(client)
        );
    }

    // FIND BY ID
    @Override
    @Transactional(readOnly = true)
    public ClientResponseDTO findById(Long id) {

        return clientMapper.toResponse(charger(id));
    }

    // FIND ALL
    @Override
    @Transactional(readOnly = true)
    public List<ClientResponseDTO> findAll() {

        return clientMapper.toResponseList(
                clientRepository.findAll()
        );
    }

    // UPDATE
    @Override
    @Transactional
    public ClientResponseDTO update(Long id, ClientRequestDTO dto) {

        Client existant = charger(id);

        clientMapper.updateEntity(dto, existant);

        return clientMapper.toResponse(
                clientRepository.save(existant)
        );
    }

    // DELETE
    @Override
    @Transactional
    public void deleteById(Long id) {

        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client", id);
        }

        clientRepository.deleteById(id);
    }

    // CHARGER CLIENT
    private Client charger(Long id) {

        return clientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Client", id)
                );
    }
}
