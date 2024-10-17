# BMG (Book Management)

## Description
BMG is a basic REST API built with Java and Spring Boot that manages a book rental system. The application implements Spring Security to handle user authentication and authorization, with different roles such as USER and ADMIN. 
## Functionality
- **Registration and Authenticaion**: Customers and Employees have different system for registration and login. During Login process a unique JWT Token is generated that holds information such as user role. 
  - Customers Authenticaion: Email and password.
  - Eployees Authenticaion: Email, password and employeeID. 
- **Role Management**: Users are assigned roles such as USER or ADMIN.
  - **ADMIN**: For Employees who can modify books content, add new books, complete customer orders. Emloyees have different interface. 
  - **USER**: Can browse available books and rent them.
- **Role-Based Access Control**: Different pages and functionalities are accessible based on the user’s role.

## API Features
- **Customer Endpoints**:
  - Register a new user
  - Log in and receive a JWT token
  - View available books
  - Make an order
- **Admin Endpoints**:
  - Add new books
  - Complete customer order
  - Check users and books they rent
  - Edit data (customers, books)
  - View all users

## Technologies Used
- **Backend**: Java, Spring Boot
- **Security**: Spring Security
- **Database**: MySQL
- **Testing**: Postman for API testing

