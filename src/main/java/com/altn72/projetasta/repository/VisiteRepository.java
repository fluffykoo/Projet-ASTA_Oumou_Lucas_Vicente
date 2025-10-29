/*
 * Via cette interface nous pouvons requêter la BDD
 * en profitant des requêtes prédéfinies utilisables directement
 *
 */

package com.altn72.projetasta.repository;
import com.altn72.projetasta.modele.Visite;
import com.altn72.projetasta.modele.tmp.VisiteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VisiteRepository extends JpaRepository<Visite, Integer> {
        @Query("""
           SELECT COUNT(v) 
           FROM Visite v 
           WHERE v.tuteurEnseignant.id = :idTuteur 
           AND v.dateVisite > CURRENT_DATE
           """)
        long countVisitesPlanifieesByTuteur(@Param("idTuteur") Integer idTuteur);

        @Query("""
           SELECT v 
           FROM Visite v 
           WHERE v.tuteurEnseignant.id = :idTuteur 
           AND v.dateVisite > CURRENT_DATE 
           ORDER BY v.dateVisite ASC
           """)
        List<Visite> findVisitesAVenirByTuteur(@Param("idTuteur") Integer idTuteur);
    }

