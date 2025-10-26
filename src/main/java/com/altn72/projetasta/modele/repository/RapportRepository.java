/*
 * Via cette interface nous pouvons requêter la BDD
 * en profitant des requêtes prédéfinies utilisables directement
 *
 */

package com.altn72.projetasta.modele.repository;
import com.altn72.projetasta.modele.Rapport;
import com.altn72.projetasta.modele.RapportId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RapportRepository extends JpaRepository<Rapport,RapportId> {
}