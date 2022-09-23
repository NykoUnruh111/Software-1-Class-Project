package ViewControllers;

/**
 * FXML Modify Part Controller class
 *
 * @author Nathaniel Unruh
 */

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyPartController implements Initializable {

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
    private TextField machineCompanyTextField;

    @FXML
    private RadioButton inHouseRadioButton;
    @FXML
    private RadioButton outsourcedRadioButton;
    @FXML
    private Text machineCompanyText;

    private boolean inHouse = true;

    /**
     * Initializes the form
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Part tempPart = Inventory.getAllParts().get(MainFormController.partModifyIndex);

        IDTextField.setText(String.valueOf(tempPart.getId()));
        NameTextField.setText(tempPart.getName());
        InvTextField.setText(String.valueOf(tempPart.getStock()));
        PriceTextField.setText(String.valueOf(tempPart.getPrice()));
        MaxTextField.setText(String.valueOf(tempPart.getMax()));
        MinTextField.setText(String.valueOf(tempPart.getMin()));
        if (tempPart instanceof InHouse) {

            machineCompanyTextField.setText(String.valueOf(((InHouse) tempPart).getMachineId()));
            inHouseSelected();

        } else if (tempPart instanceof Outsourced){

            machineCompanyTextField.setText(((Outsourced) tempPart).getCompanyName());
            outsourcedSelected();

        }

    }

    /**
     * Radio button code for if the inhouse radio button is selected
     */
    @FXML
    public void inHouseSelected () {

        //De-select opposite radio button
        outsourcedRadioButton.setSelected(false);
        inHouseRadioButton.setSelected(true);

        //Set boolean
        inHouse = true;

        //Set text field
        machineCompanyText.setText("Machine ID");

    }

    /**
     * Radio button code for if the outsourced radio button is selected
     */
    @FXML
    public void outsourcedSelected () {

        //De-select opposite radio button
        inHouseRadioButton.setSelected(false);
        outsourcedRadioButton.setSelected(true);
        //Set boolean
        inHouse = false;

        //Set text field
        machineCompanyText.setText("Company Name");

    }

    /**
     * Saves the part and returns to main menu
     * @param event
     * @throws IOException
     */
    @FXML
    public void savePart(ActionEvent event) throws IOException {

        //Check that the name field is not empty
        if (!NameTextField.getText().isEmpty()) {

            //Check that inventory text field is not empty
            if (!InvTextField.getText().isEmpty() && MainFormController.isInt(InvTextField.getText())) {

                //Check that the price field is not empty and is greater than 0
                if (!PriceTextField.getText().isEmpty() && MainFormController.isInt(PriceTextField.getText()) && Double.parseDouble(PriceTextField.getText()) > 0) {

                    //Check that the Max text field is not empty and that max is greater than the min field
                    if (!MaxTextField.getText().isEmpty() && MainFormController.isInt(MaxTextField.getText()) && !MinTextField.getText().isEmpty() && MainFormController.isInt(MinTextField.getText()) && Integer.parseInt(MaxTextField.getText()) > Integer.parseInt(MinTextField.getText()) ) {

                        //Check that machineid/company name field is not empty
                        if (!machineCompanyTextField.getText().isEmpty()) {

                            if (Integer.parseInt(InvTextField.getText()) >=  Integer.parseInt(MinTextField.getText()) && Integer.parseInt(InvTextField.getText()) <= Integer.parseInt(MaxTextField.getText())) {


                                if (inHouse) {

                                    if (MainFormController.isInt(machineCompanyTextField.getText())) {

                                        Inventory.updatePart(MainFormController.partModifyIndex, new InHouse(MainFormController.partModifyIndex, NameTextField.getText(), Double.parseDouble(PriceTextField.getText()), Integer.parseInt(InvTextField.getText()), Integer.parseInt(MinTextField.getText()), Integer.parseInt(MaxTextField.getText()), Integer.parseInt(machineCompanyTextField.getText())));

                                        Parent addParts = FXMLLoader.load(getClass().getResource("../ViewControllers/MainForm.fxml"));
                                        Scene scene = new Scene(addParts);
                                        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                        window.setScene(scene);
                                        window.show();

                                    } else {

                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.initModality(Modality.NONE);
                                        alert.setTitle("Error!");
                                        alert.setHeaderText("The machineId needs to be a number!");

                                        Optional<ButtonType> result = alert.showAndWait();

                                    }

                                } else {

                                    Inventory.updatePart(MainFormController.partModifyIndex, new Outsourced(MainFormController.partModifyIndex, NameTextField.getText(), Double.parseDouble(PriceTextField.getText()), Integer.parseInt(InvTextField.getText()), Integer.parseInt(MinTextField.getText()), Integer.parseInt(MaxTextField.getText()), machineCompanyTextField.getText()));

                                    Parent addParts = FXMLLoader.load(getClass().getResource("../ViewControllers/MainForm.fxml"));
                                    Scene scene = new Scene(addParts);
                                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    window.setScene(scene);
                                    window.show();

                                }

                            } else {

                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.initModality(Modality.NONE);
                                alert.setTitle("Error!");
                                alert.setHeaderText("The inventory must be between min and max!");

                                Optional<ButtonType> result = alert.showAndWait();

                            }
                        } else {

                            if (inHouse) {

                                //Create alert for machineID error
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.initModality(Modality.NONE);
                                alert.setTitle("Error!");
                                alert.setHeaderText("The machineId is empty!");

                                Optional<ButtonType> result = alert.showAndWait();

                            }  else {

                                //Create alert for company name error
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.initModality(Modality.NONE);
                                alert.setTitle("Error!");
                                alert.setHeaderText("The company name is empty!");

                                Optional<ButtonType> result = alert.showAndWait();

                            }

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
