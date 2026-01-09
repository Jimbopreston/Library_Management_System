package bcu.m8.librarysystem.model;

import java.time.LocalDate;

import bcu.m8.librarysystem.main.LibraryException;

public class Book {
    
    private int id; //id of book used as a 'key' to generate the book object in other classes and methods
    private String title; //title of book
    private String author; //author of book
    private String publicationYear; //publicationn year of book
    private String publisher; //publisher of book
    private boolean deletedStatus; //boolean flag which is used to know if a book should be softdeleted upon being loaded into the system from book.txt
    private Loan loan; //each book stores its loan and the loan stores the book and the patron 

    //constructor for book object takes 1 int id and 4 strings for its fields
    public Book(int id, String title, String author, String publicationYear, String publisher) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
    }
    
    // getters and setters for each field
    public int getId() {
        return id;
    } 

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }
    
    /**
     * returns the publisher field of the book.
     * @return the publisher field as a string.
     */
    public String getPublisher() {
    	return publisher;
    }
    
    /**
     * sets the publisher field of the book using string.
     * @param publisher field as string.
     */
    public void setPublisher(String publisher) {
    	this.publisher = publisher;
    }
    
    /**
     * returns the deletedStatus to indicate if a book has been softdeleted.
     * @return the deletedStatus as boolean.
     */
    public boolean getDeletedStatus() { //returns the deletedStatus boolean flag which tells the system if the book should be softdeleted used as a check in other classes
    	return deletedStatus;
    }
	
    public String getDetailsShort() { //used to print the id and title in the listbooks command and other classes
        return "Book #" + id + " - " + title;
    }

    public String getDetailsLong() { //used to show the full details of a book when a patron has loaned it
        
        return "Book #" + id + " - " + title + " Author - " +  author + " Publication Year - " + publicationYear + " Publisher - " + publisher;
    }
    
    public boolean isOnLoan() { //a flag to check if a book is on loan returns true if loan field isnt null
        return (loan != null);
    }
    
    public String getStatus() { //primarily used in the gui to show if a book is on loan or available
    	if (this.isOnLoan()) {
    		return "On Loan";
    	}else {
    		return "Available";
    	}
    }

    public LocalDate getDueDate() { //checks if book is on loan and then checks the loan and returns the dueDate used in other classes
        if(isOnLoan()) {
        	return loan.getDueDate();	
        }else {
        	return null;
        } 
    }
    
    public void setDueDate(LocalDate dueDate) throws LibraryException { //used to set the due date on a loan from the book. this is used in the renewbook command
        if (!isOnLoan()) {
        	throw new LibraryException("Cannot set due date of a book that isnt on loan");
        }else {
        	loan.setDueDate(dueDate);
        }
    }

    public Loan getLoan() { //returns the loan used in other classes 
        return loan;
    }

    public void setLoan(Loan loan) { //used to set the loan field on the book
        this.loan = loan;
    }

    public void returnToLibrary() { //sets loan to null indicating the book has been returned to the library
        loan = null;
    }
    
    /**
     * soft deletes the book object from the system but not the book.txt file.
     * @return the deleted status boolean flag is set to true.
     */
    public boolean softDeleteBook() { //sets the softdelete flag to true which removes it from the system but not the .txt file
    	return this.deletedStatus = true;
    }
}
