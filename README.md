# Bookstore Console Application

A Java-based console application for managing a bookstore, allowing users to browse, add items to cart, and checkout.

## Features

- **Book Management**: Support for different types of books (PaperBook, EBook, DemoBook)
- **Shopping Cart**: Add and manage books in a shopping cart
- **Checkout System**: Complete purchases with shipping calculations
- **Inventory Management**: Track book availability and stock levels
- **Customer Management**: Customer balance and profile information

## Project Structure

The application is organized into several packages:

- **Book**: Contains book-related classes
  - `Book.java`: Abstract base class for all book types
  - `PaperBook.java`: Physical books with stock management
  - `EBook.java`: Digital books that can be emailed
  - `DemoBook.java`: Demo/preview books that cannot be purchased

- **Customer**: Contains customer-related classes
  - `Customer.java`: Manages customer information and balance

- **Service**: Contains core business logic
  - `Inventory.java`: Manages the bookstore's book collection
  - `Cart.java`: Handles the shopping cart functionality
  - `Checkout.java`: Processes orders and payments
  - `Item.java`: Represents an item in the cart with quantity

- **Shipping**: Contains shipping-related classes
  - `ShipService.java`: Handles shipping of physical books
  - `MailService.java`: Handles emailing of electronic books

- **Interfaces**: Contains application interfaces
  - `Shippable.java`: Interface for items that can be shipped
  - `Mailable.java`: Interface for items that can be emailed

## How to Use

1. Run the `Main.java` file to start the application
2. Navigate through the menu options:
   - Add books to your cart
   - View your current cart
   - Checkout to complete your purchase
   - View your account details
   - Exit the application

## Testing

The application includes a test class (`Test.java`) that verifies key functionality:

- Adding books to cart
- Removing books from inventory
- Processing checkout
- Handling stock limits
- Managing sold-out books

To run tests, execute the `Test.java` file in the Test package.
