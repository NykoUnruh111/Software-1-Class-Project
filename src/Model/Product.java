package Model;

/**
 * Product class
 *
 * @author Nathaniel Unruh
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();;

    private int id, stock, min, max;
    private String name;
    private double price;

    public Product(int id, String name, double price, int stock, int min, int max) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;

    }

    /**
     * ID Setter
     * @param id
     */
    public void setID(int id) {

        this.id = id;

    }

    /**
     * Name setter
     * @param name
     */
    public void setName(String name) {

        this.name = name;

    }

    /**
     * Price setter
     * @param price
     */
    public void setPrice(double price) {

        this.price = price;

    }

    /**
     * Stock setter
     * @param stock
     */
    public void setStock(int stock) {

        this.stock = stock;

    }

    /**
     * Min setter
     * @param min
     */
    public void setMin(int min) {

        this.min = min;

    }

    /**
     * Max setter
     * @param max
     */
    public void setMax(int max) {

        this.max = max;

    }

    /**
     * ID getter
     * @return
     */

    public int getId() {

        return id;

    }

    /**
     * Name getter
     * @return
     */
    public String getName() {

        return name;

    }

    /**
     * Price getter
     * @return
     */
    public double getPrice() {

        return price;

    }

    /**
     * Stock getter
     * @return
     */
    public int getStock() {

        return stock;

    }

    /**
     * Min getter
     * @return
     */

    public int getMin() {

        return min;

    }

    /**
     * Max getter
     * @return
     */
    public int getMax() {

        return max;

    }

    /**
     * Add part to associated parts list
     * @param part
     */
    public void addAssociatedPart(Part part) {

        associatedParts.add(part);

    }

    /**
     * Deletes associated part from parts list
     * @param selectedAssociatedPart
     * @return
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {

        return associatedParts.remove(selectedAssociatedPart);

    }

    /**
     * Returns all associated parts from the associated parts list
     * @return associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts() {

        return associatedParts;

    }

}
