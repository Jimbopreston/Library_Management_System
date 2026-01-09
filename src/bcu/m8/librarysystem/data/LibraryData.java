package bcu.m8.librarysystem.data;

import bcu.m8.librarysystem.main.LibraryException;
import bcu.m8.librarysystem.model.Library;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LibraryData {
    
    private static final List<DataManager> dataManagers = new ArrayList<>();
    
    // runs only once when the object gets loaded to memory
    static {
        dataManagers.add(new BookDataManager()); //when the library data object is loaded the .txt files are loaded by adding their specific datamanagers
        dataManagers.add(new PatronDataManager());
        dataManagers.add(new LoanDataManager());
    }
    
    public static Library load() throws LibraryException, IOException {

        Library library = new Library();
        for (DataManager dm : dataManagers) {
            dm.loadData(library); //loads the data into the library object and returns the library for use
        }
        return library;
    }

    public static void store(Library library) throws IOException {

        for (DataManager dm : dataManagers) {
            dm.storeData(library); //stores the data into the library object and thus the .txt objects
        }
    }
    
}
 