package ru.sapteh.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.hibernate.SessionFactory;
import ru.sapteh.DAO.DAO;
import ru.sapteh.model.Client;
import ru.sapteh.model.Gender;
import ru.sapteh.service.ClietnDaoImpl;
import ru.sapteh.service.GenderDaoImp;
import ru.sapteh.service.ServiceDaoImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class RegClientController {
    private final SessionFactory factory;

    public RegClientController(SessionFactory factory) {
        this.factory = factory;
    }


    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtxFirstName;

    @FXML
    private TextField txtpatronymic;

    @FXML
    private DatePicker dateBirthday;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtDateReg;

    @FXML
    private TextField txtPhotoPath;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField genderCode;

    @FXML
    private Label labelStatus;

    @FXML
    private Button buttonReg;


    private void initialize(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        txtDateReg.setText(format.format(new Date()));
    }
    @FXML
    public void buttonAdd()throws ParseException {
        DAO<Client,Integer> daoClients = new ClietnDaoImpl(factory);
        Client client = new Client();

        client.setFirstName(txtxFirstName.getText());
        client.setLastName(txtLastName.getText());
        client.setPatronymic(txtpatronymic.getText());
        client.setEmail(txtEmail.getText());
        client.setPhotoPath(txtPhotoPath.getText());
        client.setPhone(txtPhone.getText());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = format.parse(String.valueOf(dateBirthday.getValue()));
        client.setRegistrationDate(new Date());
        client.setBirthDay(birthday);
    }
}
