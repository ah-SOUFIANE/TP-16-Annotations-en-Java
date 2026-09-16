package com.example.annotations.validation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Validateur {
    
    public static List<String> valider(Object obj) {
        List<String> erreurs = new ArrayList<>();
        Class<?> clazz = obj.getClass();
        
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            
            if (field.isAnnotationPresent(NotNull.class)) {
                try {
                    Object valeur = field.get(obj);
                    if (valeur == null) {
                        NotNull annotation = field.getAnnotation(NotNull.class);
                        erreurs.add(field.getName() + ": " + annotation.message());
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            
            if (field.isAnnotationPresent(Length.class) && field.getType() == String.class) {
                try {
                    String valeur = (String) field.get(obj);
                    if (valeur != null) {
                        Length annotation = field.getAnnotation(Length.class);
                        int min = annotation.min();
                        int max = annotation.max();
                        
                        if (valeur.length() < min || valeur.length() > max) {
                            String message = annotation.message()
                                    .replace("{min}", String.valueOf(min))
                                    .replace("{max}", String.valueOf(max));
                            erreurs.add(field.getName() + ": " + message);
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            
            if (field.isAnnotationPresent(Range.class) && 
                (field.getType() == int.class || field.getType() == Integer.class)) {
                try {
                    int valeur = field.getInt(obj);
                    Range annotation = field.getAnnotation(Range.class);
                    int min = annotation.min();
                    int max = annotation.max();
                    
                    if (valeur < min || valeur > max) {
                        String message = annotation.message()
                                .replace("{min}", String.valueOf(min))
                                .replace("{max}", String.valueOf(max));
                        erreurs.add(field.getName() + ": " + message);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return erreurs;
    }
}