package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Circo {
    public String nom_dpt;
    public String code_dpt;
    public String id;
    public String nom_region;
    public int num_circ;
    public String code_reg;
    public String coordinate;
    public Connection cn;
    public Circo(String nom_dpt, String code_dpt, String id, String nom_region, int num_circ, String code_reg, String coordinate) {
        this.nom_dpt = nom_dpt;
        this.code_dpt = code_dpt;
        this.id = id;
        this.nom_region = nom_region;
        this.num_circ = num_circ;
        this.code_reg = code_reg;
        this.coordinate = coordinate;
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

    @Override
    public String toString() {
        return "Circo{" +
                "nom_dpt='" + nom_dpt + '\'' +
                ", code_dpt='" + code_dpt + '\'' +
                ", id=" + id +
                ", nom_region='" + nom_region + '\'' +
                ", num_circ=" + num_circ +
                ", code_reg=" + code_reg +
                ", coordinate='" + coordinate + '\'' +
                '}';
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

}
