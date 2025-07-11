import Book.*;
import Customer.Customer;
import Service.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Customer customer = new Customer("Omar", 10000, "Cairo,Egypt", "omar@gmail.com");


        PaperBook ATaleOfTwoCities = new PaperBook("1", "A Tale of Two Cities", "1859", 350, true, 10, true);
        PaperBook theArtOfWar = new PaperBook("5", "The Art of War", "-500", 150, true, 5, false);
        PaperBook theGreatGatsby = new PaperBook("4", "The Great Gatsby", "2020", 300, true, 20, false);
        EBook digitalMarketing = new EBook("2", "Digital Marketing", "2020", 200, true, "PDF");
        DemoBook demoBook = new DemoBook("3", "Demo Book", "2021");

        Inventory inventory = new Inventory();
        inventory.addBook(ATaleOfTwoCities);
        inventory.addBook(digitalMarketing);
        inventory.addBook(demoBook);
        inventory.addBook(theArtOfWar);
        inventory.addBook(theGreatGatsby);

        Cart cart = new Cart();

        System.out.println("Welcome to the BookStore Console App!");
        while (true) {
            System.out.println("Enter the number of the operation you want to perform:");
            System.out.println("1. Add Book to cart");
            System.out.println("2. View cart");
            System.out.println("3. Checkout");
            System.out.println("4. View Account Details");
            System.out.println("5. Exit");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    Book selectedBook = inventory.selecetBook(sc);
                    if (selectedBook != null) {
                        int quantity = 0;
                        if (selectedBook instanceof PaperBook) {
                            while (true) {
                                try {
                                    System.out.print("Enter quantity: ");
                                    quantity = sc.nextInt();
                                    if (quantity <= 0) {
                                        System.out.println("Quantity must be a positive number. Please try again.");
                                    } else {
                                        break;
                                    }
                                } catch (java.util.InputMismatchException e) {
                                    System.out.println("Invalid input! Please enter a valid number.");
                                    sc.next();
                                }
                            }

                        } else if (selectedBook instanceof EBook) {
                            while (true) {
                                try {
                                    System.out.print("Enter quantity: ");
                                    quantity = sc.nextInt();
                                    if (quantity <= 0) {
                                        System.out.println("Quantity must be a positive number. Please try again.");
                                    } else {
                                        break;
                                    }
                                } catch (java.util.InputMismatchException e) {
                                    System.out.println("Invalid input! Please enter a valid number.");
                                    sc.next();
                                }
                            }
                        } else {
                            System.out.println("Demo Book cannot be added to cart.");
                            continue;
                        }
                        if (selectedBook instanceof PaperBook) {
                            boolean added = cart.addBook((PaperBook) selectedBook, quantity);
                            if (added)
                                System.out.println(quantity + " " + selectedBook.getTitle() + "(s) added to cart.");
                        } else {
                            boolean added = cart.addBook((EBook) selectedBook, quantity);
                            if (added)
                                System.out.println(quantity + " " + selectedBook.getTitle() + "(s) added to cart.");
                        }

                    }
                    break;

                case 2:
                    if(cart.getBooks().isEmpty()){
                        System.out.println("Your cart is empty.");
                    }
                    else{
                        System.out.println("Items in your cart:");
                        for(Item item : cart.getItems()) {
                            System.out.println(item.getQuantity() + "x " + item.getBook().getTitle() +
                                    " - Price: $" + item.getTotalPrice());
                        }
                    }
                    break;

                case 3:
                    Checkout checkout = new Checkout(customer, cart);
                    try {
                        checkout.processCheckout();
                        cart = new Cart();
                    } catch (IllegalStateException e) {
                        System.out.println("Checkout failed: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("** Account Details **");
                    System.out.println("Name: " + customer.getName() + ",\nBalance: $" + customer.getBalance());
                    System.out.println("Address: " + customer.getAddress() + ",\nEmail: " + customer.getEmail());
                    System.out.println("Books in your cart: " + cart.getBooks().size());

                    break;

                case 5:
                    System.out.println("Exiting the application. Thank you!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }

        }
    }
}
