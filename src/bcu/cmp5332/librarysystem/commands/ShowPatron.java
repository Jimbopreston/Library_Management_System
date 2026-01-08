package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import java.util.*;

public class ShowPatron implements Command {
	private final int patronId;
	
	public ShowPatron(int patronId) {
		this.patronId = patronId;
	}

	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		// TODO Auto-generated method stub
		Patron patron = library.getPatronByID(patronId);
		System.out.println(patron.getId());
		System.out.println(patron.getName());
		System.out.println(patron.getPhone());
		System.out.println(patron.getEmail());
		List<Book> books = patron.getBooks(); //add patron doesnt have any books check later on
		for (Book book :books) {
			System.out.println(book.getTitle());
		}
		System.out.println(books.size());
	}
	
	public boolean altersData() {
    	return false;
    }
}


