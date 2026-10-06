package tn.esprit.autoloc_api.repository;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.autoloc_api.domain.Contrat;


public interface IContratRepository extends CrudRepository<Contrat, Long> {
}

