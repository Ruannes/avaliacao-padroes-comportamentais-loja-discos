package br.edu.ifpb.padroes.store.search;

import br.edu.ifpb.padroes.music.Album;
import java.util.List;

public interface SearchStrategy {
    List<Album> search(List<Album> inventory, String searchTerm);
}