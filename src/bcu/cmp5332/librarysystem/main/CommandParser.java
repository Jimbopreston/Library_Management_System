package bcu.cmp5332.librarysystem.main;
import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.commands.LoadGUI;
import bcu.cmp5332.librarysystem.commands.ListBooks;
import bcu.cmp5332.librarysystem.commands.ListPatrons;
import bcu.cmp5332.librarysystem.commands.AddBook;
import bcu.cmp5332.librarysystem.commands.AddPatron;
import bcu.cmp5332.librarysystem.commands.ShowBook;
import bcu.cmp5332.librarysystem.commands.ShowPatron;
import bcu.cmp5332.librarysystem.commands.BorrowBook;
import bcu.cmp5332.librarysystem.commands.RenewBook;
import bcu.cmp5332.librarysystem.commands.ReturnBook;
import bcu.cmp5332.librarysystem.commands.DeleteBook;
import bcu.cmp5332.librarysystem.commands.DeletePatron;
import bcu.cmp5332.librarysystem.commands.ShowLoanHistory;
import bcu.cmp5332.librarysystem.commands.Help;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class CommandParser {
    
    public static Command parse(String line) throws IOException, LibraryException {
        try {
            String[] parts = line.split(" ", 3); //separates the parsed text into 3 parts maximum into a list
            String cmd = parts[0]; //the first part must always be a command

            // TODO: Link your implemented features to commands here 
            if (cmd.equals("addbook")) { //if command addbook is typed prompts the user to input the data to create a new book object
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //buffered reader used to read input from the user to create the strings needed for book object
                System.out.print("Title: ");
                String title = br.readLine();
                System.out.print("Author: ");
                String author = br.readLine();
                System.out.print("Publication Year: ");
                String publicationYear = br.readLine();
                System.out.print("Publisher: ");
                String publisher = br.readLine();
                
                return new AddBook(title, author, publicationYear, publisher); //addbook constructor which then executes the command on the system
                
            } else if (cmd.equals("addpatron")) { //same as addbook command only for patrons this time.
            	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            	System.out.print("Name: ");
            	String name = br.readLine();
            	System.out.print("Phone: ");
            	String phone = br.readLine();
            	System.out.print("Email: ");
            	String email = br.readLine();
            	
            	return new AddPatron(name, phone, email);
            	
            } else if (cmd.equals("loadgui")) {
                return new LoadGUI(); //if loadgui is input then the gui is loaded. can be set to load automatically from the gui mainwindow
                
            } else if (parts.length == 1) { // if the length of the parts is only 1 ie only a command is input. must be a command below 
                if (line.equals("listbooks")) { //lists books
                    return new ListBooks();
                } else if (line.equals("listpatrons")) { //lists patrons
                     return new ListPatrons();
                } else if (line.equals("help")) {
                    return new Help(); //shows the list of commands
                }
            } else if (parts.length == 2) { //2 parts to these commands 1 the command the other the id of a book or patron
                int id = Integer.parseInt(parts[1]); //creates the integer for id by using Integer.parseInt this generates an int instead of a string

                if (cmd.equals("showbook")) { //used to showdetails long of a book and patron details if being loaned //also check the command using .equals()
                    return new ShowBook(id);
                } else if (cmd.equals("showpatron")) { //like showbook but for patrons and shows book details if loaned and loan total
                    return new ShowPatron(id);
                } else if (cmd.equals("deletebook")) { //soft deletes a chosen book
                	return new DeleteBook(id);
                } else if (cmd.equals("deletepatron")) {//soft deletes a chosen patron
                	return new DeletePatron(id);
                } else if (cmd.equals("showloanhistory")) { //shows loan history of a chosen patron
                	return new ShowLoanHistory(id);
                }
            } else if (parts.length == 3) { //these commands require 3 parts being the command the patron id and then book id
                int patronID = Integer.parseInt(parts[1]); //they are parsed as such
                int bookID = Integer.parseInt(parts[2]);

                if (cmd.equals("borrow")) { //used for a patron to borrow book
                	return new BorrowBook(patronID, bookID);
                    
                } else if (cmd.equals("renew")) { //renews the loan on a book 
                    return new RenewBook(patronID,bookID);
                    
                } else if (cmd.equals("return")) { //returns the book to library for other patrons to loan.
                    return new ReturnBook(patronID,bookID);
                }
            }
        } catch (NumberFormatException ex) {

        }

        throw new LibraryException("Invalid command.");
    }
}
