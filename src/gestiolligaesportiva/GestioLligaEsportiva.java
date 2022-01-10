
package gestiolligaesportiva;

import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import sun.awt.FwDispatcher;

public class GestioLligaEsportiva {
    
    //Globals
    static Scanner innerText = new Scanner(System.in);
    static String pathEquips = "files/";
    static ArrayList<String> equips = new ArrayList<String>();
    static List<ArrayList<Integer>> punts = new ArrayList<ArrayList<Integer>>();
    static int partitsJugats = 0;
    static int partitsGuanyats = 0;
    static int partitsEmpatats = 0;
    static int partitsPerduts = 0;
    static int puntsTotals = 0;
    
    //Main
    public static void main(String[] args){
        try{
            recorrerFitxers();
            showMenu();
        }catch(IOException e){
            System.out.println("Hi ha un error amb l'aplicació");
            e.printStackTrace();
        }
        
    }
    
    //Menu
    static void showMenu() throws IOException{
        boolean sortir = false;
         do {
            System.out.println("");
            System.out.println("********** PUNTUATGE DE LA LLIGA **********");
            System.out.println("/                                         /");
            System.out.println("/     1. Visualitzar equips               /");
            System.out.println("/     2. Afegir equip                     /"); //amb puntuacions
            System.out.println("/     3. Modificar equip                  /");
            System.out.println("/     4. Visualitzar dades líder/cuer     /");
            System.out.println("/                                         /");
            System.out.println("*******************************************");
            
            int opcioMenu = innerText.nextInt();

            switch (opcioMenu){
                case 100:
                    showGlobalArrys();
                    break;
                case 0:
                    sortir = true;
                    break;
                case 1:
                    veurePuntuacions();
                    break;
                case 2:
                    afegirEquips();
                    break;
                case 3:
                    modificarEquip();
                    break;
                case 4:
                    veureDades();
                    break;
                default:
                    System.out.println("--> Opció no válida, escull una de les opcions del menú. <--\n--> Per sortir premi 0 <--");
            }
        }while(!sortir);
    }
    
    //Emplenar arrays
    static void recorrerFitxers() throws FileNotFoundException, IOException{
        
        File fitxer = new File(pathEquips);
        
        if(fitxer.isDirectory()){
            File[] fitxers = fitxer.listFiles();
            if(fitxers.length == 0) {
                System.out.println("\nNo hi ha arxius per actualitzar");
            }else{
                for(int i = 0; i < fitxers.length; i++){
                    String nomEquip = fitxers[i].getName();
                    nomEquip = nomEquip.substring(0, nomEquip.lastIndexOf("."));
                    
                    recorrerTextFitxer(nomEquip,fitxers[i]);
                }
            }
        }
    }
    
    static void recorrerTextFitxer(String nomEquip, File fitxer) throws FileNotFoundException, IOException {
        
        FileReader fr =  new FileReader(fitxer);             
        BufferedReader buffer =  new BufferedReader(fr);
        String linea;
        
        while(( linea = buffer.readLine() ) != null){    
            String[] puntuacio = linea.split(":");
            insertPunts(puntuacio, nomEquip);
        }
        
        buffer.close();
        fr.close();
        
    }
    
    // Emplenar array amb punts
    static void insertPunts(String[] puntuacio, String nomEquip){
        
        if(!equips.contains(nomEquip)){
            equips.add(nomEquip);
        }
        
        partitsJugats = Integer.parseInt(puntuacio[0]);
        partitsGuanyats = Integer.parseInt(puntuacio[1]);
        partitsEmpatats = Integer.parseInt(puntuacio[2]);
        partitsPerduts = Integer.parseInt(puntuacio[3]);
        puntsTotals = Integer.parseInt(puntuacio[4]);

        ArrayList<Integer> puntEquip = new ArrayList<Integer>();
        
        for(int i = 0; i <= puntuacio.length - 1; i++){
            puntEquip.add(Integer.parseInt(puntuacio[i]));
        }
        
        //if(punts.size() != equips.size()){
            punts.add(puntEquip);
        //}
        
    }
    
    //Veure equips i info
    static void veurePuntuacions(){
            
            for(int x = 0; x < equips.size(); x++){
                System.out.println("");
                System.out.println(equips.get(x) + ":");
                System.out.print(" PJ: " + punts.get(x).get(0));
                System.out.print(" PG: " + punts.get(x).get(1));
                System.out.print(" PE: " + punts.get(x).get(2));
                System.out.print(" PP: " + punts.get(x).get(3));
                System.out.println(" Punts: " + punts.get(x).get(4));
            }
        
    }
    
