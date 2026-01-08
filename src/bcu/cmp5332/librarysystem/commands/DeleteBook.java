package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;

public class DeleteBook implements Command {
	private int bookId;
	
	public DeleteBook(int bookId) {
		this.bookId = bookId;
	}
	
	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		Book book = library.getBookByID(bookId);
		try {
		if(book.isOnLoan()){
			throw new LibraryException("Cannot Delete a book that is on loan");
		}else {
			book.softDeleteBook();
			System.out.println(book.getDetailsShort() + " deleted");
		}
		}catch(LibraryException e) {
			System.out.println("Error: "+ e.getMessage());
		}
	}

	@Override
	public boolean altersData() {
		// TODO Auto-generated method stub
		return false;
	}

}
