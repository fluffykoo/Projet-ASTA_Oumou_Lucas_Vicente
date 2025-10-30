package com.altn72.projetasta.service;

import com.altn72.projetasta.modele.Personne;
import com.altn72.projetasta.repository.PersonneRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonneService {

    private final PersonneRepository personneRepository;

    public PersonneService(PersonneRepository personneRepository) {
        this.personneRepository = personneRepository;
    }

    // Récupérer toutes les personnes
    public List<Personne> getPersonnes() {
        return personneRepository.findAll();
    }

    // Récupérer une personne par son ID
    public Optional<Personne> getUnePersonne(Integer idPersonne) {
        Optional<Personne> personne = personneRepository.findById(idPersonne);
        return Optional.ofNullable(
                personne.orElseThrow(() ->
                        new IllegalStateException("La personne dont l'id est " + idPersonne + " n'existe pas"))
        );
    }

    public String RecupererNomPersonne(Integer idPersonne){
        Optional<Personne> PersonneARecuperer = getUnePersonne(idPersonne);
        return PersonneARecuperer.get().getNom();
    }

    // Supprimer une personne
    @Transactional
    public void supprimerPersonne(Integer idPersonne) {
        Optional<Personne> personne = personneRepository.findById(idPersonne);

        if (personne.isPresent()) {
            personneRepository.deleteById(idPersonne);
        } else {
            throw new IllegalStateException("La personne dont l'id est " + idPersonne + " n'existe pas");
        }
    }

    // Ajouter une nouvelle personne
    @Transactional
    public void ajouterPersonne(Personne personne) {
        personneRepository.save(personne);
    }

    // Modifier une personne existante
    @Transactional
    public void modifierPersonne(Integer idPersonne, Personne personneModifiee) {
        Personne personneToModify = personneRepository.findById(idPersonne)
                .orElseThrow(() -> new IllegalStateException("La personne dont l'id est " + idPersonne + " n'existe pas"));

        BeanUtils.copyProperties(personneModifiee, personneToModify, "id");
        personneRepository.save(personneToModify);
    }
}