package pl.coderslab;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateParser;
import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class TaskManager {
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
    public static String[][] tablica = {};
    int rozmiarTablicy=tablica.length;

    public static void wczytajPlik(String file) {
        Path path = Paths.get(file);
        if (Files.exists(path) == false) try {
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file2 = new File(file);
        try (Scanner scanner = new Scanner(file2)){
            List<String> strings = null;
            try {
                strings = Files.readAllLines(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int a= strings.size();
            tablica = new String[a][3];
            for (int i = 0; i < tablica.length; i++) {
                String[] linia = scanner.nextLine().split(",");
                tablica[i] = ArrayUtils.toStringArray(linia);
                }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void podajWartość(){
         Scanner scanner = new Scanner(System.in);
         String wybór = StringUtils.lowerCase(scanner.nextLine());

         if (wybór.equals(StringUtils.lowerCase("Dodaj"))) {
                System.out.println(ConsoleColors.GREEN+"Dodaj"); dodaj();
         } else if (wybór.equals(StringUtils.lowerCase("Usuń"))) {
                System.out.println(ConsoleColors.RED+"Usuń"); usuń();
         } else if (wybór.equals(StringUtils.lowerCase("Lista"))){
                lista();
         } else if(wybór.equals(StringUtils.lowerCase("Wyjście"))){
             System.out.println("DO ZOBACZENIA :)");
         } else {System.out.println(ConsoleColors.RED+"No weż podaj właciwą wartość!"); opcjeWyboru();};
    }

    public static void lista(){
        System.out.println(ConsoleColors.BLACK+"Lista");
        if (tablica.length==0) System.out.println("Brak zadań do wykonania! Możesz odpocząć!");
        for (int i = 0; i <tablica.length ; i++) {
            System.out.println(i + ":" + Arrays.toString(tablica[i]));
        }opcjeWyboru();
    }

    public static void dodaj(){
    tablica=Arrays.copyOf(tablica, tablica.length+1);
    Scanner scanner=new Scanner(System.in);
        System.out.println("Dodaj opis zadania:");
        String opisZadania = scanner.nextLine();

        System.out.println("Dodaj date wykonania (rrrr-mm-dd:)");
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        String dataZadania = scanner.nextLine();


        System.out.println("Czy Twoje zadanie jest ważne (true/false):");
        String ważneCzyNie = StringUtils.lowerCase(scanner.nextLine());
        while (!ważneCzyNie.equals(StringUtils.lowerCase("true")) && !ważneCzyNie.equals(StringUtils.lowerCase("false"))) {
            System.out.println("Wpisz: 'true' bądź 'false'"); ważneCzyNie = StringUtils.lowerCase(scanner.nextLine());
        }

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







