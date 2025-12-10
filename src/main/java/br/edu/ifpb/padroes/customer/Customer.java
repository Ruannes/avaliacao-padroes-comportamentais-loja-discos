package br.edu.ifpb.padroes.customer;

import br.edu.ifpb.padroes.music.Album;
import br.edu.ifpb.padroes.store.notification.StoreObserver;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Customer implements StoreObserver {

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

    // --- Implementação do Pattern Observer ---
    @Override
    public void update(Album album) {
        if (isInterestedIn(album.getGenre())) {
            System.out.println("Notifying " + this.name + " about popular " + album.getGenre() + " purchase");
        }
    }
    // -----------------------------------------

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