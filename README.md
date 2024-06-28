# DigitalStore

## Overview
This is an online shopping website built using Spring Boot, MongoDB. The application allows users to browse products, purchase them.

## Features
- User authentication and authorization
- Seller , crud operation on products
- Order processing [ Soon ]
- Admin panel for managing products and orders [ Soon ]

## Technologies Used
- **Spring Boot**: Backend framework
- **MongoDB**: NoSQL database
- **Docker**: Containerization
- **Thymeleaf**: Template engine for rendering web pages
- **Spring Data MongoDB**: MongoDB integration with Spring Boot

## Getting Started

### Prerequisites
- Java 21 or higher
- Maven 3.9+
- Docker ( optional )

### Installation

1. **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/DigitalStore.git
    cd DigitalStore
    ```

2. **Build the project:**
    ```bash
    mvn clean install
    ```

3. **Start the Spring Boot application:**
    ```bash
    mvn spring-boot:run
    ```

### Running with Docker

1. **Build the Docker image:**
    ```bash
    docker build -t DigitalStore .
    ```

2. **Run the Docker container:**
    ```bash
    docker run -d -p 8080:8080 --name DigitalStore
    ```

### Accessing the Application
- The application will be available at `http://localhost:8080`.

## Configuration

### Application Properties
The main configuration file is `src/main/resources/application.properties`. Here you can set various properties like MongoDB connection details, server port, etc.
