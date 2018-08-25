package com.springrestapi;

import com.springrestapi.models.Product;
import com.springrestapi.repositories.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;


/**
 * Created by ddongapu on 8/23/2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository repository;

    @Test
    public void testGetAllProducts_success() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("value", "10.49");
        map.put("currency_code", "USD");
        Product mockProduct = new Product("15643793", "Test product", map);
        List<Product> allproducts = singletonList(mockProduct);

        given(repository.findAll()).willReturn(allproducts);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        System.out.println(result.getResponse());

        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void testGetProductById_success() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("value", "28.49");
        map.put("currency_code", "USD");
        String productID = "13860428";
        Product mockProduct = new Product(productID, "ABCxyz", map);

        Mockito.when(repository.findByProductId(productID)).thenReturn(mockProduct);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/" + productID)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        System.out.println(result.getResponse());

        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void testGetProductById_notFound() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("value", "13.49");
        map.put("currency_code", "USD");
        String productID = "15117729";
        Product mockProduct = new Product(productID, "Sample product", map);

        Mockito.when(repository.findByProductId(Mockito.anyString())).thenReturn(mockProduct);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/" + productID)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        System.out.println(result.getResponse());

        assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    public void testUpdateProductPrice_success() throws Exception {
        String productID = "13860428";
        String requestBody = "{\"productId\":13860428,\"productName\":\"The Big Lebowski (Blu-ray) (Widescreen)\",\"current_price\":{\"value\": 33.49,\"currency_code\":\"USD\"}}";

        Mockito.when(repository.existsById(Mockito.anyString())).thenReturn(true);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/products/" + productID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(requestBody);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        System.out.println(result.getResponse());

        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void testUpdateProductPrice_badRequest() throws Exception {
        String productID = "17000000";
        String errMsg ="Id in request header and body doesn't match.";
        String requestBody = "{\"productId\":13860428,\"productName\":\"The Big Lebowski (Blu-ray)\",\"current_price\":{\"value\": 13.49,\"currency_code\":\"USD\"}}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/products/" + productID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(requestBody);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        System.out.println(result.getResponse());

        assertEquals(400, result.getResponse().getStatus());
        assertEquals(errMsg, result.getResponse().getErrorMessage());
    }
}
