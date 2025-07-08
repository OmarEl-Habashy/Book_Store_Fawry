package Shipping;

import Book.EBook;
import Interfaces.Mailable;
import Service.Item;

import java.util.ArrayList;
import java.util.List;

public class MailService {
    String email;
    EBook ebook;

    public MailService(String email, EBook ebook) {
        this.email = email;
        this.ebook = ebook;
    }

    public void sendEBook() {
        if (ebook.isAvailable()) {
            ebook.sendWithMail(ebook, email);
        } else {
            System.out.println("EBook is not available for sale.");
        }
    }
    public List<Mailable> getMailableItems(List<Item> items) {
        List<Mailable> mailableItems = new ArrayList<>();
        for (Item item : items) {
            if (item.getBook() instanceof Mailable) {
                mailableItems.add((Mailable) item.getBook());
            }
        }
        return mailableItems;
    }
}
