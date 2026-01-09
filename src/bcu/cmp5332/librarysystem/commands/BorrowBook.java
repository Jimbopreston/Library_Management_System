package bcu.cmp5332.librarysystem.commands;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Patron;
import java.time.LocalDate;

public class BorrowBook implements Command{
	private int patronId;
	private int bookId;
	
	public BorrowBook(int patronId, int bookId) {
		this.patronId = patronId;
		this.bookId = bookId;
	}

	@Override
	public void execute (Library library, LocalDate currentDate) throws LibraryException {
			Book book = library.getBookByID(bookId);
			Patron patron = library.getPatronByID(patronId);
			
			int loanPeriod = library.getLoanPeriod();
			LocalDate dueDate = currentDate.plusDays(loanPeriod);
			
			patron.borrowBook(book, dueDate);
			
			System.out.println(book.getTitle() + " has been borrowed. Return date: " + dueDate);
		
	}
	
	@Override
	public boolean altersData() {
    	return true;
    }
}
