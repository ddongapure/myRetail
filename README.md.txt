
# ProjectTitle - restdemo
restdemo is a RESTful service that can retrieve product and price details by ID. 

# Getting Started
restdemo restful application service is an implementation of GET and PUT methods, which responds to a HTTP GET request at /products/{id} and delivers product data as JSON. Also, accepts an HTTP PUT request at the same path (/products/{id}), containing a JSON request body similar to the GET response, and updates the product’s price in the data store.
The GET method gives the product details like name and price information for a given product ID and the PUT method updates the price information of the product.
The restdemo service talks to third party RESTful service to get the name of the product. When the API receives the request, it sends a request to "redsky.target.com" and retrieves the product information. 
This product information doesn't contain price that is needed by the user. The price is retrieved from a data store. (The price information of the product is stored in the MongoDB repository.)
The price information is now combined with the required product information to provide only the required product information to the user.
restdemo service is built on Spring Boot framework with dependencies including Maven for build and Mockito for testcases.


# Prerequisites
Java Spring Boot framework https://projects.spring.io/spring-boot/
Maven https://maven.apache.org/
MongoDB https://www.mongodb.com/
MongoDB Compass: database manager.
Postman https://www.getpostman.com/

# Installing
Clone the code from git repository - https://github.com/ddongapure/myRetail.git
Install MongoDB in your system - https://docs.mongodb.com/manual/installation/
Run MongoDB - Run 'mongod.exe' in order to start Mongodb
Before starting webservices server make sure that Mongodb is running on port number mentioned in the application.properties file
Make sure you are in the restdemo directory:in the project root, 
run the mvn spring-boot:run command to compile the code and start the Spring TomcatWebServer with the default port 8080 or 8090 as mentioned in application.properties(server.port).
Once the server starts, you can test your API with a REST client such as Postman.

# How to test the webservices using PostMan client

 1) Test GET Webservice
 
In the postman client select the GET option and in the url section copy the below url
http://localhost:8090/products/13860428 and the response should be:

{
    "productId": "13860428",
    "productName": "The Big Lebowski (Blu-ray)",
    "current_price": {
        "value": "13.49",
        "currency_code": "USD"
    }
}

 2) Test PUT Webservice
 
In the postman client select the PUT option and in the url section copy the below url
http://localhost:8090/products/13860428 and in the body section copy the below JSON:

{
    "productId": "13860428",
    "productName": "The Big Lebowski (Blu-ray)",
    "current_price": {
        "value": "15.00",
        "currency_code": "USD"
    }
}

and the response should be:
{"response":"Successfully updated the product price."}

# Running the tests
The testcases are present in the file: ProductControllerTests.java and can be run by the right click option in IntelliJ IDE or by cmnd: mvn clean test.
Images are provided to show successful and failure resposnses for API requests in the images folder.





