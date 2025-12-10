package br.edu.ifpb.padroes.store.search;

import br.edu.ifpb.padroes.music.Album;
import java.util.ArrayList;
import java.util.List;

public class SearchByType implements SearchStrategy {
    @Override
    public List<Album> search(List<Album> inventory, String searchTerm) {
        List<Album> results = new ArrayList<>();
        for (Album album : inventory) {
            // Compara o nome do Enum (ex: "VINYL") com o termo de busca
            if (album.getType().name().equalsIgnoreCase(searchTerm)) {
                results.add(album);
            }
        }
        return results;
    }
}