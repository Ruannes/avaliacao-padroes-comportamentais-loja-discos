package br.edu.ifpb.padroes.store.validators;

import br.edu.ifpb.padroes.customer.Customer;
import br.edu.ifpb.padroes.music.Album;

public class CreditValidator extends PurchaseValidationHandler {
    @Override
    public boolean validate(Customer customer, Album album) {
        if (customer.getCredit() < album.getPrice()) {
            System.out.println("Validation failed: Insufficient credit");
            return false;
        }
        return checkNext(customer, album);
    }
}