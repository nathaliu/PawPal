package com.example.pawpal;

public class Utilisateur {
    private long id;
    private String username;
    private String petName;
    private String species;
    private String race;
    private String gender;
    private int age;
    private String size;
    private String description;

    // Constructors
    public Utilisateur() {
    }

    public Utilisateur(String username, String petName, String species, String race, String gender, int age, String size, String description) {
        this.username = username;
        this.petName = petName;
        this.species = species;
        this.race = race;
        this.gender = gender;
        this.age = age;
        this.size = size;
        this.description = description;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}