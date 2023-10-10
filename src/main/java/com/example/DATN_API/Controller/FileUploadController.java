package com.example.DATN_API.Controller;

import com.example.DATN_API.Entity.ResponObject;
import com.example.DATN_API.Service.IStorageSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@CrossOrigin
@RequestMapping(path = "/api")
public class FileUploadController {
   @Autowired
    IStorageSerivce iStorageSerivce;


    @PostMapping("/fileUpload")
    public ResponseEntity<ResponObject> uploadFile(@RequestParam("file")MultipartFile file,
                                                     @RequestParam Optional<Integer> idProduct){
String name= iStorageSerivce.storeFile(file);
return ResponseEntity.status(HttpStatus.OK).body(new ResponObject("Ok","Ok",""));
    }


    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable("fileName") String fileName){
        try{
            byte[] bytes = iStorageSerivce.readFileContent(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        }catch (Exception exception){
            return ResponseEntity.noContent().build();
        }
    }
    @DeleteMapping("/image/delete/{idImage}")
    public void deleteImage(@PathVariable("idImage") int idImage){


    }
}