    //Afegir un equip
    static void afegirEquips() throws IOException{
        
        System.out.println("Introduïr nou equip:");
        boolean continua;
        innerText.nextLine();
        String[] nouEquip = new String[5];
        
        /************ NOM *************/
            
        System.out.println("Introdueix el nom de l'equip:");
        
        String nomEquip = innerText.nextLine();
        boolean nomCorrecte = false;
        
        if(!equips.contains(nomEquip) && !nomEquip.equals("")){
            nomCorrecte = true;
        }
        
        /************ PARTITS GUANYATS *************/
        
        do{
            try {
                System.out.println("Partits guanyats (PG):");
                partitsGuanyats = innerText.nextInt();
                nouEquip[1] = Integer.toString(partitsGuanyats);
                continua = true;
            }catch (InputMismatchException ex){
                System.out.println("Només pot ingresar números enters");
                innerText.next();
                continua = false;
            }
        }while (!continua);
        
        innerText.nextLine();
        
        /************ PARTITS EMPATATS *************/
        
        do{
            try {
                System.out.println("Partits empatats (PE):");
                partitsEmpatats = innerText.nextInt();
                nouEquip[2] = Integer.toString(partitsEmpatats);
                continua = true;
            }catch (InputMismatchException ex){
                System.out.println("Només pot ingresar números enters");
                innerText.next();
                continua = false;
            }
        }while (!continua);
        
        innerText.nextLine();
        
        /************ PARTITS PERDUTS *************/
        
        do{
            try {
                System.out.println("Partits perduts (PP):");
                partitsPerduts = innerText.nextInt();
                nouEquip[3] = Integer.toString(partitsPerduts);
                continua = true;
            }catch (InputMismatchException ex){
                System.out.println("Només pot ingresar números enters");
                innerText.next();
                continua = false;
            }
        }while (!continua);
        
        innerText.nextLine();
        
        /************ PARTITS JUGATS *************/
        
        partitsJugats = partitsGuanyats + partitsEmpatats + partitsPerduts;
        nouEquip[0] = Integer.toString(partitsJugats);
        
        /************ TOTAL PUNTS *************/
        
        puntsTotals = (partitsGuanyats * 3) + (partitsEmpatats);
        nouEquip[4] = Integer.toString(puntsTotals);
        
        /** Crear l'arxiu .txt **/
        
        if(nomCorrecte){
            FileWriter fw = new FileWriter(pathEquips + nomEquip + ".txt");
            BufferedWriter bf = new BufferedWriter(fw);

            try (PrintWriter emplenarArxiu = new PrintWriter(bf)) {
                emplenarArxiu.print(partitsJugats + ":");
                emplenarArxiu.print(partitsGuanyats + ":");
                emplenarArxiu.print(partitsEmpatats + ":");
                emplenarArxiu.print(partitsPerduts + ":");
                emplenarArxiu.print(puntsTotals);
            }

            System.out.println("\nNou equip afegit:");
            System.out.println(nomEquip + ":");
            System.out.print(" PJ: " + partitsJugats);
            System.out.print(" PG: " + partitsGuanyats);
            System.out.print(" PE: " + partitsEmpatats);
            System.out.print(" PP: " + partitsPerduts);
            System.out.println(" Punts: " + puntsTotals);

            insertPunts(nouEquip, nomEquip);
            
            fw.close();
            bf.close();
            
            equips.clear();
            punts.clear();
            recorrerFitxers();
            
        }else{
            System.out.println("\nNo s'ha pogut introduïr el nou equip.\nL'equip ja existeix als registres o el camp \"nom\" està buit.");
        }
    }
    
    //Modificar un equip
    static void modificarEquip() throws IOException{
        
        innerText.nextLine();
        System.out.println("Equip per modificar:");
        String equiPerModificar = innerText.nextLine();
        boolean existeix = false;
        
        
        File fitxer = new File(pathEquips);
        
        if(fitxer.isDirectory()){
            File[] fitxers = fitxer.listFiles();
            for(int i = 0; i < fitxers.length; i++){
                String nomEquip = fitxers[i].getName();
                nomEquip = nomEquip.substring(0, nomEquip.lastIndexOf("."));

                if(nomEquip.equals(equiPerModificar)){
                    existeix = true;
                }
            }
        }
        
        if(existeix){
            System.out.println("Vols editar: " + equiPerModificar);
            modificarDades(equiPerModificar);
        }else{
            System.out.println("No existeix aquest esquip al registre");
        }
    }
    
