package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Patron {
    
    private int id;
    private String name;
    private String phone;
    private String email;
    private final List<Book> books = new ArrayList<>();
    
    public Patron(int id, String name, String phone, String email) {
    	this.id = id;
    	this.name = name;
    	this.phone = phone;   
    	this.email = email;
    }
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public List<Book> getBooks() {
		return books;
	}

    public void borrowBook(Book book, LocalDate dueDate) throws LibraryException {
    if (book.isOnLoan()) {
    	throw new LibraryException("Cannot borrow a book that is already being borrowed");
    }else {
    	LocalDate startDate = LocalDate.now();
    	Loan loan = new Loan(this, book, startDate, dueDate);
    	book.setLoan(loan);
    	this.addBook(book);
    }
    }

	public void renewBook(Book book, LocalDate dueDate) throws LibraryException {
        // TODO: implementation here
		if(book.isOnLoan()==false) {
			throw new LibraryException("Cannot renew a book that is not being loaned");
		}else {
			book.setDueDate(dueDate);
		}
    }

    public void returnBook(Book book) throws LibraryException {
        // TODO: implementation here
    	if (book.isOnLoan()) {
    		throw new LibraryException("Cannot return a book that isnt on loan");
    	}else {
    		
    	books.remove(book);
    	book.returnToLibrary();
    	}
    }
    
    public void addBook(Book book) {
    	books.add(book);
    }
    
}
 