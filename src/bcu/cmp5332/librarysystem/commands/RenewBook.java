package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;
import java.util.*;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.model.Book;
import java.time.LocalDate;
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
		List<Book> books = library.getBooks();
		List<Patron> patrons = library.getPatrons();
		for (Book book :books) {
			if(book.getId() == bookId) {
				//dueDate = book.getDueDate()
			}
		}
        for (Patron patron : patrons) {
            if(patron.getId() == patronId) {
            	patron.renewBook(null, currentDate);
            }
        }
	}
}
