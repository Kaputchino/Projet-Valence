package org.example;

import java.util.Random;

public class Party {
    public String nom;
    public String color;

    public Party(String nom) {
        this.nom = nom;
        this.color = setColorRandomly();
    }

    private String setColorRandomly() {
        Random obj = new Random();
        int rand_num = obj.nextInt(0xffffff + 1);
// format it as hexadecimal string and print
        return String.format("#%06x", rand_num);
    }

    @Override
    public String toString() {
        return "Party{" +
                "nom='" + nom + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
