package org.example;

public class Candidate {
    public Group group;
    public Party party;
    public String prenom;
    public String nom;
    public String nuanceMin;
    public int voix;
    public float voixIns;
    public float voixExp;
    public boolean siege;

    public Candidate(Group group, Party party, String prenom, String nom, String nuanceMin, int voix, float voixIns, float voixExp, boolean siege) {
        this.group = group;
        this.party = party;
        this.prenom = prenom;
        this.nom = nom;
        this.nuanceMin = nuanceMin;
        this.voix = voix;
        this.voixIns = voixIns;
        this.voixExp = voixExp;
        this.siege = siege;
    }

    public Candidate() {

    }

    @Override
    public String toString() {
        return "Candidate{" +
                "group=" + group +
                ", party=" + party +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", nuanceMin='" + nuanceMin + '\'' +
                ", voix=" + voix +
                ", voixIns=" + voixIns +
                ", voixExp=" + voixExp +
                ", siege=" + siege +
                '}';
    }
}
