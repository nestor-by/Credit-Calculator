package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Controller {

    private ObservableList<CreditData> creditData = FXCollections.observableArrayList();
    private ObservableList<String> model = FXCollections.observableArrayList();
    @FXML
    private ImageView im_exit;
    @FXML
    private ImageView im_help;
    @FXML
    private Text text_res;
    @FXML
    private ComboBox combo_type;
    @FXML
    private TableView<CreditData> table;
    @FXML
    private TableColumn<CreditData,Integer> num;
    @FXML
    private TableColumn<CreditData,String> paymentDateCol;
    @FXML
    private TableColumn<CreditData,String> amountClo;
    @FXML
    private TableColumn<CreditData,String> principalCol;
    @FXML
    private TableColumn<CreditData,String> accruedInterestCol;
    @FXML
    private TableColumn<CreditData,String> monthlyCommissionCol;
    @FXML
    private TableColumn<CreditData,String> balancePayableCol;
    @FXML
    private TextField amount;
    @FXML
    private TextField durationInMonths;
    @FXML
    private TextField interestRate;
    @FXML
    private TextField onceCommissionAmount;
    @FXML
    private TextField onceCommissionPercent;
    @FXML
    private TextField monthlyCommissionAmount;
    @FXML
    private TextField monthlyCommissionPercent;


    @FXML
    private void initialize() {

        im_help.setImage(new Image("img/help.png"));
        im_exit.setImage(new Image("img/exit_def.png"));
        model.addAll("Аннуитетная", "Дифференцированная");
        combo_type.setPrefSize(298,28);
        combo_type.setItems(model);
        combo_type.getSelectionModel().select(0);
        text_res.setText("Кредитный калькулятор позволяет\n" +
                         "построить график погашения\n" +
                         "произвольного кредита,а также\n" +
                         "рассчитать эффективную процентную\n" +
                         "ставку по кредиту.");
        // устанавливаем тип и значение которое должно хранится в колонке
        num.setCellValueFactory(new PropertyValueFactory<CreditData, Integer>("num"));
        paymentDateCol.setCellValueFactory(new PropertyValueFactory<CreditData, String>("paymentDateCol"));
        amountClo.setCellValueFactory(new PropertyValueFactory<CreditData, String>("amountClo"));
        principalCol.setCellValueFactory(new PropertyValueFactory<CreditData, String>("principalCol"));
        accruedInterestCol.setCellValueFactory(new PropertyValueFactory<CreditData, String>("accruedInterestCol"));
        monthlyCommissionCol.setCellValueFactory(new PropertyValueFactory<CreditData, String>("monthlyCommissionCol"));
        balancePayableCol.setCellValueFactory(new PropertyValueFactory<CreditData, String>("balancePayableCol"));

        // заполняем таблицу данными
        table.setItems(creditData);
    }

    private void initData() {
        creditData.add(new CreditData(1, "Alex", "120.56", "alex@mail.com", "Hello", "123", "9900"));
        creditData.add(new CreditData(2, "Alex", "120.56", "alex@mail.com", "Hello", "123", "9900"));
        creditData.add(new CreditData(3, "Alex", "120.56", "alex@mail.com", "Hello", "123", "9900"));
        creditData.add(new CreditData(4, "Alex", "120.56", "alex@mail.com", "Hello", "123", "9900"));
        creditData.add(new CreditData(5, "Alex", "qwerty", "alex@mail.com", "Hello", "123", "9900"));
        creditData.add(new CreditData(5, "Alex", "120.56", "alex@mail.com", "Hello", "123", "9900"));
        creditData.add(new CreditData(6, "Alex", "120.56", "alex@mail.com", "Hello", "123", "9900"));
        creditData.add(new CreditData(7, "Alex", "120.56", "alex@mail.com", "Hello", "123", "9900"));
        creditData.add(new CreditData(8, "Alex", "120.56", "alex@mail.com", "Hello", "123", "9900"));
        creditData.add(new CreditData(9, "Alex", "120.56", "alex@mail.com", "Hello", "123", "9900"));
        creditData.add(new CreditData(10, "Alex", "120.56", "alex@mail.com", "Hello", "123", "9900"));
        creditData.add(new CreditData(11, "Alex", "120.56", "alex@mail.com", "Hello", "123", "9900"));
        creditData.add(new CreditData(12, "Alex", "120.56", "alex@mail.com", "Hello", "123", "9900"));
    }
    @FXML
    public void onClickMethod(){
        if(control(amount) && control(durationInMonths) && control(interestRate)){
            if(!control(onceCommissionAmount))
                onceCommissionAmount.setText("0");
            if(!control(onceCommissionPercent))
                onceCommissionPercent.setText("0");
            if(!control(monthlyCommissionAmount))
                monthlyCommissionAmount.setText("0");
            if(!control(monthlyCommissionPercent))
                monthlyCommissionPercent.setText("0");

            if(combo_type.getSelectionModel().isSelected(1)){
                System.out.print("deffer");
            }
            else{
                System.out.print("annut");
            }
            creditData.clear();
            initData();
        }

    }
    @FXML
    public void onExit(){
        System.exit(0);
    }

    @FXML
    public void onHover(){
        im_exit.setImage(new Image("img/exit.png"));
        im_help.setImage(new Image("img/help_exit.png"));
    }
    @FXML
    public void onHotHover(){
        im_exit.setImage(new Image("img/exit_def.png"));
        im_help.setImage(new Image("img/help.png"));
    }

    @FXML
    public void onHoverHelp(){
        im_help.setImage(new Image("img/help_hover.png"));
    }
    @FXML
    public void onHotHoverHelp(){
        im_help.setImage(new Image("img/help.png"));
    }

    // проверка на число
    private boolean control(TextField tf){
        double d;
        try {
            d = Double.parseDouble(tf.getText());
            if(d < 0) tf.setText(""+Math.abs(d));
        }
        catch (NumberFormatException e){
            return false;
        }
        return true;
    }

}
