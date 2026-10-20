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
        borrowedBy = null;
    }

    public String getIsbn(){return isbn;}
    public String getTitle(){return title;}
    public String getAuthor(){return author;}
    public boolean isAvailable(){return isAvailable;}
    public String getBorrowedBy(){return borrowedBy;}

    public void setAvailable(boolean isAvailable){
        this.isAvailable = isAvailable;
    }

    public void setBorrowedBy(String userId){
        this.borrowedBy = userId;
        if(userId != null){
            this.isAvailable = false;
        }else{
            this.isAvailable = true;
        }
    }

    public String getDetails(){
        return "[" + isbn + "] " + title + " by " + author;
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

    public String getId(){return id;}
    public String getName(){return name;}

    public void borrowBook(Book book){
        borrowedBooks.add(book);
        book.setBorrowedBy(id);
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
        return "User [" + id + "]: " + name;
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
            if(u.getId().equals(id)){
                return u;
            }
        }
        return null;
    }

    public void borrowBook(String id, String isbn){
        Book b = findBookByIsbn(isbn);
        User u = findUserById(id);

        if(b == null || u == null){
            System.out.println("Invalid user or book");
            return;
        }

        if(!b.isAvailable()){
            System.out.println("Book not available");
            return;
        }

        u.borrowBook(b);
        System.out.println(u.getName() + " borrowed " + b.getTitle());
    }

    public void returnBook(String id, String isbn){
        User u = findUserById(id);
        Book b = findBookByIsbn(isbn);

        if(u == null || b == null){
            System.out.println("Invalid user or book");
            return;
        }

        u.returnBook(b);
        System.out.println(u.getName() + " returned " + b.getTitle());
    }

    public ArrayList<Book> searchByTitle(String keyword){
        
        ArrayList<Book> result = new ArrayList<>();

        for(Book b : book){
            if(b.getTitle().toLowerCase().contains(keyword.toLowerCase())){
                result.add(b);
            }
        }
        return result;
    }

    public ArrayList<Book> searchByAuthor(String keyword){

        ArrayList<Book> result = new ArrayList<>();

        for(Book b : book){
            if(b.getAuthor().toLowerCase().contains(keyword.toLowerCase())){
                result.add(b);
            }
        }
        return result;
    }

    public ArrayList<Book> getAvailableBooks(){
        ArrayList<Book> result = new ArrayList<>();

        for(Book b : book){
            if(b.isAvailable()){
                result.add(b);
            }
        }
        return result;
    }
}

public class SearchFunctionality {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Read book 1 information
        String isbn1 = scanner.nextLine();
        String title1 = scanner.nextLine();
        String author1 = scanner.nextLine();
        
        // Read book 2 information
        String isbn2 = scanner.nextLine();
        String title2 = scanner.nextLine();
        String author2 = scanner.nextLine();
        
        // Read book 3 information
        String isbn3 = scanner.nextLine();
        String title3 = scanner.nextLine();
        String author3 = scanner.nextLine();
        
        // Read user information
        String userId = scanner.nextLine();
        String userName = scanner.nextLine();
        
        // Read borrow operation
        String borrowOp = scanner.nextLine();
        
        // Read search keywords
        String titleKeyword = scanner.nextLine();
        String authorKeyword = scanner.nextLine();

        // Create Book objects
        Book book1 = new Book(isbn1, title1, author1);
        Book book2 = new Book(isbn2, title2, author2);
        Book book3 = new Book(isbn3, title3, author3);

        User user = new User(userId, userName);

        Library library = new Library();
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);

        library.registerUser(user);

        String[] parts = borrowOp.split(":");
        String opUserId = parts[0];
        String opIsbn = parts[1];
        library.borrowBook(opUserId, opIsbn);

        System.out.println("Title search '"+titleKeyword+"':");
        ArrayList<Book> findBook = library.searchByTitle(titleKeyword);

        if(findBook.isEmpty()){
            System.out.println("No books found");
        }else{
            for(Book book : findBook){
                System.out.println(book.getDetails());
            }
        }

        System.out.println("Author search '"+authorKeyword+"':");
        ArrayList<Book> foundBook = library.searchByAuthor(authorKeyword);

        if(foundBook.isEmpty()){
            System.out.println("No books found");
        }else{
            for(Book book : foundBook){
                System.out.println(book.getDetails());
            }
        }

        System.out.println("Available books:");
        for(Book book : library.getAvailableBooks()){
            System.out.println(book.getDetails());
        }
    }
}
