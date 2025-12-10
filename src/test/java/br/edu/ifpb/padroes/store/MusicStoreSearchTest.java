package br.edu.ifpb.padroes.store;

import br.edu.ifpb.padroes.music.AgeRestriction;
import br.edu.ifpb.padroes.music.Album;
import br.edu.ifpb.padroes.music.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MusicStoreSearchTest {

    private MusicStore store;

    @BeforeEach
    void setUp() {
        store = new MusicStore();

        // Populando o estoque para os testes
        store.addMusic(new Album("Thriller", "Michael Jackson", MediaType.VINYL, 100.0, LocalDate.of(1982, 11, 30), AgeRestriction.GENERAL, "Pop", 10));
        store.addMusic(new Album("Back in Black", "AC/DC", MediaType.VINYL, 120.0, LocalDate.of(1980, 7, 25), AgeRestriction.GENERAL, "Rock", 5));
        store.addMusic(new Album("Bad", "Michael Jackson", MediaType.CD, 50.0, LocalDate.of(1987, 8, 31), AgeRestriction.GENERAL, "Pop", 15));
        store.addMusic(new Album("Nevermind", "Nirvana", MediaType.CD, 60.0, LocalDate.of(1991, 9, 24), AgeRestriction.PARENTAL_ADVISORY, "Grunge", 8));
    }

    @Test
    @DisplayName("Deve encontrar álbuns por título (sem distinção entre maiúsculas e minúsculas)")
    void testSearchByTitle() {
        // Busca por parte do título, minúsculo
        List<Album> results = store.searchMusic(SearchType.TITLE, "thriller");

        assertEquals(1, results.size());
        assertEquals("Thriller", results.get(0).getTitle());
    }

    @Test
    @DisplayName("Deve encontrar vários álbuns do Artista")
    void testSearchByArtist() {
        List<Album> results = store.searchMusic(SearchType.ARTIST, "Michael Jackson");

        assertEquals(2, results.size(), "Deve encontrar 2 álbuns de Michael Jackson");
    }

    @Test
    @DisplayName("Deve encontrar álbuns do gênero")
    void testSearchByGenre() {
        List<Album> results = store.searchMusic(SearchType.GENRE, "Rock");

        assertEquals(1, results.size());
        assertEquals("Back in Black", results.get(0).getTitle());
    }

    @Test
    @DisplayName("Deve encontrar álbuns do MediaType")
    void testSearchByType() {
        List<Album> results = store.searchMusic(SearchType.TYPE, "CD");

        assertEquals(2, results.size(), "Deve encontrar 2 CDs (Bad e Nevermind)");
        assertTrue(results.stream().anyMatch(a -> a.getTitle().equals("Nevermind")));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando nada for encontrado")
    void testSearchEmptyResult() {
        List<Album> results = store.searchMusic(SearchType.TITLE, "Pagode Inexistente");
        assertTrue(results.isEmpty());
    }
}