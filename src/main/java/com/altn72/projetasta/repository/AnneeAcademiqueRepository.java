package com.altn72.projetasta.repository;

import com.altn72.projetasta.modele.AnneeAcademique;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AnneeAcademiqueRepository extends JpaRepository<AnneeAcademique, Integer> {

    Optional<AnneeAcademique> findByEnCoursTrue();

    Optional<AnneeAcademique> findByCode(String code);
}