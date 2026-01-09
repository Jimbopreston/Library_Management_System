package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.commands.RenewBook;
import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

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
public class RenewBookWindow extends JFrame implements ActionListener {
	private MainWindow mw;
    private JButton renewBtn = new JButton("Renew");
    private JButton cancelBtn = new JButton("Cancel");
    private JComboBox<String> bookCombo = new JComboBox<>();

    /**
     * 
     */
    public RenewBookWindow(MainWindow mw) {
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
    	setTitle("Renew a Book");
    	
    	setSize(700,200);
		JPanel topPanel = new JPanel();
		//borderlayout means there's more space for the box
        topPanel.setLayout(new BorderLayout(10, 0));
        topPanel.add(new JLabel("Select Book to renew: "), BorderLayout.WEST);
        topPanel.add(bookCombo);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(renewBtn);
        bottomPanel.add(cancelBtn);

        renewBtn.addActionListener(this);
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
                bookCombo.addItem(b.getId() + " - " + b.getTitle() + " (Due: " + b.getDueDate() + ")");
                booksFound = true;
            }
        }

        if (!booksFound) {
            bookCombo.addItem("No books are currently loaned out");
            bookCombo.setEnabled(false);
            renewBtn.setEnabled(false);
        }
    	
    }
    
    /**
     * 
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == renewBtn) {
            renewBook();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }
    
    /**
     * 
     */
    private void renewBook() {
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
            
            Command renewCmd = new RenewBook(patronId, bookId);
            renewCmd.execute(mw.getLibrary(), LocalDate.now());
            
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
            JOptionPane.showMessageDialog(this, "Book renewed successfully.");
            this.setVisible(false);
            
    		} catch (LibraryException ex) {
    			JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    		}
    }
    

}
