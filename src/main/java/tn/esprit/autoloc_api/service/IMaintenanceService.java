
package tn.esprit.autoloc_api.service;

import tn.esprit.autoloc_api.domain.Maintenance;
import java.util.List;

public interface IMaintenanceService {

    Maintenance add(Maintenance maintenance);

    Maintenance findById(Long id);

    List<Maintenance> findAll();

    Maintenance update(Long id, Maintenance maintenance);

    void delete(Long id);
}
