package bcu.cmp5332.librarysystem.data;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class BookDataManager implements DataManager { //implements datamanager so it must have loaddata and storedata methods
    
    private final String RESOURCE = "./resources/data/books.txt"; //the file address to locate the books.txt file as a variable to call easier
    
    @Override
    public void loadData(Library library) throws IOException, LibraryException {
        try (Scanner sc = new Scanner(new File(RESOURCE))) { //scans the .txt file
            int line_idx = 1; //starts from line 1
            while (sc.hasNextLine()) {//checks for a next line and runs the code
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);//separates the string into parts called properties removing the separators
                try {
                    int id = Integer.parseInt(properties[0]); //turns the string id int an int
                    String title = properties[1]; //creating the variables needed to create a book object
                    String author = properties[2];
                    String publicationYear = properties[3];
                    String publisher = properties[4];
                    boolean isDeleted = Boolean.parseBoolean(properties[5]); //generating boolean from string by using parse boolean
                    Book book = new Book(id, title, author, publicationYear, publisher); //generates the book this happens for each book in the file
                    
                    if (isDeleted) { //checks the flag is deleted if true soft deletes the book 
                    	book.softDeleteBook();
                    }
                    
                    library.addBook(book);//adds books to the library
                } catch (NumberFormatException ex) {
                    throw new LibraryException("Unable to parse book id " + properties[0] + " on line " + line_idx
                        + "\nError: " + ex); //error occurs if a line cannot be parsed for example if the format is wrong or the data is wrong or corrupted
                }
                line_idx++;//proceeds to the next time and loops
            }
        }
    }
    
    @Override
    public void storeData(Library library) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) { //this writes to the .txt file will fail if the file not present 
            for (Book book : library.getBooks()) { //goes through eachbook in the libary list
                out.print(book.getId() + SEPARATOR); //prints the details and adds a the separator
                out.print(book.getTitle() + SEPARATOR);
                out.print(book.getAuthor() + SEPARATOR);
                out.print(book.getPublicationYear() + SEPARATOR);
                out.print(book.getPublisher() + SEPARATOR);
                out.print(book.getDeletedStatus() + SEPARATOR);
                out.println();
            }
        }
    }
}
 