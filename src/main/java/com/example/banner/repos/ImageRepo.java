package com.example.banner.repos;


import com.example.banner.domain.Image;
import com.example.banner.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ImageRepo extends CrudRepository <Image, Long> {

    Page<Image> findAll(Pageable pageable);

    Page<Image> findByName(String name, Pageable pageable);

    @Query("from Image as i where i.author = :author")
    Page<Image> findByUser(Pageable pageable, @Param("author") User author);
}
