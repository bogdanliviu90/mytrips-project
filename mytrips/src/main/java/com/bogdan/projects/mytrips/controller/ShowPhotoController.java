package com.bogdan.projects.mytrips.controller;

import com.bogdan.projects.mytrips.service.TripService;
import com.bogdan.projects.mytrips.service.TripServiceImp;
import com.bogdan.projects.mytrips.service.UserService;
import com.bogdan.projects.mytrips.validator.TripValidator;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class handles the IO process of an image display, after it has been saved on the disk
 *
 */
@Controller
public class ShowPhotoController {

    @Autowired
    TripService tripService;

    @Autowired
    UserService userService;

    @Autowired
    TripValidator tripValidator;

    /**
     * Method showImage links the path sent from the view with the folder where images are saved, then finds the invoked
     * photo and streams it via response servlet
     * @param response the response servlet, as an HttpServletResponse object
     * @param imageName the image file name, as a String value
     * @throws IOException if an error occurs while operating
     */
    @RequestMapping(value = "/myimages/{imageName}", method = RequestMethod.GET)
    public void showImage(HttpServletResponse response, @PathVariable String imageName) throws IOException {
        Path filePath = Paths.get(TripServiceImp.dir, imageName);
        File file = new File(filePath.toUri());
        InputStream input = new FileInputStream(file);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(input, response.getOutputStream());
    }
}
