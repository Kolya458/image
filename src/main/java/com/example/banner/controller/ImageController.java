package com.example.banner.controller;


import com.example.banner.domain.Image;
import com.example.banner.domain.User;
import com.example.banner.repos.ImageRepo;
import com.example.banner.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageRepo imageRepo;

    @Value("${path.upload}")
    private String uploadPath;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }


    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/index")
    public String search(
            @RequestParam(required = false, defaultValue = "") String del,
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) throws IOException {

        if (del != null) {
            imageService.deleteImage(del, pageable);
        }

        Page<Image> page = imageService.imageList(pageable, filter);

        model.addAttribute("page", page);
        model.addAttribute("url", "/index");
        model.addAttribute("filter", filter);

        return "index";
    }


    @PostMapping("/index")
    public String add(
            @AuthenticationPrincipal User user,
            @Valid Image image,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file,
            Pageable pageable
    ) throws IOException {
        image.setAuthor(user);
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);

            model.addAttribute("image", image);
        } else {
            if (!file.isEmpty()) {
                saveFile(image, file);
                model.addAttribute("image", null);
                imageRepo.save(image);
            } else {
                model.addAttribute("fileError", "Add your banner");
            }
        }
        Page<Image> page = imageRepo.findAll(pageable);
        model.addAttribute("page", page);
        model.addAttribute("url", "/index");


        return "index";

    }

    private void saveFile(@Valid Image image, @RequestParam("file") MultipartFile file) throws IOException {

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + file.getOriginalFilename();
        file.transferTo(new File(uploadPath + "/" + resultFilename));
        image.setFilename(resultFilename);
    }


    @GetMapping("/user-images/{author}")
    public String userImages(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User author,
            Model model,
            @RequestParam(required = false) Image image,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Image> page = imageService.imageListForUser(pageable, author);

        model.addAttribute("page", page);
        model.addAttribute("image", image);
        model.addAttribute("isCurrentUser", currentUser.equals(author));
        model.addAttribute("url", "/user-images/" + author.getId());


        return "userImages";
    }

    @PostMapping("/user-images/{user}")
    public String updateImage(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            @Valid Image image,
            @RequestParam("name") String name,
            @RequestParam("file") MultipartFile file,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
            BindingResult bindingResult,
            Model model
    ) throws IOException {
        //if (image.getAuthor().equals(currentUser)) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("image", image);
        }
        if (!StringUtils.isEmpty(name)) {
            image.setName(name);
            if (!file.isEmpty()) {
                saveFile(image, file);
            }
            imageRepo.save(image);
            model.addAttribute("image", image);
        }

        Page<Image> page = imageService.imageListForUser(pageable, user);
        model.addAttribute("url", "/user-images/" + user.getId());
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        model.addAttribute("page", page);

        return "redirect:/user-images/" + user.getId();
    }
}