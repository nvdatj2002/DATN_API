package com.example.DATN_API.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.DATN_API.Entity.Product;
import com.example.DATN_API.Entity.ResponObject;
import com.example.DATN_API.Entity.Shop;
import com.example.DATN_API.Entity.Storage;
import com.example.DATN_API.Service.ShopService;
import com.example.DATN_API.Service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.DATN_API.Service.ProductService;

@RestController
@RequestMapping("/api/product")
@CrossOrigin("*")
public class ProductController {
	@Autowired
	ProductService productService;
	@Autowired
	ShopService shopService;
	@Autowired
	StorageService storageService;

	@GetMapping()
	public ResponseEntity<List<Product>> getAll(@RequestParam("status") Optional<String> getstatus) {
		String status = getstatus.orElse("");
		if (status.equals("isactive")) {
			return new ResponseEntity<>(productService.findProductbyStatus(1), HttpStatus.OK);
		} else if (status.equals("unactive")) {
			return new ResponseEntity<>(productService.findProductbyStatus(2), HttpStatus.OK);
		}
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
	public ResponseEntity<ResponObject> create(@PathVariable("shop") int shop, @RequestBody Product product) {
		Shop shop2 = shopService.findById(shop);
		product.setShop(shop2);
		Product productnew = productService.createProduct(product);
		return new ResponseEntity<>(new ResponObject("SUCCESS", "Product has been added.", productnew),
				HttpStatus.CREATED);

	}

	@PutMapping("{id}")
	public ResponseEntity<ResponObject> update(@PathVariable("id") Integer id, @RequestBody Product product) {
		if (!productService.existsById(id))
			return new ResponseEntity<>(
					new ResponObject("NOT_FOUND", "Product_id: " + id + " does not exists.", product),
					HttpStatus.NOT_FOUND);

		Product productnew = productService.updateProduct(id, product);
		return new ResponseEntity<>(new ResponObject("SUCCESS", "Product has been updated.", productnew),
				HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<ResponObject> delete(@PathVariable("id") Integer id) {
		if (!productService.existsById(id))
			return new ResponseEntity<>(new ResponObject("NOT_FOUND", "Product_id: " + id + " does not exists.", id),
					HttpStatus.NOT_FOUND);
		Product product = productService.findById(id);
		product.setStatus(2);
		productService.updateProduct(id, product);
		return new ResponseEntity<>(new ResponObject("SUCCESS", "Product has been deleted.", id), HttpStatus.OK);
	}

	// Storage
	@PostMapping("/createStorage/{product}")
	public ResponseEntity<ResponObject> createStorage(@PathVariable("product") Integer product,
			@RequestBody Storage storage) {
		Product newProduct = productService.findById(product);
		storage.setId_Product(newProduct);
		Storage storagesave = storageService.createStorage(storage);
		return new ResponseEntity<>(new ResponObject("SUCCESS", "Storage has been added.", storagesave),
				HttpStatus.CREATED);
	}

	@PutMapping("/updateStorage/{id}/{idProduct}")
	public ResponseEntity<ResponObject> updateStorage(@PathVariable("id") Integer id,
			@PathVariable("idProduct") Integer idProduct, @RequestBody Storage storage) {
		Product newProduct = productService.findById(idProduct);
		storage.setId_Product(newProduct);
		Storage storagesave = storageService.updateStorage(id, storage);
		return new ResponseEntity<>(new ResponObject("SUCCESS", "Storage has been added.", storagesave),
				HttpStatus.CREATED);
	}

	@GetMapping("/{id}/shop")
	public ResponseEntity<ResponObject> getShopByProduct(@PathVariable("id") Integer id) {
		Map<Integer, Object[]> listProducts = new HashMap<>();
		Shop shop = null;
		Object[] dataReturn = null;
		// CHECK ID PRODUCT .....
		for (Shop s : shopService.findAll()) {
			for (Product p : s.getProducts()) {
				if (p.getId() == id) {
					shop = s;
					break;
				}
			}
		}

		// GET LIST PRODUCT AT SHOP NO LOOP
		if (shop != null) {
			for (Product p : shop.getProducts()) {
				listProducts.put(p.getId(), new Object[] { p.getId(), p.getProduct_name(), p.getPrice(),
						p.getCategoryItem_product(), p.getStatus() });
			}
			dataReturn = new Object[] { shop.getId(), shop.getShop_name(), shop.getAddressShop(), listProducts };
		}

		if (listProducts.size() == 0) {
			return new ResponseEntity<>(new ResponObject("error", "CÓ CÁI NỊT", null), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(new ResponObject("success", "GET LIST SUCCESS", dataReturn), HttpStatus.OK);
		}

	}
//    @GetMapping("/find")
//    public ResponseEntity<ResponObject> find(@RequestParam String key, @RequestParam String valueKeyword,
//                                             @RequestParam String idCategoryItem, @RequestParam String minQuantity, @RequestParam String maxQuantity,
//                                             @RequestParam String status) {
//
//        List<Product> products = productService.findAll();
//        int max = Integer.parseInt(maxQuantity);
//        int min = Integer.parseInt(minQuantity);
//
//        if (min >= 0 && max >= 0 && max >= min) {
//            try {
//                if (key.equals("id")) {
//                    if (idCategoryItem.equals("") || idCategoryItem == null) {
//                        if ((minQuantity.equals("") || minQuantity.equals("0"))
//                                && (maxQuantity.equals("") || maxQuantity.equals("0"))) {
//                            products = productService.findByKey(Integer.parseInt(valueKeyword), 0, 99999, "", status);
//                        } else {
//                            products = productService.findByKey(Integer.parseInt(valueKeyword), Integer.parseInt(minQuantity),
//                                    Integer.parseInt(maxQuantity), "", status);
//                        }
//                    } else {
//                        if ((minQuantity.equals("") || minQuantity.equals("0"))
//                                && (maxQuantity.equals("") || maxQuantity.equals("0"))) {
//                            products = productService.findByKey(Integer.parseInt(valueKeyword), 0, 99999, idCategoryItem, status);
//                        } else {
//                            products = productService.findByKey(Integer.parseInt(valueKeyword), Integer.parseInt(minQuantity),
//                                    Integer.parseInt(maxQuantity), idCategoryItem, status);
//                        }
//                    }
//                } else {
//                    if (idCategoryItem.equals("") || idCategoryItem == null) {
//                        if ((minQuantity.equals("") || minQuantity.equals("0"))
//                                && (maxQuantity.equals("") || maxQuantity.equals("0"))) {
//                            products = productService.findByProductName(valueKeyword, 0, 99999, "", status);
//                        } else {
//                            products = productService.findByProductName(valueKeyword, Integer.parseInt(minQuantity),
//                                    Integer.parseInt(maxQuantity), "", status);
//                        }
//                    } else {
//                        if ((minQuantity.equals("") || minQuantity.equals("0"))
//                                && (maxQuantity.equals("") || maxQuantity.equals("0"))) {
//                            products = productService.findByProductName(valueKeyword, 0, 99999, idCategoryItem, status);
//                        } else {
//                            products = productService.findByProductName(valueKeyword, Integer.parseInt(minQuantity),
//                                    Integer.parseInt(maxQuantity), idCategoryItem, status);
//                        }
//                    }
//                }
//                return new ResponseEntity<>(new ResponObject("success", "Load sản phẩm thành công!", products),
//                        HttpStatus.OK);
//            } catch (Exception e) {
//                e.printStackTrace();
//                return new ResponseEntity<>(new ResponObject("error", "Load sản phẩm thất bại!", products),
//                        HttpStatus.OK);
//            }
//        } else {
//            return new ResponseEntity<>(new ResponObject("error", "Số lượng tìm kiếm không hợp lệ!", products),
//                    HttpStatus.OK);
//        }
//    }

}