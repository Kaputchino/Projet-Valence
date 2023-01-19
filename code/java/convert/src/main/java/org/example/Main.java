package org.example;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static ArrayList<Circo> issue = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        Connection cn;
        final MysqlDataSource mysqlDS = new MysqlDataSource();
        mysqlDS.setURL("jdbc:mysql://localhost:3306/election?useLegacyDatetimeCode=false&serverTimezone=CET");
        mysqlDS.setUser("root");
        mysqlDS.setPassword("");

        try {
            cn = mysqlDS.getConnection();
            BufferedReader br = new BufferedReader(new FileReader("circo2.js"));
            String str="";
            int x = 0;
            String coordinate ="";
            ArrayList<Circo> circoArrayList = new ArrayList<>();
            while((str=br.readLine())!=null){
                //System.out.println(str);
                x++;
                if(x>=4){
                    String[] param =new String[7];

                    String[] tab = str.split(",");
                    for(int i = 0 ; i < tab.length; i++){
                        String[] elements = tab[i].split(":");
                        String element;
                        if(elements.length>1){
                            element = elements[1];
                        }else{
                            element = elements[0];
                        }
                        if(i==0){}
                        else if(i==1){
                            param[0] = cleanString(elements[2]);
                        }else if(i > 1 && i < 7){
                            element = cleanString(element);
                            param[i-1] = element;
                        }else if(i==8){
                            coordinate = elements[1];
                        }
                        else{
                            String s = tab[i].split("}")[0];
                            coordinate+=", "+s;
                        }
                    }
                    param[6] = coordinate;

                    if(param[4]!=null){
                        Circo circo = new Circo(param,cn);
                        circo.upload();
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur");
            e.printStackTrace();
        }

    }
    public static String cleanString(String element){
        String[] tab = element.split("\"");
        return tab[1];
    }
}