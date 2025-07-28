# Spring E-commerce

## Project Overview
Spring E-commerce is a demo project for an online storefront application built with Spring Boot. It provides APIs for managing products, orders, users, and payments.

## Setup and Installation

1. **Clone the repository:**
   ```bash
   git clone [repository-url]
   cd spring-ecommerce
   ```

2. **Database Configuration:**
   Ensure that you have MySQL running and update the `src/main/resources/application.properties` file with your database credentials:
   ```properties
   spring.datasource.username=your-username
   spring.datasource.password=your-password
   ```

3. **Install Dependencies:**
   ```bash
   ./mvnw clean install
   ```

## API Testing Instructions
You can test APIs using tools like Postman or Swagger UI.
- Swagger UI is available at `http://localhost:3001/swagger-ui.html`.


### Example Endpoint Usage
1. **List Products:** GET `/api/products?page=0&size=5`
2. **Create a Product:** POST `/api/products`
3. **Search Products:** GET `/api/products/search?name=value`
4. **Search Product by Category:** GET `/api/products/search?category=value`


## Build and Run Steps

1. **Build the Project:**
   ```bash
   ./mvnw clean package
   ```

2. **Run the Application:**
   ```bash
   ./mvnw spring-boot:run
   ```

The application will start on `http://localhost:3001`.