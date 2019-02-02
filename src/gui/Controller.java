package gui;

import business.UserBusiness;
import domain.Expense;
import domain.Payment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import persistence.ConnectionManager;
import persistence.ExpenseDao;
import persistence.PaymentDao;
import persistence.UserDao;
import java.time.LocalDate;
import java.util.ArrayList;

public class Controller {

    private double xOffset = 0;
    private double yOffset = 0;

    private String username;
    private String currentClass;

    private Boolean infoIsPressed = false;
    private Boolean editIsPressed = false;
    private Boolean deleteIsPressed = false;

    private ConnectionManager connectionManager = new ConnectionManager();
    private ExpenseDao expenseDao = new ExpenseDao(connectionManager);
    private PaymentDao paymentDao = new PaymentDao(connectionManager);
    private UserDao userDao = new UserDao(connectionManager);
    private UserBusiness userBusiness = new UserBusiness(userDao);

    private ArrayList list;
    private ArrayList<Pane> scrollPanes = new ArrayList<>();
    private ArrayList<Line> scrollLines = new ArrayList<>();

    private Boolean isUpdate = false;
    private int isUpdateIndex;

    private ObservableList<String> paymentComboList  = FXCollections.observableArrayList();

    private SpinnerValueFactory<Integer> valueFactory;

    @FXML
    private AnchorPane selectUserPane;

    @FXML
    private Label selectUserLbl;

    @FXML
    private Label userName;

    @FXML
    private ImageView userImg;

    @FXML
    private Button debtsBtn;

    @FXML
    private Button paymentsBtn;

    @FXML
    private Button expensesBtn;

    @FXML
    private AnchorPane debtsPane;

    @FXML
    private Label debtsLbl;

    @FXML
    private AnchorPane listPane;

    @FXML
    private AnchorPane listScrollPane;

    @FXML
    private AnchorPane editPaymentsPane;

    @FXML
    private ComboBox<String> creditorPaymentCombo;

    @FXML
    private TextField amountPaymentField;

    @FXML
    private DatePicker datePaymentDate;

    @FXML
    private Label alertPaymentLabel;

    @FXML
    private AnchorPane editExpensesPane;

    @FXML
    private TextField descriptionExpenseField;

    @FXML
    private TextField priceExpenseField;

    @FXML
    private Spinner<Integer> participantsExpenseSpinner;

    @FXML
    private CheckBox firstExpenseCheck;

    @FXML
    private CheckBox secondExpenseCheck;

    @FXML
    private DatePicker dateExpenseDate;

    @FXML
    private Label alertExpenseLabel;

