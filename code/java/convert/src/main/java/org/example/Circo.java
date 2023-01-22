package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Circo{
    public String nom_dpt;
    public String code_dpt;
    public String id;
    public String nom_region;
    public int num_circ;
    public String code_reg;
    public String coordinate;
    public Connection cn;
    public ArrayList<Candidate> selected = new ArrayList<>();
    public ArrayList<Candidate> listCandidates = new ArrayList<>();
    public Circo(String nom_dpt, String code_dpt, String id, String nom_region, int num_circ, String code_reg, String coordinate) {
        this.nom_dpt = nom_dpt;
        this.code_dpt = code_dpt;
        this.id = id;
        this.nom_region = nom_region;
        this.num_circ = num_circ;
        this.code_reg = code_reg;
        this.coordinate = coordinate;
        this.selected = new ArrayList<>();
    }

    public Circo(Connection cn) {
        this.cn = cn;
    }

    public Circo(String[] str, Connection cn){
        this.id=str[0];
        this.code_dpt=str[1];
        this.nom_dpt=str[2];
        this.nom_region=str[3];
        this.num_circ=Integer.parseInt(str[4]);
        this.code_reg=str[5];
        this.coordinate = str[6];
        this.cn = cn;
    }

    public String getNom_dpt() {
        return nom_dpt;
    }

    public void setNom_dpt(String nom_dpt) {
        this.nom_dpt = nom_dpt;
    }

    public String getCode_dpt() {
        return code_dpt;
    }

    public void setCode_dpt(String code_dpt) {
        this.code_dpt = code_dpt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom_region() {
        return nom_region;
    }

    public void setNom_region(String nom_region) {
        this.nom_region = nom_region;
    }

    public int getNum_circ() {
        return num_circ;
    }

    public void setNum_circ(int num_circ) {
        this.num_circ = num_circ;
    }

    public String getCode_reg() {
        return code_reg;
    }

    public void setCode_reg(String code_reg) {
        this.code_reg = code_reg;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public boolean upload() {
        String sql ="INSERT INTO `circonscription_desc`(`nom_dpt`, `code_dpt`, `id`, `nom_region`, `num_circ`, `code_reg`, `coordinate`) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = cn.prepareStatement(sql);
            preparedStatement.setString(1,nom_dpt);
            preparedStatement.setString(2,code_dpt);
            preparedStatement.setString(3,id);
            preparedStatement.setString(4,nom_region);
            preparedStatement.setInt(5,num_circ);
            preparedStatement.setString(6,code_reg);
            preparedStatement.setString(7,coordinate);
            return preparedStatement.execute();

        } catch (SQLException e) {
            System.out.println(this);
            Main.issue.add(this);
            System.err.println(e);
        }
        return false;
    }
    public boolean getCandidate(){
        String sql ="SELECT nuancefilles.nomMere,nuancefilles.nomFille, monde.Prénom, monde.Nom,gouv2.nuance, gouv2.voix, gouv2.voixIns,gouv2.voixExp,gouv2.siege FROM `monde` inner join unitedpartiestable on unitedpartiestable.monde_id = monde.id inner join nuancefilles on nuancefilles.nomFille = unitedpartiestable.nameParty inner join gouv2 on gouv2.nom = monde.Nom and monde.Prénom=gouv2.prenom and `monde`.`Nuance Ministère`=gouv2.nuance where monde.`Département`=? and `monde`.`Numéro Circonscription` = ?";
        try {
            PreparedStatement preparedStatement = cn.prepareStatement(sql);
            preparedStatement.setString(1,code_dpt);
            preparedStatement.setString(2,""+num_circ);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Candidate candidate = new Candidate();
                listCandidates.add(candidate);
                String mere = rs.getString(1);
                candidate.group = containGroup(mere);
                String party = rs.getString(2);
                candidate.party = containParty(party);
                candidate.prenom = rs.getString(3);
                candidate.nom = rs.getString(4);
                candidate.nuanceMin = rs.getString(5);
                candidate.voix = rs.getInt(6);
                candidate.voixIns = rs.getFloat(7);
                candidate.voixExp = rs.getFloat(8);
                String elue = rs.getString(9);
                Boolean elected = false;
                if(elue != null){
                    if(!elue.isBlank()){
                        elected = true;
                    }
                }
                candidate.siege = elected;
            }
            return true;

        } catch (SQLException e) {
            return false;
        }
    }
    public static Group containGroup(String str){
        for(Group group : Main.groupes){
            if(group.name.equals(str)){
                return group;
            }
        }
        Group group = new Group(str);
        Main.groupes.add(group);
        return group;
    }
    public static Party containParty(String str){
        for(Party party : Main.parties){
            if(party.nom.equals(str)){
                return party;
            }
        }
        Party party = new Party(str);
        Main.parties.add(party);
        return party;
    }

    @Override
    public String toString() {
        String str ="Circo{" +
                "nom_dpt='" + nom_dpt + '\'' +
                ", code_dpt='" + code_dpt + '\'' +
                ", id='" + id + '\'' +
                ", nom_region='" + nom_region + '\'' +
                ", num_circ=" + num_circ +
                ", code_reg='" + code_reg + '\'' +
                ", coordinate='" + coordinate + '\'' +
                ", listCandidates=";
                for(Candidate candidate : listCandidates){
                    str+=" \n"+candidate.toString();
                }
                str +='}';
                return  str;
    }
    public void setSelected(String str){
        selected.clear();
        for(Candidate candidate : listCandidates){
            if(candidate.group.name.equals(str)){
                selected.add(candidate);
            }
        }
    }
    public String jsonizedSelected(){
        if(selected.size()>0){
            if(selected.size()>1){
                System.out.println("trop "+this.nom_dpt+this.num_circ);
                String str=" ,\"color\": \"#000000\"";
                return str;
            }
            return selected.get(0).jsonize();
        }
        System.out.println("pas assez "+this.nom_dpt+this.num_circ);

        String str=" ,\"color\": \"#898989\"";
        return str;
    }

}
