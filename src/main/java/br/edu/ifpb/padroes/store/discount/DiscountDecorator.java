package br.edu.ifpb.padroes.store.discount;

import br.edu.ifpb.padroes.customer.Customer;
import br.edu.ifpb.padroes.music.Album;

/**
 * Classe base para os Decorators.
 * Mantém uma referência para o próximo desconto da cadeia (wrapped).
 */
public abstract class DiscountDecorator implements DiscountStrategy {
    
    protected DiscountStrategy wrapped;

    public DiscountDecorator(DiscountStrategy wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public double calculate(Album album, Customer customer) {
        return wrapped.calculate(album, customer);
    }
}