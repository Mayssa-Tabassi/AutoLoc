
package tn.esprit.autoloc_api.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc_api.dto.VehiculeRequestDTO;
import tn.esprit.autoloc_api.dto.VehiculeResponseDTO;
import tn.esprit.autoloc_api.service.IVehiculeService;

import java.util.List;

@Tag(name = "Véhicules", description = "Gestion des véhicules")
@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    // FIND ALL
    @Operation(summary = "Lister les véhicules")
    @ApiResponse(responseCode = "200", description = "Liste retournée")
    @GetMapping
    public List<VehiculeResponseDTO> findAll() {
        return vehiculeService.findAll();
    }

    // FIND BY ID
    @Operation(summary = "Consulter un véhicule")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Opération réussie"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    @GetMapping("/{id}")
    public VehiculeResponseDTO findById(
            @Parameter(description = "Identifiant")
            @PathVariable Long id) {

        return vehiculeService.findById(id);
    }

    // CREATE
    @Operation(summary = "Créer un véhicule")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Opération réussie"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehiculeResponseDTO create(
            @RequestBody VehiculeRequestDTO dto) {

        return vehiculeService.create(dto);
    }

    // UPDATE
    @Operation(summary = "Modifier un véhicule")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Opération réussie"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PutMapping("/{id}")
    public VehiculeResponseDTO update(
            @Parameter(description = "Identifiant")
            @PathVariable Long id,
            @RequestBody VehiculeRequestDTO dto) {

        return vehiculeService.update(id, dto);
    }

    // DELETE
    @Operation(summary = "Supprimer un véhicule")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Opération réussie"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @Parameter(description = "Identifiant")
            @PathVariable Long id) {

        vehiculeService.deleteById(id);
    }
}
