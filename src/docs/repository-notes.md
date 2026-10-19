
# Atelier 4 — Service, IoC et injection de dépendances

## A. Modes d'injection

L'injection de dépendances permet à Spring de fournir automatiquement les objets nécessaires à une classe.

- **Injection par attribut (`@Autowired`) :** la dépendance est injectée directement dans un attribut. Cette méthode rend les dépendances moins visibles et ne permet pas de déclarer facilement les attributs `final`.
- **Injection par constructeur :** les dépendances sont fournies au moment de la création de l'objet. Cette méthode rend les dépendances explicites, facilite les tests et permet d'utiliser des attributs `final`.

### Anomalie détectée par SonarQube

- **Fichier :** `ContratServiceImpl.java`
- **Message :** `Remove this field injection and use constructor injection instead.`
- **Cause :** le repository était injecté directement dans un attribut avec `@Autowired`.
- **Correction :** remplacer l'injection par attribut par l'injection par constructeur, en utilisant `@RequiredArgsConstructor` et un attribut `private final IContratRepository contratRepository`.

## B. Services et dépendances

Les neuf services utilisent l'injection par constructeur avec `@RequiredArgsConstructor`. Chaque implémentation déclare son repository avec `private final`, ce qui permet à Lombok de générer le constructeur nécessaire à l'injection.

| Service | Dépendance injectée |
|---|---|
| `AgenceServiceImpl` | `IAgenceRepository` |
| `EmployeServiceImpl` | `IEmployeRepository` |
| `VehiculeServiceImpl` | `IVehiculeRepository` |
| `EquipementServiceImpl` | `IEquipementRepository` |
| `ClientServiceImpl` | `IClientRepository` |
| `ReservationServiceImpl` | `IReservationRepository` |
| `ContratServiceImpl` | `IContratRepository` |
| `PaiementServiceImpl` | `IPaiementRepository` |
| `MaintenanceServiceImpl` | `IMaintenanceRepository` |

## C. Compréhension des erreurs Spring

### 1. Bean de service introuvable

**Message :** Spring ne trouve aucun bean correspondant à `IContratService`.

- **Causes possibles :** l'implémentation `ContratServiceImpl` ne porte pas `@Service`, ou son package n'est pas couvert par le component scanning.
- **Corrections :** vérifier la présence de `@Service`, le package de la classe principale Spring Boot et l'emplacement de l'implémentation.

### 2. Plusieurs beans candidats

**Message :** Spring trouve deux beans candidats, par exemple `emailNotificateur` et `smsNotificateur`, pour une même dépendance.

- **Cause :** plusieurs implémentations correspondent au type demandé et Spring ne sait pas laquelle choisir.
- **Corrections possibles :**
    - utiliser `@Primary` sur l'implémentation à privilégier par défaut ;
    - utiliser `@Qualifier` sur le paramètre du constructeur pour désigner explicitement le bean souhaité.

### 3. Dépendances circulaires

**Message :** une dépendance circulaire existe entre `clientServiceImpl` et `reservationServiceImpl`.

- **Cause :** chaque service dépend de l'autre, directement ou indirectement.
- **Correction :** revoir la conception pour supprimer la dépendance circulaire, par exemple en déplaçant la logique commune dans un troisième service. L'injection par constructeur rend ce type de cycle explicite et ne le résout pas automatiquement.

*Remarque : ces trois situations illustrent des erreurs Spring courantes. Elles ne doivent pas être présentées comme des erreurs effectivement rencontrées dans le projet si elles n'ont pas été observées dans les journaux.*

## D. SonarQube for IDE

### Anomalie 1 — Injection par attribut

- **Fichier :** `ContratServiceImpl.java`
- **Message SonarQube :** `Remove this field injection and use constructor injection instead.`
- **Cause :** l'utilisation de `@Autowired` directement sur le repository.
- **Correction :** utiliser `@RequiredArgsConstructor` et déclarer le repository en `private final`.

