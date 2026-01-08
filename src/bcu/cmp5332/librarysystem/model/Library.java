package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;
import java.util.*;

public class Library {
    
    private final int loanPeriod = 7;
    private final Map<Integer, Patron> patrons = new TreeMap<>();
    private final Map<Integer, Book> books = new TreeMap<>();

    public int getLoanPeriod() {
        return loanPeriod;
    }

    public List<Book> getBooks() {
        List<Book> out = new ArrayList<>(books.values());
        return Collections.unmodifiableList(out);
    }
    
    public List<Book> getActiveBooks() {
    	List<Book> activeBooks = new ArrayList<>();
    	for (Book book : books.values()) {
    		if (!book.getDeletedStatus()) {
    			activeBooks.add(book);
    		}
    	}
    	return activeBooks;
    }

    public Book getBookByID(int id) throws LibraryException {
        if (!books.containsKey(id)) {
            throw new LibraryException("There is no such book with that ID.");
        }
        return books.get(id);
    }
    
    public List<Patron> getPatrons(){
    	List<Patron> out = new ArrayList<>(patrons.values());
    	return Collections.unmodifiableList(out);
    }

    public Patron getPatronByID(int id) throws LibraryException {
        if (!patrons.containsKey(id)) {
        	throw new LibraryException("There are no patrons with that ID.");
        }
        return patrons.get(id);
    }

    public void addBook(Book book) {
        if (books.containsKey(book.getId())) {
            throw new IllegalArgumentException("Duplicate book ID.");
        }
        books.put(book.getId(), book);
    }

    public void addPatron(Patron patron) {
        if (patrons.containsKey(patron.getId())) {
        	throw new IllegalArgumentException("Duplicate patron ID.");
        }
    	patrons.put(patron.getId(), patron);
    }
}
 