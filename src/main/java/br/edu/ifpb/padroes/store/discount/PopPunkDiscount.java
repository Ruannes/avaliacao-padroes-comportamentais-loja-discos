package br.edu.ifpb.padroes.store.discount;

import br.edu.ifpb.padroes.customer.Customer;
import br.edu.ifpb.padroes.customer.CustomerType;
import br.edu.ifpb.padroes.music.Album;

/**
 * Decorator: Adiciona desconto se for Pop Punk e o cliente for VIP.
 */
public class PopPunkDiscount extends DiscountDecorator {

    public PopPunkDiscount(DiscountStrategy wrapped) {
        super(wrapped);
    }

    @Override
    public double calculate(Album album, Customer customer) {
        double currentDiscount = super.calculate(album, customer);

        if ("Pop Punk".equalsIgnoreCase(album.getGenre()) && customer.getType() == CustomerType.VIP) {
            return currentDiscount + (album.getPrice() * 0.05);
        }

        return currentDiscount;
    }
}