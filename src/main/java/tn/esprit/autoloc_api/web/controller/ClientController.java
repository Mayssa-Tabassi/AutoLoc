
package tn.esprit.autoloc_api.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc_api.dto.ClientRequestDTO;
import tn.esprit.autoloc_api.dto.ClientResponseDTO;
import tn.esprit.autoloc_api.service.IClientService;

import java.util.List;

@Tag(name = "Clients", description = "Gestion des clients")
@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final IClientService clientService;

    // FIND ALL
    @Operation(summary = "Lister les clients")
    @ApiResponse(responseCode = "200",
            description = "Liste retournée (éventuellement vide)")
    @GetMapping
    public List<ClientResponseDTO> findAll() {
        return clientService.findAll();
    }

    // FIND BY ID
    @Operation(summary = "Consulter un client")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Opération réussie"),
            @ApiResponse(responseCode = "404",
                    description = "Ressource introuvable")
    })
    @GetMapping("/{id}")
    public ClientResponseDTO findById(
            @Parameter(description = "Identifiant")
            @PathVariable Long id) {

        return clientService.findById(id);
    }

    // CREATE
    @Operation(summary = "Créer un client")
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Opération réussie"),
            @ApiResponse(responseCode = "400",
                    description = "Données invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponseDTO create(
            @RequestBody ClientRequestDTO dto) {

        return clientService.create(dto);
    }

    // UPDATE
    @Operation(summary = "Modifier un client")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Opération réussie"),
            @ApiResponse(responseCode = "404",
                    description = "Ressource introuvable"),
            @ApiResponse(responseCode = "400",
                    description = "Données invalides")
    })
    @PutMapping("/{id}")
    public ClientResponseDTO update(
            @Parameter(description = "Identifiant")
            @PathVariable Long id,
            @RequestBody ClientRequestDTO dto) {

        return clientService.update(id, dto);
    }

    // DELETE
    @Operation(summary = "Supprimer un client")
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

        clientService.deleteById(id);
    }
}
