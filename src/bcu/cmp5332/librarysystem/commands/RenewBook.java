package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;

public class RenewBook implements Command {
	private final int patronId;
	private final int bookId; //takes a patron and book id to identify which book/loan is having its due date renewed for the patron.
	
	public RenewBook(int patronId,int bookId) {
		this.patronId = patronId;
		this.bookId = bookId; //constructor
	}
	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		try {
			Book book = library.getBookByID(bookId); //generates the full objects using the ids
			Patron patron = library.getPatronByID(patronId);
			
			LocalDate currentDueDate = book.getDueDate(); //gets the current duedate from the book
			LocalDate newDueDate = currentDueDate.plusDays(library.getLoanPeriod()); //increases the current due date by the loan period (7 days)
			
	        patron.renewBook(book, newDueDate); //updates the loan in the patron class with new duedate
	        System.out.println("The new due date is: " + newDueDate);
	        
		}catch (LibraryException e) { //throws an error if book or patron is incorrect or other such logical errors
			System.out.println("Error: " + e.getMessage());
		}
	}
	/**
	 * sets the altersdata flag to true because this method updates the loan.txt file.
	 */
	public boolean altersData() {
    	return true;
    }
}
