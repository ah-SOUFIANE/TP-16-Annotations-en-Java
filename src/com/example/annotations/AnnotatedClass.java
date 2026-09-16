package com.example.annotations;

@Author(name = "John Doe", date = "2023-06-15")
@Version(1.0)
public class AnnotatedClass {
    
    @MethodInfo(
        description = "Méthode qui affiche des informations",
        tags = {"info", "affichage"},
        revision = 2
    )
    public void afficherInfo() {
        System.out.println("Classe annotée avec @Author");
    }
    
    @MethodInfo(description = "Méthode principale")
    public static void main(String[] args) {
        AnnotatedClass obj = new AnnotatedClass();
        obj.afficherInfo();
    }
}