# BMG (Book Management)

## Description
BMG is a basic REST API built with Java and Spring Boot that manages a book rental system. The application implements Spring Security to handle user authentication and authorization, with different roles such as USER and ADMIN. The front-end is minimal and primarily serves to demonstrate role-based access control. API interactions are primarily tested using Postman.

## Functionality
- **User Registration and Authentication**: Users can register and log in to the system.
- **Role Management**: Users are assigned roles such as USER or ADMIN.
  - **ADMIN**: Can access additional pages and functionalities, such as adding new books to the system.
  - **USER**: Can browse available books and rent them.
- **Book Rental System**: Users can rent books, and admins can manage the book inventory.
- **Role-Based Access Control**: Different pages and functionalities are accessible based on the user’s role.

## API Features
- **User Endpoints**:
  - Register a new user
  - Log in and receive a JWT token
  - View available books
  - Rent a book
- **Admin Endpoints**:
  - Add new books
  - View all users
  - Manage book inventory

## Technologies Used
- **Backend**: Java, Spring Boot
- **Security**: Spring Security
- **Database**: MySQL
- **Testing**: Postman for API testing

