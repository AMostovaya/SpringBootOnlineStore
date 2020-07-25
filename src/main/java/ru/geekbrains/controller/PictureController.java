package ru.geekbrains.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.geekbrains.model.Picture;
import ru.geekbrains.repo.PictureRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
public class PictureController {

    private final PictureRepository pictureRepository;

    public PictureController(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @GetMapping("/picture/{pictureId}")
    public void adminDownloadProductPicture(@PathVariable("pictureId") Long pictureId,
                                            HttpServletResponse response) throws IOException {

        Optional<Picture> picture = pictureRepository.findById(pictureId);
        if (picture.isPresent()) {
            response.setContentType(picture.get().getContentType());
            response.getOutputStream().write(picture.get().getPictureData().getData());
        }
    }
}
