/*
 * Via cette interface nous pouvons requêter la BDD
 * en profitant des requêtes prédéfinies utilisables directement
 *
 */

package com.altn72.projetasta.repository;
import com.altn72.projetasta.modele.MotsClef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotsClefRepository extends JpaRepository<MotsClef,String> {
} 