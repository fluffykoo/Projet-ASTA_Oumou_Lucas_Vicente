/*
 * Via cette interface nous pouvons requêter la BDD
 * en profitant des requêtes prédéfinies utilisables directement
 *
 */

package com.altn72.projetasta.modele.repository;
import com.altn72.projetasta.modele.Soutenance;
import com.altn72.projetasta.modele.tmp.SoutenanceId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoutenanceRepository extends JpaRepository<Soutenance,SoutenanceId> {
}