package bcu.cmp5332.librarysystem.model;
import static org.junit.Assert.*;
import java.time.LocalDate;
import org.junit.Test;
import bcu.cmp5332.librarysystem.main.LibraryException;

public class BookTest {
	Book book = new Book(1,"The Hunger Games", "Suzanne Collins", "2008", "Scholastic");
	Patron patron = new Patron(1,"Jim Preston","07555555555","email@email.com");
	LocalDate startDate = LocalDate.now();
	LocalDate dueDate = startDate.plusDays(7);
	//initialisation of variables for the tests to use
	
	@Test
	public void testGetId() {
		int outputA = book.getId();
		assertEquals(1,outputA); // passes if the id is 1
	}

	@Test
	public void testSetId() {
	    book.setId(2);
		assertEquals(2,book.getId()); //passes if the id changes to 2
	}

	@Test
	public void testGetTitle() {
		String outputB = book.getTitle();
		assertEquals("The Hunger Games",outputB); //passes if the title is gotten correctly
	}

	@Test
	public void testSetTitle() {
		book.setTitle("The Hunger Games : Catching Fire");
		assertEquals("The Hunger Games : Catching Fire",book.getTitle()); //passes if the title changes correctly
	}

	@Test
	public void testGetAuthor() {
		String outputC = book.getAuthor();
		assertEquals("Suzanne Collins",outputC); //passes if the author is gotten correctly
	}

	@Test
	public void testSetAuthor() {
		book.setAuthor("Susanne Colins");
		assertEquals("Susanne Colins", book.getAuthor()); //passes if the author is changed successfully
	}

	@Test
	public void testGetPublicationYear() {
		String outputD = book.getPublicationYear();
		assertEquals("2008",outputD); //passes if the publication year is gotten correctly
	}

	@Test
	public void testSetPublicationYear() {
		book.setPublicationYear("2025");
		assertEquals("2025",book.getPublicationYear()); //passes if the publication year changes correctly
	}

	@Test
	public void testGetPublisher() {
		String outputE = book.getPublisher();
		assertEquals("Scholastic",outputE);//passes if the publisher is gotten correctly
	}

	@Test
	public void testSetPublisher() {
		book.setPublisher("Penguin Books");
		assertEquals("Penguin Books", book.getPublisher());//passes if the publisher is changed correctly
	}

	@Test
	public void testGetDetailsShort() {
		String outputF = book.getDetailsShort();
		assertEquals("Book #1 - The Hunger Games",outputF);//passes if the details are printed correctly
	}

	@Test
	public void testGetDetailsLong() {
		String outputG = book.getDetailsLong(); //passes if the details are printed correctly
		assertEquals("Book #1 - The Hunger Games Author - Suzanne Collins Publication Year - 2008 Publisher - Scholastic",outputG);
	}

	@Test
	public void testIsNotOnLoan() {
		assertFalse(book.isOnLoan()); //passes if book isnt on loan as isOnLoan() gives false
	}
	
	@Test
	public void testIsOnLoan() throws LibraryException {
		patron.borrowBook(book,startDate);
		assertTrue(book.isOnLoan()); //passes if book is on loan as isOnLoan() gives true
	}

	@Test
	public void testGetStatusAvailable() {
		String outputH = book.getStatus();
		assertEquals("Available",outputH); //passes if the book isnt on loan and the string available is output
	}
	
	@Test
	public void testGetStatusOnLoan() throws LibraryException {
		patron.borrowBook(book,startDate);
		String outputI = book.getStatus();
		assertEquals("On Loan",outputI); //passes if the book is on loan and the string on loan is output
	}

	@Test
	public void testGetDueDateNull() {
		LocalDate outputJ = book.getDueDate();
		assertEquals(null,outputJ); //passes if the duedate is null indicating that the book isnt on loan
	}
	
	@Test
	public void testGetDueDate() throws LibraryException {
		patron.borrowBook(book,dueDate);
		LocalDate outputK = book.getDueDate();
		assertEquals(dueDate,outputK); //passes if the duedate is gotten correctly
	}
	
	@Test
	public void testSetDueDate() throws LibraryException {
		patron.borrowBook(book,dueDate);
		LocalDate nDueDate = dueDate.plusDays(7);
		book.setDueDate(nDueDate);
		assertEquals(nDueDate,book.getDueDate()); //passes if the new due date is set correctly
	}

	@Test
	public void testGetLoan() throws LibraryException {
		patron.borrowBook(book,dueDate);
		Loan outputL = book.getLoan();
		assertNotNull(outputL); //passes if the get loan doesnt result in null
	}

	@Test
	public void testSetLoan() {
		Loan loan = new Loan(patron,book,startDate,dueDate);
		book.setLoan(loan);
		assertNotNull(book.getLoan()); //passes if the loan is set by checking if get loan is not null
	}

	@Test
	public void testReturnToLibrary() throws LibraryException {
		patron.borrowBook(book,dueDate);
		book.returnToLibrary();
		assertNull(book.getLoan()); //passes if get loan returns null indicating that the book has been returned successfully
	}

}
