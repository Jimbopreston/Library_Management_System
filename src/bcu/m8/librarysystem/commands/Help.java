package bcu.m8.librarysystem.commands;

import java.time.LocalDate;

import bcu.m8.librarysystem.model.Library;

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
 