package Service;
import Book.Book;
public class Item {
    private Book book;
    private int quantity;

    public Item(Book book, int quantity){
        this.book = book;
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getTotalPrice() {
        return book.getPrice() * quantity;
    }

}
