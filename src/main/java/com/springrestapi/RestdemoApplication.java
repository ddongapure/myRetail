package com.springrestapi;

import com.springrestapi.models.Product;
import com.springrestapi.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class RestdemoApplication {

	@Autowired
	private ProductRepository repository;

	/**
	 * This method starts the application
	 * @param args
	 */
	public static void main(String[] args) {

		ApplicationContext ctx = SpringApplication.run(RestdemoApplication.class, args);
	}

	/**
	 * Inserting some sample data into the MongoDB database
	 */
	@PostConstruct
	public void storeSampleData() {
		Map<String, String> map = new HashMap<>();
		map.put("value", "13.49");
		map.put("currency_code", "USD");
		repository.save(new Product("13860428", "The Big Lebowski (Blu-ray) (Widescreen)", map));

		map.put("value", "500");
		map.put("currency_code", "USD");
		repository.save(new Product("15117729", "Apple IPAD", map));
	}
}
