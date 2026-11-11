package tn.esprit.autoloc_api.service;

import java.util.List;
import tn.esprit.autoloc_api.dto.EmployeRequestDTO;
import tn.esprit.autoloc_api.dto.EmployeResponseDTO;

public interface IEmployeService {

    EmployeResponseDTO create(EmployeRequestDTO dto);

    EmployeResponseDTO findById(Long id);

    List<EmployeResponseDTO> findAll();

    EmployeResponseDTO update(Long id, EmployeRequestDTO dto);

    void deleteById(Long id);
}
