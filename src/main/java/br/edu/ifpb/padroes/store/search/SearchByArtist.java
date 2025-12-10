package br.edu.ifpb.padroes.store.search;

import br.edu.ifpb.padroes.music.Album;
import java.util.ArrayList;
import java.util.List;

public class SearchByArtist implements SearchStrategy {
    @Override
    public List<Album> search(List<Album> inventory, String searchTerm) {
        List<Album> results = new ArrayList<>();
        for (Album album : inventory) {
            if (album.getArtist().toLowerCase().contains(searchTerm.toLowerCase())) {
                results.add(album);
            }
        }
        return results;
    }
}