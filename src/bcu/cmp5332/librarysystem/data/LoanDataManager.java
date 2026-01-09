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
        // TODO: implementation here
    	  try (Scanner sc = new Scanner(new File(RESOURCE))) {
              int line_idx = 1;
              while (sc.hasNextLine()) {
                  String line = sc.nextLine();
                  String[] properties = line.split(SEPARATOR, -1);
                  try {
                      int patronID = Integer.parseInt(properties[0]); //these need to be converted into integers to find which patron or book we are meant to be creating a loan with
                      int bookID = Integer.parseInt(properties[1]);
                      LocalDate startDate = LocalDate.parse(properties[2]);
                      LocalDate dueDate = LocalDate.parse(properties[3]);
                      
                      Patron patron = library.getPatronByID(patronID);
                      Book book = library.getBookByID(bookID);
                      Loan loan = new Loan(patron,book,startDate,dueDate);
                      book.setLoan(loan);
                      patron.addBook(book);
                    
                   
                  } catch (NumberFormatException ex) {
                      throw new LibraryException("Unable to parse patron id " + properties[0] + " on line " + line_idx
                          + "\nError: " + ex);
                  }
                  line_idx++;
              }
    	  }
    }

    @Override
    public void storeData(Library library) throws IOException {
    	  try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
              for (Patron patron : library.getPatrons()) {
            	  List<Book> books = patron.getBooks();
            	  if(books.size() == 0) {
            		  System.out.println();
            	  }else {            		  
            	  for(Book book : books) {
            		  out.print(patron.getId() + SEPARATOR);
            		  out.print(book.getId() + SEPARATOR);
            		  Loan loan = book.getLoan();
            		  out.print(loan.getStartDate() + SEPARATOR);
            		  out.print(loan.getDueDate() + SEPARATOR);
            		  out.println();
            	  	}
        	    }
            }
        }
    } 
}
 