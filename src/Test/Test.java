package Test;

import Book.*;
import Customer.Customer;
import Service.*;

public class Test {
    public static void main(String[] args) {
        System.out.println("Starting Bookstore Tests...");

        Inventory inventory = new Inventory();
        Customer customer = new Customer("Test Customer", 1000, "123 Test St", "test@example.com");

        PaperBook paperBook1 = new PaperBook("123", "Test Paper Book", "2022", 20.0, true, 5, true);
        PaperBook paperBook2 = new PaperBook("124", "Limited Stock Book", "2022", 30.0, true, 2, true);
        EBook eBook = new EBook("456", "Test E-Book", "2023", 15.0, true, "PDF");
        PaperBook outdatedBook = new PaperBook("789", "Outdated Book", "1980", 10.0, true, 10, true);

        inventory.addBook(paperBook1);
        inventory.addBook(paperBook2);
        inventory.addBook(eBook);
        inventory.addBook(outdatedBook);

        testAddingToCart(paperBook1, paperBook2, eBook, outdatedBook);
        testRemovingFromInventory(inventory, paperBook1);
        testCheckout(customer, paperBook2, eBook);
        testExceedingStock(paperBook2);
        testSellingOutCompletely(paperBook2);

        System.out.println("\nAll tests completed!");
    }

    private static void testAddingToCart(PaperBook paperBook1, PaperBook paperBook2, EBook eBook, PaperBook outdatedBook) {
        System.out.println("\n=== Testing Adding Books to Cart ===");

        Cart cart = new Cart();

        boolean result1 = cart.addBook(paperBook1, 2);
        System.out.println("Adding 2 copies of paper book to cart: " + (result1 ? "SUCCESS" : "FAILED"));

        boolean result2 = cart.addBook(eBook, 1);
        System.out.println("Adding 1 copy of e-book to cart: " + (result2 ? "SUCCESS" : "FAILED"));

        boolean result3 = cart.addBook(paperBook1, 10);
        System.out.println("Adding more than available stock: " + (!result3 ? "CORRECTLY REJECTED" : "FAILED - SHOULD REJECT"));

        boolean result4 = cart.addBook(paperBook1, 1);
        System.out.println("Adding to existing cart item: " + (result4 ? "SUCCESS" : "FAILED"));

        System.out.println("Cart has " + cart.getItems().size() + " items (expected: 2)");
        System.out.println("Total price: $" + cart.getTotalPrice());

        System.out.println("Cart contents:");
        for (Item item : cart.getItems()) {
            System.out.println(" - " + item.getQuantity() + "x " + item.getBook().getTitle() +
                    " - $" + item.getTotalPrice());
        }
    }

    private static void testRemovingFromInventory(Inventory inventory, PaperBook book) {
        System.out.println("\n=== Testing Removing Books from Inventory ===");

        int initialSize = inventory.getBooks().size();
        System.out.println("Initial inventory size: " + initialSize);

        inventory.removeBook(book);
        int newSize = inventory.getBooks().size();

        System.out.println("After removal: " + newSize);
        System.out.println("Book removal: " + ((initialSize - newSize == 1) ? "SUCCESS" : "FAILED"));

        inventory.addBook(book);
    }

    private static void testCheckout(Customer customer, PaperBook paperBook, EBook eBook) {
        System.out.println("\n=== Testing Checkout Process ===");

        Cart cart = new Cart();
        cart.addBook(paperBook, 1);
        cart.addBook(eBook, 1);

        double initialBalance = customer.getBalance();
        System.out.println("Initial customer balance: $" + initialBalance);
        System.out.println("Initial paper book stock: " + paperBook.getStock());

        try {
            Checkout checkout = new Checkout(customer, cart);
            checkout.processCheckout();
            System.out.println("Checkout: SUCCESS");

            System.out.println("New balance: $" + customer.getBalance());
            System.out.println("Balance deduction: " +
                    (initialBalance > customer.getBalance() ? "SUCCESS" : "FAILED"));

            System.out.println("New paper book stock: " + paperBook.getStock());
            System.out.println("Stock reduction: " +
                    (paperBook.getStock() == 1 ? "SUCCESS" : "FAILED"));

        } catch (Exception e) {
            System.out.println("Checkout failed with error: " + e.getMessage());
            System.out.println("Checkout: FAILED");
        }
    }

    private static void testExceedingStock(PaperBook paperBook) {
        System.out.println("\n=== Testing Exceeding Stock Limits ===");

        Cart cart = new Cart();
        double initialStock = paperBook.getStock();
        System.out.println("Current stock: " + initialStock);

        boolean result = cart.addBook(paperBook, (int)initialStock + 5);
        System.out.println("Adding more than available: " +
                (!result ? "CORRECTLY REJECTED" : "FAILED - SHOULD REJECT"));

        result = cart.addBook(paperBook, (int)initialStock);
        System.out.println("Adding exactly what's available: " +
                (result ? "SUCCESS" : "FAILED"));
    }

    private static void testSellingOutCompletely(PaperBook paperBook) {
        System.out.println("\n=== Testing Selling Out Completely ===");

        double currentStock = paperBook.getStock();
        System.out.println("Current stock: " + currentStock);

        if (currentStock > 0) {
            Customer customer = new Customer("Test Customer", 1000, "123 Test St", "test@example.com");
            Cart cart = new Cart();

            cart.addBook(paperBook, (int)currentStock);

            try {
                Checkout checkout = new Checkout(customer, cart);
                checkout.processCheckout();

                System.out.println("New stock: " + paperBook.getStock());
                System.out.println("Book availability: " +
                        (!paperBook.isAvailable() ? "CORRECTLY MARKED UNAVAILABLE" : "FAILED - SHOULD BE UNAVAILABLE"));

                Cart newCart = new Cart();
                boolean result = newCart.addBook(paperBook, 1);
                System.out.println("Adding sold-out book: " +
                        (!result ? "CORRECTLY REJECTED" : "FAILED - SHOULD REJECT"));

            } catch (Exception e) {
                System.out.println("Checkout failed with error: " + e.getMessage());
            }
        } else {
            System.out.println("Book is already sold out. Skipping test.");
        }
    }
}