/*
 * Via cette interface nous pouvons requêter la BDD
 * en profitant des requêtes prédéfinies utilisables directement
 *
 */

package com.altn72.projetasta.modele.repository;
import com.altn72.projetasta.modele.Visite;
import com.altn72.projetasta.modele.VisiteId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisiteRepository extends JpaRepository<Visite,VisiteId>{
}