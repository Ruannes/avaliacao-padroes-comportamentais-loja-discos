package br.edu.ifpb.padroes.store.validators;

import br.edu.ifpb.padroes.customer.Customer;
import br.edu.ifpb.padroes.music.Album;

public abstract class PurchaseValidationHandler {

    private PurchaseValidationHandler next;

    // Método pra encadear os validadores (Fluent Interface)
    public PurchaseValidationHandler linkWith(PurchaseValidationHandler next) {
        this.next = next;
        return next;
    }

    // Método abstrato que cada regra concreta vai implementar
    public abstract boolean validate(Customer customer, Album album);

    // Método auxiliar pra passar pro próximo ou retornar true se for o fim da linha
    protected boolean checkNext(Customer customer, Album album) {
        if (next == null) {
            return true;
        }
        return next.validate(customer, album);
    }
}