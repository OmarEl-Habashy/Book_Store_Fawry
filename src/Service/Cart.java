package Service;
import Book.*;

public class Cart {
    private java.util.List<PaperBook> P_Books;
    private java.util.List<EBook> E_Books;

    public Cart(){
        this.P_Books = new java.util.ArrayList<>();
        this.E_Books = new java.util.ArrayList<>();
    }

    public boolean addPaperBook(PaperBook book, int quantity) {
        if (!book.isAvailable()) {
            System.out.println("Paper Book " + book.getTitle() + " is out of stock.");
            return false;
        }
        for (PaperBook existingBook : P_Books) {
            if (existingBook.getTitle().equals(book.getTitle())) {
                System.out.println("Paper Book " + book.getTitle() + " is already in the cart.");
                return false;
            }
        }
        for (int i = 0; i < quantity; i++) {
            P_Books.add(book);
        }
        return true;
    }

    public boolean addEBook(EBook book, int quantity) {
        if (!book.isAvailable()) {
            System.out.println("E-Book " + book.getTitle() + " is out of stock.");
            return false;
        }
        for (EBook existingBook : E_Books) {
            if (existingBook.getTitle().equals(book.getTitle())) {
                System.out.println("E-Book " + book.getTitle() + " is already in the cart.");
                return false;
            }
        }
        return true;
    }


}
