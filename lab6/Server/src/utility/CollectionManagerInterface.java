package utility;

import data.LabWork;
import exception.ExecuteCommandError;
import exception.IllegalValueException;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Vector;

public interface CollectionManagerInterface {

    public Vector<LabWork> getLabWorkVector();

    public LocalDate getLastInitTime();
    public LabWork addFromInput(String args) throws ExecuteCommandError, IllegalValueException;

    LabWork addIfMin(String args) throws Exception;

    LabWork updateByIdCollection(String args) throws Exception;

    public void loadCollection() throws FileNotFoundException;
    public int getId();
    public void clear();
    public boolean checkExist(int id);



}