    static void modificarDades(String equiPerModificar) throws IOException{
        
        boolean continua;
        innerText.nextLine();
        String[] equipEditat = new String[5];
        
        /************ NOM *************/
        
        String nomEquip = equiPerModificar;
        
        /************ PARTITS GUANYATS *************/
        
        do{
            try {
                System.out.println("Partits guanyats (PG):");
                partitsGuanyats = innerText.nextInt();
                equipEditat[1] = Integer.toString(partitsGuanyats);
                continua = true;
            }catch (InputMismatchException ex){
                System.out.println("Només pot ingresar números enters");
                innerText.next();
                continua = false;
            }
        }while (!continua);
        
        innerText.nextLine(); // Després de llegir un Int, s'ha d'afegir aquesta línea
        
        /************ PARTITS EMPATATS *************/
        
        do{
            try {
                System.out.println("Partits empatats (PE):");
                partitsEmpatats = innerText.nextInt();
                equipEditat[2] = Integer.toString(partitsEmpatats);
                continua = true;
            }catch (InputMismatchException ex){
                System.out.println("Només pot ingresar números enters");
                innerText.next();
                continua = false;
            }
        }while (!continua);
        
        innerText.nextLine(); // Després de llegir un Int, s'ha d'afegir aquesta línea
        
        /************ PARTITS PERDUTS *************/
        
        do{
            try {
                System.out.println("Partits perduts (PP):");
                partitsPerduts = innerText.nextInt();
                equipEditat[3] = Integer.toString(partitsPerduts);
                continua = true;
            }catch (InputMismatchException ex){
                System.out.println("Només pot ingresar números enters");
                innerText.next();
                continua = false;
            }
        }while (!continua);
        
        innerText.nextLine(); // Després de llegir un Int, s'ha d'afegir aquesta línea
        
        /************ PARTITS JUGATS *************/
        
        partitsJugats = partitsGuanyats + partitsEmpatats + partitsPerduts;
        equipEditat[0] = Integer.toString(partitsJugats);
        
        /************ TOTAL PUNTS *************/
        
        puntsTotals = (partitsGuanyats * 3) + (partitsEmpatats);
        equipEditat[4] = Integer.toString(puntsTotals);
        
        /** Modificar l'arxiu .txt **/
        
        FileWriter fw = new FileWriter(pathEquips + nomEquip + ".txt");
        BufferedWriter bf = new BufferedWriter(fw);

        try (PrintWriter emplenarArxiu = new PrintWriter(bf)) {
            emplenarArxiu.print(partitsJugats + ":");
            emplenarArxiu.print(partitsGuanyats + ":");
            emplenarArxiu.print(partitsEmpatats + ":");
            emplenarArxiu.print(partitsPerduts + ":");
            emplenarArxiu.print(puntsTotals);
        }

        System.out.println("\nEquip modificat:");
        System.out.println(nomEquip + ":");
        System.out.print(" PJ: " + partitsJugats);
        System.out.print(" PG: " + partitsGuanyats);
        System.out.print(" PE: " + partitsEmpatats);
        System.out.print(" PP: " + partitsPerduts);
        System.out.println(" Punts: " + puntsTotals);

        //insertPunts(equipEditat, nomEquip);
        equips.clear();
        punts.clear();
        recorrerFitxers();
        
        fw.close();
        bf.close();
    }
    
    //Veure el líder i el cuer de la lliga
    static void veureDades(){
        veureLider(equips,punts);
        veureCuer(equips,punts);
    }
    
    static void veureLider(ArrayList<String> equips, List<ArrayList<Integer>> punts) {
        
        int max = 0;
        String equip = null;
        
        for(int x = 0; x < equips.size(); x++){
            if(punts.get(x).get(4) > max){
                max = punts.get(x).get(4);
                equip = equips.get(x);
                partitsJugats = punts.get(x).get(0);
                partitsGuanyats = punts.get(x).get(1);
                partitsEmpatats = punts.get(x).get(2);
                partitsPerduts = punts.get(x).get(3);
            }
        }
        
        System.out.println("El líder es: " + equip + " amb " + max + " punts");
        System.out.print(" PJ: " + partitsJugats);
        System.out.print(" PG: " + partitsGuanyats);
        System.out.print(" PE: " + partitsEmpatats);
        System.out.print(" PP: " + partitsPerduts + "\n");
    }
    
    static void veureCuer(ArrayList<String> equips, List<ArrayList<Integer>> punts){
        
        int min = punts.get(0).get(4);
        String equip = equips.get(0);
        
        for(int x = 0; x < equips.size(); x++){
            if(punts.get(x).get(4) < min){
                min = punts.get(x).get(4);
                equip = equips.get(x);
                partitsJugats = punts.get(x).get(0);
                partitsGuanyats = punts.get(x).get(1);
                partitsEmpatats = punts.get(x).get(2);
                partitsPerduts = punts.get(x).get(3);
            }
        }
        
        System.out.println("El cuer es: " + equip + " amb " + min + " punts");
        System.out.print(" PJ: " + partitsJugats);
        System.out.print(" PG: " + partitsGuanyats);
        System.out.print(" PE: " + partitsEmpatats);
        System.out.print(" PP: " + partitsPerduts + "\n");
    }
    
    //Veure arrays amb totes les dades
    static void showGlobalArrys(){
        
        System.out.println(equips);
        System.out.println(punts);
    
    }
    
}