Cette anomalie a été observée pendant l'expérience d'injection par attribut, puis corrigée en restaurant l'injection par constructeur.

### Anomalie 2 — Import inutilisé

- **Fichier :** `ContratServiceImpl.java`
- **Message SonarQube :** `Remove this unused import 'lombok.RequiredArgsConstructor'.`
- **Cause :** l'import de Lombok était conservé alors que l'annotation `@RequiredArgsConstructor` avait été temporairement retirée.
- **Correction :** supprimer l'import lorsqu'il est inutilisé, ou le conserver lorsque l'annotation est présente.

Cette anomalie était liée à une modification temporaire. Elle ne doit pas être comptée comme une anomalie persistante si l'import est maintenant utilisé.

### Anomalie 3 — Gestion des exceptions

Le remplacement temporaire de `ResourceNotFoundException` par `RuntimeException` n'a pas produit d'alerte dans l'analyse SonarQube effectuée.

Il ne s'agit donc pas d'une anomalie SonarQube confirmée. La correction retenue consiste à conserver `ResourceNotFoundException` afin de signaler explicitement l'absence d'un contrat lors d'une recherche par identifiant.

*Bilan : deux anomalies SonarQube ont été observées pendant les expérimentations. La troisième entrée documente un choix de conception, et non une alerte détectée. Pour présenter trois anomalies détectées, il faut identifier une troisième alerte réelle dans SonarQube.*

## E. Questions de compréhension

### 1. Où se trouve le `new` de `ContratServiceImpl` et qui l'exécute ?

Dans une application Spring, le conteneur IoC crée et gère les instances des classes déclarées comme beans. Spring instancie `ContratServiceImpl` et lui fournit les dépendances nécessaires. Le code applicatif n'a donc généralement pas besoin d'écrire explicitement `new ContratServiceImpl(...)`.

### 2. Pourquoi le contrôleur dépend-il de `IContratService` plutôt que de `ContratServiceImpl` ?

Le contrôleur dépend de l'interface afin de réduire le couplage avec l'implémentation concrète. Cela permet de remplacer l'implémentation sans modifier le contrôleur, tant que le contrat défini par l'interface est respecté.

### 3. Pourquoi un service singleton doit-il rester sans état mutable lié à une requête ?

Spring utilise généralement le scope singleton pour les services. Une même instance peut donc être utilisée par plusieurs requêtes simultanément. Stocker des données temporaires propres à une requête dans des attributs modifiables du service peut provoquer des conflits entre requêtes. Les données propres à chaque opération doivent rester dans des variables locales ou dans des objets adaptés.

### 4. Pourquoi la méthode `update` charge-t-elle l'entité existante avant d'appeler `save` ?

La méthode recherche d'abord l'entité pour vérifier qu'elle existe. Elle modifie ensuite ses attributs et sauvegarde les changements. Cette approche évite de traiter une entité inexistante comme une mise à jour valide et permet de préserver les attributs qui ne doivent pas être modifiés.

### 5. Quelle est la différence entre `@Component` et `@Bean`, entre `@Primary` et `@Qualifier` ? Pourquoi `IPaiementService` est-il en lecture seule ?

- **`@Component` :** annotation placée sur une classe pour permettre à Spring de la détecter automatiquement.
- **`@Bean` :** annotation placée sur une méthode d'une classe de configuration ; l'objet retourné par cette méthode est enregistré dans le conteneur Spring.
- **`@Primary` :** indique le bean à privilégier lorsqu'il existe plusieurs candidats compatibles.
- **`@Qualifier` :** permet de sélectionner explicitement un bean particulier parmi plusieurs candidats.

`IPaiementService` est en lecture seule parce que son interface expose uniquement les opérations de consultation. Les méthodes de création, de modification ou de suppression ne doivent pas être ajoutées sans justification fonctionnelle dans le périmètre de l'atelier.