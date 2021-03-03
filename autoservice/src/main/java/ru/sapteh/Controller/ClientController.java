package ru.sapteh.Controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.Observable;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.DAO.DAO;
import ru.sapteh.model.Client;
import ru.sapteh.model.ClientService;
import ru.sapteh.model.User;
import ru.sapteh.service.ClientServiceDaoImpl;
import ru.sapteh.service.ClietnDaoImpl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class ClientController {

    private final SessionFactory factory;

    public  ClientController(){
        factory = new Configuration().configure().buildSessionFactory();
    }

    private final ObservableList<Client> clientObservableList = FXCollections.observableArrayList();


    @FXML
    private TableView<Client> tableWiewClient;
    @FXML
    private TableColumn<Client, Integer> iD;
    @FXML
    private TableColumn<Client,String> lastName;
    @FXML
    private TableColumn<Client,String> firstName;
    @FXML
    private TableColumn<Client, String> patronymic;
    @FXML
    private TableColumn<Client, Date> birthday;
    @FXML
    private TableColumn<Client, Date> registrationDate;
    @FXML
    private TableColumn<Client, String> email;
    @FXML
    private TableColumn<Client,String> phone;
    @FXML
    private TableColumn<Client, Character> genderCode;
    @FXML
    private TableColumn<Client, String> photoPath;

    @FXML
    private ComboBox<Integer> comboBoxRole;

    @FXML
    private ComboBox<Integer> comboBox;

    @FXML
    private Pagination pagination;

    @FXML
    private TextField textFieldPoisc;

    @FXML
    private Button buttonNewClient;





    @FXML
    private ComboBox<String> comboBoxgender;




    public ClientController(SessionFactory factory) {
        this.factory = factory;
    }

    ObservableList<String> listGender = FXCollections.observableArrayList("м", "ж");
    List<Client> listClient =new ArrayList<>();

    private int  fromDateSize;
    private int page;


    @FXML
    public void initialize(){

        comboBoxgender.setItems(listGender);


        initClient();
        initDataFrom();
        initTable();
        searchClient(clientObservableList);

//        Комбо box
        fromDateSize = clientObservableList.size();
        ObservableList<Integer> options = FXCollections.observableArrayList( 10, 20, 50, fromDateSize);
        comboBox.setItems(options);
        comboBox.setValue(options.get(0));
        comboBox.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    int valueComboBox = comboBox.getValue();

//                    page

                    page = (int) (Math.ceil(fromDateSize * 1.0 / valueComboBox));

                    //Pagination
                    pagination.setPageCount(page);
                    pagination.setCurrentPageIndex(0);
                    tableWiewClient.setItems(
                            FXCollections.observableArrayList(
                                    clientObservableList.subList(pagination.getCurrentPageIndex(), newValue))

                    );
                    pagination.currentPageIndexProperty()
                            .addListener((observable1, oldValue1, newValue1) -> {
                                tableWiewClient.setItems(
                                        FXCollections.observableArrayList(
                                                clientObservableList.subList(
                                                        valueComboBox * (newValue1.intValue() + 1)
                                                                -  valueComboBox,
                                                        valueComboBox * (newValue1.intValue() + 1)))
                                );
                            });
                });
    }


    private void initDataFrom(){
        DAO<Client, Integer> clientDAOImpl = new ClietnDaoImpl(factory);
        DAO<ClientService, Integer> clientServiceDaoImpl = new ClientServiceDaoImpl(factory);
        clientServiceDaoImpl.readAll();
        List<Client> clientList = clientDAOImpl.readAll();
        clientObservableList.addAll(clientList);
    }

    private void initTable(){



        tableWiewClient.setItems(clientObservableList);
        iD.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getId()));
        lastName.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getLastName()));
        firstName.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getFirstName()));
        patronymic.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getPatronymic()));
        birthday.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getBirthDay()));
        registrationDate.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getRegistrationDate()));
        email.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getEmail()));
        phone.setCellValueFactory(c-> new SimpleObjectProperty<>(c.getValue().getPhone()));
        genderCode.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getGender().getCode()));
        photoPath.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getPhotoPath()));

    }

    public void searchClient(ObservableList<Client> list){
        FilteredList<Client> filteredList = new FilteredList<>(list, p -> true);

        textFieldPoisc.textProperty().addListener((observableValue, s, t1) ->{
            filteredList.setPredicate(client -> {
                if (t1 == null || t1.isEmpty()){
                    return  true;
                }
                String filter = t1.toLowerCase();

                if (client.getFirstName().toLowerCase().contains(filter)){
                    return  true;
                } else if (client.getLastName().toLowerCase().contains(filter)){
                    return true;
                }else if (client.getPatronymic().toLowerCase().contains(filter)){
                    return true;
                }
                return false;
            });
        } );
        SortedList<Client> sortedList = new SortedList<>(filteredList);
        tableWiewClient.setItems(sortedList);
    }

    public void initClient(){
        DAO<Client, Integer> daoClients = new ClietnDaoImpl(factory);
        clientObservableList.addAll(daoClients.readAll());
    }

    public void buttonRegCl() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/reg.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Reg");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(parent));
        stage.showAndWait();
    }

    public void savePdf() throws IOException, DocumentException {
        String pdfProject = "test.pdf";
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(pdfProject));

//      Открытие документа
        document.open();

//        Добавление иконки
        Image image = Image.getInstance("./src/main/resources/auto.jpg");
        image.scaleAbsoluteHeight(70);
        image.scaleAbsoluteWidth(100);
        image.setAlignment(Element.ALIGN_RIGHT);
        document.add(image);


//        Текст rus.
        String Fount  = "./src/main/resources/fount/arial.ttf";

        BaseFont  bf = BaseFont.createFont(Fount, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bf, 10, Font.NORMAL);


        Paragraph paragraph = new Paragraph("Привет", font);
        paragraph.setSpacingAfter(20);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

//        Количест востолбцов
        int numColumn  = tableWiewClient.getColumns().size();
        PdfPTable table = new PdfPTable(10);
//         Получить имена столбца
        ObservableList<TableColumn<Client, ?>> columns = tableWiewClient.getColumns();

        columns.forEach(c -> new PdfPCell(new Phrase(c.getText())));

        for (TableColumn<Client, ?> column : columns){
            table.addCell(new PdfPCell(new Phrase(column.getText())));
        }

        table.setHeaderRows(1);

        tableWiewClient.getColumns().forEach(c ->{
            table.addCell(new PdfPCell(new Phrase()));
            table.addCell(new PdfPCell(new Phrase()));
            table.addCell(new PdfPCell(new Phrase()));
            table.addCell(new PdfPCell(new Phrase()));
            table.addCell(new PdfPCell(new Phrase()));
            table.addCell(new PdfPCell(new Phrase()));
            table.addCell(new PdfPCell(new Phrase()));
            table.addCell(new PdfPCell(new Phrase()));
            table.addCell(new PdfPCell(new Phrase()));
            table.addCell(new PdfPCell(new Phrase()));
            try {
                document.add(table);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        });
        document.close();

        System.out.println("Готово");
    }
}
