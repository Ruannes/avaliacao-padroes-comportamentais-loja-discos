package br.edu.ifpb.padroes.store.discount;

import br.edu.ifpb.padroes.customer.Customer;
import br.edu.ifpb.padroes.customer.CustomerType;
import br.edu.ifpb.padroes.music.Album;

/**
 * Componente Base: Calcula o desconto inicial baseado no tipo de cliente.
 */
public class CustomerDiscount implements DiscountStrategy {

    @Override
    public double calculate(Album album, Customer customer) {
        CustomerType type = customer.getType();
        
        if (type == CustomerType.VIP) {
            return album.getPrice() * 0.20;
        } else if (type == CustomerType.PREMIUM) {
            return album.getPrice() * 0.15;
        } else {
            // REGULAR e padrão
            return album.getPrice() * 0.05;
        }
    }
}