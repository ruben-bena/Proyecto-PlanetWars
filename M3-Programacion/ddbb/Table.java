package ddbb;

// Defines all methods shared between Table objects
public interface Table {
    public void insertRow();
    public void getRow(int id);
    public void deleteRow();
    public void modifyRow();
}
