package org.starwars;

import java.util.List;

public class FilmData {
    private final String title, opening_crawl, director, producer, release_date, created, edited, url;
    private final int episode_id;
    private final List<String> characters, planets, starships, vehicles, species;

    public FilmData(String title, String opening_crawl, String director, String producer,
                    String release_date, String created, String edited, String url,
                    int episode_id, List<String> characters, List<String> planets, List<String> starships,
                    List<String> vehicles, List<String> species) {
        this.title = title;
        this.opening_crawl = opening_crawl;
        this.director = director;
        this.producer = producer;
        this.release_date = release_date;
        this.created = created;
        this.edited = edited;
        this.url = url;
        this.episode_id = episode_id;
        this.characters = characters;
        this.planets = planets;
        this.starships = starships;
        this.vehicles = vehicles;
        this.species = species;
    }

    public String getTitle() {
        return title;
    }

    public String getOpening_crawl() {
        return opening_crawl;
    }

    public String getDirector() {
        return director;
    }

    public String getProducer() {
        return producer;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int getEpisode_id() {
        return episode_id;
    }

    public List<String> getCharacters() {
        return characters;
    }

    public List<String> getPlanets() {
        return planets;
    }

    public List<String> getStarships() {
        return starships;
    }

    public List<String> getVehicles() {
        return vehicles;
    }

    public List<String> getSpecies() {
        return species;
    }

    public String getCreated() {
        return created;
    }

    public String getEdited() {
        return edited;
    }

    public String getUrl() {
        return url;
    }
}
