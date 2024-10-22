package com.ust.rest.model;
 
import lombok.Data;
 
@Data
public class Product {
	
	public Product(int productID, String brand, String description, int qty, int price) {
		// TODO Auto-generated constructor stub
		this.productID = productID;
		this.brand = brand;
		this.description = description;
		this.qty= qty;
		this.price = price;
	}
	
	private long productID;
	private String brand;
	private String description;
	private int qty;
	private int price;
 
}