package com.example.DATN_API.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.DATN_API.Entity.*;
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
	public ResponseEntity<List<Product>> getAll(@RequestParam("status") Optional<String> getstatus,@RequestParam("shop") Optional<Integer> idshop) {
		String status = getstatus.orElse("");
		Shop shop=shopService.findById(idshop.orElse(0));
		if (status.equals("isactive")) {
			return new ResponseEntity<>(productService.findProductbyStatus(1,shop), HttpStatus.OK);
		} else if (status.equals("unactive")) {
			return new ResponseEntity<>(productService.findProductbyStatus(2,shop), HttpStatus.OK);
		}
		return new ResponseEntity<>(productService.findAll(shop), HttpStatus.OK);
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
		return new ResponseEntity<>(new ResponObject("success", "Thêm thành công.", productnew),
				HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	public ResponseEntity<ResponObject> update(@PathVariable("id") Integer id, @RequestBody Product product) {
		if (!productService.existsById(id))
			return new ResponseEntity<>(
					new ResponObject("error", "Sản phẩm : " + id + "không tồn tại.", product),
					HttpStatus.NOT_FOUND);

		Product productnew = productService.updateProduct(id, product);
		return new ResponseEntity<>(new ResponObject("success", "Cập nhật thành công.", productnew),
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
		return new ResponseEntity<>(new ResponObject("success", "Xóa thành công.", id), HttpStatus.OK);
	}

	// Storage
	@PostMapping("/createStorage/{product}")
	public ResponseEntity<ResponObject> createStorage(@PathVariable("product") Integer product,
			@RequestBody Storage storage) {
		Product newProduct = productService.findById(product);
		storage.setProduct(newProduct);
		Storage storagesave = storageService.createStorage(storage);
		return new ResponseEntity<>(new ResponObject("success", "Thêm thành công.", storagesave),
				HttpStatus.CREATED);
	}

	@PutMapping("/updateStorage/{id}/{idProduct}")
	public ResponseEntity<ResponObject> updateStorage(@PathVariable("id") Integer id,
			@PathVariable("idProduct") Integer idProduct, @RequestBody Storage storage) {
		Product newProduct = productService.findById(idProduct);
		storage.setProduct(newProduct);
		Storage storagesave = storageService.updateStorage(id, storage);
		return new ResponseEntity<>(new ResponObject("SUCCESS", "Storage has been added.", storagesave),
				HttpStatus.CREATED);
	}

	@GetMapping("/find")
	public ResponseEntity<ResponObject> find(@RequestParam String key, @RequestParam String valueKeyword,
											 @RequestParam String idCategoryItem, @RequestParam String minQuantity, @RequestParam String maxQuantity,
											 @RequestParam String status, @RequestParam String stocking, @RequestParam("shop") int idshop) {
		Shop shop = shopService.findById(idshop);
		List<Product> products = productService.findAll(shop);
		List<Object[]> listProduct = new ArrayList<>();

		if (key.equals("id")) {
			if (idCategoryItem.equals("") || idCategoryItem == null) {
				products = productService.findByKey(valueKeyword, "", status,shop);
			} else {

				products = productService.findByKey(valueKeyword, idCategoryItem, status,shop);

			}
		} else {
			if (idCategoryItem.equals("") || idCategoryItem == null) {
				products = productService.findByProductName(valueKeyword, "", status,shop);
			} else {
				products = productService.findByProductName(valueKeyword, idCategoryItem, status,shop);
			}
		}
		for (Product p : products) {
			// GET LIST IMAGE
			List<String> listImage = new ArrayList<>();
			for (ImageProduct image : p.getImage_product()) {
				listImage.add(image.getUrl());
			}
			// GET QUANTITY PRODUCT IN STORAGE
			int quantityInStorage = 0;
			for (Storage st : p.getListStorage()) {
				quantityInStorage += st.getQuantity();
			}
			// GET QUANTITY PRODUCT IN ORDER
			int quantityInOrder = 0;
			for (OrderDetail order : p.getListOrderDetail()) {
				quantityInOrder += order.getQuantity();
			}
			listProduct.add(new Object[] { p.getId(), listImage, p.getProduct_name(),
					p.getCategoryItem_product().getType_category_item(), p.getPrice(), p.getCreate_date(),
					p.getStatus(), quantityInStorage - quantityInOrder });
		}
		// CHECK QUANTITY
		if (stocking.equals("")) {
			if ((minQuantity.equals("") || minQuantity.equals("0"))
					&& (maxQuantity.equals("") || maxQuantity.equals("0"))) {
				return new ResponseEntity<>(new ResponObject("success", "Load sản phẩm thành công!", listProduct),
						HttpStatus.OK);
			} else {
				int max = Integer.parseInt(maxQuantity);
				int min = Integer.parseInt(minQuantity);
				if (min >= 0 && max >= 0 && max >= min) {
					List<Object[]> listFilter = listProduct.stream().filter(product -> {
						return (int) product[7] >= min && (int) product[7] <= max;
					}).collect(Collectors.toList());
					return new ResponseEntity<>(new ResponObject("success", "Load sản phẩm thành công!", listFilter),
							HttpStatus.OK);
				} else {
					return new ResponseEntity<>(
							new ResponObject("error", "Số lượng tìm kiếm không hợp lệ!", listProduct), HttpStatus.OK);
				}
			}
		} else {
			List<Object[]> listFilter = listProduct.stream().filter(product -> {
				return (int) product[7] == 0;
			}).collect(Collectors.toList());
			return new ResponseEntity<>(new ResponObject("success", "Load sản phẩm thành công!", listFilter),
					HttpStatus.OK);
		}
	}


}