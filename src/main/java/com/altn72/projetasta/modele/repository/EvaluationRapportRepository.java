/*
 * Via cette interface nous pouvons requêter la BDD
 * en profitant des requêtes prédéfinies utilisables directement
 *
 */

package com.altn72.projetasta.modele.repository;
import com.altn72.projetasta.modele.EvaluationRapport;
import com.altn72.projetasta.modele.EvaluationRapportId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRapportRepository extends JpaRepository<EvaluationRapport, EvaluationRapportId> {
}