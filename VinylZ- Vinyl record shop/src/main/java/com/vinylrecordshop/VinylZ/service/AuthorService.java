package com.vinylrecordshop.VinylZ.service;

import com.vinylrecordshop.VinylZ.model.Author;
import com.vinylrecordshop.VinylZ.model.Genre;
import com.vinylrecordshop.VinylZ.model.Vinyl;

import java.util.List;

public interface AuthorService {

    List<Author> listAllAuthors();

    Author findAuthorById(Long id);

    Author createAuthor(String firstName, String lastName);

    Author updateAuthor(Long id, String firstName, String lastName);

    Author deleteAuthor(Long id);
}
