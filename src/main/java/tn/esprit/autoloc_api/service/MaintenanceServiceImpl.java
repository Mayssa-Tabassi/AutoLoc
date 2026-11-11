
package tn.esprit.autoloc_api.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc_api.domain.Maintenance;
import tn.esprit.autoloc_api.domain.Vehicule;
import tn.esprit.autoloc_api.dto.MaintenanceRequestDTO;
import tn.esprit.autoloc_api.dto.MaintenanceResponseDTO;
import tn.esprit.autoloc_api.exception.ResourceNotFoundException;
import tn.esprit.autoloc_api.mapper.MaintenanceMapper;
import tn.esprit.autoloc_api.repository.IMaintenanceRepository;
import tn.esprit.autoloc_api.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final MaintenanceMapper maintenanceMapper;

    // CREATE
    @Override
    @Transactional
    public MaintenanceResponseDTO create(MaintenanceRequestDTO dto) {

        verifierDates(dto);

        Maintenance maintenance = maintenanceMapper.toEntity(dto);

        maintenance.setVehicule(chargerVehicule(dto.vehiculeId()));

        return maintenanceMapper.toResponse(
                maintenanceRepository.save(maintenance)
        );
    }

    // FIND BY ID
    @Override
    @Transactional(readOnly = true)
    public MaintenanceResponseDTO findById(Long id) {

        return maintenanceMapper.toResponse(charger(id));
    }

    // FIND ALL
    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceResponseDTO> findAll() {

        return maintenanceMapper.toResponseList(
                maintenanceRepository.findAll()
        );
    }

    // UPDATE
    @Override
    @Transactional
    public MaintenanceResponseDTO update(Long id, MaintenanceRequestDTO dto) {

        Maintenance existante = charger(id);

        verifierDates(dto);

        maintenanceMapper.updateEntity(dto, existante);

        existante.setVehicule(chargerVehicule(dto.vehiculeId()));

        return maintenanceMapper.toResponse(
                maintenanceRepository.save(existante)
        );
    }

    // DELETE
    @Override
    @Transactional
    public void deleteById(Long id) {

        maintenanceRepository.delete(charger(id));
    }

    // CHARGER MAINTENANCE
    private Maintenance charger(Long id) {

        return maintenanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Maintenance", id)
                );
    }

    // CHARGER VEHICULE
    private Vehicule chargerVehicule(Long idVehicule) {

        return vehiculeRepository.findById(idVehicule)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Véhicule", idVehicule)
                );
    }

    // VERIFIER LES DATES
    private void verifierDates(MaintenanceRequestDTO dto) {

        if (dto.dateFin() != null &&
                dto.dateFin().isBefore(dto.dateDebut())) {

            throw new IllegalArgumentException(
                    "La date de fin ne peut pas être antérieure à la date de début"
            );
        }
    }
}
