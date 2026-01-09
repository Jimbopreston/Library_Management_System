package bcu.m8.librarysystem.commands;
import bcu.m8.librarysystem.main.LibraryException;
import bcu.m8.librarysystem.model.Book;
import bcu.m8.librarysystem.model.Library;

import java.time.LocalDate;
import java.util.List;

public class ListBooks implements Command {

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        List<Book> books = library.getActiveBooks(); //gets the books that havent been from the library and populates the list books
        for (Book book : books) { //iterates through each book running the below code
        	if(book.getDeletedStatus() == false) //checks the book again making sure it hasnt been softdeleted
            System.out.println(book.getDetailsShort()); //prints the id and name of the book
        }
        System.out.println(books.size() + " book(s)");//once each book has been done it prints the total amount of books in the list
    }
    /**
     * altersdata flag set to false because method makes no changes to any .txt file.
     */
    public boolean altersData() {
    	return false;
    }
}
 