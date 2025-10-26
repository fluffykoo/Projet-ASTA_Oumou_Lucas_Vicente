/*
 * Via cette interface nous pouvons requêter la BDD
 * en profitant des requêtes prédéfinies utilisables directement
 *
 */

package com.altn72.projetasta.modele.repository;
import com.altn72.projetasta.modele.Personne;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonneRepository extends JpaRepository<Personne,Integer> {
}