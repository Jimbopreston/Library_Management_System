package bcu.m8.librarysystem.commands;

import java.time.LocalDate;

import bcu.m8.librarysystem.main.LibraryException;
import bcu.m8.librarysystem.model.Library;
import bcu.m8.librarysystem.model.Loan;
import bcu.m8.librarysystem.model.Patron;

import java.util.List;

public class ShowLoanHistory implements Command {
private int patronId; //takes patron id as field

	public ShowLoanHistory(int patronId) {
		this.patronId = patronId; //constructor
	}
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
