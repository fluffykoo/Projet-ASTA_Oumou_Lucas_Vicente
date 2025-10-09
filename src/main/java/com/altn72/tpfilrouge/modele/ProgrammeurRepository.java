/**
 * Via cette interface nous pouvons requêter la BDD
 * en profitant des requêtes prédéfinies utilisables directement
 *
 */

package com.altn72.tpfilrouge.modele;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgrammeurRepository extends JpaRepository<Programmeur, Integer> {
}