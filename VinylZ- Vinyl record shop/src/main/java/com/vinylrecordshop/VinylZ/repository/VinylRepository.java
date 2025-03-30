package com.vinylrecordshop.VinylZ.repository;

import com.vinylrecordshop.VinylZ.model.Author;
import com.vinylrecordshop.VinylZ.model.Vinyl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VinylRepository extends JpaRepository<Vinyl, Long> {

    List<Vinyl> findAllByVinylNameLikeAndAuthorsContaining(String nameLikePattern, Author author);
    List<Vinyl> findAllByVinylNameLike(String nameLikePattern);
    List<Vinyl> findAllByAuthorsContaining(Author author);
}

