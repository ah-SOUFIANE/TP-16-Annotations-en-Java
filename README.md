# TP 16 - Annotations Java

Cours : Fondamentaux et Concepts Avancés de la Programmation Java

Ce TP contient 14 étapes sur les annotations en Java : annotations standard, création d'annotations personnalisées, traitement par réflexion, annotations répétables, et un système de validation de données basé sur les annotations.

## Objectifs

- Comprendre le concept et l'utilité des annotations en Java
- Utiliser les annotations standard de Java (@Deprecated, @Override, @SuppressWarnings)
- Créer des annotations personnalisées avec @Retention et @Target
- Traiter les annotations à l'exécution avec la réflexion (Class, Field, Method)
- Créer des annotations répétables avec @Repeatable
- Développer une application de validation de données basée sur des annotations

## Prérequis

- JDK 11 ou supérieur
- Un IDE (IntelliJ IDEA, Eclipse, etc.)
- Connaissances de base en Java : classes, interfaces, héritage

## Compilation et exécution

Le projet s'appelle `AnnotationsLab`, avec les classes réparties entre les packages `com.example.annotations` et `com.example.annotations.validation`.

```bash
cd src
javac com/example/annotations/*.java
java com.example.annotations.StandardAnnotationsDemo
```

## Étape 1 : Création du projet

Créer un projet Java nommé `AnnotationsLab` avec un package `com.example.annotations`.

## Étape 2 : Exploration des annotations standard

Illustrer les trois annotations standard de Java : `@Deprecated`, `@Override` et `@SuppressWarnings`.

```java
@Deprecated
public void ancienneMethode() {
    System.out.println("Cette méthode est obsolète");
}

@Override
public String toString() {
    return "StandardAnnotationsDemo";
}
```

**Exercice 2** : enrichir `@Deprecated` avec `since` et `forRemoval` (Java 9+) :

```java
@Deprecated(since = "1.2", forRemoval = true)
public void ancienneMethode() { ... }
```

Classe : `StandardAnnotationsDemo.java`

## Étape 3 : Création d'une annotation simple

Définir une annotation personnalisée `@Author` applicable sur une classe.

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Author {
    String name();
    String date();
}
```

`@Retention(RUNTIME)` rend l'annotation lisible par réflexion ; `@Target(TYPE)` la restreint aux classes/interfaces/enums.

Classe : `Author.java`

## Étape 4 : Utilisation de l'annotation personnalisée

Appliquer `@Author` à une classe `AnnotatedClass`.

```java
@Author(name = "John Doe", date = "2023-06-15")
public class AnnotatedClass {
    public void afficherInfo() {
        System.out.println("Classe annotée avec @Author");
    }
}
```

**Exercice 3** : créer une annotation `@Version` avec un élément `value` de type `double` et l'appliquer à `AnnotatedClass`.

Classe : `AnnotatedClass.java`

## Étape 5 : Accès aux annotations par réflexion

Créer `AnnotationProcessor` qui lit les annotations `@Author` et `@Version` d'une classe via `isAnnotationPresent` et `getAnnotation`.

```java
public static void processClass(Class<?> clazz) {
    if (clazz.isAnnotationPresent(Author.class)) {
        Author author = clazz.getAnnotation(Author.class);
        System.out.println("Auteur: " + author.name());
        System.out.println("Date: " + author.date());
    }
}
```

Classe : `AnnotationProcessor.java`

## Étape 6 : Création d'annotations pour les méthodes

Définir `@MethodInfo`, applicable aux méthodes, avec des éléments par défaut.

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MethodInfo {
    String description();
    String[] tags() default {};
    int revision() default 1;
}
```

Classe : `MethodInfo.java`, modification de `AnnotatedClass.java`

## Étape 7 : Traitement des annotations de méthodes

Étendre `AnnotationProcessor.processClass` pour parcourir `clazz.getDeclaredMethods()` et lire `@MethodInfo` sur chacune.

```java
Method[] methods = clazz.getDeclaredMethods();
for (Method method : methods) {
    if (method.isAnnotationPresent(MethodInfo.class)) {
        MethodInfo methodInfo = method.getAnnotation(MethodInfo.class);
        System.out.println("Description: " + methodInfo.description());
    }
}
```

Classe : `AnnotationProcessor.java` (mise à jour)

## Étape 8 : Création d'annotations répétables

Définir une annotation `@Bug` répétable via `@Repeatable(Bugs.class)`, avec son annotation-conteneur `@Bugs`.

```java
@Repeatable(Bugs.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Bug {
    int id();
    String description();
    String status() default "OPEN";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Bugs {
    Bug[] value();
}
```

