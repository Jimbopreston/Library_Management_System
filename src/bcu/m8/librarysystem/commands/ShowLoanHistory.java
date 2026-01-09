package bcu.m8.librarysystem.commands;

import java.time.LocalDate;

import bcu.m8.librarysystem.main.LibraryException;
import bcu.m8.librarysystem.model.Library;
import bcu.m8.librarysystem.model.Loan;
import bcu.m8.librarysystem.model.Patron;

import java.util.List;
/**
 * Shows a patrons loan history.
 * It is an implementation of the Command interface and its execute and altersdata methods to show a patrons loan history.
 * It is created by command parser when the "showloanhistory" command is given by the user.
 * The class has a constructor to initialise the fields needed to execute the command.
 */
public class ShowLoanHistory implements Command {
private int patronId; //takes patron id as field

	public ShowLoanHistory(int patronId) {
		this.patronId = patronId; //constructor
	}
	/**
	 * the execute method generates the patron object from the patron id and then checks the size of its books list.
	 * If the list has a size of 0, the method throws a message saying that the patron has no loan history
	 * @exception throws LibraryException when patron has no loan history
	 * @param Has parameters of library and currentDate.
	 */
	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		Patron patron = library.getPatronByID(patronId); //generation of patron object
		List <Loan> loanHistory = patron.getLoanHistory(); //populating list of this class with list of in patrons class
		if(loanHistory.size() == 0 ) { //checks the size before running any code
			throw new LibraryException(patron.getId() + " - "+ patron.getName()+ " has no loan history"); //if 0 then no loan history present
		}
		System.out.println(patron.getId() + " - " + patron.getName());//otherwise prints the details of patron and the details of each loan
		for(Loan loan : loanHistory) {
			System.out.println("Book Title: " + loan.getBook().getTitle());
			System.out.println("Start Date: " + loan.getStartDate());
			System.out.println("Due Date: " + loan.getDueDate());
			System.out.println("Return Date: " + loan.getReturnDate());
		}
	}
	/**
     * altersdata flag set to false because method makes no changes to any .txt file.
     */
	@Override
	public boolean altersData() {
		// TODO Auto-generated method stub
		return false;
	}

}
