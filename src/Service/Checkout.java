package Service;
import Book.*;

import Customer.Customer;
import Interfaces.Mailable;
import Interfaces.Shippable;
import Shipping.MailService;
import Shipping.ShipService;

import java.util.List;

public class Checkout {
    private double totalPrice;
    private Customer customer;
    private Cart cart;
    private static final double SHIPPING_RATE_PER_BOOK= 10;

    public Checkout(Customer customer,Cart cart) {
        this.customer = customer;
        this.totalPrice = 0.0;
        this.cart = cart;
    }
    public void processCheckout() {
        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        validateItem(cart.getItems());
        double sumWithoutShipping = cart.getTotalPrice();

        totalPrice = sumWithoutShipping + SHIPPING_RATE_PER_BOOK * cart.getPaperBookCount();

        if (customer.getBalance() < totalPrice) {
            throw new IllegalStateException("Insufficient balance for checkout. Your balance is " + customer.getBalance() +
                    ", but the total amount is " + totalPrice);
        }

        updateInventoryStock(cart.getItems());

        // Process shipping for paper books
        processShippableItems(cart.getItems());
        // Process mailing for e-books
        processMailableItems(cart.getItems());

        customer.deductBalance(totalPrice);
        System.out.println("** Checkout receipt **");
        for (Item item : cart.getItems()) {
            Book book = item.getBook();
            String type = (book instanceof PaperBook) ? "Paper Book" : "E-Book";
            System.out.println(item.getQuantity() + "x " + type + ": " + book.getTitle() +
                    " - $" + item.getTotalPrice());
        }
        System.out.println("** Checkout successful! **");
        System.out.println("** Thank you for your purchase, " + customer.getName() + "! **");
        System.out.println("Total price: " + totalPrice);
        System.out.println("Your new balance is: " + customer.getBalance() + "\n\n");
    }

    private void validateItem(java.util.List<Item> items) {
        for (Item item : items) {
            Book book = item.getBook();
            if (book instanceof PaperBook) {
                if (!((PaperBook) book).isAvailable()) {
                    throw new IllegalStateException("Book " + book.getTitle() +
                            (book.isOutDated() ? " is outdated." : " is out of stock."));
                }
            } else if (book instanceof EBook) {
                if (!((EBook) book).isAvailable()) {
                    throw new IllegalStateException("Book " + book.getTitle() +
                            (book.isOutDated() ? " is outdated." : " is not available."));
                }
            }
        }

    }
    private void processShippableItems(List<Item> items) {
        ShipService shipService = new ShipService(customer.getAddress(), null);
        List<Shippable> shippableItems = shipService.getShippableItems(items);
        for (Shippable item : shippableItems) {
            PaperBook paperBook = (PaperBook) item;
            paperBook.ship(paperBook, customer.getAddress());
        }
    }

    private void processMailableItems(List<Item> items) {
        MailService mailService = new MailService(customer.getAddress(), null);
        List<Mailable> mailableItems = mailService.getMailableItems(items);
        for (Mailable item : mailableItems) {
            EBook ebook = (EBook) item;
            ebook.sendWithMail(ebook, customer.getEmail());
        }
    }

    private void updateInventoryStock(List<Item> items) {
        for (Item item : items) {
            Book book = item.getBook();
            if (book instanceof PaperBook) {
                PaperBook paperBook = (PaperBook) book;
                double currentStock = paperBook.getStock();
                paperBook.setStock(currentStock - item.getQuantity());

                if (paperBook.getStock() <= 0) {
                    paperBook.setStock(0);
                    paperBook.setForSale(false);
                }
            }
        }
    }


}
