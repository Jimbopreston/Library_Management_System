package bcu.m8.librarysystem.gui;

import bcu.m8.librarysystem.commands.Command;
import bcu.m8.librarysystem.commands.ReturnBook;
import bcu.m8.librarysystem.data.LibraryData;
import bcu.m8.librarysystem.main.LibraryException;
import bcu.m8.librarysystem.model.Book;
import bcu.m8.librarysystem.model.Library;
import bcu.m8.librarysystem.model.Patron;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.JComboBox;

/**
 * 
 */
public class ReturnBookWindow extends JFrame implements ActionListener{
	private MainWindow mw;
    private JButton returnBtn = new JButton("Return");
    private JButton cancelBtn = new JButton("Cancel");
    
    //combo box displays dropdown lists.
    private JComboBox<String> bookCombo = new JComboBox<>();

    /**
     * 
     */
    public ReturnBookWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }
    
    /**
     * 
     */
    private void initialize() {
    	try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        } 
		setTitle("Return a book");
		
		setSize(700,200);
		JPanel topPanel = new JPanel();
		//borderlayout means there's more space for the box
        topPanel.setLayout(new BorderLayout(10, 0));
        topPanel.add(new JLabel("Select Book to return: "), BorderLayout.WEST);
        topPanel.add(bookCombo);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(returnBtn);
        bottomPanel.add(cancelBtn);

        returnBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        setVisible(true);
        
        //pulls the data from the library to fill the lists.
        List<Book> books = mw.getLibrary().getActiveBooks();
        //tracks if a borrowed book has been found
        boolean booksFound = false;
        
        //fills the book combo with only books that are loaned out
        for (Book b : books) {
            if (b.isOnLoan()) {
                Patron p = b.getLoan().getPatron();
                bookCombo.addItem(b.getId() + " - " + b.getTitle() + " (Borrowed by: " + p.getName() + ")");
                booksFound = true;
            }
        }

        if (!booksFound) {
            bookCombo.addItem("No books are currently loaned out");
            bookCombo.setEnabled(false);
            returnBtn.setEnabled(false);
        }
    	 	
    }
    
    /**
     * 
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == returnBtn) {
            returnBook();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }
    
    /**
     * 
     */
    private void returnBook() {
    	try {
    		//gets whatever box is selected as a string
    		String selection = (String) bookCombo.getSelectedItem();
    		
    		//in case there are no more books left
    		if (selection == null || selection.equals("No books are currently loaned out")) {
    			return;
    		}
    		
    		//gets the ID from the string
    		int bookId = Integer.parseInt(selection.split(" - ")[0]);
    		
    		//gets the patron id
    		Book b = mw.getLibrary().getBookByID(bookId);
            int patronId = b.getLoan().getPatron().getId();
            
            //creates and executes the return book command
            Command returnCmd = new ReturnBook(patronId, bookId);
            returnCmd.execute(mw.getLibrary(), LocalDate.now());
            
            //lets the GUI save to the text files.
            try {
                LibraryData.store(mw.getLibrary());
            //Error message
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving data. Rolling back changes."
                		+ "\nError Details: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                //rolls back the changes if it cannot save to the text file.
                try {
                	Library cleanLib = LibraryData.load();
                  	mw.setLibrary(cleanLib);
                } catch (Exception ex) {
                	JOptionPane.showMessageDialog(this, "Rollback failed: " + ex.getMessage());
                }
            }
            
            //refresh, confirm, close
            mw.displayBooks();
            JOptionPane.showMessageDialog(this, "Book returned successfully.");
            this.setVisible(false);
    		
    		} catch (LibraryException ex) {
    			JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    		}
    }

}
