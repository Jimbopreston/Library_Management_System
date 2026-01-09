package bcu.m8.librarysystem.gui;

import bcu.m8.librarysystem.main.LibraryException;
import bcu.m8.librarysystem.model.Book;
import bcu.m8.librarysystem.model.Library;
import bcu.m8.librarysystem.model.Patron;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;

public class MainWindow extends JFrame implements ActionListener {

    private JMenuBar menuBar;
    private JMenu adminMenu;
    private JMenu booksMenu;
    private JMenu membersMenu;

    private JMenuItem adminExit;

    private JMenuItem booksView;
    private JMenuItem booksAdd;
    private JMenuItem booksDel;	
    private JMenuItem booksIssue;
    private JMenuItem booksReturn;
    private JMenuItem booksRenew;

    private JMenuItem memView;
    private JMenuItem memAdd;
    private JMenuItem memDel;

    private Library library;

    public MainWindow(Library library) {

        initialize();
        this.library = library;
    } 
    
    public Library getLibrary() {
        return library;
    }
    
    // Needed for the rollback. It tells the MainWindow to stop looking at the corrupted memory.
    public void setLibrary (Library library) {
    	this.library = library;
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }

        setTitle("Library Management System");

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //adding adminMenu menu and menu items
        adminMenu = new JMenu("Admin");
        menuBar.add(adminMenu);

        adminExit = new JMenuItem("Exit");
        adminMenu.add(adminExit);
        adminExit.addActionListener(this);

        // adding booksMenu menu and menu items
        booksMenu = new JMenu("Books");
        menuBar.add(booksMenu);

        booksView = new JMenuItem("View");
        booksAdd = new JMenuItem("Add");
        booksDel = new JMenuItem("Delete");
        booksIssue = new JMenuItem("Issue");
        booksReturn = new JMenuItem("Return");
        booksRenew = new JMenuItem("Renew");
        booksMenu.add(booksView);
        booksMenu.add(booksAdd);
        booksMenu.add(booksDel);
        booksMenu.add(booksIssue);
        booksMenu.add(booksReturn);
        booksMenu.add(booksRenew);
        for (int i = 0; i < booksMenu.getItemCount(); i++) {
            booksMenu.getItem(i).addActionListener(this);
        }

        // adding membersMenu menu and menu items
        membersMenu = new JMenu("Patrons");
        menuBar.add(membersMenu);

        memView = new JMenuItem("View");
        memAdd = new JMenuItem("Add");
        memDel = new JMenuItem("Delete");

        membersMenu.add(memView);
        membersMenu.add(memAdd);
        membersMenu.add(memDel);

        memView.addActionListener(this);
        memAdd.addActionListener(this);
        memDel.addActionListener(this);

        setSize(800, 500);

        setVisible(true);
        setAutoRequestFocus(true);
        toFront();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
/* Uncomment the following line to not terminate the console app when the window is closed */
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);        

    }	

