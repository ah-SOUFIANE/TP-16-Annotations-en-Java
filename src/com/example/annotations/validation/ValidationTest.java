package com.example.annotations.validation;

import java.util.List;

public class ValidationTest {
    
    public static void main(String[] args) {
        Utilisateur utilisateurValide = new Utilisateur("John Doe", "john.doe@example.com", 30);
        List<String> erreursValide = Validateur.valider(utilisateurValide);
        
        System.out.println("Validation de l'utilisateur valide:");
        if (erreursValide.isEmpty()) {
            System.out.println("Aucune erreur trouvée");
        } else {
            for (String erreur : erreursValide) {
                System.out.println("- " + erreur);
            }
        }
        
        Utilisateur utilisateurInvalide = new Utilisateur("Jo", null, 15);
        List<String> erreursInvalide = Validateur.valider(utilisateurInvalide);
        
        System.out.println("\nValidation de l'utilisateur invalide:");
        if (erreursInvalide.isEmpty()) {
            System.out.println("Aucune erreur trouvée");
        } else {
            for (String erreur : erreursInvalide) {
                System.out.println("- " + erreur);
            }
        }
    }
}