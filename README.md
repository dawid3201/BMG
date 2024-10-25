# BMG (Book Management)

## Description
BMG is a book managment website made with Java and Spring Boot. On the website Users can register, login and order books. There is a separate interface for Employees and Customers. Employees can add and modify books, modify customer information, confirm users orders. Everything is protected with Spring Security so normal Customers cannot access Emloyees data. 
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
- **Emloyee Endpoints**:
  - Add new books
  - Complete customer order
  - Check users and books they rent
  - Edit data (customers, books)
  - View all users
  - View all orders

## Technologies Used
- **Backend**: Java, Spring Boot
- **Security**: Spring Security, JWT, OAuth 2.0
- **Database**: MySQL
- **Testing**: Postman for API testing

