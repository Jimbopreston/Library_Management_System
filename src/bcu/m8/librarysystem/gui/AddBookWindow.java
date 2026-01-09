package bcu.m8.librarysystem.gui;

import bcu.m8.librarysystem.commands.AddBook;
import bcu.m8.librarysystem.commands.Command;
import bcu.m8.librarysystem.data.LibraryData;
import bcu.m8.librarysystem.main.LibraryException;
import bcu.m8.librarysystem.model.Library;

import java.io.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;


/**
 * A GUI window that adds new books to the library.
 * 
 * The AddBookWindow class handles the user input for the books details, executes the {@link AddBook} command,
 * as well as ensuring data is updated and saved correctly.
 */
public class AddBookWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private JTextField titleText = new JTextField();
    private JTextField authText = new JTextField();
    private JTextField pubDateText = new JTextField();
    private JTextField pubText = new JTextField();

    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");
    
    /**
     * Constructor for the AddBookWindow.
     * @param mw The parent {@link MainWindow}
     * Needed for refreshing the book display, and accessing the Library model.
     */
    public AddBookWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    /**
     * Initializes the contents of the frame.
     */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        } 

        setTitle("Add a New Book");

        setSize(300, 200);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(5, 2));
        topPanel.add(new JLabel("Title : "));
        topPanel.add(titleText);
        topPanel.add(new JLabel("Author : "));
        topPanel.add(authText);
        topPanel.add(new JLabel("Publishing Date : "));
        topPanel.add(pubDateText);
        topPanel.add(new JLabel("Publisher : "));
        topPanel.add(pubText);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(addBtn);
        bottomPanel.add(cancelBtn);

        addBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        setVisible(true);

    }
    
    /**
     * Handles the button click events for the window.
     * @param ae The {@link ActionEvent} triggered by clicking the Add or Cancel button.
     */

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            addBook();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }

    }
    
    /**
     * Reads the input from the GUI, creates an {@link AddBook} command, and executes it.
     * It also ensures that data is updated and saved correctly. If the save fails, it rollsback.
     */
    private void addBook() {
        try {
            String title = titleText.getText();
            String author = authText.getText();
            String publicationYear = pubDateText.getText();
            String publisher = pubText.getText();
            // create and execute the AddBook Command
            Command addBook = new AddBook(title, author, publicationYear, publisher);
            addBook.execute(mw.getLibrary(), LocalDate.now());
            
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
            
            // refresh the view with the list of books
            mw.displayBooks();
            //confirms addition
            JOptionPane.showMessageDialog(this, "Book added successfully.");
            // hide (close) the AddBookWindow
            this.setVisible(false);
        } catch (LibraryException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        // ----------------------
    }

}
