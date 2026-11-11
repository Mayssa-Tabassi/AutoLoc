package tn.esprit.autoloc_api.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc_api.dto.ContratRequestDTO;
import tn.esprit.autoloc_api.dto.ContratResponseDTO;
import tn.esprit.autoloc_api.service.IContratService;

import java.util.List;

@RestController
@RequestMapping("/contrats")
@RequiredArgsConstructor
public class ContratController {

    private final IContratService contratService;

    @GetMapping
    public List<ContratResponseDTO> findAll() {
        return contratService.findAll();
    }

    @GetMapping("/{id}")
    public ContratResponseDTO findById(@PathVariable Long id) {
        return contratService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContratResponseDTO create(@Valid @RequestBody ContratRequestDTO contrat) {
        return contratService.create(contrat);
    }

    @PutMapping("/{id}")
    public ContratResponseDTO update(
            @PathVariable Long id,
            @Valid @RequestBody ContratRequestDTO contrat) {
        return contratService.update(id, contrat);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        contratService.deleteById(id);
    }
}
