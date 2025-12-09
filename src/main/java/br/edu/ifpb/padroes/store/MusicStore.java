package br.edu.ifpb.padroes.store;

import br.edu.ifpb.padroes.customer.Customer;
import br.edu.ifpb.padroes.music.Album;
import br.edu.ifpb.padroes.store.discount.CustomerDiscount;
import br.edu.ifpb.padroes.store.discount.DiscountStrategy;
import br.edu.ifpb.padroes.store.discount.PopPunkDiscount;
import br.edu.ifpb.padroes.store.discount.VinylDiscount;
import br.edu.ifpb.padroes.store.notification.StoreObserver;
import br.edu.ifpb.padroes.store.validators.AgeValidator;
import br.edu.ifpb.padroes.store.validators.CreditValidator;
import br.edu.ifpb.padroes.store.validators.PurchaseValidationHandler;
import br.edu.ifpb.padroes.store.validators.StockValidator;

import java.util.ArrayList;
import java.util.List;

public class MusicStore {

    private List<Album> inventory = new ArrayList<>();
    // A lista de customers atua também como lista de Observers
    private List<Customer> customers = new ArrayList<>();

    private PurchaseValidationHandler validationChain;

    public MusicStore() {
        // Inicializa a Chain of Responsibility (Validação)
        this.validationChain = new StockValidator();
        this.validationChain
                .linkWith(new CreditValidator())
                .linkWith(new AgeValidator());
    }

    public void addMusic(Album album) {
        inventory.add(album);
        System.out.println("Added: " + album.getTitle());
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public List<Album> searchMusic(SearchType searchType, String searchTerm) {
        List<Album> results = new ArrayList<>();

        // (Nota: Dá pra refatorar esse trecho com strategy pra busca tbm)
        if (searchType.equals(SearchType.TITLE)) {
            for (Album album : inventory) {
                if (album.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                    results.add(album);
                }
            }
        } else if (searchType.equals(SearchType.ARTIST)) {
            for (Album album : inventory) {
                if (album.getArtist().toLowerCase().contains(searchTerm.toLowerCase())) {
                    results.add(album);
                }
            }
        } else if (searchType.equals(SearchType.GENRE)) {
            for (Album album : inventory) {
                if (album.getGenre().toLowerCase().contains(searchTerm.toLowerCase())) {
                    results.add(album);
                }
            }
        } else if (searchType.equals(SearchType.TYPE)) {
            for (Album album : inventory) {
                if (album.getType().name().equalsIgnoreCase(searchTerm)) {
                    results.add(album);
                }
            }
        }

        return results;
    }

    public void purchaseMusic(Customer customer, Album album) {
        // 1. Validação (Chain of Responsibility)
        if (validatePurchase(customer, album)) {
            
            // 2. Cálculo de Desconto (Decorator)
            DiscountStrategy discountCalculator = new CustomerDiscount(); 
            discountCalculator = new VinylDiscount(discountCalculator);   
            discountCalculator = new PopPunkDiscount(discountCalculator); 
            
            double discount = discountCalculator.calculate(album, customer);
            double finalPrice = album.getPrice() - discount;

            // 3. Processamento da Transação
            System.out.println("Purchase: " + album.getFormattedName() + " by " + customer.getName());
            System.out.println("Original price: $" + album.getPrice());
            System.out.println("Discount: $" + discount);
            System.out.println("Final price: $" + finalPrice);

            album.decreaseStock();
            customer.addPurchase(album);

            // 4. Notificação (Observer)
            notifyObservers(album, customer);

        } else {
            System.out.println("Purchase failed due to validation error.");
        }
    }

    private boolean validatePurchase(Customer customer, Album album) {
        return validationChain.validate(customer, album);
    }

    // Método auxiliar do Observer
    private void notifyObservers(Album album, Customer buyer) {
        for (StoreObserver observer : customers) {
            // Não notificamos quem está comprando
            if (!observer.equals(buyer)) {
                observer.update(album);
            }
        }
    }

    public List<Album> getInventory() {
        return inventory;
    }
}