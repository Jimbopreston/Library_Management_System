package bcu.m8.librarysystem.data;

import bcu.m8.librarysystem.main.LibraryException;
import bcu.m8.librarysystem.model.Library;
import bcu.m8.librarysystem.model.Patron;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class PatronDataManager implements DataManager {

    private final String RESOURCE = "./resources/data/patrons.txt"; //locates the .txt file
    
    @Override
    public void loadData(Library library) throws IOException, LibraryException {
    	  try (Scanner sc = new Scanner(new File(RESOURCE))) { //reads the .txt file
              int line_idx = 1;//starts at line 1
              while (sc.hasNextLine()) {//while line has information
                  String line = sc.nextLine();
                  String[] properties = line.split(SEPARATOR, -1); //separates the information and removes dividers
                  try {
                      int id = Integer.parseInt(properties[0]);
                      String name = properties[1]; //next the name and then the other details
                      String phone = properties[2];
                      String email = properties[3];
                      boolean isDeleted = Boolean.parseBoolean(properties[4]); //to check if patron is meant to be softdeleted
                      Patron patron = new Patron(id, name, phone, email); //creates patron object
                      
                      if (isDeleted) { //if is true
                      	patron.softDeletePatron(); //soft deletes the patron
                      }
                      
                      library.addPatron(patron); //adds patron to the library
                  } catch (NumberFormatException ex) { //catches errors to do with file
                      throw new LibraryException("Unable to parse patron id " + properties[0] + " on line " + line_idx
                          + "\nError: " + ex); //if cant parse a line displays which line it cant 
                  }
                  line_idx++; //goes to the next line
              }
          }
    }

    @Override
    public void storeData(Library library) throws IOException {
    	  try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
              for (Patron patron : library.getPatrons()) {
                  out.print(patron.getId() + SEPARATOR);
                  out.print(patron.getName() + SEPARATOR);
                  out.print(patron.getPhone() + SEPARATOR);
                  out.print(patron.getEmail() + SEPARATOR);
                  out.print(patron.getDeletedStatus() + SEPARATOR);
                  out.println();
              }
         }
    }
}
 