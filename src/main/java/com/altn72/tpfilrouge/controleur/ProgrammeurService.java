package com.altn72.tpfilrouge.controleur;

import com.altn72.tpfilrouge.modele.Programmeur;
import com.altn72.tpfilrouge.modele.ProgrammeurRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProgrammeurService {
    private final ProgrammeurRepository programmeurRepository;

    public ProgrammeurService(ProgrammeurRepository programmeurRepository) {
        this.programmeurRepository = programmeurRepository;
    }
    //récupère tous les programmeurs
    public List<Programmeur> afficherInfosProgrammeurs() {
        return programmeurRepository.findAll();
    }

    //récupère un programmeur par son id
    public Optional<Programmeur> getUnProgrammeur(Integer idProgrammeur) {

        Optional<Programmeur> unProgrammeur = programmeurRepository.findById(idProgrammeur);
        if (unProgrammeur.isPresent()) {
            return unProgrammeur;
        }

    throw new IllegalStateException("Ce programmeur n'existe pas");
    }
    //supprime un programmeur par son id
    public Optional<Programmeur> deleteUnProgrammeur(Integer idProgrammeur) {
        Optional<Programmeur> programmeur = programmeurRepository.findById(idProgrammeur);

        if (programmeur.isPresent()) {
            programmeurRepository.deleteById(idProgrammeur);
            return programmeur;
        }
            throw new IllegalStateException("Ce programmeur n'existe pas");
    }

    //modifie les infos d'un programmeur avec PUT
//    public Optional<Programmeur> putUnProgrammeur(Integer idProgrammeur, Programmeur programmeurModifie) {
//        Optional<Programmeur> programmeur = programmeurRepository.findById(idProgrammeur);
//
//        if (programmeur.isPresent()) {
//            Programmeur p = programmeur.get();
//            p.setNom(programmeurModifie.getNom());
//            p.setPrenom(programmeurModifie.getPrenom());
//            p.setAdresse(programmeurModifie.getAdresse());
//            p.setLangage(programmeurModifie.getLangage());
//            p.setLivre(programmeurModifie.getLivre());
//            p.setSalaire(programmeurModifie.getSalaire());
//
//            programmeurRepository.save(p);
//            return Optional.of(p);
//        }
//            throw new IllegalStateException(" Ce programmeur n'existe pas");
//    }
    @Transactional
    public void modifierProgrammeur(Integer idProgrammeur, Programmeur programmeurModifie) {
        Programmeur programmeurToModify = programmeurRepository.findById(idProgrammeur)
                .orElseThrow(); //Récupère le programmeur ou lance une exception s’il n’existe pas

        if (programmeurToModify != null) { //Sécurité supplémentaire (même si orElseThrow garantit qu’il existe)
            BeanUtils.copyProperties(programmeurModifie, programmeurToModify, "id");
            //Copie toutes les propriétés du programmeurModifie vers celui existant sauf l’ID pour ne pas écraser la clé primaire

            programmeurRepository.save(programmeurToModify);
            //Persiste les changements dans la base
        }
    }
//    public Programmeur putProgrammeur(Integer idProgrammeur, Programmeur programmeurModifie) {
//        Programmeur programmeur = programmeurRepository.findById(idProgrammeur).orElseThrow(() -> new IllegalStateException("Ce programmeur n'existe pas"));
//
//        programmeur.setNom(programmeurModifie.getNom());
//        programmeur.setPrenom(programmeurModifie.getPrenom());
//        programmeur.setAdresse(programmeurModifie.getAdresse());
//        programmeur.setLangage(programmeurModifie.getLangage());
//        programmeur.setLivre(programmeurModifie.getLivre());
//        programmeur.setSalaire(programmeurModifie.getSalaire());
//
//        return programmeurRepository.save(programmeur);
//
//    }

    //modifier les infos d'un programmeur de manière partielle
    public Optional<Programmeur> patchUnProgrammeur(Integer idProgrammeur, Programmeur programmeurModifie) {
        Optional<Programmeur> programmeur = programmeurRepository.findById(idProgrammeur);

        if (programmeur.isPresent()) {
            Programmeur p = programmeur.get();

            if (programmeurModifie.getNom() != null) p.setNom(programmeurModifie.getNom());//
            if (programmeurModifie.getPrenom() != null) p.setPrenom(programmeurModifie.getPrenom());
            if (programmeurModifie.getAdresse() != null) p.setAdresse(programmeurModifie.getAdresse());
            if (programmeurModifie.getLangage() != null) p.setLangage(programmeurModifie.getLangage());
            if (programmeurModifie.getLivre() != null) p.setLivre(programmeurModifie.getLivre());
            if (programmeurModifie.getSalaire() != null) p.setSalaire(programmeurModifie.getSalaire());

            programmeurRepository.save(p);
            return Optional.of(p);
        } else {
            throw new IllegalStateException(" Ce programmeur n'existe pas");
        }
    }
    //ajout d'un nouveau Programmeur
    public Programmeur postUnProgrammeur(Programmeur nouveauProgrammeur) {
        return programmeurRepository.save(nouveauProgrammeur);
    }
}
//    @Autowired
//    ProgrammeurRepository programmeurRepository;
//
//    public ProgrammeurService(ProgrammeurRepository programmeurRepository) {
//        this.programmeurRepository = programmeurRepository;
//    }
//    public List<Programmeur>afficherInfosProgrammeurs(){
//        return programmeurRepository.findAll();
//    }
//    public List<Programmeur> getProgrammeurs() {
//        return programmeurRepository.findAll();
//    }


