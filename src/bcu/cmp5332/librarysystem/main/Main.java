package bcu.cmp5332.librarysystem.main;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.model.Library;

import java.io.*;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws IOException, LibraryException {
        
        Library library = LibraryData.load(); //runs library data.load which loads the 3 .txt data files into the library object

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //intialisation of the buffered reader to read for the commands being input

        System.out.println("Library system");
        System.out.println("Enter 'help' to see a list of available commands.");
        while (true) {
            System.out.print("> ");
            String line = br.readLine();//users input
            if (line.equals("exit")) {
                break; //if exit types closes the loop and therefore the program
            }

            try {
                Command command = CommandParser.parse(line); //parses the users input
                command.execute(library, LocalDate.now()); // //executes the command
                if(command.altersData() == true) { //checks if the command alters data of the .txt files
                	try {
                		LibraryData.store(library); //if successful stores the data added or removed etc after the command updating the records
                    }catch(IOException e){ //catches for file errors such as corruption of read only files or file missing
                        	System.out.println("ERROR: Could not save correctly. Rolling back to previous state..."); //message indicating error saving
                        	library = LibraryData.load(); //loads the library data prior to the error to resume the program function
                        	System.out.println("Rollback completed."); //indicates a rollback was done to the user
                	}
                }
            } catch (LibraryException ex) {
                System.out.println(ex.getMessage());
            }
        }
        LibraryData.store(library); //stores the library data upon exiting
        System.exit(0);
    }
}
