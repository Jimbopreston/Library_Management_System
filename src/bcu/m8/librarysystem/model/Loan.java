package bcu.m8.librarysystem.model;

import java.time.LocalDate;

public class Loan {
    
    private Patron patron; //patron who is loaning book
    private Book book; //book object loaned by patron
    private LocalDate startDate; //date book was loaned
    private LocalDate dueDate;//date the book is supposed to be returned by the patron
    private boolean loanTerminated = false; //boolean flag to check if a loan is terminated(old,returned)
    private LocalDate returnDate; //the date that the book was returned to library if applicable
    
    public Loan(Patron patron, Book book, LocalDate startDate, LocalDate dueDate) { //constructor for loan object
        this.patron = patron;
        this.book = book;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }
 //setters and getters for loan object
	public Patron getPatron() {
		return patron;
	}

	public void setPatron(Patron patron) {
		this.patron = patron;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	
	/**
	 * the loanTerminated flag used to load patron loanHistory from the loans.txt file.
	 * @return boolean true or false of LoanTerminated.
	 */
	public boolean getLoanTerminated() { //returns the loanTerminated flag. used in the check used in loandatamanager to generate loanhistory lists for patrons
		return loanTerminated;
	}
	
	/**
	 * sets the loanTerminated flag to true.
	 */
	public void setLoanTerminatedTrue() { //used to set the loanterminated flag to true indicating the book has been returned and the loan is now old.
		this.loanTerminated = true;
	}
	
	/**
	 * returns the return date for a when a patron returned a book used for loanHistory.
	 * @return LocalDate returnDate.
	 */
	public LocalDate getReturnDate() { //gets the date of return of book
		return returnDate;
	}
	
	/**
	 * sets a loans returnDate.
	 * @param LocalDate returnDate.
	 */
	public void setReturnDate(LocalDate returnDate) { //sets the date of return of the book
		this.returnDate = returnDate;
	}
	
	/**
	 * sets the return date to null for proper filestorage of loanhistory.
	 */
	public void setReturnDateNull() { //sets the return date to null. used for data storage for loans that havent been returned yet.
		this.returnDate = null;
	}
    
}
 