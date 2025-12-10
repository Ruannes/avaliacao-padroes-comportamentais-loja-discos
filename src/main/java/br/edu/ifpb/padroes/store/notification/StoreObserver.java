package br.edu.ifpb.padroes.store.notification;

import br.edu.ifpb.padroes.music.Album;

/**
 * Interface Observer.
 * Define o contrato para qualquer objeto que queira ser notificado sobre eventos da loja.
 */
public interface StoreObserver {
    void update(Album album);
}