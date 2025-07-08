package Service;

import Book.Book;

public class Inventory {
    private java.util.List<Book> books;

    public Inventory() {
        this.books = new java.util.ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }
    public void removeBook(Book book){
        books.remove(book);
    }
    public java.util.List<Book> getBooks(){
        return new java.util.ArrayList<>(books);
    }
    public Book selecetBook(java.util.Scanner sc){
        while(true){
            System.out.println("\nAvailable Books: ");
            System.out.println("0. Return to main menu");
            for(int i = 0; i < books.size(); i++){
                Book book = books.get(i);
                System.out.println(i + 1 + ". " + book.getTitle() + " - Price: $" + book.getPrice());
            }

            System.out.println("Enter Product name: ");
            int bookNumber = sc.nextInt();
            if (bookNumber == 0) {
                return null;
            }
            if(bookNumber >=1 && bookNumber <= books.size()){
                return books.get(bookNumber - 1);
            }
            System.out.println("Invalid book number! Please try again.");
        }
    }
}
