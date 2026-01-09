package bcu.m8librarysystem.data;

import bcu.m8.librarysystem.main.LibraryException;
import bcu.m8.librarysystem.model.Library;

import java.io.IOException;

public interface DataManager { //the interface for the datamanagers all of them store and load data and need the separator
    
    public static final String SEPARATOR = "::";
    
    public void loadData(Library library) throws IOException, LibraryException;
    public void storeData(Library library) throws IOException;
}
 