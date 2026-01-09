package bcu.m8.librarysystem.commands;

import bcu.m8.librarysystem.gui.MainWindow;
import bcu.m8.librarysystem.main.LibraryException;
import bcu.m8.librarysystem.model.Library;

import java.time.LocalDate;

public class LoadGUI implements Command {

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        new MainWindow(library); //generates a new window object which contains all the gui features
    }
    /**
     * altersdata flag set to false because method makes no changes to any .txt file.
     */
    public boolean altersData() {
    	return false;
    }
    
}
 