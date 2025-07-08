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

        for (Item item : items) {
            if (item.getBook().getIsbn().equals(book.getIsbn())) {
                System.out.println("Paper Book " + book.getTitle() + " is already in the cart.");
                return false;
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
                System.out.println("E-Book " + book.getTitle() + " is already in the cart.");
                return false;
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
}