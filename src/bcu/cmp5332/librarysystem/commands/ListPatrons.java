package bcu.cmp5332.librarysystem.commands;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;
import java.time.LocalDate;
import java.util.*;


public class ListPatrons implements Command {
	
	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException{
		List<Patron> patrons = library.getActivePatrons(); //populates the patrons list using the non deleted patrons list
        for (Patron patron : patrons) {//iterates through each patron
        	if(patron.getDeletedStatus() == false) { //checks again that patron isnt soft deleted
            System.out.println(patron.getId() + " - " + patron.getName()); //prints the id and name of the patron
        	}
        }
        System.out.println(patrons.size() + " patron(s)"); //prints the size of the patrons list
	}
	/**
     * altersdata flag set to false because method makes no changes to any .txt file.
     */
	public boolean altersData() {
    	return false;
    }
}
