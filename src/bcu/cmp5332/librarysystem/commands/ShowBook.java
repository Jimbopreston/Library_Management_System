package bcu.cmp5332.librarysystem.commands;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;
import java.time.LocalDate;


public class ShowBook implements Command {
	private final int bookId;


	public ShowBook(int bookId) {
		this.bookId = bookId;
	}
	
	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
	
		Book book = library.getBookByID(bookId);
				System.out.println(book.getDetailsLong());
				if(book.isOnLoan()) {
					Loan loan = book.getLoan();
					Patron patron = loan.getPatron();
					System.out.println("Patron ID: " + patron.getId());
					System.out.println("Patron Name : " + patron.getName());
					System.out.println("Patron Phone Number: " + patron.getPhone());
					System.out.println("Patron Email: " + patron.getEmail());
					System.out.println("Due Date = " + book.getDueDate());
					}
	}
}	