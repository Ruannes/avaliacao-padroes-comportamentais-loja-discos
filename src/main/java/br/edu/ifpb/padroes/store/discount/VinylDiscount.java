package br.edu.ifpb.padroes.store.discount;

import br.edu.ifpb.padroes.customer.Customer;
import br.edu.ifpb.padroes.music.Album;
import br.edu.ifpb.padroes.music.MediaType;

/**
 * Decorator: Adiciona desconto se for Vinil antigo (< 1980).
 */
public class VinylDiscount extends DiscountDecorator {

    public VinylDiscount(DiscountStrategy wrapped) {
        super(wrapped);
    }

    @Override
    public double calculate(Album album, Customer customer) {
        double currentDiscount = super.calculate(album, customer);
        
        if (album.getType() == MediaType.VINYL && album.getReleaseDate().getYear() < 1980) {
            return currentDiscount + (album.getPrice() * 0.10);
        }
        
        return currentDiscount;
    }
}