package ViewControllers;

/**
 * FXML Main Form Controller class
 *
 * @author Nathaniel Unruh
 */

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXML;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {

    @FXML
    TextField SearchPartsTextField;

    @FXML
    TextField SearchProductsTextField;

    @FXML
    TableView<Part> partTable;

    @FXML
    TableColumn<Part, Integer> partIDCol;

    @FXML
    TableColumn<Part, String> partNameCol;

    @FXML
    TableColumn<Part, Integer> partInvLevelCol;

    @FXML
    TableColumn<Part, Double> partPriceCol;

    @FXML
    TableView<Product> productTable;

    @FXML
    TableColumn<Product, Integer> productIDCol;

    @FXML
    TableColumn<Product, String> productNameCol;

    @FXML
    TableColumn<Product, Integer> productInvLevelCol;

    @FXML
    TableColumn<Product, Double> productPriceCol;


    public static int partModifyIndex = 0;
    public static int productModifyIndex = 0;

    public static int partID = 0;
    public static int productID = 0;

    private ObservableList<Part> visibleParts = FXCollections.observableArrayList();
    private ObservableList<Product> visibleProducts = FXCollections.observableArrayList();

    /**
     * Initializes the form
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        System.out.println("IM ALIIIIVE!!!");

        //partTable.getSelectionModel().setCellSelectionEnabled(true);
        //productTable.getSelectionModel().setCellSelectionEnabled(true);

        visibleParts = Inventory.getAllParts();
        visibleProducts = Inventory.getAllProducts();

        populateTables();


    }

    /**
     * Populates both tables
     */
    public void populateTables() {

        //Populate part columns
        partIDCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        partNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        partInvLevelCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getStock()).asObject());
        partPriceCol.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());

        //Populate product columns
        productIDCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        productNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        productInvLevelCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getStock()).asObject());
        productPriceCol.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());

        productTable.setItems(visibleProducts);
        partTable.setItems(visibleParts);

    }


    /**
     * Searches the part table using the string from the search text field
     */
    @FXML
    public void searchPart() {

        if (!SearchPartsTextField.getText().isEmpty()) {

            visibleParts = FXCollections.observableArrayList();

            System.out.println("Searching for part!");

            if (isInt(SearchPartsTextField.getText())) {

                //Search for ID using integer
                System.out.println("Integer");

                Part tempPart = Inventory.lookupPart(Integer.parseInt(SearchPartsTextField.getText()));

                if (tempPart != null)
                    visibleParts.add(tempPart);



            } else {

                //Search using part name
                visibleParts = Inventory.lookupPart(SearchPartsTextField.getText());

            }



            if (visibleParts.isEmpty()) {

                Alert noResults = new Alert(Alert.AlertType.ERROR);
                noResults.initModality(Modality.NONE);
                noResults.setTitle("Results");
                noResults.setHeaderText("No results found!");

                Optional<ButtonType> failResult = noResults.showAndWait();
                //System.out.println("No results found!");

            }



        } else {

            // visibleParts = Inventory.getAllParts();
            Alert boxEmpty = new Alert(Alert.AlertType.ERROR);
            boxEmpty.setTitle("Empty search!");
            boxEmpty.setHeaderText("Nothing in search box!");

            Optional<ButtonType> failResult = boxEmpty.showAndWait();

            visibleParts = Inventory.getAllParts();

        }

        populateTables();

    }


    /**
     * Opens the add part form
     * @param event
     * @throws IOException
     */
    @FXML
    public void addPart(ActionEvent event) throws IOException {

        //Open the Add Part form
        Parent addParts = FXMLLoader.load(getClass().getResource("../ViewControllers/AddPartForm.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }


    /**
     * Opens the modify form for the selected Part
     * @param event
     * @throws IOException
     */
    @FXML
    public void modifyPart(ActionEvent event) throws IOException {


        if (partTable.getSelectionModel().getSelectedIndex() >= 0) {

            partModifyIndex = partTable.getSelectionModel().getSelectedIndex();
            //Open the Modify Part form
            Parent addParts = FXMLLoader.load(getClass().getResource("../ViewControllers/ModifyPartForm.fxml"));
            Scene scene = new Scene(addParts);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

        } else {

            System.out.println("Nothing selected!");

        }


    }

    /**
     * Delete part from the parts table
     * @param event
     */
    @FXML
    public void deletePart(ActionEvent event) {

        if (partTable.getSelectionModel().getSelectedIndex() >= 0) {

            Part tempPart = Inventory.getAllParts().get(partTable.getSelectionModel().getSelectedIndex());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Deletion confirmation");
            alert.setHeaderText("Are you sure you want to delete?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {

                Inventory.deletePart(tempPart);
                System.out.println(tempPart.getName() + " was removed.");


            } else {

                //Display deletion failure alert
                Alert deleteFailed = new Alert(Alert.AlertType.ERROR);
                deleteFailed.initModality(Modality.NONE);
                deleteFailed.setTitle("Deletion failed!");
                deleteFailed.setHeaderText("The deletion was cancelled");

                Optional<ButtonType> failResult = deleteFailed.showAndWait();

                System.out.println(tempPart.getName() + " was not removed.");

            }


            System.out.println(partTable.getSelectionModel().getSelectedIndex());


        } else {

            System.out.println("Nothing selected!");

        }


    }

    /**
     * Searches products in the product table using the string typed into the search field
     */
    @FXML
    public void searchProduct() {

        if (!SearchProductsTextField.getText().isEmpty()) {

            visibleProducts = FXCollections.observableArrayList();

            System.out.println("Searching for part!");

            if (isInt(SearchProductsTextField.getText())) {

                //Search using productID
                Product tempProduct = Inventory.lookupProduct(Integer.parseInt(SearchProductsTextField.getText()));

                if (tempProduct != null)
                    visibleProducts.add(tempProduct);


            } else {

                //Search using product name
                visibleProducts = Inventory.lookupProduct(SearchProductsTextField.getText());

            }


            if (visibleProducts.isEmpty()) {

                Alert noResults = new Alert(Alert.AlertType.ERROR);
                noResults.initModality(Modality.NONE);
                noResults.setTitle("Results");
                noResults.setHeaderText("No results found!");

                Optional<ButtonType> failResult = noResults.showAndWait();

            }

        } else {

            // visibleParts = Inventory.getAllParts();
            Alert boxEmpty = new Alert(Alert.AlertType.ERROR);
            boxEmpty.setTitle("Empty search!");
            boxEmpty.setHeaderText("Nothing in search box!");

            Optional<ButtonType> failResult = boxEmpty.showAndWait();

            visibleProducts = Inventory.getAllProducts();

        }

        populateTables();

    }

    /**
     * Opens the add product form to add a new product to the product table
     * @param event
     * @throws IOException
     */
    @FXML
    public void addProduct(ActionEvent event) throws IOException {

        //Open the Add Product form
        Parent addParts = FXMLLoader.load(getClass().getResource("../ViewControllers/AddProductForm.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    /**
     * Opens the modify product form for the selected product from product table
     * @param event
     * @throws IOException
     */
    @FXML
    public void modifyProduct(ActionEvent event) throws IOException {

        if (productTable.getSelectionModel().getSelectedIndex() >= 0) {

            productModifyIndex = productTable.getSelectionModel().getSelectedIndex();

            //Open the Modify Product form
            Parent addParts = FXMLLoader.load(getClass().getResource("../ViewControllers/ModifyProductForm.fxml"));
            Scene scene = new Scene(addParts);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

        } else {

            System.out.println("Nothing selected!");

        }
    }

    /**
     * Deletes selected product from the product table
     * @param event
     */
    @FXML
    public void deleteProduct(ActionEvent event) {

        if (productTable.getSelectionModel().getSelectedIndex() >= 0) {

            Product tempProduct = Inventory.getAllProducts().get(productTable.getSelectionModel().getSelectedIndex());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Deletion confirmation");
            alert.setHeaderText("Are you sure you want to delete?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {

                if (tempProduct.getAllAssociatedParts().size() > 0) {

                    //Display deletion failure alert
                    Alert deleteFailed = new Alert(Alert.AlertType.ERROR);
                    deleteFailed.initModality(Modality.NONE);
                    deleteFailed.setTitle("Deletion failed!");
                    deleteFailed.setHeaderText("The deletion failed due to parts being associated with product");

                    Optional<ButtonType> failResult = deleteFailed.showAndWait();
                    System.out.println(tempProduct.getName() + " was not removed.");

                } else {

                    Inventory.deleteProduct(tempProduct);

                }

            } else {

                //Display deletion failure alert
                Alert deleteFailed = new Alert(Alert.AlertType.ERROR);
                deleteFailed.initModality(Modality.NONE);
                deleteFailed.setTitle("Deletion failed!");
                deleteFailed.setHeaderText("The deletion was cancelled");

                Optional<ButtonType> failResult = deleteFailed.showAndWait();
                System.out.println(tempProduct.getName() + " was not removed.");

            }

        } else {

            System.out.println("Nothing selected!");

        }

    }

    /**
     * Checks if string is a number
     * @param str
     * @return
     */
    public static boolean isInt(String str) {

        int i = 0;

        if (str == null) {
             return false;
        }

        int strLength = str.length();

        if (strLength == 0) {

            return false;

        }

        if (str.charAt(0) == '-') {

            if (strLength == 1) {
                return false;
            }

            i++;

        }

        for (; i < strLength; i++) {

            char c = str.charAt(i);

            //Skip over '.'
            if (c == '.') {

                //do nothing

            } else if(c < '0' || c > '9') {

                return false;

            }

        }

        return true;

    }

    /**
     * Exits the program
     */
    @FXML
    public void exitProgram() {

        System.exit(0);

    }

}
