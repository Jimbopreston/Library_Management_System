package bcu.m8.librarysystem.model;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import bcu.m8.librarysystem.main.LibraryException;

public class PatronTest {
	Patron patron = new Patron(1,"Jim Preston","07555555555","email@email.com");
	Book book = new Book(1,"The Hunger Games", "Suzanne Collins", "2008", "Scholastic");
	LocalDate startDate = LocalDate.now();
	LocalDate dueDate = startDate.plusDays(7);
	
	@Test
	public void testGetId() {
		int outputA = patron.getId();
		assertEquals(1,outputA); // passes if the id is 1
	}

	@Test
	public void testSetId() {
		patron.setId(2);
		assertEquals(2,patron.getId());
	}

	@Test
	public void testGetName() {
		String outputB = patron.getName();
		assertEquals("Jim Preston",outputB); // passes if the id is 1
	}

	@Test
	public void testSetName() {
		patron.setName("James Preston");
		assertEquals("James Preston", patron.getName());
	}

	@Test
	public void testGetPhone() {
		String outputC = patron.getPhone();
		assertEquals("07555555555",outputC); // passes if the id is 1
	}

	@Test
	public void testSetPhone() {
		patron.setPhone("07333333333");
		assertEquals("07333333333",patron.getPhone());
	}

	@Test
	public void testGetEmail() {
		String outputD = patron.getEmail();
		assertEquals("email@email.com",outputD);
	}

	@Test
	public void testSetEmail() {
		patron.setEmail("email@gmail.com");
		assertEquals("email@gmail.com",patron.getEmail());
	}

	@Test
	public void testGetBooks() {
		assertNotNull(patron.getBooks());
	}
	
	@Test
	public void testGetBooksAfterBorrowing() throws LibraryException {
	    patron.borrowBook(book, dueDate);
	    assertEquals(1, patron.getBooks().size());
	    assertTrue(patron.getBooks().contains(book));
	}

	@Test
	public void testBorrowBook() throws LibraryException {
		patron.borrowBook(book, dueDate);
		assertEquals(1, patron.getBooks().size());
		assertTrue(patron.getBooks().contains(book));
		assertTrue(book.isOnLoan());
	}

	@Test
	public void testRenewBook() throws LibraryException {
		patron.borrowBook(book, dueDate);
		LocalDate nDueDate = dueDate.plusDays(7);
		patron.renewBook(book, nDueDate);
		assertEquals(nDueDate,book.getDueDate());
	}

	@Test
	public void testReturnBook() throws LibraryException {
		patron.borrowBook(book, dueDate);
		patron.returnBook(book);
		assertEquals(0, patron.getBooks().size());
	    assertFalse(patron.getBooks().contains(book));

	}

	@Test
	public void testAddBook() {
		patron.addBook(book);
		assertEquals(1, patron.getBooks().size());
	}

}
