package com.ust.rest.controller;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.ust.rest.model.Product;
 
@RestController //Spring MVC - Response - we return a ViewResolver - write to body of html
@RequestMapping("/product/api1.0")//root of the resource
public class ProductController {
	ArrayList<Product> productsList = new ArrayList<>();
	{
		productsList.add(new Product(101,"Nike","Feather Walk",10,15000));
		productsList.add(new Product(102,"Adidas","Comfort Walk",30,13000));
		productsList.add(new Product(103,"Puma","Firm Grip",12,12000));
	}
	@GetMapping(value="/product/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> getById(@PathVariable long id){
		Optional<Product> optional = productsList.stream().filter(product -> product.getProductID()==id).findFirst();
		return ResponseEntity.status(HttpStatus.OK).body(optional.get());
	}
 
	@GetMapping(value="/product/brand/{brand}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> getByBrand(@PathVariable String brand){
		Optional<Product> optional = productsList.stream().filter(product -> product.getBrand().equals(brand)).findFirst();
		return ResponseEntity.status(HttpStatus.OK).body(optional.get());
	}
	@GetMapping(value="/productsinfo" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Product>> displayProducts(){
		return ResponseEntity.ok(productsList);
}
	@PostMapping(value="/addProduct",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> addProduct(@RequestBody Product product){
		Boolean flag = false;
		if(product!=null)
			flag = productsList.add(product);
		return flag ? ResponseEntity.status(201).body(product) : ResponseEntity.status(404).body(null);
	}
	@PutMapping(value="/modify")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product){
		Optional<Product> optional = productsList.stream().filter(p -> p.getProductID()==product.getProductID()).findFirst();
		Product temp = optional.get();
		temp.setBrand(product.getBrand());
		temp.setQty(product.getQty());
		temp.setPrice(product.getPrice());
		int index = productsList.indexOf(temp);
		productsList.remove(index);
		productsList.add(index, temp);
		//logger.info("index of temp ="+index);
		return null;
	}
	@DeleteMapping(value = "/delete/{id}")
	public void removeProduct(@PathVariable long id) {
		Optional<Product> optional = productsList.stream().filter(p -> p.getProductID()==id).findFirst();
		productsList.remove(optional.get());
	}
}
