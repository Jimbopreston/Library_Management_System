package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;

public class RenewBook implements Command {
	private final int patronId;
	private final int bookId;
	
	public RenewBook(int patronId,int bookId) {
		this.patronId = patronId;
		this.bookId = bookId;
	}
	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		try {
			Book book = library.getBookByID(bookId);
			Patron patron = library.getPatronByID(patronId);
			
			LocalDate renewDate = book.getDueDate();
			renewDate.plusDays(library.getLoanPeriod());
			
	        patron.renewBook(book, renewDate);
	        System.out.println("The new due date is: " + renewDate);
	        
		}catch (LibraryException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public boolean altersData() {
    	return true;
    }
}
