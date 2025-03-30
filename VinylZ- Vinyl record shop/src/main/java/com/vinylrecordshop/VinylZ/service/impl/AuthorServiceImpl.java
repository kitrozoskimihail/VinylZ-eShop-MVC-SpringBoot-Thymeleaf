package com.vinylrecordshop.VinylZ.service.impl;

import com.vinylrecordshop.VinylZ.model.Author;
import com.vinylrecordshop.VinylZ.model.exceptions.InvalidAuthorIdException;
import com.vinylrecordshop.VinylZ.repository.AuthorRepository;
import com.vinylrecordshop.VinylZ.service.AuthorService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    @Override
    public List<Author> listAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author findAuthorById(Long id) {
        return authorRepository.findById(id).orElseThrow(InvalidAuthorIdException::new);
    }

    @Override
    public Author createAuthor(String firstName, String lastName) {
        Author author = new Author();

        author.setFirstName(firstName);
        author.setLastName(lastName);

        return authorRepository.save(author);
    }

    @Override
    public Author updateAuthor(Long id, String firstName, String lastName) {
        Author author = findAuthorById(id);

        author.setFirstName(firstName);
        author.setLastName(lastName);

        return authorRepository.save(author);
    }

    @Override
    public Author deleteAuthor(Long id) {
        Author author = findAuthorById(id);
        authorRepository.delete(author);
        return author;
    }
}
