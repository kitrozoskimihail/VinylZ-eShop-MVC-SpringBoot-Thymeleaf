package com.vinylrecordshop.VinylZ.service.impl;

import com.vinylrecordshop.VinylZ.model.Author;
import com.vinylrecordshop.VinylZ.model.Genre;
import com.vinylrecordshop.VinylZ.model.Vinyl;
import com.vinylrecordshop.VinylZ.model.exceptions.InvalidVinylIdException;
import com.vinylrecordshop.VinylZ.repository.AuthorRepository;
import com.vinylrecordshop.VinylZ.repository.VinylRepository;
import com.vinylrecordshop.VinylZ.service.VinylService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class VinylServiceImpl implements VinylService {

    private final VinylRepository vinylRepository;
    private final AuthorRepository authorRepository;

    public VinylServiceImpl(VinylRepository vinylRepository, AuthorRepository authorRepository) {
        this.vinylRepository = vinylRepository;
        this.authorRepository = authorRepository;
    }


    @Override
    public List<Vinyl> listAllVinyls() {
        return vinylRepository.findAll();
    }

    @Override
    public Vinyl findVinylById(Long id) {
        return vinylRepository.findById(id).orElseThrow(InvalidVinylIdException::new);
    }

    @Override
    public Vinyl createVinyl(String vinylName, String price, Date releaseDate, String imageUrl, Integer stock, Genre genre, List<Long> authorIDs) {
        Vinyl vinyl = new Vinyl();
        List<Author> authorsList = authorRepository.findAllById(authorIDs);

        vinyl.setVinylName(vinylName);
        vinyl.setPrice(price);
        vinyl.setReleaseDate(releaseDate);
        vinyl.setGenre(genre);
        vinyl.setStock(stock);
        vinyl.setAuthors(authorsList);
        vinyl.setImageUrl(imageUrl);

        return vinylRepository.save(vinyl);
    }

    @Override
    public Vinyl updateVinyl(Long id, String vinylName, String price, Date releaseDate, String imageUrl, Integer stock, Genre genre, List<Long> authorIDs) {
        Vinyl vinyl = findVinylById(id);
        List<Author> authorsList = authorRepository.findAllById(authorIDs);

        vinyl.setVinylName(vinylName);
        vinyl.setPrice(price);
        vinyl.setReleaseDate(releaseDate);
        vinyl.setStock(stock);
        vinyl.setImageUrl(imageUrl);
        vinyl.setGenre(genre);
        vinyl.setAuthors(authorsList);

        return vinylRepository.save(vinyl);
    }

    @Override
    public Vinyl deleteVinyl(Long id) {
        Vinyl vinyl = findVinylById(id);
        vinylRepository.delete(vinyl);

        return vinyl;
    }

    @Override
    public List<Vinyl> listVinylsByNameAndAuthor(String nameSearch, Long authorId) {
        Author author = (authorId != null) ? authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Invalid Author ID")) : null;

        String nameLikePattern = (nameSearch != null) ? "%" + nameSearch + "%" : null;

        List<Vinyl> vinyls;

        if (nameLikePattern != null && author != null) {
            vinyls = vinylRepository.findAllByVinylNameLikeAndAuthorsContaining(nameLikePattern, author);
        } else if (nameLikePattern != null) {
            vinyls = vinylRepository.findAllByVinylNameLike(nameLikePattern);
        } else if (author != null) {
            vinyls = vinylRepository.findAllByAuthorsContaining(author);
        } else {
            vinyls = vinylRepository.findAll();
        }

        return vinyls;
    }

}