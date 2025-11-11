package br.edu.ifpb.padroes.music;

import java.time.LocalDate;

public class Album {

    private String title;
    private String artist;
    private MediaType type; // "CD", "VINYL", "DIGITAL"
    private double price;
    private LocalDate releaseDate;
    private AgeRestriction ageRestriction;
    private String genre;
    private int stock;

    public Album(String title, String artist, MediaType type, double price, LocalDate releaseDate, AgeRestriction ageRestriction, String genre, int stock) {
        this.title = title;
        this.artist = artist;
        this.type = type;
        this.price = price;
        this.releaseDate = releaseDate;
        this.ageRestriction = ageRestriction;
        this.genre = genre;
        this.stock = stock;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public MediaType getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public int getStock() {
        return stock;
    }

    public void decreaseStock() {
        this.stock--;
    }

    public String getFormattedName() {
        return String.format("%s - %s (%s)", this.getTitle(), this.getArtist(), this.getReleaseDate().getYear());
    }

    public AgeRestriction getAgeRestriction() {
        return ageRestriction;
    }
}
