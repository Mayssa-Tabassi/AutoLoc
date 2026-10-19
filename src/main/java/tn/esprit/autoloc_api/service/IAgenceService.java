package tn.esprit.autoloc_api.service;

import java.util.List;
import tn.esprit.autoloc_api.domain.Agence;

public interface IAgenceService {

    Agence create(Agence agence);

    Agence findById(Long id);

    List<Agence> findAll();

    Agence update(Long id, Agence agence);

    void deleteById(Long id);
}