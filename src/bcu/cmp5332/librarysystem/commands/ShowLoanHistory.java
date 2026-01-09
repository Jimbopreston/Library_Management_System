package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import java.util.List;

public class ShowLoanHistory implements Command {
private int patronId;

	public ShowLoanHistory(int patronId) {
		this.patronId = patronId;
	}
	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		Patron patron = library.getPatronByID(patronId);
		List <Loan> loanHistory = patron.getLoanHistory();
		if(loanHistory.size() == 0 ) {
			throw new LibraryException(patron.getId() + " - "+ patron.getName()+ " has no loan history");
		}
		System.out.println(patron.getId() + " - " + patron.getName());
		for(Loan loan : loanHistory) {
			System.out.println("Book Title: " + loan.getBook().getTitle());
			System.out.println("Start Date: " + loan.getStartDate());
			System.out.println("Due Date: " + loan.getDueDate());
			System.out.println("Return Date: " + loan.getReturnDate());
		}
	}

	@Override
	public boolean altersData() {
		// TODO Auto-generated method stub
		return false;
	}

}
