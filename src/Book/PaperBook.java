package Book;

import Interfaces.Shippable;

public class PaperBook extends Book implements Shippable {
    private double Stock;
    private boolean Shipping;

    public PaperBook(String isbn, String title, String releaseYear, double price, Boolean ForSale, double Stock, boolean Shipping) {
        super(isbn, title, releaseYear, price, ForSale);
        this.Stock = Stock;
        this.Shipping = Shipping;

    }

    public double getStock() {
        return Stock;
    }
    public void setStock(double stock) {
        Stock = stock;
    }
    public boolean isShipping() {
        return Shipping;
    }
    public void setShipping(boolean shipping) {
        Shipping = shipping;
    }
    public boolean isAvailable(){
        return Stock > 0 && isForSale();
    }

    @Override
    public void ship(PaperBook book, String address){
        System.out.println("Book store: Sending " + book.getTitle() + " to " + address);
    }


}
