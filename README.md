# API Manage Product

This is a Spring Boot application for managing products.

## Setup Instructions

### 1. Database Configuration

Before running the application, you **MUST** configure your database connection strings.

1. Locate the file `src/main/resources/application.properties.example`.
2. Copy it and rename the copy to `application.properties` in the same directory (`src/main/resources/`).
3. Open `src/main/resources/application.properties` and update the following lines with your own MySQL database credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/<your_database_name>?useSSL=false&serverTimezone=UTC
spring.datasource.username=<your_username>
spring.datasource.password=<your_password>
```

Ensure you have a MySQL database running and the schema `<your_database_name>` exists.

### 2. Run the Application

You can run the application using Maven:

```bash
./mvnw spring-boot:run
```

The application will start on port 8080 (default).

---

## API Endpoints

### 1. Get All Products

Retrieve a list of all active (non-deleted) products.

- **URL**: `/api/products`
- **Method**: `GET`
- **Success Response**:
  - **Code**: 200 OK
  - **Content**:
    ```json
    {
      "status": "OK",
      "message": "Get all products successfully",
      "data": [
        {
          "id": 1,
          "name": "Product A",
          "price": 10000.0,
          "description": "Description A",
          "stock": 50
        }
      ]
    }
    ```

### 2. Add Product

Create a new product.

- **URL**: `/api/products/add`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "name": "New Product",
    "price": 15000.0,
    "description": "Product Description",
    "stock": 100
  }
  ```
- **Success Response**:
  - **Code**: 201 CREATED
  - **Content**:
    ```json
    {
      "status": 201,
      "message": "Product added successfully",
      "data": {
        "id": 2,
        "name": "New Product",
        "price": 15000.0,
        "description": "Product Description",
        "stock": 100
      }
    }
    ```

### 3. Update Product

Update an existing product's details.

- **URL**: `/api/products/update/{id}`
- **Method**: `PUT`
- **Request Body**:
  ```json
  {
    "name": "Updated Name",
    "price": 20000.0,
    "description": "Updated Description",
    "stock": 80
  }
  ```
- **Success Response**:
  - **Code**: 200 OK
  - **Content**:
    ```json
    {
      "status": 200,
      "message": "Product updated successfully",
      "data": {
        "id": 2,
        "name": "Updated Name",
        "price": 20000.0,
        "description": "Updated Description",
        "stock": 80
      }
    }
    ```

### 4. Update Stock

Reduce the stock of a product (simulating a sale).

- **URL**: `/api/products/update-stock/{id}`
- **Method**: `PATCH`
- **Request Body**:
  ```json
  {
    "sellStock": 5
  }
  ```
- **Success Response**:
  - **Code**: 200 OK
  - **Content**:
    ```json
    {
      "status": 200,
      "message": "Stock updated successfully",
      "data": {
        "id": 2,
        "name": "Updated Name",
        "price": 20000.0,
        "description": "Updated Description",
        "stock": 75
      }
    }
    ```

### 5. Delete Product

Soft delete a product by ID.

- **URL**: `/api/products/delete/{id}`
- **Method**: `DELETE`
- **Success Response**:
  - **Code**: 204 NO CONTENT

## Edge Cases for Validation Testing

Use the following scenarios to test the robustness and validation logic of the API.

### 1. Add / Update Product Validation

Test these with `POST /api/products/add` or `PUT /api/products/update/{id}`.

| Scenario           | Input Data                   | Expected Result                                                      |
| :----------------- | :--------------------------- | :------------------------------------------------------------------- |
| **Empty Name**     | `"name": ""`                 | **400 Bad Request**<br>Validation error: Name is required            |
| **Negative Price** | `"price": -100`              | **400 Bad Request**<br>Validation error: Price must be > 0           |
| **Minimum Stock**  | `"stock": -1`                | **400 Bad Request**<br>Validation error: Stock must be >= 0          |
| **Duplicate Name** | `"name": "Existing Product"` | **400 Bad Request**<br>Validation error: Product name already exists |

**NOTES:** Updating a product with its current name will not trigger a duplicate name error.

### 2. Update Stock Validation

Test with `PATCH /api/products/update-stock/{id}`.

| Scenario                | Input Data                             | Expected Result                                                                          |
| :---------------------- | :------------------------------------- | :--------------------------------------------------------------------------------------- |
| **Negative Sell Stock** | `"sellStock": -5`                      | **400 Bad Request**<br>Validation error: Sell stock must be positive (handled by `@Min`) |
| **Insufficient Stock**  | `"sellStock": 100` (where stock < 100) | **400 Bad Request**<br>Validation error: Stock is not enough                             |

### 3. General Edge Cases

| Scenario              | Action                                | Expected Result                                                      |
| :-------------------- | :------------------------------------ | :------------------------------------------------------------------- |
| **Product Not Found** | Update/Stock/Delete with ID `99999`   | **404 Not Found**<br>Error: Product not found                        |
| **Invalid ID Format** | Request to `/api/products/update/abc` | **400 Bad Request**<br> Validation error: Invalid value for id 'abc' |
