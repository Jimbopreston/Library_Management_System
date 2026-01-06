package bcu.cmp5332.librarysystem.commands;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;
import java.time.LocalDate;
import java.util.List;

public class ShowBook implements Command {
	private final int bookId;


	public ShowBook(int bookId) {
		this.bookId = bookId;
	}
	
	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
	// check if showbook in library books list if yes print details
		List<Book> books = library.getBooks();
		for (Book book :books) {
			if(book.getId() == bookId) {
				System.out.println(book.getDetailsLong());
			}
			if(book.getId() == bookId) {
				if(book.isOnLoan()) {
					book.getLoan();
			}
			}
		}
	}
}	