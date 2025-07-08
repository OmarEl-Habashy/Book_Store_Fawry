package Book;

public class EBook extends Book implements Mailable {
    private String FileType;

    public EBook(String isbn, String title, String releaseYear, double price, Boolean ForSale,String FileType) {
        super(isbn, title, releaseYear, price, ForSale);
        this.FileType = FileType;
    }

    public String getFileType() {
        return FileType;
    }
    public void setFileType(String fileType) {
        FileType = fileType;
    }
    public boolean isAvailable() {
        return isForSale();
    }
}
