package com.example.DATN_API.Controller;

import com.example.DATN_API.Entity.ResponObject;
import com.example.DATN_API.Service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/shop")
public class ShopController {
    @Autowired
    ShopService shopService;
    @GetMapping("/findAll")
    public ResponseEntity<ResponObject> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponObject(
                "SUCCESS","find shop by product",shopService.findAll()
        ));
    }
    @GetMapping("/findByProduct/{id}")
    public ResponseEntity<ResponObject> findByProduct(@PathVariable("id") int idProduct){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponObject(
                "SUCCESS","find shop by product",shopService.findShopByProduct(idProduct)
        ));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponObject> findById(@PathVariable("id") int idShop){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponObject(
                "SUCCESS","find shop by product",shopService.findById(idShop)
        ));
    }
}
