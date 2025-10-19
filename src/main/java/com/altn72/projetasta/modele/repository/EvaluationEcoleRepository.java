package com.altn72.projetasta.modele.repository;

import com.altn72.projetasta.modele.EvaluationEcole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationEcoleRepository extends JpaRepository<EvaluationEcole, Integer> {
}