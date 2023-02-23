# ReceiptApplication
## Stack

-   Java
-   Spring Boot

## Running the Application

To run the application, you can use the `main` method in the `ReceiptApplication` class as the entry point.

## Description of Endpoints

The application has a single endpoint at `/check` that accepts GET requests with the following request parameters:

-   `itemIdAndQuantity`: a list of strings, where each string represents an item and its quantity in the format `"id-quantity"`
-   `discountCard` (optional): a string representing a discount card in the format `"card-number"`


## Example
http://localhost:8080/check?itemIdAndQuantity=3-7&itemIdAndQuantity=2-3&discountCard=card-1234

In the example you provided, the URL makes a request to the `/check` endpoint with the following request parameters:

-   `itemIdAndQuantity=3-7&itemIdAndQuantity=2-3`: this requests a receipt with 7 units of product 3 and 3 units of product 2.
-   `discountCard=card-1234`: this requests that the receipt be generated with a 30% discount applied using the discount card with number `card-1234`.

The output of the request will be a string in the following format:

Receipt:
Product 3 x7 $3.33 = $20.97 with discount 
Product 2 x3 $2.00 = $6.00 
Total: $18.87 
Discount card: card-1234 percent 30%

And create file output.txt
