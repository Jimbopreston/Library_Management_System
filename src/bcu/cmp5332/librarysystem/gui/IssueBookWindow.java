package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.commands.BorrowBook;
import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Patron;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.JComboBox;

public class IssueBookWindow extends JFrame implements ActionListener {
	private MainWindow mw;
	private JButton issueBtn = new JButton("Issue");
	private JButton cancelBtn = new JButton("Cancel");
	
	//combo box displays dropdown lists.
	private JComboBox<String> patronCombo = new JComboBox<>();
	private JComboBox<String> bookCombo = new JComboBox<>();
	
	public IssueBookWindow(MainWindow mw) {
		this.mw = mw;
		initialize();
	}
	
	private void initialize() {
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        } 
		
		setTitle("Issue a book");
		
		setSize(350,200);
		JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 2));
        topPanel.add(new JLabel("Select Patron : "));
        topPanel.add(patronCombo);
        topPanel.add(new JLabel("Select Book : "));
        topPanel.add(bookCombo);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(issueBtn);
        bottomPanel.add(cancelBtn);

        issueBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        setVisible(true);
        
        //pulls the data from the library to fill the lists.
        List<Patron> patrons = mw.getLibrary().getPatrons();
        List<Book> books = mw.getLibrary().getBooks();
        
        //fills the patron combo
        for (Patron p : patrons) {
        	patronCombo.addItem(p.getId() + " - " + p.getName());
        }
        
        //fills the book combo with only books that are available
        for (Book b : books) {
        	if (!b.isOnLoan()) {
        		bookCombo.addItem(b.getId() + " - " + b.getTitle());
        		}
        	}
    	}
	
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == issueBtn) {
                issueBook();
            } else if (ae.getSource() == cancelBtn) {
                this.setVisible(false);
            }
		
        }
        
        private void issueBook() {
        	try {
        		//gets whatever box is selected as a string
        		String patronStr = (String) patronCombo.getSelectedItem();
        		String bookStr = (String) bookCombo.getSelectedItem();
        		
        		//in case there are no more books left.
        		if (patronStr == null || bookStr == null) {
        			throw new LibraryException("You must select both a patron and a book.");
        		}
        		
        		//gets the ID from the string
        		int patronId = Integer.parseInt(patronStr.split(" - ")[0]);
                int bookId = Integer.parseInt(bookStr.split(" - ")[0]);
                
                //creates and executes the borrow book command
                Command borrow = new BorrowBook(patronId, bookId);
                borrow.execute(mw.getLibrary(), LocalDate.now());
                
                //refreshes the displayBooks window
                mw.displayBooks();
                
                //closes the window
                this.setVisible(false);
        		
        	} catch (LibraryException ex) {
        		JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        	}
        	
        }

}
