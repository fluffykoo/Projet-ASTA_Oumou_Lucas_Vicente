/*
 * Via cette interface nous pouvons requêter la BDD
 * en profitant des requêtes prédéfinies utilisables directement
 *
 */

package com.altn72.projetasta.modele.repository;
import com.altn72.projetasta.modele.PossederMotsClef;
import com.altn72.projetasta.modele.PossederMotsClefId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PossederMotsClefRepository extends JpaRepository<PossederMotsClef,PossederMotsClefId> {
}