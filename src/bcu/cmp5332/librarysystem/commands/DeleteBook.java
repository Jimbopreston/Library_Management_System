package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
/**
 * The DeleteBook command for soft deleting books from the system.
 * It is an implementation of the Command interface and its execute(Library, LocalDate) method soft deletes a book from the library.
 * It is created and executed by the CommandParser when the "DeleteBook" command is given by the user.
 * The class has a constructor to initialise the fields needed to execute the command.
 * 
 */
public class DeleteBook implements Command {
	private int bookId; //delete book requires the book id 
	
	public DeleteBook(int bookId) {
		this.bookId = bookId; //constructor
	}
	/**
	 * Soft Delete a book from library.
	 * The method should use the bookId to generate a book object which then calls upon the books method to soft delete the book from the library.
	 * Also displays a message telling the user which book has been deleted.
	 * @exception a LibraryException is thrown when a book is on loan this is to prevent books that are on loan from being deleted without being returned.
	 * @param takes Library and currentDate as parameters.
	 */
	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		Book book = library.getBookByID(bookId); //uses the getbookbyid to get the full book object from the id
		
			if(book.isOnLoan()){ //checks if on loan because you dont want to delete a book that is on loan
				throw new LibraryException("Cannot Delete a book that is on loan");
			}
			
			book.softDeleteBook(); //soft deletes book from the system
			System.out.println(book.getDetailsShort() + " deleted"); //prints which book has been removed.
					
		}
	/**
	 * altersdata flag is set to true because this command changes the flag in the loans.txt file.
	 */
		
	@Override
	public boolean altersData() {
		// TODO Auto-generated method stub
		return true;
	}

}
