package com.example.DATN_API.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.DATN_API.Reponsitories.LikeProductReponsitory;

@RestController
@CrossOrigin("*")
public class LikeProductController {
	@Autowired
    private LikeProductReponsitory likeProductRepository;

	@GetMapping("/api/likeProducts")
    public String getLikeProductsByAccountId(@RequestParam("accountId") int accountId) {
        return likeProductRepository.findProductDetailsByAccountId(accountId);
    }
}
