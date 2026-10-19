import java.util.ArrayList;
import java.util.Scanner;

class Book{
    private String isbn;
    private String title;
    private String author;
    private boolean isAvailable;
    private String borrowedBy;
    
    public Book(String isbn, String title, String author){
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
        this.borrowedBy = null;
    }

    public String getIsbn(){return isbn;}
    public String getTitle(){return title;}
    public String getAuthor(){return author;}
    public boolean getIsAvailable(){return isAvailable;}
    public String getBorrowedBy(){return borrowedBy;}

    public void setBorrowedBy(String userId){
        this.borrowedBy = userId;
        if(userId != null){
            isAvailable = false;
        }else{
            isAvailable = true; 
        }
    }

    public String getDetails(){
        return "[" + isbn +"] " + title + " by " + author;  
    }
}

class User{
    private String id;
    private String name;
    private ArrayList<Book> borrowedBooks;

    public User(String id, String name){
        this.id = id;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getUserId(){return id;}
    public String getName(){return name;}
    
    public void borrowBook(Book book){
        borrowedBooks.add(book);
        book.setBorrowedBy(this.id);
    }

    public void returnBook(Book book){
        borrowedBooks.remove(book);
        book.setBorrowedBy(null);
    }

    public int getBorrowedCount(){
        return borrowedBooks.size();
    }

    @Override
    public String toString(){
        return "User["+id+"]: " + name;
    }
}

class Library{
    private ArrayList<Book> book;
    private ArrayList<User> user;

    public Library(){
        this.book = new ArrayList<>();
        this.user = new ArrayList<>();
    }

    public void addBook(Book book){
        this.book.add(book);
    }

    public void registerUser(User user){
        this.user.add(user);
    }

    public Book findBookByIsbn(String isbn){
        for(Book b : book){
            if(b.getIsbn().equals(isbn)){
                return b;
            }
        }
        return null;
    }

    public User findUserById(String id){
        for(User u : user){
            if(u.getUserId().equals(id)){
                return u;
            }
        }
        return null;
    }

    public void borrowBook(String userId, String isbn){

        Book book = findBookByIsbn(isbn);
        User user = findUserById(userId);

        if(book == null || user == null){
            System.out.println("Invalid book or user");
            return;
        }

        if(!book.getIsAvailable()){
            System.out.println("Book not available");
            return;
        }

        user.borrowBook(book);

        System.out.println(user.getName() + " borrowed " + book.getTitle());
    }

    public void returnBook(String userId, String isbn){

        Book book = findBookByIsbn(isbn);
        User user = findUserById(userId);

        if(book == null || user == null){
            System.out.println("Invalid book or user");
            return;
        }

        user.returnBook(book);

        System.out.println(user.getName() + " returned " + book.getTitle());
    }

    public void printSummary(){
        for(Book b : book){
            String status = b.getIsAvailable() ? "Available" : "Borrowed";
            System.out.println(b.getIsbn() + ": " + status);
        }
    }
}

public class BorrowingSystem {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);

        String isbn1 = scan.nextLine();
        String title1 = scan.nextLine();
        String author1 = scan.nextLine();

        String isbn2 = scan.nextLine();
        String title2 = scan.nextLine();
        String author2 = scan.nextLine();

        String userId = scan.nextLine();
        String userName = scan.nextLine();

        String operationsStr = scan.nextLine();

        Book book1 = new Book(isbn1, title1, author1);
        Book book2 = new Book(isbn2, title2, author2);

        User user = new User(userId, userName);

        Library library = new Library();
        library.addBook(book1);
        library.addBook(book2);
        library.registerUser(user);

        for(String op : operationsStr.split(",")){
            String[] parts = op.split(":");

            String action = parts[0];
            String targetUserId = parts[1];
            String targetIsbn = parts[2];

            if(action.equals("borrow")){
                library.borrowBook(targetUserId, targetIsbn);
            }else if(action.equals("return")){
                library.returnBook(targetUserId, targetIsbn);
            }
        }

        library.printSummary();
    }
}
