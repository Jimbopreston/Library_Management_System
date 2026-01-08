package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

public class DeletePatron implements Command {
	private int patronId;
	
	public DeletePatron(int patronId) {
		this.patronId = patronId;
	}
	
	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		Patron patron = library.getPatronByID(patronId);
		try {
			if(patron.getBooks().size() > 0){
				throw new LibraryException("Cannot Delete a Patron that has loans");
			}else {
				patron.softDeletePatron();
				System.out.println(patron.getId() + " - " + patron.getName() + " deleted");
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
