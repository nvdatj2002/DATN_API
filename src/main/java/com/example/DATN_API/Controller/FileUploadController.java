package com.example.DATN_API.Controller;

import com.example.DATN_API.Entity.ImageProduct;
import com.example.DATN_API.Entity.Product;
import com.example.DATN_API.Entity.ResponObject;
import com.example.DATN_API.Service.IStorageSerivce;
import com.example.DATN_API.Service.ImageProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping(path = "/api/uploadImageProduct")
public class FileUploadController {
    @Autowired
    IStorageSerivce iStorageSerivce;
    @Autowired
    ImageProductService imageProductService;

    @GetMapping()
    public ResponseEntity<List<ImageProduct>> getAll() {
        return new ResponseEntity<>(imageProductService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{fileName:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable("fileName") String fileName) {
        try {
            byte[] bytes = iStorageSerivce.readFileContent(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        } catch (Exception exception) {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    public ResponseEntity<ResponObject> uploadFile(@RequestParam("file") MultipartFile file,
                                                   @RequestParam Product idProduct) {
        String name = iStorageSerivce.storeFile(file);
        ImageProduct ImageProduct = new ImageProduct();
        ImageProduct.setProduct_image(idProduct);
        ImageProduct.setUrl(name);
        imageProductService.createImageProduct(ImageProduct);
        return new ResponseEntity<>(new ResponObject("SUCCESS", "Image has been added.", name),
                HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponObject> UpdateFile(@PathVariable("id") Integer id, @RequestParam("file") MultipartFile file,
                                                   @RequestParam Product idProduct) {

        String name = iStorageSerivce.storeFile(file);
        ImageProduct ImageProduct = new ImageProduct();
        ImageProduct.setProduct_image(idProduct);
        ImageProduct.setUrl(name);
        imageProductService.updateImageProduct(id, ImageProduct);
        return new ResponseEntity<>(new ResponObject("SUCCESS", "Image has been updated.", name),
                HttpStatus.CREATED);
    }


    @DeleteMapping("/image/delete/{idImage}")
    public void deleteImage(@PathVariable("idImage") int idImage) {


    }
}