/* Uncomment the following code to run the GUI version directly from the IDE */
//    public static void main(String[] args) throws IOException, LibraryException {
//        Library library = LibraryData.load();
//        new MainWindow(library);			
//    }



    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == adminExit) {
            System.exit(0);
        } else if (ae.getSource() == booksView) {
            displayBooks();
            
        } else if (ae.getSource() == booksAdd) {
            new AddBookWindow(this);
            
        } else if (ae.getSource() == booksDel) {
        	displayBooks();
            new DeleteBookWindow(this);
            
        } else if (ae.getSource() == booksIssue) {
        	displayBooks();
            new IssueBookWindow(this);
            
        } else if (ae.getSource() == booksReturn) {
        	displayBooks();
        	new ReturnBookWindow(this);
            
        } else if (ae.getSource() == booksRenew) {
        	displayBooks();
        	new RenewBookWindow(this);
            
        }else if (ae.getSource() == memView) {
            displayPatrons();
            
        } else if (ae.getSource() == memAdd) {
        	displayPatrons();
            new AddPatronWindow(this);
            
        } else if (ae.getSource() == memDel) {
        	displayPatrons();
            new DeletePatronWindow(this);
            
        }
    }
    
    //task 6.1
    //method to show the loan details (used in displayBooks when a book is clicked in GUI)
    private void showLoanDetails(int bookId) {
        try {
        	//accesses the model
            Book book = library.getBookByID(bookId);
            
            //if the book is on a loan, it retrieves the loan and patron objects, makes a message and displays it in a pop up. 
            if (book.isOnLoan()) {
                Patron patron = book.getLoan().getPatron();
                
                String message = "--- BOOK IS ON LOAN ---\n\n" +
                                 "Title: " + book.getTitle() + "\n" +
                                 "Due Date: " + book.getDueDate() + "\n\n" +
                                 "--- Patron Details ---\n" +
                                 "ID: " + patron.getId() + "\n" +
                                 "Name: " + patron.getName() + "\n" +
                                 "Phone: " + patron.getPhone() + "\n" +
                                 "Email: " + patron.getEmail();
                
                JOptionPane.showMessageDialog(this, message, "Loan Details", JOptionPane.INFORMATION_MESSAGE);
                      
            } else { //if it's not on loan, it makes a pop-up saying the book is available.
            	String message = "--- BOOK IS AVAILABLE TO BORROW ---";
            	
            	JOptionPane.showMessageDialog(this, message, "Loan Details", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (LibraryException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //task 6.3
    //method to show the details of books a patron has borrowed (used in displayPatrons when a patron is clicked in GUI)
    private void showPatronBookDetails(int patronId) {
    	try {
    		Patron patron = library.getPatronByID(patronId);
            List<Book> booksOnLoan = patron.getBooks();
            
            if (booksOnLoan.size() > 0) {
            	String message = "Name: " + patron.getName() + "\n\n" +
            					 "--- BOOKS CURRENTLY BORROWED ---\n";
            	
            	for (Book book : booksOnLoan) {
            		message += "\n\n" +
            				"ID: " + book.getId() + "\n" +
            				"Title: " + book.getTitle() + "\n" +
            				"Author: " + book.getAuthor() + "\n" +
                            "Due Date: " + book.getDueDate();
                }
                
                JOptionPane.showMessageDialog(this, message, "Patron Loans", JOptionPane.INFORMATION_MESSAGE);
            }
            
    	} catch (LibraryException ex) {
    		JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    	}
    }

    public void displayBooks() {
        List<Book> booksList = library.getActiveBooks();
        // headers for the table
        // added book ID to be stored in column 0
        String[] columns = new String[]{"ID", "Title", "Author", "Pub Date", "Publisher", "Status"};

        Object[][] data = new Object[booksList.size()][6];
        for (int i = 0; i < booksList.size(); i++) {
            Book book = booksList.get(i);
            data[i][0] = book.getId();
            data[i][1] = book.getTitle();
            data[i][2] = book.getAuthor();
            data[i][3] = book.getPublicationYear();
            data[i][4] = book.getPublisher();
            data[i][5] = book.getStatus();
        }

        JTable table = new JTable(data, columns);
        
        //Task 6.1
        //detects clicks
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row != -1) {
                	//gets the ID from column 0, makes it a string
                    String idStr = table.getValueAt(row, 0).toString();
                    //turns the idStr into an integer for showLoanDetails to use
                    int bookId = Integer.parseInt(idStr);
                    
                    //shows the loan details for the specific booked clicked.
                    showLoanDetails(bookId);
                }
            }
        });
        
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
        this.repaint();
    }
    
    public void displayPatrons() {
        List<Patron> patronList = library.getActivePatrons();
        // headers for the table
        // added book ID to be stored in column 0
        String[] columns = new String[]{"ID", "Name", "Phone", "Email", "Books on loan"};

        Object[][] data = new Object[patronList.size()][5];
        for (int i = 0; i < patronList.size(); i++) {
            Patron patron = patronList.get(i);
            data[i][0] = patron.getId();
            data[i][1] = patron.getName();
            data[i][2] = patron.getPhone();
            data[i][3] = patron.getEmail();
            data[i][4] = patron.getBooks().size();
        }

        JTable table = new JTable(data, columns);
        
        //task 6.3
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row != -1) {
                	//gets the ID from column 0, makes it a string
                    String idStr = table.getValueAt(row, 0).toString();
                    //turns the idStr into an integer for showLoanDetails to use
                    int patronId = Integer.parseInt(idStr);
                    
                    //shows the loan details for the specific booked clicked.
                    showPatronBookDetails(patronId);
                }
            }
        });
        
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
        this.repaint();
    }
 
    
}
