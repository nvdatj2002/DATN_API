package com.example.DATN_API.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.DATN_API.Entity.Product;
import com.example.DATN_API.Entity.Shop;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.DATN_API.Reponsitories.ProductReponsitory;

@Service
public class ProductService {
    @Autowired
    ProductReponsitory productReponsitory;

    public List<Product> findAll() {
        return productReponsitory.findAll();
    }

    public List<Product> findAll(Shop shop) {
        return productReponsitory.findAllByShop(shop);
    }

    public List<Shop> findByName(Optional<String> search, Optional<Integer> stt) {
        Page<Shop> shopPage = null;
        String keyword = search.orElse("");
        int status = stt.orElse(1);
        List<Shop> shops = new ArrayList<>();

        List<Product> products = productReponsitory.findByName(keyword, status);
        List<Integer> listIdShop = new ArrayList<>();
        products.stream().forEach(item -> {
            if (!listIdShop.contains(item.getShop().getId())) {
                listIdShop.add(item.getShop().getId());
            }
        });
        if(listIdShop.size() == 0){
            return null;
        }
        for (int i = 0; i < listIdShop.size(); i++) {
            List<Product> productList = findProductsByShopId(products,listIdShop.get(i));
            Shop shop = new Shop();
            shop.setProducts(productList);
            shop.setShop_name(productList.get(0).getShop().getShop_name());
            shops.add(shop);
        }
        return shops;
    }

    public static List<Product> findProductsByShopId(List<Product> products, int shopId) {
        return products.stream()
                .filter(product -> product.getShop().getId() == shopId)
                .collect(Collectors.toList());
    }

    public List<Product> findProductbyStatus(int status) {
        return productReponsitory.getProductbyStatus(status);
    }

    public Page<Product> getPageProduct(Optional<Integer> stt, Optional<Integer> offset, Optional<Integer> sp, Optional<String> field) {
        String sort = field.orElse("create_date");
        int itemStart = offset.orElse(0);
        ;
        int sizePage = sp.orElse(20);
        int status = sp.orElse(1);

        return productReponsitory.getPageProduct(status, PageRequest.of(itemStart, sizePage, Sort.Direction.DESC, sort));
    }

    public Product findById(int id) {
        Optional<Product> product = productReponsitory.findById(id);
        return product.get();
    }

    public Product createProduct(Product product) {
        try {
            Product productsave = productReponsitory.save(product);
            return productsave;
        } catch (Exception e) {
            e.printStackTrace();
            LogError.saveToLog(e);
        }
        return null;
    }

    public Product updateProduct(int id, Product product) {
        Product productold = findById(id);
        product.setId(id);
        product.setShop(productold.getShop());
        product.setCreate_date(productold.getCreate_date());
        product.setStart_promotion(productold.getStart_promotion());
        product.setEnd_promotion(productold.getEnd_promotion());
        try {
            Product productsave = productReponsitory.save(product);
            return productsave;
        } catch (Exception e) {
            e.printStackTrace();
            LogError.saveToLog(e);
        }
        return null;
    }

    public void deleteProduct(int id) {
        productReponsitory.deleteById(id);
    }

    public Boolean existsById(Integer id) {
        return productReponsitory.existsById(id) ? true : false;
    }

    public List<Product> findByKey(String keyword, String idCategoryItem, String status, Shop shop) {
        return productReponsitory.findByKey(keyword, idCategoryItem, status, shop);
    }

    public List<Product> findByProductName(String keyword, String idCategoryItem, String status, Shop shop) {
        return productReponsitory.findByProductName(keyword, idCategoryItem, status, shop);
    }


}
