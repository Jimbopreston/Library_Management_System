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
                      int id = Integer.parseInt(properties[0]);
                      String patron = properties[1]; //these need to be converted into integers to find which patron or book we are meant to be creating a loan with
                      String book = properties[2];
                      String startDate = properties[3];
                      String dueDate = properties[3];
                      
                    //  Loan loan = new Loan(patron, book, startDate, dueDate);
                   
                  } catch (NumberFormatException ex) {
                      throw new LibraryException("Unable to parse book id " + properties[0] + " on line " + line_idx
                          + "\nError: " + ex);
                  }
                  line_idx++;
              }
    	  }
    }

    @Override
    public void storeData(Library library) throws IOException {
        // TODO: implementation here
    	  try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
              for (Book book : library.getBooks()) {
                  out.print(book.getId() + SEPARATOR);
                  out.print(book.getTitle() + SEPARATOR);
                  out.print(book.getAuthor() + SEPARATOR);
                  out.print(book.getPublicationYear() + SEPARATOR);
                  out.println();
            }
        }
    } 
}
 