package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import java.util.*;

public class ShowPatron implements Command {
	private final int patronId; //command uses patron id
	
	public ShowPatron(int patronId) {
		this.patronId = patronId; //constructor
	}

	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		// TODO Auto-generated method stub
		Patron patron = library.getPatronByID(patronId); //generation of the patron object
		if(patron.getDeletedStatus() == false) { //checks the patron isnt soft deleted 
			System.out.println("Patron ID: " + patron.getId()); //prints the patrons details
			System.out.println("Patron Name: " + patron.getName());
			System.out.println("Patron Phone Number: " + patron.getPhone());
			System.out.println("Patron Email: " + patron.getEmail());
			List<Book> books = patron.getBooks();
			if(books.size()>0) { //checks the book list size if greater than 0 runs code below
				for (Book book :books) { //iterates through the books list 
					System.out.println("Book Title: " + book.getTitle()); //prints each books title
				}
				System.out.println("Total amount of books loaned: " + books.size()); //prints the total amount of books on loan
			}
			
			}else {
				System.out.println("Cannot find patron you are looking for");//cant find patron if soft deleted
		}
	}
	/**
	 * alterdata flag set to false as showpatron does not alter .txt files.
	 */
	public boolean altersData() {
    	return false;
    }
}


