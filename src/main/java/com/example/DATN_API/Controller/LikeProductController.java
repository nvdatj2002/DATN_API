package com.example.DATN_API.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.DATN_API.Reponsitories.LikeProductReponsitory;
import com.example.DATN_API.Entity.*;

@RestController
@CrossOrigin("*")
public class LikeProductController {
	@Autowired
    private LikeProductReponsitory likeProductRepository;

	@GetMapping("/api/likeProducts")
	public String getLikeProductsByAccountId(@RequestParam("accountId") int accountId) {
	    return likeProductRepository.findProductDetailsByAccountId(accountId);
	}

	
	

	@PostMapping("/api/like_Product")
	public ResponseEntity<String> likeProduct(@RequestParam("accountId") int accountId, @RequestParam("productId") int productId) {
	        LikeProduct likeProduct = likeProductRepository.findByProductLikeIdAndAccountLikeId(productId, accountId);
	    if (likeProduct != null) {
	        return ResponseEntity.badRequest().body("Sản phẩm đã được like trước đó.");
	    }
	    return ResponseEntity.ok("Sản phẩm đã được like.");
	}

	
	@DeleteMapping("/api/unlike_Product")
    public ResponseEntity<String> unlikeProduct(@RequestParam("accountId") int accountId, @RequestParam("productId") int productId) {
        LikeProduct likeProduct = likeProductRepository.findByProductLikeIdAndAccountLikeId(productId, accountId);
        if (likeProduct == null) {
            return ResponseEntity.badRequest().body("Sản phẩm chưa được like.");
        }

        likeProductRepository.delete(likeProduct);
        return ResponseEntity.ok("Sản phẩm đã được unlike.");
    }
}
