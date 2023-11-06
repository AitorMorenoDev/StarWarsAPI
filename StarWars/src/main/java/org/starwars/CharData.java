package org.starwars;

import java.io.Serializable;
import java.util.List;

public class CharData implements Serializable {


    private final String name;
    private final List<String> species;

    public CharData(String name, List<String> species) {
        this.name = name;
        this.species = species;
    }

    public String getName() {
        return name;
    }

    public List<String> getSpecies() {
        return species;
    }

}
