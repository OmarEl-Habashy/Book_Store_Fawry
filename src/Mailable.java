import Book.EBook;

public interface Mailable {
    void sendWithMail(EBook book, String email);
}
