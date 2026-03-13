package com.zio.apis;

import com.zio.data.dto.GeneralDTO;
import com.zio.data.dto.RecipeDTO;
import com.zio.service.CreationService;
import com.zio.service.PlanService;
import com.zio.service.S3Storage;
import com.zio.util.ZioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.ArrayList;

@RestController
@RequestMapping("cdn")
/***
 * controller for cdn resource access
 */
public class CDNController {

    @Autowired
    S3Storage s3Storage;

    @GetMapping("images")
    public ResponseEntity<Void> getRecipeImage(@RequestParam String imgUrl) {
        String presignedUrl = s3Storage.getImgDownloadUrl(imgUrl);
        return ResponseEntity
                .status(HttpStatus.FOUND) // 302
                .location(URI.create(presignedUrl))
                .build();
    }

}
