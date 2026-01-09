package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;
import java.util.*;

public class Library {
    
    private final int loanPeriod = 7;
    private final Map<Integer, Patron> patrons = new TreeMap<>();
    private final Map<Integer, Book> books = new TreeMap<>();

    public int getLoanPeriod() {
        return loanPeriod; //returns the loan period which is used to calculate overdue books and the due date of books
    }

    public List<Book> getBooks() { //returns a unmodifiablelist of book objects important when you want the list of books in the library without updating them
        List<Book> out = new ArrayList<>(books.values());
        return Collections.unmodifiableList(out);
    } 
    
    /**
     * used to return the active books in the system as a list, books that have the deletedStatus set to true do not appear.
     * @return list of book objects with deletedStatus flag set to false.
     */
    public List<Book> getActiveBooks() { // shows only the books that havent been softdeleted when listbooks command is used
    	List<Book> activeBooks = new ArrayList<>(); //makes a list to add the books that dont have the deletedStatus flag set to true
    	for (Book book : books.values()) { //makes a list with all the books to check for the flag
    		if (!book.getDeletedStatus()) { //flag check
    			activeBooks.add(book); //adds to the list
    		}
    	}
    	return activeBooks; //returns the list with only the non deleted books
    }

    public Book getBookByID(int id) throws LibraryException { //gets the book object from only the id used in many classes primarily the datamanager classes
        if (!books.containsKey(id)) {
            throw new LibraryException("There is no such book with that ID.");
        }
        return books.get(id);
    }
    
    public List<Patron> getPatrons(){ //returns an unmodifiable list of the patrons
    	List<Patron> out = new ArrayList<>(patrons.values());
    	return Collections.unmodifiableList(out);
    }
    
    public List<Patron> getActivePatrons(){ //same as getactivebooks only this time for patrons
    	List<Patron> activePatrons = new ArrayList<>();
        for (Patron patron : patrons.values()) {
            if (!patron.getDeletedStatus()) {
                activePatrons.add(patron);
            }
        }
        return activePatrons;
    }

    public Patron getPatronByID(int id) throws LibraryException { //same as getBookById but for patrons instead
        if (!patrons.containsKey(id)) {
        	throw new LibraryException("There are no patrons with that ID.");
        }
        return patrons.get(id);
    }

    public void addBook(Book book) { //adds a book to the treemap checking if the id is already present this is used in loading data from the .txt files
        if (books.containsKey(book.getId())) {
            throw new IllegalArgumentException("Duplicate book ID.");
        }
        books.put(book.getId(), book);
    }

    public void addPatron(Patron patron) { //same as above but for patrons instead.
        if (patrons.containsKey(patron.getId())) {
        	throw new IllegalArgumentException("Duplicate patron ID.");
        }
    	patrons.put(patron.getId(), patron);
    }
}
 