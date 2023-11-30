package com.example.DATN_API.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.DATN_API.Entity.Product;
import com.example.DATN_API.Entity.Shop;
import com.example.DATN_API.Reponsitories.ProductReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.DATN_API.Reponsitories.ShopReponsitory;

@Service
public class ShopService {
    @Autowired
    ShopReponsitory ShopReponsitory;
    @Autowired
    ProductReponsitory productReponsitory;

    public List<Shop> findAll() {
        return ShopReponsitory.findAll();
    }

    public Shop findById(int id) {
        Optional<Shop> Shop = ShopReponsitory.findById(id);
        return Shop.get();
    }
	public Shop findShopByProduct(int idProduct){
		return ShopReponsitory.findShopByProduct(idProduct);
	}

    public Shop createShop(Shop Shop) {
       return ShopReponsitory.save(Shop);
    }

    public Shop updateShop(Shop shop) {
        return ShopReponsitory.save(shop);
    }

    public void deleteShop(int id) {
        ShopReponsitory.deleteById(id);
    }

    public Boolean existsById(Integer id) {
        return ShopReponsitory.existsById(id) ? true : false;
    }

    public Page<Shop> findShopByStatusProduct(Optional<Integer> status){
        int stt = status.orElse(1);
        Pageable pageable = PageRequest.of(0,20, Sort.by("create_date"));
        return ShopReponsitory.findShopByStatusProduct(stt,pageable);
    }
    public List<Shop> findShopByStatusProductSearch(Optional<Integer> status,Optional<Integer> typeSearch,Optional<String> search){
        int stt = status.orElse(1);
        int type = typeSearch.orElse(1);
        String keyword = search.orElse("");
        System.out.println("id "+keyword);
        System.out.println("type "+type);
        System.out.println("stt "+stt);

        Pageable pageable = PageRequest.of(0,20, Sort.by("create_date"));
        if(type == 1){
        return ShopReponsitory.findShopSearchByIdProduct(stt,keyword);
        }else if (type == 2){
            return findShopByNameProduct(search,status);
        }else {
            return ShopReponsitory.findByName(stt,"%"+keyword+"%");
        }
    }
    public List<Shop> findShopByName(Optional<String> search, Optional<Integer> stt) {
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
            List<Product> productList = findIdShop(products,listIdShop.get(i));
            Shop shop = new Shop();
            shop.setProducts(productList);
            shop.setShop_name(productList.get(0).getShop().getShop_name());
            shops.add(shop);
        }
        return shops;
    }
    public List<Shop> findShopByNameProduct(Optional<String> search, Optional<Integer> stt) {
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
            List<Product> productList = findIdShop(products,listIdShop.get(i));
            Shop shop = new Shop();
            shop.setProducts(productList);
            shop.setShop_name(productList.get(0).getShop().getShop_name());
            shops.add(shop);
        }
        return shops;
    }
    public static List<Product> findIdShop(List<Product> products, int shopId) {
        return products.stream()
                .filter(product -> product.getShop().getId() == shopId)
                .collect(Collectors.toList());
    }
}
