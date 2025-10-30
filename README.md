<details open>
<summary><strong>README – Installation et Configuration</strong></summary>

# ASTA – Application de Suivi des Tutorats d’Apprentis

## 1. Clonage du projet

```bash
git clone -b main https://github.com/fluffykoo/Projet-ASTA_Oumou_Lucas_Vicente.git
cd Projet-ASTA_Oumou_Lucas_Vicente
```

## 2. Configuration de l’environnement

Créer un fichier `.env` à la racine du projet :

```bash
touch .env
```

Y copier le contenu suivant :

```env
DB_PASSWORD=AVNS_P2mB9863Tkc2kZ-idk1
```

Assurez-vous que votre serveur MariaDB est en marche avant de lancer l’application.

## 3. Identifiants de connexion

Un compte tuteur enseignant par défaut est préconfiguré :

| Identifiant | Mot de passe (en clair) | Détails techniques |
|--------------|--------------------------|--------------------|
| `jaugustin`  | `tuteur123`              | Le mot de passe est hashé en base (BCrypt) côté back-end. |

## 4. Lancement du projet

### Avec IntelliJ IDEA :
1. Ouvrir le projet (`File → Open → Projet-ASTA_Oumou_Lucas_Vicente`)
2. Vérifier que le JDK configuré est Java 17
3. Lancer la classe principale :

   ```
   src/main/java/com/altn72/projetasta/ProjetAstaApplication.java
   ```

### Ou en ligne de commande :

```bash
./mvnw spring-boot:run
```

