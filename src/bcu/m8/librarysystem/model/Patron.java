package bcu.m8.librarysystem.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bcu.m8.librarysystem.main.LibraryException;

public class Patron {
    
    private int id; //patronId used in various classes
    private String name; //patron name
    private String phone; //patron phone number
    private String email; //patron email address
    private boolean deletedStatus; //deletedstatus flag tells system if the patron should be softdeleted from the system
    private int bookLimit = 2; //book limit can be altered to control how many books a patron can have on loan
    private final List<Book> books = new ArrayList<>(); //list of books a patron currently has
    private final List<Loan> loanHistory = new ArrayList<>(); //list of previous loans of the patron
    
    public Patron(int id, String name, String phone, String email) { //constructor for the patron using some of the above fields
    	this.id = id;
    	this.name = name;
    	this.phone = phone;   
    	this.email = email;
    }
    
    //getters and setters of the patron class
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
	/**
	 * Gets the patrons email address.
	 * @return the email of the patron as a string.
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * sets the email address of the patron.
	 * @param the string will be set as the patrons email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public List<Book> getBooks() { //returns the list of books. used in datamanager to store info in .txt files 
		return books;
	}
	/**
	 * a list of all the loans a patron has had including the book start date due date and the return date.
	 * @return a list of the patrons loan history.
	 */
	public List<Loan> getLoanHistory(){ //used in the showloanhistory command and shows the patrons previous loans
		return loanHistory;
	}
	/**
	 * returns the boolean value of the deletedstatus of a patron.
	 * @return the boolean value of deletedStatus.
	 */
	public boolean getDeletedStatus() { //deletedstatus flag to tell system whether patron should be softdeleted or not
		return deletedStatus;
	}
	/**
	 * returns the booklimit of a patron as an int.
	 * @return int value of bookLimit field.
	 */
	public int getBookLimit() { //returns the value of book limit used to check the limit against current amoount of books to prevent patrons loaning too many books
		return bookLimit;
	}
	
	
    public void borrowBook(Book book, LocalDate dueDate) throws LibraryException { //borrow book method. creates a loan and puts the book in the books list aswell as sets the loan of the book object
	    if(this.getBooks().size() < this.getBookLimit()) { //book limit being used
		    if (book.isOnLoan()) { //checks if book is already on loan.
		    	throw new LibraryException("Cannot borrow a book that is already being borrowed");
		    }else {
		    	LocalDate startDate = LocalDate.now(); //localDate.now() gets the current date for start date of loan
		    	Loan loan = new Loan(this, book, startDate, dueDate);
		    	book.setLoan(loan);
		    	this.addBook(book);
		    }
	    }else {
	    	throw new LibraryException("Cannot borrow anymore books"); //throws library exception if patron is at loan limit
	    }
    }

	public void renewBook(Book book, LocalDate dueDate) throws LibraryException { //used to renew the loan by a week
        // TODO: implementation here
		if(book.isOnLoan()==false) {
			throw new LibraryException("Cannot renew a book that is not being loaned");
		}else {
			book.setDueDate(dueDate);
		}
    }

    public void returnBook(Book book) throws LibraryException { //returns the book to the library and adds the loan to the loan history
        
    	if (!book.isOnLoan()) {
    		throw new LibraryException("Cannot return a book that isnt on loan");
    	}else {
    	Loan oldLoan = book.getLoan();
    	LocalDate returnDate = LocalDate.now();
    	oldLoan.setReturnDate(returnDate);
    	oldLoan.setLoanTerminatedTrue();
    	this.addLoanToLH(oldLoan);
    	books.remove(book);
    	book.returnToLibrary();
    	}
    }
    
    public void addBook(Book book) { //adds book to books list of patron
    	books.add(book);
    }
    /** 
     * Adds a loan object to the patrons loan history list.
     * @param loan object from the patrons previous loan.
     */
    public void addLoanToLH(Loan loan) { //adds a loan to the loan history
    	loanHistory.add(loan);
    }
    
    /**
     * soft deletes the patron from the system, doesnt delete them from patron.txt.
     * @return sets the deletedstatus boolean to true.
     */
    public boolean softDeletePatron() { //softdeletes the patron from the system not the .txt file
    	return this.deletedStatus = true;
    }
}
 