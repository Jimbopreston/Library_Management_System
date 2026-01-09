package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.model.Library;

import java.time.LocalDate;

public class AddPatron implements Command {

    private final String name; //fields needed for command add patron
    private final String phone;
    private final String email;

    public AddPatron(String name, String phone, String email) { //constructor for add patron
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        // TODO: implementation here
    	int maxId = 0;
    	if (library.getPatrons().size() > 0) {
    		int lastIndex = library.getPatrons().size() - 1;
            maxId = library.getPatrons().get(lastIndex).getId(); //same maths as in addbook gets the latest id for unique ids for each patron
    	}
        Patron patron = new Patron(++maxId, name, phone, email); //creation of patron object
        library.addPatron(patron); //adds patron to library
        System.out.println("Patron #" + patron.getId() + " added."); //message indicating successful creation
    }
    /**
     * sets the altersdata flag to true because this method adds a patron to patron.txt file.
     */
    public boolean altersData() {
    	return true;
    }
}
 