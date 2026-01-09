package bcu.cmp5332.librarysystem.commands;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Patron;
import java.time.LocalDate;

public class BorrowBook implements Command{
	private int patronId;
	private int bookId; //borrowbook command requires both book and patron ids to create a loan
	
	public BorrowBook(int patronId, int bookId) {
		this.patronId = patronId;
		this.bookId = bookId;
	}

	@Override
	public void execute (Library library, LocalDate currentDate) throws LibraryException {
			Book book = library.getBookByID(bookId);
			Patron patron = library.getPatronByID(patronId);
			
			int loanPeriod = library.getLoanPeriod();
			LocalDate dueDate = currentDate.plusDays(loanPeriod); //used to get the due date for the loan by adding loan period(7days) onto the current date
			
			patron.borrowBook(book, dueDate); //runs borrowbook method
			
			System.out.println(book.getTitle() + " has been borrowed. Return date: " + dueDate); //success message
		
	}
	/**
	 * sets the altersdata flag to true because this method adds a loan to the loan.txt file.
	 */
	@Override
	public boolean altersData() {
    	return true;
    }
}
