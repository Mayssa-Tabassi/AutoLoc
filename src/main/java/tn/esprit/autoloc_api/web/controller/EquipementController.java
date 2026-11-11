
package tn.esprit.autoloc_api.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc_api.dto.EquipementRequestDTO;
import tn.esprit.autoloc_api.dto.EquipementResponseDTO;
import tn.esprit.autoloc_api.service.IEquipementService;

import java.util.List;

@Tag(name = "Equipements", description = "Gestion des équipements")
@RestController
@RequestMapping("/equipements")
@RequiredArgsConstructor
public class EquipementController {

    private final IEquipementService equipementService;

    // FIND ALL
    @Operation(summary = "Lister les équipements")
    @ApiResponse(responseCode = "200", description = "Liste retournée")
    @GetMapping
    public List<EquipementResponseDTO> findAll() {
        return equipementService.findAll();
    }

    // FIND BY ID
    @Operation(summary = "Consulter un équipement")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Opération réussie"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    @GetMapping("/{id}")
    public EquipementResponseDTO findById(
            @Parameter(description = "Identifiant")
            @PathVariable Long id) {

        return equipementService.findById(id);
    }

    // CREATE
    @Operation(summary = "Créer un équipement")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Opération réussie"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipementResponseDTO create(
            @RequestBody EquipementRequestDTO dto) {

        return equipementService.create(dto);
    }

    // UPDATE
    @Operation(summary = "Modifier un équipement")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Opération réussie"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PutMapping("/{id}")
    public EquipementResponseDTO update(
            @Parameter(description = "Identifiant")
            @PathVariable Long id,
            @RequestBody EquipementRequestDTO dto) {

        return equipementService.update(id, dto);
    }

    // DELETE
    @Operation(summary = "Supprimer un équipement")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Opération réussie"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @Parameter(description = "Identifiant")
            @PathVariable Long id) {

        equipementService.deleteById(id);
    }
}
