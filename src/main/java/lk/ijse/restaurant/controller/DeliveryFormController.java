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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.restaurant.bo.BoFactory;
import lk.ijse.restaurant.bo.custom.DeliveryBO;
import lk.ijse.restaurant.dto.DeliveryDTO;
import lk.ijse.restaurant.view.DeliveryTM;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class DeliveryFormController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtEmployeeid;
    @FXML
    private TextField txtCustomerid;
    @FXML
    private TextField txtOrderid;
    @FXML
    private TextField txtDetails;
    @FXML
    private TextField txtLocation;
    @FXML
    private TableView tblDelivery;
    @FXML
    private TableColumn colCode;
    @FXML
    private TableColumn colEmployeeid;
    @FXML
    private TableColumn colCustomerid;
    @FXML
    private TableColumn colOrderid;
    @FXML
    private TableColumn colDetails;
    @FXML
    private TableColumn colLocation;
    @FXML
    private Label lbldateandtime;

    private DeliveryBO deliveryBO = BoFactory.getBoFactory().getBO(BoFactory.BOTypes.DELIVERY);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd      hh:mm");
        Date date = new Date();
        lbldateandtime.setText(simpleDateFormat.format(date));

        getAll();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colEmployeeid.setCellValueFactory(new PropertyValueFactory<>("employeeid"));
        colCustomerid.setCellValueFactory(new PropertyValueFactory<>("customerid"));
        colOrderid.setCellValueFactory(new PropertyValueFactory<>("orderid"));
        colDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
    }

    private void getAll() {
        try {
            ObservableList<DeliveryTM> observableList = FXCollections.observableArrayList();
            List<DeliveryDTO> deliveryDTOList = deliveryBO.loadAllDelivers();

            for (DeliveryDTO deliveryDTO : deliveryDTOList) {
                observableList.add(new DeliveryTM(
                        deliveryDTO.getCode(),
                        deliveryDTO.getEmployeeid(),
                        deliveryDTO.getCustomerid(),
                        deliveryDTO.getOrderid(),
                        deliveryDTO.getDetails(),
                        deliveryDTO.getLocation()
                ));
            }
            tblDelivery.setItems(observableList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void saveOnAction(ActionEvent event) {
        try {
            DeliveryDTO deliveryDTO = new DeliveryDTO(
                    txtCode.getText(),
                    txtEmployeeid.getText(),
                    txtCustomerid.getText(),
                    txtOrderid.getText(),
                    txtDetails.getText(),
                    txtLocation.getText()
            );

            if (deliveryBO.saveDelivers(deliveryDTO) > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved Successfully...!").show();
                getAll();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void searchOnAction(ActionEvent event) {
        try {
            DeliveryDTO deliveryDTO = deliveryBO.searchDeliver(txtCode.getText());
            if (deliveryDTO != null) {
                txtEmployeeid.setText(deliveryDTO.getEmployeeid());
                txtCustomerid.setText(deliveryDTO.getCustomerid());
                txtOrderid.setText(deliveryDTO.getOrderid());
                txtDetails.setText(deliveryDTO.getDetails());
                txtLocation.setText(deliveryDTO.getLocation());
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void updateOnAction(ActionEvent event) {
        try {
            DeliveryDTO deliveryDTO = new DeliveryDTO(
                    txtCode.getText(),
                    txtEmployeeid.getText(),
                    txtCustomerid.getText(),
                    txtOrderid.getText(),
                    txtDetails.getText(),
                    txtLocation.getText()
            );

            if (deliveryBO.updateDeliver(deliveryDTO) > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated Successfully...!").show();
                getAll();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void deleteOnAction(ActionEvent event) {
        try {
            if (deliveryBO.deleteDeliver(txtCode.getText()) > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted Successfully...!").show();
                getAll();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
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
