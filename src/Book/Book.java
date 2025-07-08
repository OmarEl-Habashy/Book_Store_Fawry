package Book;

public abstract class Book {
    private String isbn;
    private String title;
    private String releaseYear;
    private double price;
    private Boolean ForSale = null;

    public Book(String isbn, String title, String releaseYear, double price,Boolean ForSale){
        this.isbn = isbn;
        this.title = title;
        this.releaseYear = releaseYear;
        this.price=price;
        this.ForSale = ForSale;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Boolean isForSale() {
        return ForSale && !isOutDated();
    }
    public Boolean isOutDated(){
        int year = Integer.parseInt(releaseYear);
        return year < 1990;
    }
}
