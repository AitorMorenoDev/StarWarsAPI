package org.starwars;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Management {

    static Scanner sc = new Scanner(System.in);
    private static void ShowMenu() {
        System.out.println("Choose an option:");
        System.out.println("1. Converter");
        System.out.println("2. Add character");
        System.out.println("3. Save characters");
        System.out.println("4. Show all the characters in the array");
        System.out.println("5. Character's specie");
        System.out.println("6. Show XML data");
        System.out.println("7. Exit");
        System.out.println();
    }

    public static void ExecuteMenu() throws IOException {
        ArrayList<CharData> characters = new ArrayList<>();
        Utils.loadCharacters(characters);
        int option;
        do {
            ShowMenu();
            option = sc.nextInt();
            System.out.println();
            switch (option) {
                case 1 -> Converter();
                case 2 -> AddCharacter(characters);
                case 3 -> SaveCharacters(characters);
                case 4 -> ShowCharacters(characters);
                case 5 -> CharacterSpecie();
                case 6 -> ShowXMLData();
                case 7 -> Exit();
                default -> System.out.println("Invalid option \n");
            }
        } while (option != 7);
    }

    private static void Converter() throws IOException {
        FilmData filmdata;
        boolean confirm = false;

        do {
            System.out.print("Please, enter the number of the film you want to convert: ");
            int filmCode = sc.nextInt();
            sc.nextLine();

            filmdata = Utils.getFilmData(filmCode);

            System.out.println("The film you want to convert is: " + filmdata.getTitle() + ".");
            System.out.print("Are you sure? (Y/N): ");
            String response = sc.nextLine();

            if (response.equalsIgnoreCase("Y")) {
                confirm = true;
            }

        } while (!confirm);

        System.out.println("Film converted to XML");
        String xmlFilm = Utils.ConvertToXML(filmdata);
        Utils.SaveXML(xmlFilm, "resultado.xml");
        System.out.println();
    }

    private static void AddCharacter(ArrayList<CharData> characters) {
        while (true) {
            System.out.print("Please, enter the number of the character you want to add: ");
            int numChar = sc.nextInt();
            sc.nextLine();

            CharData chardata;
            try {
                chardata = Utils.getCharData(numChar);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.println("The character you want to add is: " + chardata.getName() + ".");
            System.out.print("Are you sure? (Y/N): ");
            String confirmation = sc.nextLine();

            if (confirmation.equalsIgnoreCase("Y")) {
                boolean charExists = characters.stream().anyMatch(character -> character.getName().equals(chardata.getName()));

                if (charExists) {
                    System.out.println("The character already exists.");
                } else {
                    characters.add(chardata);
                    System.out.println("The character has been added.");
                }
            }

            System.out.print("Do you want to add another character? (Y/N): ");
            String answer = sc.nextLine();
            if (!answer.equalsIgnoreCase("Y")) {
                break;
            }
        }
    }

    private static void SaveCharacters(ArrayList<CharData> characters) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream("personajes.dat");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(characters);
            System.out.println("Characters saved at personajes.dat.");
            System.out.println();
        }
    }

    private static void ShowCharacters(ArrayList<CharData> characters) {
        System.out.println("Characters in the array: ");

        if (characters.isEmpty()) {
            System.out.println("There are no characters in the array.");
        }
        for (CharData character : characters) {
            System.out.println(character.getName());
        }
        System.out.println();
    }

    private static void CharacterSpecie() {
        System.out.print("Please, enter the number of the character you want to know his specie: ");
        int numChar = sc.nextInt();
        sc.nextLine();

        CharData chardata;
        try {
            chardata = Utils.getCharData(numChar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Species of " + chardata.getName() + ": ");
        if (chardata.getSpecies().isEmpty()) {
            System.out.println("Human");
            System.out.println();
        } else {
            for (String specie : chardata.getSpecies()) {
                try {
                    System.out.println(Utils.getSpeciesName(specie));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println();
            }
        }
    }

    private static void ShowXMLData() throws IOException {
        String xml = Utils.readXML("resultado.xml");
        System.out.println("Film: " + xml.substring(xml.indexOf("<title>") + 7, xml.indexOf("</title>")));
        System.out.println("Episode id: " + xml.substring(xml.indexOf("<episode_id>") + 12, xml.indexOf("</episode_id>")));
        System.out.println();
        System.out.println("Opening crawl: " + xml.substring(xml.indexOf("<opening_crawl>") + 15, xml.indexOf("</opening_crawl>")));
        System.out.println();
        System.out.println("Director: " + xml.substring(xml.indexOf("<director>") + 10, xml.indexOf("</director>")));
        System.out.println("Producer: " + xml.substring(xml.indexOf("<producer>") + 10, xml.indexOf("</producer>")));
        System.out.println("Release date: " + xml.substring(xml.indexOf("<release_date>") + 14, xml.indexOf("</release_date>")));
        System.out.println();

        System.out.println("Characters: ");
        Utils.printElementsXML("character", xml);
        System.out.println("Planets: ");
        Utils.printElementsXML("planet", xml);
        System.out.println("Planets: ");
        Utils.printElementsXML("planet", xml);
        System.out.println("Starships: ");
        Utils.printElementsXML("starship", xml);
        System.out.println("Vehicles: ");
        Utils.printElementsXML("vehicle", xml);
        System.out.println("Species: ");
        Utils.printElementsXML("specie", xml);

        System.out.println("Created: " + xml.substring(xml.indexOf("<created>") + 9, xml.indexOf("</created>")));
        System.out.println("Edited: " + xml.substring(xml.indexOf("<edited>") + 8, xml.indexOf("</edited>")));
        System.out.println("URL: " + xml.substring(xml.indexOf("<url>") + 5, xml.indexOf("</url>")));
        System.out.println();
    }

    private static void Exit() {
        System.out.println("Thank you for using the Star Wars API. Goodbye!");
    }
}
