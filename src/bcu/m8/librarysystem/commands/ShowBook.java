package bcu.m8.librarysystem.commands;
import bcu.m8.librarysystem.main.LibraryException;
import bcu.m8.librarysystem.model.Book;
import bcu.m8.librarysystem.model.Library;
import bcu.m8.librarysystem.model.Loan;
import bcu.m8.librarysystem.model.Patron;

import java.time.LocalDate;


public class ShowBook implements Command {
	private final int bookId; //showbook takes bookid command


	public ShowBook(int bookId) {
		this.bookId = bookId; //constructor
	}
	
	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		Book book = library.getBookByID(bookId); //generation of book object from id
		if(book.getDeletedStatus()==false) { //checks if book is soft deleted
				System.out.println(book.getDetailsLong()); //prints all the books details if isnt
				if(book.isOnLoan()) { //if book has a loan displays the patron details
					Loan loan = book.getLoan(); //getting loan object from the book object
					Patron patron = loan.getPatron(); //getting the patron from the load
					System.out.println("Patron ID: " + patron.getId()); //printing of patron details
					System.out.println("Patron Name : " + patron.getName());
					System.out.println("Patron Phone Number: " + patron.getPhone());
					System.out.println("Patron Email: " + patron.getEmail());
					System.out.println("Due Date = " + book.getDueDate());
					}
		}else {
			System.out.println("Cannot find book you are looking for"); //if book is soft deleted system cant find book and sends this message to the user
		}
	}
	/**
     * altersdata flag set to false because method makes no changes to any .txt file.
     */
	public boolean altersData() {
    	return false;
    }
}	