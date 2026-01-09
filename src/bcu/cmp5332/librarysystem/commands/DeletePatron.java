package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;
/**
 * Deletes a patron from the library.
 * It is an implementation of the Command interface and its execute and altersdata methods to soft delete a patron.
 * It is created by command parser when the "deletepatron" command is given by the user.
 * The class has a constructor to initialise the fields needed to execute the command.
 */
public class DeletePatron implements Command {
	private int patronId;
	
	public DeletePatron(int patronId) {
		this.patronId = patronId;
	}
	/**
	 * the execute method generates the patron object from the patron id and then checks the size of its books list.
	 * If the list has a size of 0, the method calls upon the patrons softdeletepatron method and deletes the patron on the system but not from the patron.txt file.
	 * @exception throws LibraryException when the patron has more than 0 books to prevent deleting a patron with books loaned which would prevent retrieval of the books.
	 * @param Has parameters of library and currentDate.
	 */
	
	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		Patron patron = library.getPatronByID(patronId); //generates patron by getting the data from the library using id
		
			if(patron.getBooks().size() > 0){ //checks the patron isnt loaning any books to prevent loss of book objects
				throw new LibraryException("Cannot Delete a Patron that has loans");
			}
			
			patron.softDeletePatron(); //deletes the patron from system not the .txt files
			System.out.println(patron.getId() + " - " + patron.getName() + " deleted"); //tells user which patron has been deleted
			
		}

	/**
	 * altersdata flag is set to true as this method sets the deletedstatus flag to true in the patrons.txt file.
	 */
	@Override
	public boolean altersData() {
		// TODO Auto-generated method stub
		return true;
	}

}
