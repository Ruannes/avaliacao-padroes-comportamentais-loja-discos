package br.edu.ifpb.padroes.store;

import br.edu.ifpb.padroes.customer.Customer;
import br.edu.ifpb.padroes.music.Album;
import br.edu.ifpb.padroes.store.discount.CustomerDiscount;
import br.edu.ifpb.padroes.store.discount.DiscountStrategy;
import br.edu.ifpb.padroes.store.discount.PopPunkDiscount;
import br.edu.ifpb.padroes.store.discount.VinylDiscount;
import br.edu.ifpb.padroes.store.validators.AgeValidator;
import br.edu.ifpb.padroes.store.validators.CreditValidator;
import br.edu.ifpb.padroes.store.validators.PurchaseValidationHandler;
import br.edu.ifpb.padroes.store.validators.StockValidator;

import java.util.ArrayList;
import java.util.List;

public class MusicStore {

    private List<Album> inventory = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();

    // Campo para a cadeia de responsabilidade (Validação)
    private PurchaseValidationHandler validationChain;

    public MusicStore() {
        // Inicializa a cadeia de responsabilidade
        // Ordem: Estoque -> Crédito -> Idade
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
        // 1. Executa a Chain of Responsibility para validar a compra
        if (validatePurchase(customer, album)) {
            
            // 2. Executa o Decorator para calcular o desconto
            // Base (Regra do Cliente) -> Decorator (Vinil) -> Decorator (Pop Punk)
            DiscountStrategy discountCalculator = new CustomerDiscount(); 
            discountCalculator = new VinylDiscount(discountCalculator);   
            discountCalculator = new PopPunkDiscount(discountCalculator); 
            
            double discount = discountCalculator.calculate(album, customer);
            double finalPrice = album.getPrice() - discount;

            System.out.println("Purchase: " + album.getFormattedName() + " by " + customer.getName());
            System.out.println("Original price: $" + album.getPrice());
            System.out.println("Discount: $" + discount);
            System.out.println("Final price: $" + finalPrice);

            album.decreaseStock();
            customer.addPurchase(album);

            // (Opcional) Notificação - Futuro Observer Pattern
            for (Customer c : customers) {
                if (c.isInterestedIn(album.getGenre()) && !c.equals(customer)) {
                    System.out.println("Notifying " + c.getName() + " about popular " + album.getGenre() + " purchase");
                }
            }
        } else {
            // Mensagem genérica caso a cadeia falhe silenciosamente (embora os validadores imprimam o erro)
            System.out.println("Purchase failed due to validation error.");
        }
    }

    // Delegador para a cadeia de validação
    public boolean validatePurchase(Customer customer, Album album) {
        return validationChain.validate(customer, album);
    }

    public List<Album> getInventory() {
        return inventory;
    }
}