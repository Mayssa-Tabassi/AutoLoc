package tn.esprit.autoloc_api.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc_api.dto.PaiementResponseDTO;
import tn.esprit.autoloc_api.service.IPaiementService;

import java.util.List;

@RestController
@RequestMapping("/paiements")
@RequiredArgsConstructor
public class PaiementController {

    private final IPaiementService paiementService;

    @GetMapping
    public List<PaiementResponseDTO> findAll() {
        return paiementService.findAll();
    }

    @GetMapping("/{id}")
    public PaiementResponseDTO findById(@PathVariable Long id) {
        return paiementService.findById(id);
    }
}
