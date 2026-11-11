package tn.esprit.autoloc_api.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc_api.domain.Agence;
import tn.esprit.autoloc_api.domain.Employe;
import tn.esprit.autoloc_api.dto.EmployeRequestDTO;
import tn.esprit.autoloc_api.dto.EmployeResponseDTO;
import tn.esprit.autoloc_api.exception.ResourceNotFoundException;
import tn.esprit.autoloc_api.mapper.EmployeMapper;
import tn.esprit.autoloc_api.repository.IAgenceRepository;
import tn.esprit.autoloc_api.repository.IEmployeRepository;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeRepository employeRepository;
    private final IAgenceRepository agenceRepository;
    private final EmployeMapper employeMapper;

    @Override
    @Transactional
    public EmployeResponseDTO create(EmployeRequestDTO dto) {
        Employe employe = employeMapper.toEntity(dto);
        employe.setAgence(chargerAgence(dto.agenceId()));
        return employeMapper.toResponse(employeRepository.save(employe));
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeResponseDTO findById(Long id) {
        return employeMapper.toResponse(charger(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeResponseDTO> findAll() {
        return employeMapper.toResponseList(employeRepository.findAll());
    }

    @Override
    @Transactional
    public EmployeResponseDTO update(Long id, EmployeRequestDTO dto) {
        Employe existant = charger(id);
        employeMapper.updateEntity(dto, existant);
        existant.setAgence(chargerAgence(dto.agenceId()));
        return employeMapper.toResponse(employeRepository.save(existant));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employé", id);
        }
        employeRepository.deleteById(id);
    }

    private Employe charger(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employé", id));
    }

    /** L'agence est facultative : null signifie « aucune agence ». */
    private Agence chargerAgence(Long idAgence) {
        if (idAgence == null) {
            return null;
        }
        return agenceRepository.findById(idAgence)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", idAgence));
    }
}
