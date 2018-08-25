package com.springrestapi;

/**
 * Created by ddongapu on 8/20/2018.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springrestapi.exceptions.ProductBadReqException;
import com.springrestapi.exceptions.ProductNotFoundException;
import com.springrestapi.models.Product;
import com.springrestapi.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    /**
     * Gets productId information from Target Redsky service and pricing information from MongoDB NoSQL
     * database and gives out a JSON response.
     * @param id Id of product we need information about.
     * @return
     * @throws ProductNotFoundException
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product getProductDetailsById(@PathVariable("id") String  id) throws ProductNotFoundException {

        RestTemplate restTemplate = new RestTemplate();
        Product product = new Product();

        String url = "https://redsky.target.com/v2/pdp/tcin/"+ id +"?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";

        Map<String, String> urlVariables = new HashMap<String, String>();
        urlVariables.put("id", id);

        ObjectMapper infoMapper = new ObjectMapper();
        Map<String, Map> infoMap;

        try {

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, urlVariables);
            infoMap = infoMapper.readValue(response.getBody(), Map.class);

            product.productId = id;
            Map<String, Map> productMap = infoMap.get("product");
            Map<String, Map> itemMap = productMap.get("item");
            Map<String, String> prodDescrMap = itemMap.get(("product_description"));
            product.productName = prodDescrMap.get("title");

            Product productInfoFromRepo = repository.findByProductId(id);

            product.current_price = productInfoFromRepo.current_price;

        } catch (Exception e) {
            throw new ProductNotFoundException();
        }

        return product;
    }

    /**
     * Stores product info in a NoSQL database.
     * Accepts a json request body in the following format
     * {"productId":13860428,"productName":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 13.49,"currency_code":"USD"}}
     * @param prodInfo Product info JSON request body
     * @param productId Id of product that need to be stored.
     * @return
     * @throws ProductNotFoundException
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String updateProductPrice(@RequestBody Product prodInfo, @PathVariable("id") String productId) throws ProductBadReqException, ProductNotFoundException {
        if (!prodInfo.productId.equalsIgnoreCase(productId)) {
            throw new ProductBadReqException();
        }

        if(repository.existsById(productId)) {
            repository.save(prodInfo);
        } else {
            throw new ProductNotFoundException();
        }
        return "{\"response\":\"Successfully updated the product price.\"}";
    }
}

