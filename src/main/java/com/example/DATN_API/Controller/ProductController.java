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
import com.example.DATN_API.Reponsitories.ProductReponsitory;
import com.example.DATN_API.Service.ShopService;
import com.example.DATN_API.Service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	@Autowired
	ProductReponsitory productReponsitory;

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
						p.getCategoryItem_product(), p.getStatus(), p.getImage_product() });
			}
			dataReturn = new Object[] { shop.getId(), shop.getShop_name(), shop.getAddressShop(), listProducts,shop.getImage() };
		}

		if (listProducts.size() == 0) {
			return new ResponseEntity<>(new ResponObject("error", "Không có thông tin", null), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(new ResponObject("success", "GET LIST SUCCESS", dataReturn), HttpStatus.OK);
		}

	}

	@GetMapping("/top10")
	public ResponseEntity<List<Object[]>> getTop10Products() {
		List<Object[]> top10Products = productReponsitory.getTop10Products();
		if (top10Products.isEmpty()) {
			// không có dữ liệu
			return ResponseEntity.noContent().build();
		} else {
			// có dữ liệu và trả về kết quả
			return ResponseEntity.ok(top10Products);
		}
	}

// Hiển thị những sản phẩm tương tự theo categoryItem_product
@GetMapping("/{id}/similar-products")
public ResponseEntity<ResponObject> findSimilarProducts(@PathVariable("id") Integer id) {
	if (productService.existsById(id)) {
		List<Product> similarProducts = productReponsitory.findSimilarProducts(id);
		Object responseData = new Object[] { "similarProducts", similarProducts };
		return new ResponseEntity<>(new ResponObject("SUCCESS", "Similar products retrieved successfully.", responseData), HttpStatus.OK);
	}

	return new ResponseEntity<>(new ResponObject("NOT_FOUND", "Product with id: " + id + " not found.", null), HttpStatus.NOT_FOUND);
}

//
//	@GetMapping("/search")
//	public ResponseEntity<Page<Product>> searchProductsByName(
//			@RequestParam("name") String productName,
//			@RequestParam(name = "page", defaultValue = "0") int page,
//			@RequestParam(name = "size", defaultValue = "10") int size
//	) {
//		Pageable pageable = PageRequest.of(page, size);
//		Page<Product> result = productReponsitory.searchByNamePaginated(productName, pageable);
//
//		if (result.isEmpty()) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//
//		return new ResponseEntity<>(result, HttpStatus.OK);
//	}
//
//	@GetMapping("/paginated")
//	public ResponseEntity<Page<Product>> getAllPaginated(
//			@RequestParam(name = "page", defaultValue = "0") int page,
//			@RequestParam(name = "size", defaultValue = "10") int size
//	) {
//		Pageable pageable = PageRequest.of(page, size);
//		Page<Product> result = productReponsitory.findAllPaginated(pageable);
//
//		if (result.isEmpty()) {
//			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		}
//
//		return new ResponseEntity<>(result, HttpStatus.OK);
//	}
}