package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.commands.DeleteBook;
import bcu.cmp5332.librarysystem.commands.DeletePatron;
import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.model.Library;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;

public class DeletePatronWindow extends JFrame implements ActionListener {
	private MainWindow mw;
    private JButton deleteBtn = new JButton("Delete");
    private JButton cancelBtn = new JButton("Cancel");
    private JComboBox<String> patronCombo = new JComboBox<>();

    public DeletePatronWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }
    
    private void initialize() {
    	try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        	
        } 
    	
    	setTitle("Delete a patron");
    	
    	setSize(300, 200);
    	JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 2));
        topPanel.add(new JLabel("Select Patron : "));
        topPanel.add(patronCombo);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(deleteBtn);
        bottomPanel.add(cancelBtn);
        
        deleteBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        
        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        setVisible(true);
        
        //pulls the data from the library to fill the lists.
        List<Patron> patrons = mw.getLibrary().getActivePatrons();
        //tracks if a deletable book has been found
        boolean patronsFound = false;
        
        //fills the book combo with only books that are available
        for (Patron p : patrons) {
        	if (p.getBooks().isEmpty()) {
        		patronCombo.addItem(p.getId() + " - " + p.getName());
        		patronsFound = true;
        		}
        	}
        //if patronsFound is false, stops the combo and buttons from working.
        if (!patronsFound) {
        	patronCombo.addItem("There are no more patrons that can be deleted");
        	patronCombo.setEnabled(false);
        	deleteBtn.setEnabled(false);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == deleteBtn) {
            deletePatron();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }
	
    }
    
    private void deletePatron() {
    	try {
    		//gets whatever box is selected as a string
    		String selection = (String) patronCombo.getSelectedItem();
    		
    		//in case there are no more patrons left
    		if (selection == null || selection.equals("There are no more patrons that can be deleted")) {
    			return;
    		}
    		
    		//gets the ID from the string
            int patronId = Integer.parseInt(selection.split(" - ")[0]);
            
            //Deletes the patron
            Command deletePatronCmd = new DeletePatron(patronId);
            deletePatronCmd.execute(mw.getLibrary(), LocalDate.now());
            
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
            mw.displayPatrons();
            JOptionPane.showMessageDialog(this, "Patron deleted successfully.");
            this.setVisible(false);
    		
    		
    		
    	} catch (LibraryException ex) {
    		JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    	}
    }
    
    	
        
    
        
    	
}
