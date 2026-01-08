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
		if(patron.getDeletedStatus() == false) {
			System.out.println(patron.getId());
			System.out.println(patron.getName());
			System.out.println(patron.getPhone());
			System.out.println(patron.getEmail());
			List<Book> books = patron.getBooks();
			if(books.size()>0) {
				for (Book book :books) {
					System.out.println(book.getTitle());
				}
				System.out.println(books.size());
			}
			
			}else {
				System.out.println("Cannot find patron you are looking for");
		}
	}
	
	public boolean altersData() {
    	return false;
    }
}


