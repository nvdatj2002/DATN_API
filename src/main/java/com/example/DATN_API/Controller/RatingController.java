package com.example.DATN_API.Controller;

import com.example.DATN_API.Entity.Account;
import com.example.DATN_API.Entity.Product;
import com.example.DATN_API.Entity.Rate;
import com.example.DATN_API.Reponsitories.AccountReponsitory;
import com.example.DATN_API.Reponsitories.ProductReponsitory;
import com.example.DATN_API.Reponsitories.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collections;
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
    public ResponseEntity<?> getRating(@PathVariable int productId) {
        try {
            Product product = productReponsitory.findById(productId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sản phẩm không có"));

            List<Rate> ratings = rateRepository.findByProduct_rate(product);

            // Kiểm tra xem có đánh giá nào không
            if (ratings != null && !ratings.isEmpty()) {
                return new ResponseEntity<>(ratings, HttpStatus.OK);
            } else {
                // Trả về danh sách rỗng nếu không có đánh giá
                return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Lỗi xảy ra khi xử lý yêu cầu", HttpStatus.INTERNAL_SERVER_ERROR);
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
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sản phẩm không có"));

            Account account = accountReponsitory.findById(accountId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tài khoản không được tìm thấy"));

            // Kiểm tra xem tài khoản đã đánh giá sản phẩm chưa
            if (rateRepository.existsByAccount_rateAndProduct_rate(account, product)) {
                return new ResponseEntity<>("Bạn đã đánh giá sản phẩm này.", HttpStatus.BAD_REQUEST);
            }

            Rate rate = new Rate();
            rate.setProduct_rate(product);
            rate.setAccount_rate(account);
            rate.setStar(start);
            rate.setDescription(description);
            rate.setCreateDate(LocalDateTime.now());

            rateRepository.save(rate);
            return new ResponseEntity<>("Đã đánh giá sản phẩm thành công", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/avg/{productId}")
    public Double getAverageStarByProduct(@PathVariable int productId) {
        Product product = productReponsitory.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sản phẩm không có"));

        // Lấy giá trị trung bình sao từ cơ sở dữ liệu
        Double averageStar = rateRepository.findAverageStarByProduct(product);

        // Kiểm tra giá trị trung bình sao là null
        return averageStar != null ? Math.round(averageStar * 100.0) / 100.0 : 0.0;
    }
}
