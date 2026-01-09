package bcu.cmp5332.librarysystem.data;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Patron;
import java.time.LocalDate;
import bcu.cmp5332.librarysystem.model.Library;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.List;

public class LoanDataManager implements DataManager {
    
    public final String RESOURCE = "./resources/data/loans.txt";

    @Override
    public void loadData(Library library) throws IOException, LibraryException {
    	  try (Scanner sc = new Scanner(new File(RESOURCE))) { //opening the loan.txt file
              int line_idx = 1; //starts at line 1
              while (sc.hasNextLine()) { // while theres data on the line
                  String line = sc.nextLine();
                  String[] properties = line.split(SEPARATOR, -1); //splits and removes the separators
                  try {
                      int patronID = Integer.parseInt(properties[0]); //converts the strings into patron and book ids which can
                      int bookID = Integer.parseInt(properties[1]); //be used to get the objects since loandatamanager loads last
                      LocalDate startDate = LocalDate.parse(properties[2]); //parses the strings into localdate objects
                      LocalDate dueDate = LocalDate.parse(properties[3]);
                      Boolean loanTerminated = Boolean.parseBoolean(properties[4]); //check the loanterminated flag used to determine the kind of loan
                      LocalDate returnDate = null; //initiates the returndate 
                      if (!properties[5].isEmpty()) { //checks if theres a value
                          returnDate = LocalDate.parse(properties[5]); //if there is it is parsed otherwise left null(blank)
                      }

                      if (!loanTerminated) { //loan isnt an old one by checking the flag isnt true
                          Patron patron = library.getPatronByID(patronID); //generates the patron and book objects to generate the loan object
                          Book book = library.getBookByID(bookID);
                          Loan loan = new Loan(patron, book, startDate, dueDate);
                          book.setLoan(loan); //adds the loan to the book
                          patron.addBook(book); // adds book to books list in patrons
                      } else { //otherwise loan is old and is apart of loanhistory list
                          Patron patron = library.getPatronByID(patronID); //same as above
                          Book book = library.getBookByID(bookID);
                          Loan oldLoan = new Loan(patron, book, startDate, dueDate);//same as above
                          oldLoan.setLoanTerminatedTrue(); //sets the flag to true on the loan object
                          oldLoan.setReturnDate(returnDate); //sets the return dat of the loan
                          patron.addLoanToLH(oldLoan); //adds the old loan to the loan history list in patron
                      }
                   
                  } catch (NumberFormatException ex) {
                      throw new LibraryException("Unable to parse patron id " + properties[0] + " on line " + line_idx
                          + "\nError: " + ex); //if unable to parse data on line due to corruption of wrong storage throws an error explaining where it went wrong
                  }
                  line_idx++; //proceeds to the next loan in the .txt file
              }
    	  }
    }

    @Override
    public void storeData(Library library) throws IOException {
    	  try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) { //writes to the file
              for (Patron patron : library.getPatrons()) { //iterates through the patrons list
            	  List<Book> books = patron.getBooks(); //iterates through the books list of each patron
            	  if(books.size() == 0) { //checks if the patron has any books on loan
            		  System.out.println(); //if no prints nothing 
            	  }else {
            	  for(Book book : books) { //iterating through each book in books
            		  out.print(patron.getId() + SEPARATOR); //prints the patron id into the file with separator
            		  out.print(book.getId() + SEPARATOR);//prints the book id into the file with separator
            		  Loan loan = book.getLoan(); //gets the loan from the book
            		  out.print(loan.getStartDate() + SEPARATOR); //prints the startdate to file with separator
            		  out.print(loan.getDueDate() + SEPARATOR); //prints duedate into file
            		  out.print(loan.getLoanTerminated() + SEPARATOR);//prints loan terminated boolean as true or false
            		  if (loan.getLoanTerminated()) { //if set to true
            			    out.print(loan.getReturnDate() + SEPARATOR); //prints the return date
            			} else {
            			    out.print("" + SEPARATOR); //otherwise return date is left blank
            		  out.println();//separates lines in the file
            	  	}
        	    }
          }
            	 List <Loan> oldLoans = patron.getLoanHistory(); //stores the old loans from a patrons loan history list
            	 for(Loan loan : oldLoans) { //iterates through each loan
            		 out.print(loan.getPatron().getId() + SEPARATOR); //prints their details with separators
            		 out.print(loan.getBook().getId() + SEPARATOR);
            		 out.print(loan.getStartDate() + SEPARATOR);
            		 out.print(loan.getDueDate() + SEPARATOR);
            		 out.print(loan.getLoanTerminated() + SEPARATOR); //all old loans have this set to true and have a return date.
            		 out.print(loan.getReturnDate() + SEPARATOR);
            		 out.println(); //line to separate
            		 
            	 
            	 }
            }
        }
    } 
}
 