package Service;
import Book.*;

import Customer.Customer;
import Shipping.MailService;
import Shipping.ShipService;

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
    public void processCheckout(){
        if (cart.getItems().isEmpty()){
            throw new IllegalStateException("Cart is empty");
        }

        validateItem(cart.getItems());
        double sumWithoutShipping = cart.getTotalPrice();

        totalPrice = sumWithoutShipping + SHIPPING_RATE_PER_BOOK * cart.getPaperBookCount();

        if (customer.getBalance() < totalPrice) {
            throw new IllegalStateException("Insufficient balance for checkout. Your balance is " + customer.getBalance() +
                    ", but the total amount is " + totalPrice);
        }

        customer.deductBalance(totalPrice);
        processEachitem(cart.getItems());
        System.out.println("** Checkout successful! **");
        System.out.println("** Thank you for your purchase, " + customer.getName() + "! **");


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

    private void processEachitem(java.util.List<Item> items){
        for(Item item : items) {
            if(item.getBook() instanceof PaperBook) {
                ShipService shipService = new ShipService(customer.getAddress(), (PaperBook)item.getBook());
                shipService.shipPaperBook();
            } else if(item.getBook() instanceof EBook) {
                MailService mailService = new MailService(customer.getEmail(),(EBook)item.getBook());
                mailService.sendEBook();
            }
        }
    }
}
