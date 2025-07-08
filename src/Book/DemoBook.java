package Book;

public class DemoBook extends Book{
    public DemoBook(String isbn, String title, String releaseYear, double price, Boolean ForSale) {
        super(isbn, title, releaseYear, 0, false);
    }
}