    @FXML
    void draggableDragged(MouseEvent event) {
        Stage stage = (Stage) userName.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    @FXML
    void draggablePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    void alejoBtn(MouseEvent event) throws Exception {
        setUsername("Alejo");
    }

    @FXML
    void ianBtn(MouseEvent event) throws Exception {
        setUsername("Ian");
    }

    @FXML
    void totiBtn(MouseEvent event) throws Exception {
        setUsername("Toti");
    }

    @FXML
    void aboutClicked(MouseEvent event) throws Exception {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("about.fxml"));
        stage.setScene(new Scene(root, 208, 156));
        double width = 208;
        double height = 156;
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);
        stage.show();
        stage.setAlwaysOnTop(true);
    }

    @FXML
    void changeUserClicked(MouseEvent event) {
        selectUserPane.setVisible(true);
        selectUserLbl.setVisible(true);
        expensesBtn.setDisable(true);
        paymentsBtn.setDisable(true);
        debtsBtn.setDisable(true);
        debtsPane.setVisible(false);
        listPane.setVisible(false);
        editPaymentsPane.setVisible(false);
        editExpensesPane.setVisible(false);
        userName.setVisible(false);
        userImg.setVisible(false);
        debtsBtn.setId(null);
        paymentsBtn.setId(null);
        expensesBtn.setId(null);
    }

    @FXML
    void closeClicked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void debtsClicked(MouseEvent event) throws Exception {
        if (debtsBtn.getId()==null) {
            debtsBtn.setId("clickedButton");
            paymentsBtn.setId(null);
            expensesBtn.setId(null);
            listPane.setVisible(false);
            listPane.setVisible(false);
            editPaymentsPane.setVisible(false);
            editExpensesPane.setVisible(false);
            debtsPane.setVisible(true);
            debtsLbl.setText(userBusiness.getDebt(username));
        }
    }

    @FXML
    void expensesClicked(MouseEvent event) throws Exception {
        if (expensesBtn.getId()==null) {
            expensesBtn.setId("clickedButton");
            paymentsBtn.setId(null);
            debtsBtn.setId(null);
            debtsPane.setVisible(false);
            editPaymentsPane.setVisible(false);
            editExpensesPane.setVisible(false);
            listPane.setVisible(true);
            list = expenseDao.sqlGetList();
            currentClass = "Expense";
            showScroll();
            showInfoButtons();
        }
    }

    @FXML
    void minimizedClicked(MouseEvent event) {
        Stage stage = (Stage)debtsBtn.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void paymentsClicked(MouseEvent event) throws Exception {
        if (paymentsBtn.getId()==null) {
            paymentsBtn.setId("clickedButton");
            debtsBtn.setId(null);
            expensesBtn.setId(null);
            debtsPane.setVisible(false);
            editExpensesPane.setVisible(false);
            editPaymentsPane.setVisible(false);
            listPane.setVisible(true);
            list = paymentDao.sqlGetList();
            currentClass = "Payment";
            showScroll();
        }
    }

    @FXML
    void newBtnClicked(MouseEvent event) {
        listPane.setVisible(false);
        if (currentClass.equals("Payment")) {
            editPaymentsPane.setVisible(true);
            datePaymentDate.setValue(null);
            amountPaymentField.setText(null);
            creditorPaymentCombo.getSelectionModel().select(null);
            creditorPaymentCombo.getEditor().setEditable(false);
        } else {
            editExpensesPane.setVisible(true);
            descriptionExpenseField.setText(null);
            priceExpenseField.setText(null);
            firstExpenseCheck.setSelected(false);
            secondExpenseCheck.setSelected(false);
            dateExpenseDate.setValue(null);
            valueFactory =  new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 50, 2);
            participantsExpenseSpinner.setValueFactory(valueFactory);
        }
        isUpdate = false;
    }

    @FXML
    void savePaymentBtnClicked(MouseEvent event) throws Exception {

        Payment payment = new Payment();

        if (isUpdate){
            Payment modifyPayment = (Payment) list.get(isUpdateIndex);
            payment.setId(modifyPayment.getId());
        }

        try {
            payment.setUserName(username);
            int valueLength = amountPaymentField.getText().length();
            if (valueLength<4 || amountPaymentField.getText().charAt(valueLength-3)!='.'){
                alertPaymentLabel.setText("Money format must be $.$$");
                alertPaymentLabel.setVisible(true);
                return;
            }
            int value = Integer.parseInt(amountPaymentField.getText().substring(0, valueLength-3));
            value = value * 100 + Integer.parseInt(amountPaymentField.getText().substring(valueLength-2, valueLength));
            payment.setValue(value);
            payment.setCreditor(creditorPaymentCombo.getValue());
            payment.setDate(datePaymentDate.getValue().toString());

            if (isUpdate) {
                Payment modifyPayment = (Payment) list.get(isUpdateIndex);
                modifyPayment.setValue(-modifyPayment.getValue());
                userDao.sqlUpdateDebtsByPayment(modifyPayment);
                paymentDao.sqlUpdate(payment);
            } else {
                paymentDao.sqlInsert(payment);
            }

            userDao.sqlUpdateDebtsByPayment(payment);
            alertPaymentLabel.setVisible(false);
        } catch (NullPointerException e) {
            alertPaymentLabel.setText("Please complete all the fields before saving the payment");
            alertPaymentLabel.setVisible(true);
            return;
        } catch (NumberFormatException e) {
            alertPaymentLabel.setText("Money format must be $.$$");
            alertPaymentLabel.setVisible(true);
            return;
        }

        editPaymentsPane.setVisible(false);
        listPane.setVisible(true);
        list = paymentDao.sqlGetList();
        showScroll();
    }

    @FXML
    void cancelPaymentBtnClicked(MouseEvent event) throws Exception {
        editPaymentsPane.setVisible(false);
        listPane.setVisible(true);
        showScroll();
        showInfoButtons();
        alertPaymentLabel.setVisible(false);
    }

    @FXML
    void saveExpenseBtnClicked(MouseEvent event) throws Exception {

        Expense expense = new Expense();


        if (isUpdate) {
                Expense modifyExpense = (Expense) list.get(isUpdateIndex);
                expense.setId(modifyExpense.getId());
        }
        try {
            expense.setUserName(username);
            expense.setDescription(descriptionExpenseField.getText());
            int valueLength = priceExpenseField.getText().length();
            if (valueLength<4 || priceExpenseField.getText().charAt(valueLength-3)!='.'){
                alertExpenseLabel.setText("Money format must be $.$$");
                alertExpenseLabel.setVisible(true);
                return;
            }
            int value = Integer.parseInt(priceExpenseField.getText().substring(0, valueLength-3));
            value = value * 100 + Integer.parseInt(priceExpenseField.getText().substring(valueLength-2, valueLength));
            expense.setValue(value);
            expense.setPeople(participantsExpenseSpinner.getValue());
            expense.setDate(dateExpenseDate.getValue().toString());
            switch (username) {
                case "Alejo":   expense.setAlejoSpent(false);
                                expense.setIanSpent(firstExpenseCheck.isSelected());
                                expense.setTotiSpent(secondExpenseCheck.isSelected());
                                break;
                case "Ian":     expense.setAlejoSpent(firstExpenseCheck.isSelected());
                                expense.setIanSpent(false);
                                expense.setTotiSpent(secondExpenseCheck.isSelected());
                                break;
                default:        expense.setAlejoSpent(firstExpenseCheck.isSelected());
                                expense.setIanSpent(secondExpenseCheck.isSelected());
                                expense.setTotiSpent(false);
            }
            if (isUpdate) {
                Expense modifyExpense = (Expense) list.get(isUpdateIndex);
                modifyExpense.setValue(-modifyExpense.getValue());
                userDao.sqlUpdateDebtsByExpense(modifyExpense);
                expenseDao.sqlUpdate(expense);
            } else {
                expenseDao.sqlInsert(expense);
            }

            userDao.sqlUpdateDebtsByExpense(expense);

            editExpensesPane.setVisible(false);
            listPane.setVisible(true);
            list = expenseDao.sqlGetList();
            showScroll();
            showInfoButtons();
            alertExpenseLabel.setVisible(false);
        } catch (NullPointerException e) {
            alertExpenseLabel.setText("Please complete all the fields before saving the expense");
            alertExpenseLabel.setVisible(true);
        } catch (NumberFormatException e) {
            alertExpenseLabel.setText("Money format must be $.$$");
            alertExpenseLabel.setVisible(true);
        }
    }

    @FXML
    void cancelExpenseBtnClicked(MouseEvent event) {
        editExpensesPane.setVisible(false);
        listPane.setVisible(true);
        showScroll();
        showInfoButtons();
        alertExpenseLabel.setVisible(false);
    }

    private void setUsername(String username) throws Exception{
        this.username = username;
        userName.setText(username);
        userName.setVisible(true);
        selectUserPane.setVisible(false);
        selectUserLbl.setVisible(false);
        paymentComboList.clear();
        creditorPaymentCombo.getItems().clear();


        Image image;
        switch (username) {
            case "Alejo":   image = new Image(getClass().getResourceAsStream("media/Alejo.png"));
                            paymentComboList.addAll("Ian", "Toti");
                            firstExpenseCheck.setText("Ian");
                            secondExpenseCheck.setText("Toti");
                            break;
            case "Ian":     image = new Image(getClass().getResourceAsStream("media/Ian.png"));
                            paymentComboList.addAll("Alejo", "Toti");
                            firstExpenseCheck.setText("Alejo");
                            secondExpenseCheck.setText("Toti");
                            break;
            default:        image = new Image(getClass().getResourceAsStream("media/Toti.png"));
                            paymentComboList.addAll("Alejo", "Ian");
                            firstExpenseCheck.setText("Alejo");
                            secondExpenseCheck.setText("Ian");
        }

        creditorPaymentCombo.setItems(paymentComboList);
        userImg.setImage(image);

        userImg.setVisible(true);
        debtsBtn.setId("clickedButton");
        debtsBtn.setDisable(false);
        paymentsBtn.setId(null);
        paymentsBtn.setDisable(false);
        expensesBtn.setId(null);
        expensesBtn.setDisable(false);
        editPaymentsPane.setVisible(false);
        debtsPane.setVisible(true);
        debtsLbl.setText(userBusiness.getDebt(username));
    }

    private void showScroll () {

        listScrollPane.getChildren().clear();
        scrollLines.clear();
        scrollPanes.clear();
        infoIsPressed = false;
        editIsPressed = false;
        deleteIsPressed = false;

        if (list.size()>0){

            listScrollPane.setPrefHeight(list.size()*45+30);

            if (list.size()>1){
                int linePosition = 60;
                for (int i=1; i<list.size(); i++){
                    Line line = new Line(15, linePosition, 594, linePosition);
                    line.setStrokeWidth(0.03);
                    line.setStroke(Paint.valueOf("#BBBBBB"));
                    scrollLines.add(line);
                    linePosition+=45;
                }
                listScrollPane.getChildren().addAll(scrollLines);
            }

            int panePosition = 14;
            for (int i=0; i<list.size(); i++) {

                Pane pane = new Pane();
                pane.setPrefSize(579, 47);
                pane.setLayoutX(15);
                pane.setLayoutY(panePosition);
                pane.setPrefWidth(579);
                pane.setPrefHeight(47);
                pane.setId("listPane");

                Label label = new Label("     " + list.get(i).toString());
                label.setTextFill(Paint.valueOf("#BBBBBB"));
                label.setFont(Font.font("Segoe UI", 12));
                label.setLayoutY(17);

                Button editButton = new Button();
                editButton.setId("editButton");
                editButton.setCursor(Cursor.HAND);
                editButton.setLayoutX(514);
                editButton.setLayoutY(11);
                editButton.setOnAction((ActionEvent ae)-> {
                    editButtonPressed(scrollPanes.indexOf(pane));
                });

                Button deleteButton = new Button();
                deleteButton.setId("deleteButton");
                deleteButton.setCursor(Cursor.HAND);
                deleteButton.setLayoutX(542);
                deleteButton.setLayoutY(11);
                deleteButton.setOnAction((ActionEvent ae) -> {
                    deleteButtonPressed(scrollPanes.indexOf(pane));
                });

                pane.getChildren().addAll(label, editButton, deleteButton);

                scrollPanes.add(pane);

                panePosition += 45;

            }

            listScrollPane.getChildren().addAll(scrollPanes);

        }

    }

    private void showInfoButtons (){

        for (Pane pane: scrollPanes) {
            Button infoButton = new Button();
            infoButton.setId("infoButton");
            infoButton.setCursor(Cursor.HAND);
            infoButton.setLayoutX(pane.getPrefWidth()-95);
            infoButton.setLayoutY(11);
            infoButton.setOnAction((ActionEvent ae)-> {
                infoButtonPressed(scrollPanes.indexOf(pane));
            });
            pane.getChildren().add(infoButton);
        }
    }

    private void editButtonPressed(int index) {
        if (currentClass.equals("Payment")) {
            Payment payment = (Payment) list.get(index);
            if (username.equals(payment.getUserName())) {
                listPane.setVisible(false);
                editPaymentsPane.setVisible(true);
                creditorPaymentCombo.getSelectionModel().select(payment.getCreditor());
                String amount = payment.getValue() / 100 + ".";
                if (payment.getValue() % 100<10){
                    amount = amount + "0" + payment.getValue() % 100;
                } else {
                    amount = amount + payment.getValue() % 100;
                }
                amountPaymentField.setText(amount);
                int year = Integer.parseInt(payment.getDateFormatted().substring(6, 10));
                int month = Integer.parseInt(payment.getDateFormatted().substring(0, 2));
                int day = Integer.parseInt(payment.getDateFormatted().substring(3, 5));
                LocalDate date = LocalDate.of(year, month, day);
                datePaymentDate.setValue(date);
                isUpdate = true;
                isUpdateIndex = index;
            } else {
                if (!editIsPressed && !deleteIsPressed) {
                    listScrollPane.setPrefHeight(list.size() * 45 + 73);
                    scrollPanes.get(index).setPrefHeight(90);
                    Label label = new Label();
                    label.setTextFill(Paint.valueOf("#BBBBBB"));
                    label.setFont(Font.font("Segoe UI", 12));
                    label.setLayoutY(57);
                    scrollPanes.get(index).setId("selectedPane");
                    scrollPanes.get(index).getChildren().add(label);
                    label.setText("     You can't edit an entry submitted by another user.");
                    editIsPressed = true;
                    for (int i = index; i < list.size() - 1; i++) {
                        scrollPanes.get(i + 1).setTranslateY(43);
                        scrollLines.get(i).setTranslateY(43);
                    }
                } else {
                    if (scrollPanes.get(index).getPrefHeight()==47 || deleteIsPressed) {
                        showScroll();
                        editButtonPressed(index);
                    } else {
                        showScroll();
                    }
                }
            }
        } else {
            Expense expense = (Expense) list.get(index);
            if (username.equals(expense.getUserName())) {
                listPane.setVisible(false);
                editExpensesPane.setVisible(true);
                descriptionExpenseField.setText(expense.getDescription());
                String price = expense.getValue() / 100 + ".";
                if (expense.getValue() % 100<10){
                    price = price + "0" + expense.getValue() % 100;
                } else {
                    price = price + expense.getValue() % 100;
                }
                priceExpenseField.setText(price);

                valueFactory =  new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 50, expense.getPeople());
                participantsExpenseSpinner.setValueFactory(valueFactory);

                switch (username) {
                    case "Alejo":   firstExpenseCheck.setSelected(expense.didIanSpend());
                                    secondExpenseCheck.setSelected(expense.didTotiSpend());
                                    break;
                    case "Ian":     firstExpenseCheck.setSelected(expense.didAlejoSpend());
                                    secondExpenseCheck.setSelected(expense.didTotiSpend());
                                    break;
                    default:        firstExpenseCheck.setSelected(expense.didAlejoSpend());
                                    secondExpenseCheck.setSelected(expense.didIanSpend());
                }


                int year = Integer.parseInt(expense.getDateFormatted().substring(6, 10));
                int month = Integer.parseInt(expense.getDateFormatted().substring(0, 2));
                int day = Integer.parseInt(expense.getDateFormatted().substring(3, 5));
                LocalDate date = LocalDate.of(year, month, day);
                dateExpenseDate.setValue(date);
                isUpdate = true;
                isUpdateIndex = index;
            } else {
                if (!editIsPressed && !deleteIsPressed) {
                    listScrollPane.setPrefHeight(list.size() * 45 + 73);
                    scrollPanes.get(index).setPrefHeight(90);
                    Label label = new Label();
                    label.setTextFill(Paint.valueOf("#BBBBBB"));
                    label.setFont(Font.font("Segoe UI", 12));
                    label.setLayoutY(57);
                    scrollPanes.get(index).setId("selectedPane");
                    scrollPanes.get(index).getChildren().add(label);
                    label.setText("     You can't edit an entry submitted by another user.");
                    editIsPressed = true;
                    for (int i = index; i < list.size() - 1; i++) {
                        scrollPanes.get(i + 1).setTranslateY(43);
                        scrollLines.get(i).setTranslateY(43);
                    }
                } else {
                    if (scrollPanes.get(index).getPrefHeight()==47 || deleteIsPressed) {
                        showScroll();
                        showInfoButtons();
                        editButtonPressed(index);
                    } else {
                        showScroll();
                        showInfoButtons();
                    }
                }
            }
        }
    }

    private void deleteButtonPressed(int index) {

        String objectUser;

        if (!deleteIsPressed && !editIsPressed && !infoIsPressed) {
            listScrollPane.setPrefHeight(list.size()*45+73);
            scrollPanes.get(index).setPrefHeight(90);
            Label label = new Label();
            label.setTextFill(Paint.valueOf("#BBBBBB"));
            label.setFont(Font.font("Segoe UI", 12));
            label.setLayoutY(57);
            scrollPanes.get(index).setId("selectedPane");
            scrollPanes.get(index).getChildren().add(label);
            if (currentClass.equals("Expense")) {
                Expense expense = (Expense) list.get(index);
                objectUser = expense.getUserName();
            } else {
                Payment payment = (Payment) list.get(index);
                objectUser = payment.getUserName();
            }
            if (username.equals(objectUser)) {
                label.setText("     Are you sure you want to delete this entry?");
                Button confirmDeleteButton = new Button();
                confirmDeleteButton.setCursor(Cursor.HAND);
                confirmDeleteButton.setText("CONFIRM");
                confirmDeleteButton.setTextFill(Paint.valueOf("#BBBBBB"));
                confirmDeleteButton.setId("confirmDeleteButton");
                confirmDeleteButton.setFont(Font.font("Segoe UI BLACK", 12));
                confirmDeleteButton.setLayoutX(scrollPanes.get(index).getPrefWidth()-88);
                confirmDeleteButton.setLayoutY(51);
                confirmDeleteButton.setOnAction((ActionEvent ae)-> {
                    confirmDeleteButtonClicked(index);
                });
                scrollPanes.get(index).getChildren().add(confirmDeleteButton);
                deleteIsPressed = true;
            } else {
                label.setText("     You can't delete an entry submitted by another user.");
                deleteIsPressed = true;
            }
            for (int i=index; i<list.size()-1; i++){
                scrollPanes.get(i+1).setTranslateY(43);
                scrollLines.get(i).setTranslateY(43);
            }
        } else {
            if (scrollPanes.get(index).getPrefHeight()==47 || editIsPressed || infoIsPressed) {
                showScroll();
                deleteButtonPressed(index);
            } else {
                showScroll();
            }
            if (currentClass.equals("Expense")) {
                showInfoButtons();
            }
        }
    }

    private void confirmDeleteButtonClicked(int index) {
        try {
            if (currentClass.equals("Expense")) {
                Expense expense = (Expense) list.get(index);
                expense.setValue(-expense.getValue());
                userDao.sqlUpdateDebtsByExpense(expense);
                expenseDao.sqlDelete(expense.getId());
                list = expenseDao.sqlGetList();
                showInfoButtons();
            } else if (currentClass.equals("Payment")) {
                Payment payment = (Payment) list.get(index);
                payment.setValue(-payment.getValue());
                userDao.sqlUpdateDebtsByPayment(payment);
                paymentDao.sqlDelete(payment.getId());
                list = paymentDao.sqlGetList();
            }
            showScroll();
        } catch (Exception e){
            System.out.println("Exception thrown on confirmDeleteButtonClicked");
        }
    }

    private void infoButtonPressed(int index) {

        if (!infoIsPressed && !editIsPressed && !deleteIsPressed) {
            if (listScrollPane.getHeight() != list.size() * 45 + 30 || deleteIsPressed) {
                showScroll();
                showInfoButtons();
            }
            listScrollPane.setPrefHeight(list.size() * 47 + 77);
            scrollPanes.get(index).setPrefHeight(90);
            Expense expense = (Expense) list.get(index);
            Label info = new Label("     " + expense.getDescription());
            info.setTextFill(Paint.valueOf("#BBBBBB"));
            info.setFont(Font.font("Segoe UI", 12));
            info.setLayoutY(57);
            scrollPanes.get(index).setId("selectedPane");
            scrollPanes.get(index).getChildren().add(info);
            infoIsPressed = true;
            for (int i=index; i<list.size()-1; i++){
                scrollPanes.get(i+1).setTranslateY(43);
                scrollLines.get(i).setTranslateY(43);
            }
        } else {
            if (scrollPanes.get(index).getPrefHeight()==47 || editIsPressed || deleteIsPressed) {
                showScroll();
                infoButtonPressed(index);
            } else {
                showScroll();
                showInfoButtons();
            }
        }

    }

}