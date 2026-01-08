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
	private int bookId;

	public ReturnBook(int patronId, int bookId) {
		this.patronId = patronId;
		this.bookId = bookId;
	}
	
	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		// TODO Auto-generated method stub
		try {
			Book book = library.getBookByID(bookId);
			Patron patron = library.getPatronByID(patronId);
			
			if (!book.isOnLoan()) {
				System.out.println("This book is not currently on loan.");
				return;
			}
			
			Loan loan = book.getLoan();
			LocalDate dueDate = loan.getDueDate();
			long daysBetween = ChronoUnit.DAYS.between(dueDate,currentDate);
			if(dueDate.isBefore(currentDate)) {
				System.out.println("Book was overdue by " + daysBetween + " days."  );
			}
			
			patron.returnBook(book);
			System.out.println(book.getTitle() + " has been returned.");
			
	}catch(LibraryException e) {
		System.out.println("Error: " + e.getMessage());
	}

	}
	
	public boolean altersData() {
    	return true;
    }
}