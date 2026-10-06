package tn.esprit.autoloc_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc_api.domain.Client;

public interface IClientRepository extends JpaRepository<Client, Long> {
}