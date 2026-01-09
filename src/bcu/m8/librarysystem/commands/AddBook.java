package bcu.m8.librarysystem.commands;

import bcu.m8.librarysystem.main.LibraryException;
import bcu.m8.librarysystem.model.Book;
import bcu.m8.librarysystem.model.Library;

import java.time.LocalDate;

public class AddBook implements  Command {

    private final String title; //addbook command fields needed in the execute method
    private final String author;
    private final String publicationYear;
    private final String publisher;

    public AddBook(String title, String author, String publicationYear, String publisher) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.publisher = publisher; //constructor for the command ensuring that the data needed is supplied when command is run
    }
    
    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        int maxId = 0; // temp placeholder variable used later in method to generate the latest id number in the list preventing duplicates
    	if (library.getBooks().size() > 0) { //checks if library has books for the id number
    		int lastIndex = library.getBooks().size() - 1; //-1 used to get the correct last id number
            maxId = library.getBooks().get(lastIndex).getId();//getting of the last id number in the list
    	}
        Book book = new Book(++maxId, title, author, publicationYear, publisher); //increases the id number by 1 for unique id and constructs the new book object
        library.addBook(book);// adds book to library list
        System.out.println("Book #" + book.getId() + " added."); //message indicating book was made successfully
    }
    /**
     * sets the altersdata flag to true because the method adds a book to the book.txt file.
     */
    public boolean altersData() {
    	return true;
    }
}