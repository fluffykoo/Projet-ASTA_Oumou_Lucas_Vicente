//package com.altn72.projetasta.security;
//
//import com.altn72.projetasta.modele.TuteurEnseignant;
//import com.altn72.projetasta.modele.repository.TuteurEnseignantRepository;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class TuteurDetailsService implements UserDetailsService {
//
//    private final TuteurEnseignantRepository tuteurEnseignantRepository;
//
//    public TuteurDetailsService(TuteurEnseignantRepository tuteurEnseignantRepository) {
//        this.tuteurEnseignantRepository = tuteurEnseignantRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        TuteurEnseignant tuteur = tuteurEnseignantRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("Tuteur non trouvé : " + email));
//
//        //Creation d'un utilisateur Spring Security avec les infos du tuteur
//        return User.builder()
//                .username(tuteur.getEmail())
//                .password(tuteur.getPassword())
//                .roles("TUTEUR") // un seul rôle
//                .disabled(!tuteur.isEnabled())
//                .build();
//    }
//}
package com.altn72.projetasta.security;

import com.altn72.projetasta.modele.TuteurEnseignant;
import com.altn72.projetasta.modele.repository.TuteurEnseignantRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TuteurDetailsService implements UserDetailsService {

    private final TuteurEnseignantRepository tuteurEnseignantRepository;

    public TuteurDetailsService(TuteurEnseignantRepository tuteurEnseignantRepository) {
        this.tuteurEnseignantRepository = tuteurEnseignantRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        TuteurEnseignant tuteur = tuteurEnseignantRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Tuteur non trouvé : " + email));

        // Retourne ton utilisateur personnalisé
        return new TuteurDetails(tuteur);
    }
}