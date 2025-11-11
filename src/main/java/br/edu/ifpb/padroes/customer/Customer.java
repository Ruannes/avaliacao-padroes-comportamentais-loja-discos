package br.edu.ifpb.padroes.customer;

import br.edu.ifpb.padroes.music.Album;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Customer {

    private String name;
    private List<Album> purchases = new ArrayList<>();
    private List<String> interests = new ArrayList<>();
    private double credit;
    private CustomerType type;
    private LocalDate dateOfBirth;

    public Customer(String name, List<Album> purchases, List<String> interests, double credit, CustomerType type, LocalDate dateOfBirth) {
        this.name = name;
        this.purchases = purchases;
        this.interests = interests;
        this.credit = credit;
        this.type = type;
        this.dateOfBirth = dateOfBirth;
    }

    public void addInterest(String genre) {
        interests.add(genre);
    }

    public boolean isInterestedIn(String genre) {
        return interests.contains(genre);
    }

    public void addPurchase(Album album) {
        purchases.add(album);
    }

    public String getName() { return name; }
    public List<Album> getPurchases() { return purchases; }
    public double getCredit() { return credit; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public int getPurchaseCount() { return purchases.size(); }

    public CustomerType getType() {
        return type;
    }
}
