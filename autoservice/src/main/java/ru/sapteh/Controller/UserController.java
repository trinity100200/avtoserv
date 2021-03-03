package ru.sapteh.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.DAO.DAO;
import ru.sapteh.model.User;
import ru.sapteh.service.UserDaoImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    final SessionFactory factory;

    public UserController(){
        factory = new Configuration().configure().buildSessionFactory();
    }
    ObservableList<String> listRole = FXCollections.observableArrayList("admin", "user");
    List<User> listUser = new ArrayList<>();

    @FXML
    private PasswordField txtPass;

    @FXML
    private TextField txtLogin;

    @FXML
    private Label status;

    @FXML
    private Button butttonVxod;

    @FXML
    private ComboBox<String> role;

    @FXML
    private Button buttonRegistration;




    public  void initialize(){
        role.setItems(listRole);
        inUser();
    }

    public void inUser(){
        DAO<User, Integer> daoClients = new UserDaoImpl(factory);
        listUser.addAll(daoClients.readAll());
    }
    @FXML
    public void buttonSignIn() throws IOException {

        for (User user: listUser){
            if (user.getLogin().equals(txtLogin.getText()) && user.getPassword().equals(txtPass.getText())){

                butttonVxod.getScene().getWindow().hide();
                Parent parent = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Fail");
                stage.setScene(new Scene(parent));
                stage.show();
            }else status.setText("Неверный логинили  или пароль");
        }

    }

    @FXML
    public void ButtonReg() throws IOException {
        buttonRegistration.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("/view/reg.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Reg");
        stage.setScene(new Scene(parent));
        stage.show();
    }




}
