package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.model.Loan;

public class ReturnBook implements Command {
	private int patronId;
	private int bookId; //return command takes book and patron ids to clear the loan and return the book to library

	public ReturnBook(int patronId, int bookId) {
		this.patronId = patronId;
		this.bookId = bookId;
	}
	
	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		// TODO Auto-generated method stub
		try {
			Book book = library.getBookByID(bookId);
			Patron patron = library.getPatronByID(patronId); //generation of book and patrons objects using ids
			
			if (!book.isOnLoan()) { //checks book isnt already on loan meaning book cant be returned
				System.out.println("This book is not currently on loan.");
				return;
			}
			
			Loan loan = book.getLoan(); //generates the loan object from the book
			LocalDate dueDate = loan.getDueDate(); //gets the due date
			long daysBetween = ChronoUnit.DAYS.between(dueDate,currentDate); //calculation of days between for seeing how long the book was overdue by before returning
			if(dueDate.isBefore(currentDate)) { //checks if the duedate is after the current date
				System.out.println("Book was overdue by " + daysBetween + " days."  ); //message telling patron they returned book overdue
			}
			
			patron.returnBook(book); //return of book gets rid of loan and put it in loanhistory and removes book from books list in patrons and clears the loan on the book
			System.out.println(book.getTitle() + " has been returned."); //confirmation message
			
	}catch(LibraryException e) {
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