Classe : `Bug.java`

## Étape 9 : Utilisation d'annotations répétables

Appliquer plusieurs fois `@Bug` sur une même classe.

```java
@Bug(id = 1001, description = "Erreur d'affichage", status = "FIXED")
@Bug(id = 1002, description = "Problème de performance")
public class BuggyClass { ... }
```

Classe : `BuggyClass.java`

## Étape 10 : Traitement d'annotations répétables

Lire toutes les occurrences de `@Bug` avec `getAnnotationsByType`.

```java
public static void processClassWithBugs(Class<?> clazz) {
    Bug[] bugs = clazz.getAnnotationsByType(Bug.class);
    for (Bug bug : bugs) {
        System.out.println("ID: " + bug.id() + " — " + bug.description());
    }
}
```

Classe : `AnnotationProcessor.java` (mise à jour)

## Étape 11 : Création d'annotations de validation

Définir trois annotations de champ pour un mini-framework de validation : `@NotNull`, `@Length` et `@Range`.

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Length {
    int min() default 0;
    int max() default Integer.MAX_VALUE;
    String message() default "La longueur doit être entre {min} et {max}";
}
```

Classes : `NotNull.java`, `Length.java`, `Range.java`

## Étape 12 : Création d'une classe à valider

Annoter les champs d'une classe `Utilisateur` avec `@NotNull`, `@Length` et `@Range`.

```java
public class Utilisateur {
    @NotNull
    @Length(min = 3, max = 50)
    private String nom;

    @NotNull
    @Length(min = 5, max = 100)
    private String email;

    @Range(min = 18, max = 120)
    private int age;
}
```

Classe : `Utilisateur.java`

## Étape 13 : Création d'un validateur

Parcourir les champs d'un objet par réflexion (`getDeclaredFields`) et appliquer les règles selon les annotations présentes, en construisant la liste des messages d'erreur.

```java
public static List<String> valider(Object obj) {
    List<String> erreurs = new ArrayList<>();
    for (Field field : obj.getClass().getDeclaredFields()) {
        field.setAccessible(true);
        if (field.isAnnotationPresent(NotNull.class)) {
            if (field.get(obj) == null) {
                erreurs.add(field.getName() + ": " + field.getAnnotation(NotNull.class).message());
            }
        }
        // ... Length et Range suivent le même principe
    }
    return erreurs;
}
```

Classe : `Validateur.java`

## Étape 14 : Test de la validation

Valider un utilisateur correct et un utilisateur invalide, et afficher les erreurs détectées.

Résultat attendu (extrait) :

```
Validation de l'utilisateur valide:
Aucune erreur trouvée

Validation de l'utilisateur invalide:
- nom: La longueur doit être entre 3 et 50
- email: Le champ ne peut pas être null
- age: La valeur doit être entre 18 et 120
```

Classe : `ValidationTest.java`

## Exercices supplémentaires (non implémentés dans ce dépôt)

- **@Loggable** : annotation de méthode qui enregistre l'heure d'appel, les paramètres et la valeur de retour
- **@RequiresRole** : annotation de méthode qui vérifie le rôle de l'utilisateur avant exécution
- **@ConfigValue** : annotation de champ qui injecte une valeur lue depuis un fichier de propriétés

## Structure du projet

```
AnnotationsLab/
├── src/
│   └── com/example/annotations/
│       ├── StandardAnnotationsDemo.java
│       ├── Author.java
│       ├── Version.java
│       ├── AnnotatedClass.java
│       ├── AnnotationProcessor.java
│       ├── MethodInfo.java
│       ├── Bug.java
│       ├── BuggyClass.java
│       └── validation/
│           ├── NotNull.java
│           ├── Length.java
│           ├── Range.java
│           ├── Utilisateur.java
│           ├── Validateur.java
│           └── ValidationTest.java
├── README.md
└── videos/
    └── demo.mp4
```

## Concepts mobilisés

- Annotations standard (@Deprecated, @Override, @SuppressWarnings)
- Méta-annotations @Retention et @Target pour définir une annotation personnalisée
- Lecture d'annotations par réflexion (isAnnotationPresent, getAnnotation, getAnnotations)
- Annotations répétables (@Repeatable, getAnnotationsByType)
- Réflexion sur les champs (getDeclaredFields, setAccessible, get/getInt)
- Système de validation déclaratif basé sur les annotations

## Démo vidéo

Une seule vidéo montre l'exécution des 14 étapes, dans l'ordre.

[Voir la démo vidéo](videos/demo.mp4)

## Auteur

Soufiane Ait Hmad — TP16 Java, ENS Marrakech
