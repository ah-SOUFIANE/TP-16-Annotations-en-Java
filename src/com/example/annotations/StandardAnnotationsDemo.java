package com.example.annotations;

import java.util.ArrayList;
import java.util.List;

public class StandardAnnotationsDemo {
    
    @Deprecated(since = "1.2", forRemoval = true)
    public void ancienneMethode() {
        System.out.println("Cette méthode est obsolète");
    }
    
    @Override
    public String toString() {
        return "StandardAnnotationsDemo";
    }
    
    @SuppressWarnings("unchecked")
    public List<String> getList() {
        ArrayList<?> list = new ArrayList<>();
        return (List<String>) list;
    }
    
    public static void main(String[] args) {
        StandardAnnotationsDemo demo = new StandardAnnotationsDemo();
        
        demo.ancienneMethode();
        System.out.println(demo);
        
        List<String> liste = demo.getList();
        System.out.println("Liste créée: " + liste);
    }
}