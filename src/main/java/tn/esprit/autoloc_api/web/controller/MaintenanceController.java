
package tn.esprit.autoloc_api.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc_api.dto.MaintenanceRequestDTO;
import tn.esprit.autoloc_api.dto.MaintenanceResponseDTO;
import tn.esprit.autoloc_api.service.IMaintenanceService;

import java.util.List;

@Tag(name = "Maintenances",
        description = "Suivi des maintenances des véhicules")

@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

    private final IMaintenanceService maintenanceService;

    // FIND ALL
    @Operation(summary = "Lister les maintenances")
    @ApiResponse(responseCode = "200",
            description = "Liste retournée (éventuellement vide)")
    @GetMapping
    public List<MaintenanceResponseDTO> findAll() {
        return maintenanceService.findAll();
    }

    // FIND BY ID
    @Operation(summary = "Consulter une maintenance")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Opération réussie"),
            @ApiResponse(responseCode = "404",
                    description = "Ressource introuvable")
    })
    @GetMapping("/{id}")
    public MaintenanceResponseDTO findById(
            @Parameter(description = "Identifiant")
            @PathVariable Long id) {

        return maintenanceService.findById(id);
    }

    // CREATE
    @Operation(summary = "Créer une maintenance")
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Opération réussie"),
            @ApiResponse(responseCode = "400",
                    description = "Données invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaintenanceResponseDTO create(
            @RequestBody MaintenanceRequestDTO dto) {

        return maintenanceService.create(dto);
    }

    // UPDATE
    @Operation(summary = "Modifier une maintenance")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Opération réussie"),
            @ApiResponse(responseCode = "404",
                    description = "Ressource introuvable"),
            @ApiResponse(responseCode = "400",
                    description = "Données invalides")
    })
    @PutMapping("/{id}")
    public MaintenanceResponseDTO update(
            @Parameter(description = "Identifiant")
            @PathVariable Long id,
            @RequestBody MaintenanceRequestDTO dto) {

        return maintenanceService.update(id, dto);
    }

    // DELETE
    @Operation(summary = "Supprimer une maintenance")
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Opération réussie"),
            @ApiResponse(responseCode = "404",
                    description = "Ressource introuvable")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @Parameter(description = "Identifiant")
            @PathVariable Long id) {

        maintenanceService.deleteById(id);
    }
}
