package com.altn72.projetasta.modele.repository;

import com.altn72.projetasta.modele.TuteurEnseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TuteurEnseignantRepository extends JpaRepository<TuteurEnseignant, Integer> {
}