//package com.altn72.projetasta.modele.tmp;
//
//import com.altn72.projetasta.modele.Apprenti;
//import com.altn72.projetasta.modele.MotsClef;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.annotations.OnDelete;
//import org.hibernate.annotations.OnDeleteAction;
//
//@Getter
//@Setter
//@Entity
//@Table(name = "posseder_mots_clefs", schema = "ASTA")
//public class PossederMotsClef {
//    @EmbeddedId
//    private PossederMotsClefId id;
//
//    @MapsId("idPersonne")
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JoinColumn(name = "Id_personne", nullable = false)
//    private Apprenti idPersonne;
//
//    @MapsId("nomMotClef")
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JoinColumn(name = "Nom_mot_clef", nullable = false)
//    private MotsClef nomMotClef;
//
//}