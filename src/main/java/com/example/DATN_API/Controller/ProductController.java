package com.example.DATN_API.Controller;

import java.util.List;

import com.example.DATN_API.Entity.Product;
import com.example.DATN_API.Entity.ResponObject;
import com.example.DATN_API.Entity.Shop;
import com.example.DATN_API.Service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.DATN_API.Service.ProductService;

@RestController
@RequestMapping("/api/product")
@CrossOrigin("*")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    ShopService shopService;
    @GetMapping()
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> findById(@PathVariable Integer id) {
        if (productService.existsById(id)) {
            return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/shop/{shop}")
    public ResponseEntity<ResponObject> create(@PathVariable("shop") int shop,@RequestBody Product product) {
        Shop shop2=shopService.findById(shop);
        product.setShop(shop2);
        Product productnew= productService.createProduct(product);
        return new ResponseEntity<>(new ResponObject("SUCCESS", "Product has been added.",productnew),
                HttpStatus.CREATED);

    }

    @PutMapping("{id}")
    public ResponseEntity<ResponObject> update(@PathVariable Integer id, @RequestBody Product product) {
        if (!productService.existsById(id))
            return new ResponseEntity<>(
                    new ResponObject("NOT_FOUND", "Product_id: " + id + " does not exists.", product),
                    HttpStatus.NOT_FOUND);

        productService.updateProduct(id, product);
        return new ResponseEntity<>(new ResponObject("SUCCESS", "Product has been updated.", product), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponObject> delete(@PathVariable Integer id) {
        if (!productService.existsById(id))
            return new ResponseEntity<>(new ResponObject("NOT_FOUND", "Product_id: " + id + " does not exists.", id),
                    HttpStatus.NOT_FOUND);
        productService.deleteProduct(id);
        return new ResponseEntity<>(new ResponObject("SUCCESS", "Product has been deleted.", id), HttpStatus.OK);
    }

}