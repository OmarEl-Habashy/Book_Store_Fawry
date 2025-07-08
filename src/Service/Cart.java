package Service;
import Book.*;

public class Cart {
    private java.util.List<Item> items;

    public Cart() {
        this.items = new java.util.ArrayList<>();
    }

    public int getPaperBookCount() {
        int count = 0;
        for (Item item : items) {
            if (item.getBook() instanceof PaperBook) {
                count += item.getQuantity();
            }
        }
        return count;
    }

    public boolean addBook(PaperBook book, int quantity) {
        if (!book.isAvailable()) {
            System.out.println("Paper Book " + book.getTitle() +
                    (book.isOutDated() ? " is Outdated" : " is out of stock"));
            return false;
        }

        if (quantity > book.getStock()) {
            System.out.println("Cannot add " + quantity + " copies of " + book.getTitle() +
                    ". Only " + (int)book.getStock() + " in stock.");
            return false;
        }

        for (Item item : items) {
            if (item.getBook().getIsbn().equals(book.getIsbn())) {
                int existingQuantity = item.getQuantity();

                if (existingQuantity + quantity > book.getStock()) {
                    int remainingStock = (int)book.getStock() - existingQuantity;
                    System.out.println("Cannot add " + quantity + " copies. You already have " +
                            existingQuantity + " in cart. Available stock: " + remainingStock);
                    return false;
                }

                item.setQuantity(existingQuantity + quantity);
                System.out.println("Updated quantity of " + book.getTitle() + " in cart to " + item.getQuantity());
                return true;
            }
        }

        items.add(new Item(book, quantity));
        return true;
    }

    public boolean addBook(EBook book, int quantity) {
        if (!book.isAvailable()) {
            System.out.println("E-Book " + book.getTitle() +
                    (book.isOutDated() ? " is Outdated" : " is not available"));
            return false;
        }

        for (Item item : items) {
            if (item.getBook().getIsbn().equals(book.getIsbn())) {
                item.setQuantity(item.getQuantity() + quantity);
                System.out.println("Updated quantity of " + book.getTitle() + " in cart to " + item.getQuantity());
                return true;
            }
        }

        items.add(new Item(book, quantity));
        return true;
    }

    public java.util.List<Book> getBooks() {
        java.util.List<Book> books = new java.util.ArrayList<>();
        for (Item item : items) {
            books.add(item.getBook());
        }
        return books;
    }

    public java.util.List<Item> getItems() {
        return new java.util.ArrayList<>(items);
    }

    public double getTotalPrice() {
        double total = 0;
        for (Item item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public void previewOrder() {
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("** Order Preview **");
        for (Item item : items) {
            Book book = item.getBook();
            String type = (book instanceof PaperBook) ? "Paper Book" : "E-Book";
            System.out.println(item.getQuantity() + "x " + type + ": " + book.getTitle() +
                    " - $" + item.getTotalPrice());
        }

        double subtotal = getTotalPrice();
        double shippingCost = SHIPPING_RATE_PER_BOOK * getPaperBookCount();
        double total = subtotal + shippingCost;

        System.out.println("----------------------");
        System.out.println("Subtotal:\t$" + subtotal);
        System.out.println("Shipping:\t$" + shippingCost);
        System.out.println("Total:\t\t$" + total);
        System.out.println("----------------------");
    }

    private static final double SHIPPING_RATE_PER_BOOK = 10;
}