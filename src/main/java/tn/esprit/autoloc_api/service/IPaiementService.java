
package tn.esprit.autoloc_api.service;

import java.util.List;
import tn.esprit.autoloc_api.domain.Paiement;

public interface IPaiementService {

    Paiement findById(Long id);

    List<Paiement> findAll();
}
