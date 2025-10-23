package com.altn72.projetasta.modele.repository;

import com.altn72.projetasta.modele.TuteurEnseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TuteurEnseignantRepository extends JpaRepository<TuteurEnseignant, Integer> {
    Optional<TuteurEnseignant> findByEmail(String email);// sert d'id pour spring security
}