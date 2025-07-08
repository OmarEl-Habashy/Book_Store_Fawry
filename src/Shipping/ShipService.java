package Shipping;

import Book.PaperBook;
import Interfaces.Shippable;
import Service.Item;

import java.util.ArrayList;
import java.util.List;

public class ShipService {
    private String address;
    private PaperBook paperBook;

    public ShipService(String address, PaperBook paperBook) {
        this.address = address;
        this.paperBook = paperBook;
    }

    public void shipPaperBook() {
        if (paperBook.isAvailable()) {
            paperBook.ship(paperBook, address);
        } else {
            System.out.println("PaperBook is not available for sale.");
        }
    }

    public List<Shippable> getShippableItems(List<Item> items) {
        List<Shippable> shippableItems = new ArrayList<>();
        for (Item item : items) {
            if (item.getBook() instanceof Shippable) {
                shippableItems.add((Shippable) item.getBook());
            }
        }
        return shippableItems;
    }
}
