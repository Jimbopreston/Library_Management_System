package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;

import java.time.LocalDate;

public interface Command { //command is an interface which when implemented by other classes defines the methods those classes must use.
	//this is useful as it acts like a blueprint/guideline for different classes, they all act a similar way due to this and can all use the same 
	//interface improves readability of code for people and makes code easier to test and change.
	//keeps things consistent they are best used when you will need to do something multiple times such as the commands implemented here
	// interface is a form of polymorphism

    public static final String HELP_MESSAGE = "Commands:\n"                         //list of different commands the user can input
            + "\tlistbooks                       print all books*\n"
            + "\tlistpatrons                     print all patrons\n"
            + "\taddbook                         add a new book*\n"
            + "\taddpatron                       add a new patron\n"
            + "\tdeletebook                      delete a book\n"
            + "\tdeletepatron                    delete a patron\n"
            + "\tshowbook                        show book details\n"
            + "\tshowpatron                      show patron details\n"
            + "\tshowloanhistory                 show a patrons loan history\n"
            + "\tborrow                          borrow a book\n"
            + "\trenew                           renew a book\n"
            + "\treturn                          return a book\n"
            + "\tloadgui                         loads the GUI version of the app*\n"
            + "\thelp                            prints this help message*\n"
            + "\texit                            exits the program*";

    
    public void execute(Library library, LocalDate currentDate) throws LibraryException;
    
    /**
     * a boolean flag for if a particular command alters data in the .txt files.
     * @return a boolean true or false.
     */
    public boolean altersData();
    
}
 