Puis accéder à l’application :
[http://localhost:8080/login](http://localhost:8080/login)

## 5. Connexion à l’interface

- Identifiant : `jaugustin`
- Mot de passe : `tuteur123`

Une fois connecté, le tuteur est redirigé vers le tableau de bord ASTA :
- Liste des apprentis
- Détails, visites, évaluations et rapports
- Ajout ou modification d’un apprenti

## 6. Stack technique

| Composant | Technologie |
|------------|-------------|
| Backend | Spring Boot 3 + JPA/Hibernate |
| Frontend | Thymeleaf + Bootstrap 5 |
| Base de données | MariaDB |
| ORM | Hibernate |
| Sécurité | Spring Security (BCrypt + form login) |

## 7. Auteurs

Projet réalisé par :  
Oumou Camara, Lucas Baury, Vicente Seixas

Dernière mise à jour : Octobre 2025

</details>
<details>

<summary><strong>Rapport Technique – Projet ASTA</strong></summary>

# Rapport - Projet ASTA - Oumou CAMARA / Vicente SEIXAS / Lucas BAURY

Ajouter le **.env** à la racine du projet ou rentrer en dur le mot de passe dans application.properties pour pouvoir acceder à la base de donnée distante.

## 1. Contexte et objectifs
Le projet **ASTA (Application de Suivi des Tutorat d’Apprentis)** a été conçu dans le cadre du module de Développement Full Stack avec Java.  
L’objectif principal est de fournir un outil **centralisé et sécurisé** permettant aux **tuteurs enseignants** de suivre leurs apprentis : visualisation des missions, visites, soutenances et évaluations pédagogiques.

L’application vise à reproduire un environnement de gestion dans lequel les données sont **normalisées**, **sécurisées** et **exploitées de manière transversale** via un tableau de bord personnalisé.

---

## 2. Architecture générale

### 2.1 Organisation du code
Le projet suit une **architecture en couches claire et découplée**, organisée comme suit :

| Couche | Dossier | Rôle |
|:--|:--|:--|
| Présentation (MVC) | `src/main/java/com/altn72/projetasta/controleur` | Gère les routes web et l’intégration des vues Thymeleaf |
| Métier (Service) | `src/main/java/com/altn72/projetasta/service` | Encapsule les règles métier et la logique fonctionnelle |
| Persistance (Repository) | `src/main/java/com/altn72/projetasta/repository` | Couche d’accès aux données via Spring Data JPA |
| Sécurité | `src/main/java/com/altn72/projetasta/security` | Gestion de l’authentification et des accès utilisateurs |
| Vue | `src/main/resources/templates` | Interfaces utilisateurs Thymeleaf |

Ce découpage permet une maintenance facile.

---

### 2.2 Technologies clés
- **Spring Boot 3.5.6**
- **Thymeleaf** pour le rendu des vues
- **Spring Security (BCrypt + DaoAuthenticationProvider)**
- **Spring Data JPA / Hibernate**
- **MySQL**
- **Lombok** pour alléger les entités
- **Swagger UI** pour la documentation REST
- **Bootstrap 5 + FontAwesome** pour l’interface graphique

---

## 3. Choix et conception du modèle de données

### 3.1 Héritage JPA : `Personne` comme racine
Toutes les entités représentant des acteurs humains (`Apprenti`, `TuteurEnseignant`, `MaitreApprentissage`) héritent de la classe abstraite `Personne` :

```java
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Personne { ... }
```
*Fichier :* `src/main/java/com/altn72/projetasta/modele/Personne.java`

Ce choix d’héritage **JOINED** permet :
- de mutualiser les champs communs (nom, prénom, email, téléphone),
- d’éviter la redondance entre les tables,
- et de faciliter les jointures dans les vues et les rapports.

---

### 3.2 Apprenti : entité pivot du système
L’entité `Apprenti` (`modele/Apprenti.java`) est la plus riche :
- clé primaire partagée avec `Personne` via `@MapsId`,
- relation `@ManyToOne` vers `Entreprise`,
- `@OneToMany` vers `Visite`, `Rapport` et `Soutenance`,
- `@ManyToMany` vers `MotsClef` pour caractériser les missions.

```java
@OneToMany(mappedBy = "apprenti", cascade = CascadeType.ALL)
private List<Visite> visites;
```

Ces relations en cascade ont été choisies pour **garantir la cohérence transactionnelle** :  
supprimer un apprenti entraîne automatiquement la suppression de ses visites, rapports et soutenances.

Les mots-clés (`motsCles`) sont gérés via `@ElementCollection` pour les cas simples saisis depuis le formulaire (par exemple : *Spring Boot, API REST, et autres).

---

### 3.3 Entreprise et maître d’apprentissage
L’entité `Entreprise` (`modele/Entreprise.java`) est indépendante et reliée à plusieurs apprentis :
```java
@OneToMany(mappedBy = "entreprise")
private List<Apprenti> apprentis;
```
Ce choix permet de **naviguer rapidement** depuis une entreprise vers la liste de ses apprentis dans le tableau de bord.

Les maîtres d’apprentissage sont eux aussi des `Personne`, reliés à une ou plusieurs visites et soutenances.

---

### 3.4 Visite, Rapport, Évaluation, Soutenance
Chaque entité secondaire complète la traçabilité du suivi :
- `Visite.java` : relie tuteur, apprenti et entreprise. Le service (`VisiteService.java`) automatise l’association du **tuteur connecté** via le `SecurityContext`.
- `Rapport.java` : lie un apprenti à ses livrables.
- `EvaluationRapport.java` : rattache une note à un **triplet apprenti/rapport/tuteur**, renforçant la cohérence des données.
- `Soutenance.java` : centralise les évaluateurs et le calendrier final.

Chaque entité sensible utilise `@OnDelete(CASCADE)` pour **préserver l’intégrité référentielle** et éviter les orphelins.

---

## 4. Sécurité et authentification

*Fichier :* `security/SecurityConfig.java`  
*Service associé :* `service/TuteurEnseignantService.java`

Nous avons choisi **Spring Security avec un DaoAuthenticationProvider** :

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(auth -> auth
                    .requestMatchers("/login", "/error", "/css/**").permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin(form -> form
                    .loginPage("/login")
                    .defaultSuccessUrl("/dashboard", true)
            )
            .logout(logout -> logout.logoutSuccessUrl("/login?logout"));
    return http.build();
}
```

L’accès à **Swagger** est volontairement restreint aux utilisateurs authentifiés afin d’éviter toute exposition publique des endpoints REST.

Les mots de passe sont stockés **hachés avec BCrypt**, et les identifiants des tuteurs sont **générés automatiquement** à partir du nom et prénom.

---

## 5. Couche métier et logique applicative

### 5.1 Services dédiés
Chaque entité sensible dispose de sa couche métier dans `service/`.  
Par exemple :
- `ApprentiService.java` → gestion CRUD + mise à jour des personnes associées,
- `VisiteService.java` → ajout d’une visite liée au tuteur connecté,
- `RapportService.java` → récupération des rapports non évalués,
- `EvaluationRapportService.java` → création d’évaluations validées métier,
- `TuteurEnseignantService.java` → création et encodage des utilisateurs sécurisés.

Ce découplage assure la **réutilisabilité du code** entre les contrôleurs REST (`/api/...`) et les vues Thymeleaf.

---

## 6. Couche présentation et intégration Thymeleaf

### 6.1 Contrôleurs MVC
Les contrôleurs du package `controleur/` assurent la synchronisation entre modèle et vue :
- `DashboardControleur.java` → vue d’ensemble personnalisée du tuteur connecté (KPI, alertes, visites à venir),
- `ApprentiControleur.java` → gestion complète d’un apprenti (création, visites, évaluations, soutenances),
- `EntrepriseControleur.java` → liste et ajout des entreprises partenaires,
- `VisiteControleur.java` → gestion du calendrier de visites.

Chaque ajout (`@PostMapping`) renvoie une **redirection avec message flash** pour informer l’utilisateur du succès ou de l’erreur, par exemple :

```java
redirectAttributes.addFlashAttribute("successMessage", "Apprenti ajouté avec succès !");
return "redirect:/apprentis/" + apprenti.getId();
```

---

### 6.2 Vues Thymeleaf
Les templates sous `resources/templates` suivent une logique modulaire :
- `layout.html` → template global (barre de navigation, session utilisateur),
- `dashboard.html` → résumé des apprentis et visites à venir,
- `ajouter-apprenti.html` → formulaire dynamique avec liste d’entreprises et saisie des mots-clés,
- `apprenti-details.html` → fiche consolidée d’un apprenti avec sections Visite, Évaluation, Soutenance.

Le moteur Thymeleaf simplifie le binding des objets (`th:object`, `th:field`) tout en restant intégré à la logique Spring MVC.

---

## 7. Documentation API et tests

### 7.1 API REST
Un équivalent REST complet est disponible sous `/api/*` :
- `/api/apprentis`
- `/api/entreprises`
- `/api/visites`
- `/api/evaluations-ecole`

Swagger UI est activé en environnement de développement pour tester les endpoints sans passer par l’interface Thymeleaf.

---

## 8. Gouvernance et configuration
*Fichier :* `application.properties`

- `spring.datasource.url` → connexion MySQL distant
- `spring.jpa.hibernate.ddl-auto=update` → migration incrémentale
- `spring.mvc.hiddenmethod.filter.enabled=true` → support des `PUT` et `DELETE` dans les formulaires Thymeleaf

Les mots de passe de connexion base de données sont injectés via **variables d’environnement** (`DB_PASSWORD`), garantissant la confidentialité des identifiants.

---

## 9. Axes d’amélioration

1. **Export PDF** des fiches apprentis pour archivage administratif.
2. **Statistiques globales** sur les filières, les rapports évalués et les visites effectuées.
3. **Achivage des anciens élèves** après création d'une nouvelle année.

---

## 11. Conclusion

ASTA est désormais une application **fonctionnelle, sécurisée et extensible**.  
L’accent a été mis sur la **clarté du modèle métier**, la **cohérence relationnelle** et la **séparation des responsabilités** dans le code.

Ce socle robuste permet d’envisager potentiellement :
- une ouverture REST complète vers un front-end moderne (React, Next.js),
- l’ajout de fonctionnalités pédagogiques (suivi des notes, notifications, statistiques),
- et une exploitation réelle dans un environnement académique.

</details>