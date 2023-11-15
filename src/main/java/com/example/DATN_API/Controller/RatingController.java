package com.example.DATN_API.Controller;


import com.example.DATN_API.Entity.Account;
import com.example.DATN_API.Entity.Product;
import com.example.DATN_API.Entity.Rate;
import com.example.DATN_API.Reponsitories.AccountReponsitory;
import com.example.DATN_API.Reponsitories.ProductReponsitory;
import com.example.DATN_API.Reponsitories.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ratings")
@CrossOrigin(origins = "*")
public class RatingController {

    @Autowired
    RateRepository rateRepository;

    @Autowired
    AccountReponsitory accountReponsitory;

    @Autowired
    ProductReponsitory productReponsitory;

    @GetMapping("/{productId}")
    public ResponseEntity<List<Rate>> getRating(@PathVariable int productId) {
        try {
            Product product = productReponsitory.findById(productId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

            List<Rate> ratings = rateRepository.findByProduct_rate(product);

            if (ratings != null && !ratings.isEmpty()) {
                return new ResponseEntity<>(ratings, HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No ratings found for the product");
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addRating(@RequestBody Map<String, Object> ratingData) {
        try {
            int productId = (int) ratingData.get("productId");
            int accountId = (int) ratingData.get("accountId");
            int start = (int) ratingData.get("start");
            String description = (String) ratingData.get("description");

            Product product = productReponsitory.findById(productId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

            Account account = accountReponsitory.findById(accountId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

            // Kiểm tra xem tài khoản đã đánh giá sản phẩm chưa
            if (rateRepository.existsByAccount_rateAndProduct_rate(account, product)) {
                return new ResponseEntity<>("You have already rated this product.", HttpStatus.BAD_REQUEST);
            }

            Rate rate = new Rate();
            rate.setProduct_rate(product);
            rate.setAccount_rate(account);
            rate.setStar(start);
            rate.setDescription(description);
            rate.setCreateDate(LocalDateTime.now());

            rateRepository.save(rate);
            return new ResponseEntity<>("Rating added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/avg/{productId}")
    public Double getAverageStarByProduct(@PathVariable int productId) {
        Product product = productReponsitory.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        // Lấy giá trị trung bình sao từ cơ sở dữ liệu
        Double averageStar = rateRepository.findAverageStarByProduct(product);

        // Kiểm tra giá trị trung bình sao là null
        if (averageStar != null) {
            // Nếu không phải là null, làm tròn và trả về giá trị
            return Math.round(averageStar * 100.0) / 100.0;
        } else {
            // Nếu là null trả về một giá trị mặc định
            return 0.0; //
        }
    }





}