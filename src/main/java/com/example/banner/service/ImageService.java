package com.example.banner.service;

import com.example.banner.domain.Image;
import com.example.banner.domain.User;
import com.example.banner.repos.ImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ImageService {

    @Autowired
    public ImageRepo imageRepo;

    public Page<Image> imageList(Pageable pageable, String filter){
        if (filter != null && !filter.isEmpty()){
            return imageRepo.findByName(filter, pageable);
        } else {
            return imageRepo.findAll(pageable);
        }
    }






    public void deleteImage(String name, Pageable pageable) throws IOException {

        Page<Image> byName = imageRepo.findByName(name, pageable);
        imageRepo.deleteAll(byName);
        //Files.deleteIfExists(Paths.get("/C/Users/Nic/IdeaProjects/banner/uploads/" + "/" +name));
    }


    public Page<Image> imageListForUser(Pageable pageable, User author) {
        return imageRepo.findByUser(pageable, author);
    }
}