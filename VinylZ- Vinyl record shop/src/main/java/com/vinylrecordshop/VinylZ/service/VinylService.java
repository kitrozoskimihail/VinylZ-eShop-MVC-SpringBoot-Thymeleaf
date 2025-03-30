package com.vinylrecordshop.VinylZ.service;

import com.vinylrecordshop.VinylZ.model.Genre;
import com.vinylrecordshop.VinylZ.model.Vinyl;

import java.util.Date;
import java.util.List;


public interface VinylService {

    List<Vinyl> listAllVinyls();

    Vinyl findVinylById(Long id);

    Vinyl createVinyl(String vinylName, String price, Date releaseDate, String imageUrl, Integer stock, Genre genre, List<Long> authorIDs);

    Vinyl updateVinyl(Long id, String vinylName, String price, Date releaseDate, String imageUrl, Integer stock, Genre genre, List<Long> authorIDs);

    Vinyl deleteVinyl(Long id);

    List<Vinyl> listVinylsByNameAndAuthor(String nameSearch, Long authorId);
}
