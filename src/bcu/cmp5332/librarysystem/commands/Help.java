package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Library;

import java.time.LocalDate;

public class Help implements Command {

    @Override
    public void execute(Library library, LocalDate currentDate) {
        System.out.println(Command.HELP_MESSAGE); //prints the help message from command.java displaying the different commands the user can use
    }
    /**
     * altersdata flag set to false because method makes no changes to any .txt file.
     */
    public boolean altersData() {
    	return false;
    }
}
 