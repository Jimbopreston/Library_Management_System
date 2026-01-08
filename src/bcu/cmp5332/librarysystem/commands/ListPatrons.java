package bcu.cmp5332.librarysystem.commands;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;
import java.time.LocalDate;
import java.util.*;


public class ListPatrons implements Command {
	
	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException{
		List<Patron> patrons = library.getPatrons();
        for (Patron patron : patrons) {
        	if(patron.getDeletedStatus() == false) {
            System.out.println(patron.getName());
        	}
        }
        System.out.println(patrons.size() + " patron(s)");
	}
	
	public boolean altersData() {
    	return false;
    }
}
