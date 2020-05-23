package pl.coderslab;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        wczytajPlik("tasks.csv");
        opcjeWyboru();
    }

    public static void opcjeWyboru() {
        String[] opcje = {"Dodaj", "Usuń", "Lista", "Wyjście"};
        System.out.println(ConsoleColors.BLUE + "Wybierz działanie:");
        for (int i = 0; i < opcje.length; i++) {
            System.out.println(ConsoleColors.BLACK + ArrayUtils.toString(opcje[i]));
        }podajWartość();
    }
    public static String[][] tablica = new String[1][1];
    int rozmiarTablicy=tablica.length;

    public static void wczytajPlik(String file) {
        Path path = Paths.get(file);
        if (Files.exists(path) == false) try {
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }


        int liczbaLinii = 0;
        try {
            liczbaLinii = Files.readAllLines(path).size();
        } catch (IOException e) {
            e.printStackTrace();
        }

        tablica = new String[liczbaLinii][3];
        try {
            List<String> linia = Files.readAllLines(path);
            for (int i = 0; i < linia.size(); i++) {
                String[] podział = linia.get(i).split(",");
                for (int j = 0; i < podział.length; i++) {
                    tablica[i][j] = podział[j];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    public static void podajWartość(){
        Scanner scanner = new Scanner(System.in);
        String wybór = StringUtils.lowerCase(scanner.nextLine());

        if (wybór.contains(StringUtils.lowerCase("Dodaj"))) {
            System.out.println(ConsoleColors.GREEN+"Dodaj"); dodaj();
        } else if (wybór.contains(StringUtils.lowerCase("Usuń"))) {
            System.out.println(ConsoleColors.RED+"Usuń"); usuń();
        } else if (wybór.contains(StringUtils.lowerCase("Lista"))){
            System.out.println(ConsoleColors.BLACK+"Lista");
            for (int i = 0; i <tablica.length ; i++) {
                System.out.println(i+":"+ Arrays.toString(tablica[i]));
            }
        } else if(wybór.contains("Wyjście")){
            System.out.println("DO ZOBACZENIA :)");
        } else System.out.println(ConsoleColors.RED+"No weż podaj właciwą wartość!");

        opcjeWyboru();
    }

    public static void dodaj(){
        tablica=Arrays.copyOf(tablica, tablica.length+1);
        Scanner scanner=new Scanner(System.in);
        System.out.println("Dodaj opis zadania:");
        String opisZadania = scanner.nextLine();
        System.out.println("Dodaj date wykonania (rrrr-mm-dd:)");
        String dataZadania = scanner.nextLine();
        System.out.println("Czy Twoje zadanie jest ważne (true/false):");
        String ważneCzyNie = scanner.nextLine();

        String[] noweZadanie= {opisZadania, dataZadania, ważneCzyNie};
        tablica[tablica.length-1]=noweZadanie;
        wyjście();
        opcjeWyboru();


    }

    public static void usuń(){
        Scanner scanner=new Scanner(System.in);
        System.out.println(ConsoleColors.RED + "USUSŃ");
        System.out.println("Podaj numer zadania do usunięcia:");
        int numerZadania=scanner.nextInt();
        tablica=ArrayUtils.remove(tablica, numerZadania);
        wyjście();
        opcjeWyboru();
    }

    public static void wyjście(){
        Path path = Paths.get("tasks.csv");
        List<String> outList = new ArrayList<>();
        for (int i = 0; i <tablica.length ; i++) {
            outList.add(ArrayUtils.toString(tablica[i]));
        }
        try {
            Files.write(path, outList);
        } catch (IOException ex) {
            System.out.println("Nie można zapisać pliku.");
        }
    }

}







