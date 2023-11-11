package com.example.DATN_API.Controller;
import com.example.DATN_API.Entity.Shop;
import com.example.DATN_API.Entity.ResponObject;
import com.example.DATN_API.Service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shop")
@CrossOrigin("*")
public class ShopController {
    @Autowired
    ShopService shopService;

    @GetMapping()
    public ResponseEntity<List<Shop>> getAll() {
        return new ResponseEntity<>(shopService.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Shop> findById(@PathVariable Integer id) {
        if (shopService.existsById(id)) {
            return new ResponseEntity<>(shopService.findById(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<ResponObject> create(@RequestParam("image") MultipartFile image, @RequestParam("shopName") String Shopname, @RequestParam("idAccount") Integer idAccount) {


//        Shop shopnew = shopService.createShop(shop);
        return new ResponseEntity<>(new ResponObject("SUCCESS", "shop has been added.", "shopnew"),
                HttpStatus.CREATED);
    }

    @PutMapping("/admin/update/{id}/{status}")
    public ResponseEntity<ResponObject> updatestatusAdmin(@PathVariable("id") Integer id,@PathVariable("status") Integer status) {
        Shop shop=shopService.findById(id);
        shop.setStatus(status);
        Shop shopnew = shopService.updateShop(shop);
        return new ResponseEntity<>(new ResponObject("SUCCESS", "shop has been updated.", shopnew), HttpStatus.OK);
    }

//    @DeleteMapping("{id}")
//    public ResponseEntity<ResponObject> delete(@PathVariable("id") Integer id) {
//        if (!shopService.existsById(id))
//            return new ResponseEntity<>(new ResponObject("NOT_FOUND", "shop_id: " + id + " does not exists.", id),
//                    HttpStatus.NOT_FOUND);
//        Shop shop = shopService.findById(id);
//        shop.setStatus(2);
//        shopService.updateshop(id, shop);
//        return new ResponseEntity<>(new ResponObject("SUCCESS", "shop has been deleted.", id), HttpStatus.OK);
//    }

}