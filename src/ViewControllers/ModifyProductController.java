package ViewControllers;

/**
 * FXML Modify Product Controller class
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {

    @FXML
    private TextField IDTextField;

    @FXML
    private TextField NameTextField;

    @FXML
    private TextField InvTextField;

    @FXML
    private TextField PriceTextField;

    @FXML
    private TextField MaxTextField;

    @FXML
    private TextField MinTextField;

    @FXML
    private TextField SearchTextField;

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
    TableView<Part> addedPartTable;

    @FXML
    TableColumn<Part, Integer> addedPartIDCol;

    @FXML
    TableColumn<Part, String> addedPartNameCol;

    @FXML
    TableColumn<Part, Integer> addedPartInvLevelCol;

    @FXML
    TableColumn<Part, Double> addedPartPriceCol;

    @FXML
    TextField SearchPartsTextField;

    private ObservableList<Part> visibleParts = FXCollections.observableArrayList();
    private ObservableList<Part> addedPartList = FXCollections.observableArrayList();

    /**
     * Initializes the form
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Product tempProduct = Inventory.getAllProducts().get(MainFormController.productModifyIndex);

        IDTextField.setText(String.valueOf(tempProduct.getId()));
        NameTextField.setText(tempProduct.getName());
        InvTextField.setText(String.valueOf(tempProduct.getStock()));
        PriceTextField.setText(String.valueOf(tempProduct.getPrice()));
        MaxTextField.setText(String.valueOf(tempProduct.getMax()));
        MinTextField.setText(String.valueOf(tempProduct.getMin()));

        visibleParts = Inventory.getAllParts();

        if (tempProduct.getAllAssociatedParts().size() > 0) {
            for (int i = 0; i < tempProduct.getAllAssociatedParts().size(); i++) {

                addedPartList.add(tempProduct.getAllAssociatedParts().get(i));

            }
        }

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

        //Populate added part columns
        addedPartIDCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        addedPartNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        addedPartInvLevelCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getStock()).asObject());
        addedPartPriceCol.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());


        partTable.setItems(visibleParts);
        addedPartTable.setItems(addedPartList);

    }

    /**
     * Search the part table using the String in search text field
     */
    @FXML
    public void searchPart() {

        if (!SearchPartsTextField.getText().isEmpty()) {

            visibleParts = FXCollections.observableArrayList();

            System.out.println("Searching for part!");

            if (MainFormController.isInt(SearchPartsTextField.getText())) {

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

            Alert boxEmpty = new Alert(Alert.AlertType.ERROR);
            boxEmpty.setTitle("Empty search!");
            boxEmpty.setHeaderText("Nothing in search box!");

            Optional<ButtonType> failResult = boxEmpty.showAndWait();

            visibleParts = Inventory.getAllParts();

        }

        populateTables();

    }

    /**
     * Add part from the top table to the added parts table
     */
    @FXML
    public void addPart() {

        if (partTable.getSelectionModel().getSelectedIndex() >= 0) {

            addedPartList.add(Inventory.getAllParts().get(partTable.getSelectionModel().getSelectedIndex()));

        } else {

            //Display nothing selected alert
            Alert addFailed = new Alert(Alert.AlertType.ERROR);
            addFailed.initModality(Modality.NONE);
            addFailed.setTitle("Nothing selected!");
            addFailed.setHeaderText("There was no item selected to add!");

            Optional<ButtonType> failResult = addFailed.showAndWait();

        }

    }

    /**
     * Remove parts from the added part table
     */
    @FXML
    public void removeAssociatedPart() {

        if (addedPartTable.getSelectionModel().getSelectedIndex() >= 0) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Removal confirmation");
            alert.setHeaderText("Are you sure you want to remove?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {

                addedPartList.remove(addedPartTable.getSelectionModel().getSelectedIndex());
            } else {

                //Display deletion failure alert
                Alert deleteFailed = new Alert(Alert.AlertType.ERROR);
                deleteFailed.initModality(Modality.NONE);
                deleteFailed.setTitle("Removal failed!");
                deleteFailed.setHeaderText("The removal was cancelled");

                Optional<ButtonType> failResult = deleteFailed.showAndWait();

            }

        } else {

            //Display nothing selected alert
            Alert deleteFailed = new Alert(Alert.AlertType.ERROR);
            deleteFailed.initModality(Modality.NONE);
            deleteFailed.setTitle("Nothing selected!");
            deleteFailed.setHeaderText("There was no item selected to remove!");

            Optional<ButtonType> failResult = deleteFailed.showAndWait();

        }

    }

    /**
     * Saves the product and returns to main menu
     * @param event
     * @throws IOException
     */
    @FXML
    public void saveProduct(ActionEvent event) throws IOException {

        //Check that the name field is not empty
        if (!NameTextField.getText().isEmpty()) {

            //Check that inventory text field is not empty
            if (!InvTextField.getText().isEmpty() && MainFormController.isInt(InvTextField.getText())) {

                //Check that the price field is not empty and is greater than 0
                if (!PriceTextField.getText().isEmpty() && MainFormController.isInt(PriceTextField.getText()) && Double.parseDouble(PriceTextField.getText()) > 0) {

                    //Check that the Max text field is not empty and that max is greater than the min field
                    if (!MaxTextField.getText().isEmpty() && MainFormController.isInt(MaxTextField.getText()) && !MinTextField.getText().isEmpty() && MainFormController.isInt(MinTextField.getText()) && Integer.parseInt(MaxTextField.getText()) > Integer.parseInt(MinTextField.getText()) ) {

                        if (Integer.parseInt(InvTextField.getText()) >=  Integer.parseInt(MinTextField.getText()) && Integer.parseInt(InvTextField.getText()) <= Integer.parseInt(MaxTextField.getText())) {



                            Product tempProduct = new Product(MainFormController.productModifyIndex, NameTextField.getText(), Double.parseDouble(PriceTextField.getText()), Integer.parseInt(InvTextField.getText()), Integer.parseInt(MinTextField.getText()), Integer.parseInt(MaxTextField.getText()));

                            for (int i = 0; i < addedPartList.size(); i++) {

                                tempProduct.addAssociatedPart(addedPartList.get(i));

                            }

                            Inventory.updateProduct(MainFormController.productModifyIndex, tempProduct);

                            System.out.println("Product Saved!");

                            Parent addParts = FXMLLoader.load(getClass().getResource("../ViewControllers/MainForm.fxml"));
                            Scene scene = new Scene(addParts);
                            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            window.setScene(scene);
                            window.show();

                        } else {

                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.initModality(Modality.NONE);
                            alert.setTitle("Error!");
                            alert.setHeaderText("The inventory must be between min and max!");

                            Optional<ButtonType> result = alert.showAndWait();

                        }


                    } else {

                        //Create alert for min/max error
                        if (MainFormController.isInt(MaxTextField.getText()) && MainFormController.isInt(MinTextField.getText())) {

                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.initModality(Modality.NONE);
                            alert.setTitle("Error!");
                            alert.setHeaderText("Max must be greater than min!");

                            Optional<ButtonType> result = alert.showAndWait();

                        } else {

                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.initModality(Modality.NONE);
                            alert.setTitle("Error!");
                            alert.setHeaderText("Max and min must be a number!");

                            Optional<ButtonType> result = alert.showAndWait();

                        }


                    }

                } else {

                    //Create alert if price is below 0
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initModality(Modality.NONE);
                    alert.setTitle("Error!");
                    alert.setHeaderText("Price must be a number and above 0!");

                    Optional<ButtonType> result = alert.showAndWait();

                }

            } else {

                //Create alert for inventory error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.NONE);
                alert.setTitle("Error!");
                alert.setHeaderText("Inventory must be a number!");

                Optional<ButtonType> result = alert.showAndWait();

            }



        } else {

            //Create alert if the name field is empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.NONE);
            alert.setTitle("Error!");
            alert.setHeaderText("Name is empty!");

            Optional<ButtonType> result = alert.showAndWait();

        }

    }

    /**
     * Returns to main menu without saving
     * @param event
     * @throws IOException
     */
    @FXML
    public void cancel(ActionEvent event) throws IOException {

        //Return to Main form
        Parent addParts = FXMLLoader.load(getClass().getResource("../ViewControllers/MainForm.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

}
