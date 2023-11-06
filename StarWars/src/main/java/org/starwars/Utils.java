package org.starwars;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static FilmData getFilmData(int filmCode) throws IOException {
        URL filmUrl = new URL ("https://swapi.dev/api/films/" + filmCode + "/?format=json");

        try (InputStream is = filmUrl.openStream();
             InputStreamReader reader = new InputStreamReader(is)) {
            JsonObject obj = JsonParser.parseReader(reader).getAsJsonObject();

            Gson gson = new Gson();
            return gson.fromJson(obj, FilmData.class);
        }
    }
    public static String ConvertToXML(FilmData filmdata) {
        //Inside this method, I use auxiliary methods appendElement and appendList to avoid repeating code
        StringBuilder xml = new StringBuilder();

        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<").append("filmdata").append(">");
        appendElement(xml, "title", filmdata.getTitle());
        appendElement(xml, "episode_id", String.valueOf(filmdata.getEpisode_id()));
        appendElement(xml, "opening_crawl", filmdata.getOpening_crawl());
        appendElement(xml, "director", filmdata.getDirector());
        appendElement(xml, "producer", filmdata.getProducer());
        appendElement(xml, "release_date", filmdata.getRelease_date());

        appendList(xml, "characters", filmdata.getCharacters());
        appendList(xml, "planets", filmdata.getPlanets());
        appendList(xml, "starships", filmdata.getStarships());
        appendList(xml, "vehicles", filmdata.getVehicles());
        appendList(xml, "species", filmdata.getSpecies());

        appendElement(xml, "created", filmdata.getCreated());
        appendElement(xml, "edited", filmdata.getEdited());
        appendElement(xml, "url", filmdata.getUrl());
        xml.append("<").append("/filmdata").append(">");

        return xml.toString();
    }

    // Both methods below are auxiliary methods to convert the FilmData object to XML
    // (elements and arrays), just to avoid repeating code
    private static void appendElement(StringBuilder xml, String tagName, String value) {
        xml.append("<").append(tagName).append(">").append(value).append("</").append(tagName).append(">");
    }
    private static void appendList(StringBuilder xml, String listName, List<String> values) {
        xml.append("<").append(listName).append(">");
        for (String value : values) {
            appendElement(xml, listName.substring(0, listName.length() - 1), value);
        }
        xml.append("</").append(listName).append(">");
    }
    public static void SaveXML (String xml, String filename) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filename)) {
            fileWriter.write(xml);
        }
    }
    public static CharData getCharData(int charCode) throws IOException {
        URL charUrl = new URL ("https://swapi.dev/api/people/" + charCode + "/?format=json");

        try (InputStream is = charUrl.openStream();
             InputStreamReader reader = new InputStreamReader(is)) {
            JsonObject obj = JsonParser.parseReader(reader).getAsJsonObject();

            Gson gson = new Gson();
            return gson.fromJson(obj, CharData.class);
        }
    }
    public static String getSpeciesName (String url) throws IOException {
        URL speciesUrl = new URL (url);

        try (InputStream is = speciesUrl.openStream();
             InputStreamReader reader = new InputStreamReader(is)) {
            JsonObject obj = JsonParser.parseReader(reader).getAsJsonObject();

            return obj.get("name").getAsString();
        }
    }
    public static String readXML(String filename) throws IOException {
        // Method to read the XML file and return it as a String
        try (java.io.FileReader fileReader = new java.io.FileReader(filename)) {
            StringBuilder xml = new StringBuilder();
            int i;
            while ((i = fileReader.read()) != -1) {
                xml.append((char) i);
            }
            return xml.toString();
        }
    }
    public static void printElementsXML (String elementName, String xml) {
        // Auxiliary method to print the array elements of the XML, just to avoid repeating code
        int elementStart = xml.indexOf("<" + elementName + ">");
        int elementEnd = xml.indexOf("</" + elementName + "s>");

        if (elementStart != -1 && elementEnd != -1) {
            String elementsXml = xml.substring(elementStart, elementEnd);
            String[] elementsArray = elementsXml.split("</" + elementName + ">");

            for (String element : elementsArray) {
                String elementContent = element.substring(element.indexOf(">") + 1);
                System.out.println(elementContent);
            }
        } else {
            System.out.println(elementName + " not found in XML.");
        }
        System.out.println();

    }
    public static void loadCharacters(ArrayList<CharData> characters) {
        File file = new File("personajes.dat");

        if (!file.exists()) {
            // If the file doesn't exist, create it and initialize with an empty ArrayList
            try (FileOutputStream fileOutputStream = new FileOutputStream(file);
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
                objectOutputStream.writeObject(new ArrayList<CharData>());
            } catch (IOException e) {
                throw new RuntimeException("Error creating the file: " + e.getMessage());
            }
        }
            // If exists, load the characters from the file and add them to the ArrayList
        try (FileInputStream fileInputStream = new FileInputStream(file);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            characters.addAll((ArrayList<CharData>) objectInputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}