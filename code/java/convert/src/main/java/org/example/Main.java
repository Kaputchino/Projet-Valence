package org.example;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static ArrayList<Circo> issue;
    public static ArrayList<Group> groupes;
    public static ArrayList<Party> parties;
    public static ArrayList<Circo> circos;

    public static void main(String[] args) throws IOException {
        Connection cn;

        final MysqlDataSource mysqlDS = new MysqlDataSource();
        mysqlDS.setURL("jdbc:mysql://localhost:3306/election?useLegacyDatetimeCode=false&serverTimezone=CET");
        mysqlDS.setUser("root");
        mysqlDS.setPassword("");
        groupes = new ArrayList<>();
        issue = new ArrayList<>();
        parties = new ArrayList<>();
        circos = new ArrayList<>();
        try {
            cn = mysqlDS.getConnection();
            readJS(cn);
            ArrayList<Circo> circoProb = new ArrayList<>();
            for(Circo circo : circos){
                if(!circo.getCandidate()){
                }
                if(circo.selected.size()==1){
                    //System.out.println(circo.num_circ+" "+circo.nom_dpt);
                }else{
                    circoProb.add(circo);

                }
            }
            writeJS("NUPES");
            writeJS("ENSEMBLE");
            writeJS("RPS");
            writeJS("UDC");
            writeJS("CRIC");
            writeJS("EAC");
            writeJS("FGR");
            writeJS("EAC");
            writeJS("FPU");
            writeJS("LRDP");
            writeJS("TUPV");
            writeJS("UPF");
            writeJS("EMP");

            writeJS("Tribune sur l’Europe");

            //System.out.println(circos.get(0).selected.get(0).jsonize());

        } catch (SQLException e) {
            System.out.println("Erreur");
            e.printStackTrace();
        }

    }
    private static void writeJS(String group){
        try {
            for(Circo circo : circos){
                circo.setSelected(group);
            }
            BufferedReader br = new BufferedReader(new FileReader("circo2.js"));
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(group+"Circo.js")));
            String str="";
            int i=1;
            while((str=br.readLine())!=null){
                if(i>4 && i<570){
                    getDepCirco(str);
                    String newStr = "";
                    String[] split = str.split("\\{");
                    Boolean content = false;
                    int j = 0;
                    Circo c = find(getDepCirco(str));
                    for(String string : split){
                        if(j==2){
                            String[] elements = string.split(",");
                            elements[2] += c.jsonizedSelected();
                            String st ="";
                            for(String el : elements){
                                st+=","+el;
                            }
                            newStr += "{"+st.substring(1);
                        }else{
                            newStr += "{"+string;

                        }
                        j++;

                    }
                    newStr = newStr.substring(1);
                    //modify line
                    //bw.write(str);
                    bw.newLine();
                    bw.write(newStr);
                }else{
                    bw.newLine();
                    bw.write(str);
                }
                i++;
            }
            bw.close();
            exportColor(group);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void readJS(Connection cn) throws IOException {
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
                    Circo circo = new Circo(param, cn);
                    circos.add(circo);
                }
            }
        }
    }

    public static String cleanString(String element){
        String[] tab = element.split("\"");
        return tab[1];
    }
    public static Circo find(PlaceHolderStringInt phsi){
        for(Circo circo1: circos){
            if(circo1.num_circ==phsi.anInt){
                if(phsi.string.equals(circo1.code_dpt)){
                    return circo1;
                }
            }
        }
        return null;
    }
    public static PlaceHolderStringInt getDepCirco(String line){
        PlaceHolderStringInt placeHolderStringInt = new PlaceHolderStringInt();
        String[] elements = line.split(":");
        placeHolderStringInt.string = elements[4].split(",")[0].split("\"")[1];
        String circ = elements[7].split("\"")[1];
        placeHolderStringInt.anInt = Integer.parseInt(circ);
        return placeHolderStringInt;
    }
    public static void exportColor(String nom){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(nom+"Color.json")));
            String str="[";
            for (Party party : parties){
                if(party.nom.toUpperCase().contains(nom.toUpperCase())){
                    str +="{\"party\": \""+party.nom+"\", \"colors\": \""+party.color+"\"},\n";
                }
            }
            str = str.substring(0,str.length()-2) + "]";
            bw.write(str);
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}