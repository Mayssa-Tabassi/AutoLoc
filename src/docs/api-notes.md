# Atelier 5 — Spring MVC et SpringDoc : API REST, tests Postman et documentation Swagger

## A. Endpoints

Préfixe commun `/api` défini une seule fois par `server.servlet.context-path=/api`.

| Contrôleur | Verbe et route | Service appelé | Succès | Erreurs |
|---|---|---|---|---|
| AgenceController | GET /api/agences | `findAll()` | 200 | — |
| | GET /api/agences/{id} | `findById(id)` | 200 | 404 |
| | POST /api/agences | `create(agence)` | 201 | 400 |
| | PUT /api/agences/{id} | `update(id, agence)` | 200 | 404, 400 |
| | DELETE /api/agences/{id} | `deleteById(id)` | 204 | 404 |
| EmployeController | GET/GET{id}/POST/PUT/DELETE `/api/employes` | `IEmployeService` | 200/200/201/200/204 | 404, 400 |
| VehiculeController | idem `/api/vehicules` | `IVehiculeService` | 200/200/201/200/204 | 404, 400 |
| EquipementController | idem `/api/equipements` | `IEquipementService` | 200/200/201/200/204 | 404, 400 |
| ClientController | idem `/api/clients` | `IClientService` | 200/200/201/200/204 | 404, 400 |
| ReservationController | idem `/api/reservations` | `IReservationService` | 200/200/201/200/204 | 404, 400 |
| ContratController | idem `/api/contrats` (cascade vers les paiements) | `IContratService` | 200/200/201/200/204 | 404, 400 |
| MaintenanceController | idem `/api/maintenances` (`add()` / `delete()`) | `IMaintenanceService` | 200/200/201/200/204 | 404, 400 |
| PaiementController | GET /api/paiements | `findAll()` | 200 | — |
| | GET /api/paiements/{id} | `findById(id)` | 200 | 404 |
| | POST /api/paiements | (aucune méthode) | — | 405 |

Les 404 viennent de `ResourceNotFoundException`, les 400 de `IllegalArgumentException` (et d'un JSON illisible) : les contrôleurs ne les produisent jamais eux-mêmes, c'est `GlobalExceptionHandler` qui les traduit. Une violation d'intégrité (ex. supprimer une agence qui a encore des véhicules) donne un 409.

## B. Annotations Jackson

| Association | Annotation | Justification |
|---|---|---|
| `Contrat.paiements` ↔ `Paiement.contrat` | `@JsonManagedReference("contrat-paiements")` côté liste, `@JsonBackReference("contrat-paiements")` côté parent | Vraie composition (`cascade = ALL`) : les paiements se créent dans le JSON du contrat, et Jackson renseigne automatiquement `paiement.contrat` à la lecture. |
| `Agence.employes`, `Agence.vehicules` | `@JsonIgnore` | Côté inverse : l'enfant désigne son parent par un identifiant (`{"agence": {"idAgence": 1}}`). |
| `Client.reservations`, `Vehicule.reservations` | `@JsonIgnore` | Même logique ; on n'utilise **pas** `@JsonBackReference` sur `Reservation.client`, sinon la propriété serait ignorée à la lecture et le POST créerait une réservation sans client. |
| `Equipement.vehicules` | `@JsonIgnore` | Côté inverse du `@ManyToMany` (le propriétaire est `Vehicule.equipements`). |
| `Reservation.contrat` | `@JsonIgnore` | Côté inverse du `@OneToOne` (le propriétaire est `Contrat.reservation`). |

**`@JsonBackReference` vs `@JsonIgnore`** : `@JsonBackReference` omet la propriété à la sérialisation mais la *reconstruit* à la désérialisation (lien parent ↔ enfant restauré), alors que `@JsonIgnore` l'ignore dans les deux sens.

## C. Postman et Swagger

- **Postman** sert à *tester* : requêtes rejouables avec assertions. Exemple de l'atelier : `GET /api/contrats/9999` doit renvoyer 404 avec un corps JSON (statut, message, horodatage), et le scénario de cascade vérifie qu'un `POST /api/contrats` avec deux paiements les rend visibles dans `GET /api/paiements`.
- **Swagger UI** sert à *documenter et explorer* : le contrat est généré depuis le code, toujours synchronisé. Exemple : la section *Schemas* décrit `Contrat` à partir de l'entité, et `@ApiResponse(404)` documente le cas « contrat introuvable ».

## D. Anomalies SonarQube

À compléter avec **vos** résultats d'analyse (clic droit sur `web.controller` > *Analyze with SonarQube for IDE*). Points typiques à vérifier dans ce projet :

| Règle | Explication | Correction |
|---|---|---|
| Import inutilisé | `ContratController` importait deux fois `DeleteMapping` (import explicite en plus de `org.springframework.web.bind.annotation.*`). | Import redondant supprimé. |
| Entité JPA exposée par l'API | Les contrôleurs renvoient directement les entités (couplage API/base). | Provisoire, remplacé par des DTO au Chapitre 10. |

## E. Questions

1. **Composant qui reçoit toutes les requêtes** : la `DispatcherServlet`. Elle interroge le `HandlerMapping`, qui retrouve la méthode du contrôleur à partir du verbe HTTP et de l'URL.
2. **Dépendre de `IContratService`** et non du repository ni de l'implémentation : respect des couches (le contrôleur ne contient aucune logique d'accès aux données), couplage faible et possibilité de remplacer l'implémentation.
3. **201 / 204 / 404** : un POST crée une ressource (201 Created) ; un DELETE réussi n'a rien à renvoyer (204 No Content). Une ressource absente est une erreur du client, pas du serveur : `ResourceNotFoundException` est traduite en 404, pas en 500.
4. **Paiement en lecture seule** : `PaiementService` n'expose que `findAll()` et `findById()` ; les paiements sont créés via le contrat (cascade). Un `POST /api/paiements` reçoit **405 Method Not Allowed**.
5. **Risques d'exposer les entités JPA** : couplage API/base, champs internes visibles, boucles de sérialisation, proxys LAZY, modification involontaire du modèle. `Contrat.paiements` utilise `@JsonManagedReference` car c'est une composition (les paiements naissent dans le JSON du contrat) ; `Agence.vehicules` utilise `@JsonIgnore` car le véhicule est créé seul et désigne son agence par un identifiant.
