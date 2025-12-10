package br.edu.ifpb.padroes.store;

import br.edu.ifpb.padroes.customer.Customer;
import br.edu.ifpb.padroes.customer.CustomerType;
import br.edu.ifpb.padroes.music.Album;
import br.edu.ifpb.padroes.store.discount.CustomerDiscount;
import br.edu.ifpb.padroes.store.discount.DiscountStrategy;
import br.edu.ifpb.padroes.store.discount.PopPunkDiscount;
import br.edu.ifpb.padroes.store.discount.VinylDiscount;
import br.edu.ifpb.padroes.store.notification.StoreObserver;
import br.edu.ifpb.padroes.store.search.*;
import br.edu.ifpb.padroes.store.validators.AgeValidator;
import br.edu.ifpb.padroes.store.validators.CreditValidator;
import br.edu.ifpb.padroes.store.validators.PurchaseValidationHandler;
import br.edu.ifpb.padroes.store.validators.StockValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicStore {

    private List<Album> inventory = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();

    private PurchaseValidationHandler validationChain;
    private Map<SearchType, SearchStrategy> searchStrategies = new HashMap<>();

    public MusicStore() {
        // Inicializa a Chain of Responsibility (Validação)
        this.validationChain = new StockValidator();
        this.validationChain
                .linkWith(new CreditValidator())
                .linkWith(new AgeValidator());

        // Inicializa as Estratégias de Busca (Strategy Pattern)
        searchStrategies.put(SearchType.TITLE, new SearchByTitle());
        searchStrategies.put(SearchType.ARTIST, new SearchByArtist());
        searchStrategies.put(SearchType.GENRE, new SearchByGenre());
        searchStrategies.put(SearchType.TYPE, new SearchByType());
    }

    public void addMusic(Album album) {
        inventory.add(album);
        System.out.println("Added: " + album.getTitle());
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public List<Album> searchMusic(SearchType searchType, String searchTerm) {
        SearchStrategy strategy = searchStrategies.get(searchType);
        if (strategy == null) {
            throw new IllegalArgumentException("Search strategy not implemented for: " + searchType);
        }
        return strategy.search(inventory, searchTerm);
    }

    public void purchaseMusic(Customer customer, Album album) {
        if (validatePurchase(customer, album)) {
            // Usa a nova lógica de desconto
            DiscountStrategy discountCalculator = getDiscountChain();

            double discount = discountCalculator.calculate(album, customer);
            double finalPrice = album.getPrice() - discount;

            System.out.println("Purchase: " + album.getFormattedName() + " by " + customer.getName());
            System.out.println("Original price: $" + album.getPrice());
            System.out.println("Discount: $" + discount);
            System.out.println("Final price: $" + finalPrice);

            album.decreaseStock();
            customer.addPurchase(album);

            notifyObservers(album, customer);
        } else {
            System.out.println("Purchase failed due to validation error.");
        }
    }

    // Método auxiliar para montar a cadeia de descontos
    private DiscountStrategy getDiscountChain() {
        DiscountStrategy discountCalculator = new CustomerDiscount();
        discountCalculator = new VinylDiscount(discountCalculator);
        discountCalculator = new PopPunkDiscount(discountCalculator);
        return discountCalculator;
    }

    // Esse método adapta a chamada antiga pra a nova lógica
    public double calculateDiscount(Album album, CustomerType customerType) {
        // Cria um cliente falso só com o Tipo,
        // porque a nova lógica pede um objeto Customer, mas o teste que já tem só passa o tipo.
        Customer dummyCustomer = new Customer("Dummy", null, null, 0, customerType, null);

        // Chama a nova cadeia de decorators
        return getDiscountChain().calculate(album, dummyCustomer);
    }

    private boolean validatePurchase(Customer customer, Album album) {
        return validationChain.validate(customer, album);
    }

    private void notifyObservers(Album album, Customer buyer) {
        for (StoreObserver observer : customers) {
            if (!observer.equals(buyer)) {
                observer.update(album);
            }
        }
    }

    public List<Album> getInventory() {
        return inventory;
    }
}