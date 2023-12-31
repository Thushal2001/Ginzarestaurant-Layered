package lk.ijse.restaurant.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.restaurant.bo.BoFactory;
import lk.ijse.restaurant.bo.custom.ItemBO;
import lk.ijse.restaurant.dto.ItemDTO;
import lk.ijse.restaurant.view.ItemTM;
import lk.ijse.restaurant.util.Validation;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ManagestoreFormController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtUnitprice;
    @FXML
    private TextField txtQtyonhand;
    @FXML
    private TableView tblItem;
    @FXML
    private TableColumn colCode;
    @FXML
    private TableColumn colDescription;
    @FXML
    private TableColumn colUnitprice;
    @FXML
    private TableColumn colQtyonhand;
    @FXML
    private Label lbldateandtime;
    @FXML
    private Button btnSave;

    private ItemBO itemBO= BoFactory.getBoFactory().getBO(BoFactory.BOTypes.ITEM);

    private LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern description = Pattern.compile("^([A-Z a-z 0-9]{4,40})$");
    Pattern unitPrice = Pattern.compile("^([0-9]{1,8}.?[0-9]{0,2})$");
    Pattern qtyOnHand = Pattern.compile("^([0-9]{1,6})$");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd      hh:mm");
        Date date = new Date();
        lbldateandtime.setText(simpleDateFormat.format(date));

        getAll();
        setCellValueFactory();

        disableButtons();
        storeValidations();
    }

    private void disableButtons() {
        btnSave.setDisable(true);

    }

    private void clearAllTxt() {
        txtCode.clear();
        txtDescription.clear();
        txtUnitprice.clear();
        txtQtyonhand.clear();

        disableButtons();
        txtDescription.requestFocus();
        setBorders(txtCode, txtDescription, txtUnitprice, txtQtyonhand);
    }

    public void setBorders(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.setStyle("-fx-border-color: transparent");
        }
    }

    private void storeValidations() {
        map.put(txtDescription, description);
        map.put(txtUnitprice, unitPrice);
        map.put(txtQtyonhand, qtyOnHand);
    }

    @FXML
    private void txtKeyRelease(KeyEvent keyEvent) {
        Object response = Validation.validate(map, btnSave);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField txtnext = (TextField) response;
                txtnext.requestFocus();
            }
        }
    }

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitprice.setCellValueFactory(new PropertyValueFactory<>("unitprice"));
        colQtyonhand.setCellValueFactory(new PropertyValueFactory<>("qtyonhand"));
    }

    private void getAll() {
        try {
            ObservableList<ItemTM> observableList = FXCollections.observableArrayList();
            List<ItemDTO> itemDTOList = itemBO.loadAllItems();

            for (ItemDTO itemDTO : itemDTOList) {
                observableList.add(new ItemTM(
                        itemDTO.getCode(),
                        itemDTO.getDescription(),
                        itemDTO.getUnitprice(),
                        itemDTO.getQtyonhand()
                ));
            }
            tblItem.setItems(observableList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void saveOnAction(ActionEvent event) {
        try {
            ItemDTO itemDTO = new ItemDTO(
                    txtCode.getText(),
                    txtDescription.getText(),
                    Double.parseDouble(txtUnitprice.getText()),
                    Integer.parseInt(txtQtyonhand.getText())
            );

            if (itemBO.saveItem(itemDTO) > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved Successfully...!").show();
                tblItem.refresh();
                getAll();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
        clearAllTxt();
    }

    @FXML
    private void searchOnAction(ActionEvent event) {
        try {
            ItemDTO itemDTO = itemBO.searchItem(txtCode.getText());
            if (itemDTO != null) {
                txtDescription.setText(itemDTO.getDescription());
                txtUnitprice.setText(String.valueOf(itemDTO.getUnitprice()));
                txtQtyonhand.setText(String.valueOf(itemDTO.getQtyonhand()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void updateOnAction(ActionEvent event) {
        try {
            ItemDTO itemDTO = new ItemDTO(
                    txtCode.getText(),
                    txtDescription.getText(),
                    Double.parseDouble(txtUnitprice.getText()),
                    Integer.parseInt(txtQtyonhand.getText())
            );

            if (itemBO.updateItem(itemDTO) > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated Successfully...!").show();
                getAll();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
        clearAllTxt();
    }

    @FXML
    private void deleteOnAction(ActionEvent event) {
        try {
            if (itemBO.deleteItem(txtCode.getText()) > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted Successfully...!").show();
                getAll();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
        clearAllTxt();
    }

    @FXML
    private void backOnAction(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/cashierdashboard_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Cashier Dashboard");
        stage.setResizable(false);
        stage.show();
    }
}
