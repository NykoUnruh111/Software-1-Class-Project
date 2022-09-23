package Model;

/**
 * Inventory class
 *
 * @author Nathaniel Unruh
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds part to allparts list
     * @param newPart
     */
    public static void addPart (Part newPart) {

        allParts.add(newPart);

    }

    /**
     * Adds product to allproducts list
     * @param newProduct
     */
    public static void addProduct(Product newProduct) {

        allProducts.add(newProduct);

    }

    /**
     * Looks part up based on partID
     * @param partID
     * @return
     */
    public static Part lookupPart(int partID) {

        for (int i = 0; i < allParts.size(); i++) {

            if (allParts.get(i).getId() == partID) {

                return allParts.get(i);

            }
        }

        return null;

    }

    /**
     * Looks up product based on productID
     * @param productID
     * @return
     */
    public static Product lookupProduct(int productID) {

        for (int i = 0; i < allProducts.size(); i++) {

            if (allProducts.get(i).getId() == productID) {

                return allProducts.get(i);

            }


        }

        return null;

    }

    /**
     * Looks up part based on partName string
     * @param partName
     * @return
     */
    public static ObservableList<Part> lookupPart(String partName) {

        ObservableList<Part> tempPartList = FXCollections.observableArrayList();

        for (int i = 0; i < allParts.size(); i++) {

            if (allParts.get(i).getName().contains(partName)) {

                tempPartList.add(allParts.get(i));
            }

        }

        return tempPartList;

    }

    /**
     * Looks up product based on productName string
     * @param productName
     * @return
     */
    public static ObservableList<Product> lookupProduct(String productName) {

        ObservableList<Product> tempProductList = FXCollections.observableArrayList();

        for (int i = 0; i < allProducts.size(); i++) {

            if (allProducts.get(i).getName().contains(productName)) {

                tempProductList.add(allProducts.get(i));

            }

        }

        return tempProductList;

    }

    /**
     * updates part at Index
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart) {

        allParts.set(index, selectedPart);

    }

    /**
     * updates product at Index
     * @param index
     * @param newProduct
     */
    public static void updateProduct(int index, Product newProduct) {

        allProducts.set(index, newProduct);

    }

    /**
     * Deletes selected part
     * @param selectedPart
     * @return
     */
    public static boolean deletePart(Part selectedPart) {

        if (selectedPart != null) {

            allParts.remove(selectedPart);

            return true;

        }

        return false;

    }

    /**
     * Deletes selected Product
     * @param selectedProduct
     * @return
     */
    public static boolean deleteProduct(Product selectedProduct) {

        if (selectedProduct != null) {

            allProducts.remove(selectedProduct);

            return true;

        }

        return false;

    }

    /**
     * Returns all parts in allParts list
     * @return
     */
    public static ObservableList<Part> getAllParts() {

        return allParts;

    }

    /**
     * Returns all products in allPrducts list
     * @return
     */
    public static ObservableList<Product> getAllProducts() {

        return allProducts;

    }
}
