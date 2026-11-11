
package tn.esprit.autoloc_api.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc_api.dto.EmployeRequestDTO;
import tn.esprit.autoloc_api.dto.EmployeResponseDTO;
import tn.esprit.autoloc_api.service.IEmployeService;

import java.util.List;

@Tag(name = "Employés", description = "Gestion des employés")
@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final IEmployeService employeService;

    // FIND ALL
    @Operation(summary = "Lister les employés")
    @ApiResponse(responseCode = "200",
            description = "Liste retournée (éventuellement vide)")
    @GetMapping
    public List<EmployeResponseDTO> findAll() {
        return employeService.findAll();
    }

    // FIND BY ID
    @Operation(summary = "Consulter un employé")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Opération réussie"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    @GetMapping("/{id}")
    public EmployeResponseDTO findById(
            @Parameter(description = "Identifiant")
            @PathVariable Long id) {

        return employeService.findById(id);
    }

    // CREATE
    @Operation(summary = "Créer un employé")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Opération réussie"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeResponseDTO create(
            @RequestBody EmployeRequestDTO dto) {

        return employeService.create(dto);
    }

    // UPDATE
    @Operation(summary = "Modifier un employé")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Opération réussie"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PutMapping("/{id}")
    public EmployeResponseDTO update(
            @Parameter(description = "Identifiant")
            @PathVariable Long id,
            @RequestBody EmployeRequestDTO dto) {

        return employeService.update(id, dto);
    }

    // DELETE
    @Operation(summary = "Supprimer un employé")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Opération réussie"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @Parameter(description = "Identifiant")
            @PathVariable Long id) {

        employeService.deleteById(id);
    }
}
