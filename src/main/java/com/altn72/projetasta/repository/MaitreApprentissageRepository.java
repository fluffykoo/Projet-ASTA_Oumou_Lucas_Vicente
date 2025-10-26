/*
 * Via cette interface nous pouvons requêter la BDD
 * en profitant des requêtes prédéfinies utilisables directement
 *
 */

package com.altn72.projetasta.repository;
import com.altn72.projetasta.modele.MaitreApprentissage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaitreApprentissageRepository extends JpaRepository<MaitreApprentissage, Integer> {
} 