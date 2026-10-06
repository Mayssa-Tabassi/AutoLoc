package tn.esprit.autoloc_api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc_api.domain.Employe;

public interface IEmployeRepository extends JpaRepository<Employe, Long> {
}