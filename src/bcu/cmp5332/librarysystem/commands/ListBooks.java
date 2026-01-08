package bcu.cmp5332.librarysystem.commands;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;
import java.time.LocalDate;
import java.util.List;

public class ListBooks implements Command {

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        List<Book> books = library.getActiveBooks();
        for (Book book : books) {
        	if(book.getDeletedStatus() == false)
            System.out.println(book.getDetailsShort());
        }
        System.out.println(books.size() + " book(s)");
    }
    
    public boolean altersData() {
    	return false;
    }
}
 