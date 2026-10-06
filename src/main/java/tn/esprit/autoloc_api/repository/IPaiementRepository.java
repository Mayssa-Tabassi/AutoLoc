package tn.esprit.autoloc_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc_api.domain.Paiement;

public interface IPaiementRepository extends JpaRepository<Paiement, Long> {
}