package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

import su.ugatu.moodle.is.credit_calc.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;


public class Controller {
    private CreditApplication application;
    private CreditOffer offer;
    private ObservableList<CreditData> creditData = FXCollections.observableArrayList();
    private ObservableList<String> model = FXCollections.observableArrayList();
    @FXML
    private WebView webView;
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
        combo_type.setPrefSize(298, 28);
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

        if(combo_type.getSelectionModel().isSelected(1)){
            application.setPaymentType(CreditPaymentType.DIFFERENTIAL);
            CreditProposal proposal = offer.calculateProposal(application);
            printProposal(proposal);
        }
        else{
            application.setPaymentType(CreditPaymentType.ANNUITY);
            CreditProposal proposal = offer.calculateProposal(application);
            printProposal(proposal);
        }
    }

    @FXML
    public void onClickMethod(){
        creditData.clear();
        if(control(amount) && control(durationInMonths) && control(interestRate)){
            application = new CreditApplicationImpl(new BigDecimal(amount.getText()));
            application.setDurationInMonths(Integer.valueOf(durationInMonths.getText()));
            offer = new CreditOfferImpl();
            offer.setRate(new BigDecimal(interestRate.getText()).divide(new BigDecimal(100)));

            if(control(onceCommissionAmount))
                offer.setOnceCommissionAmount(new BigDecimal(onceCommissionAmount.getText()));
            else
                onceCommissionAmount.setText("0");
            if(control(onceCommissionPercent))
                offer.setOnceCommissionPercent(new BigDecimal(onceCommissionPercent.getText()).divide(new BigDecimal(100)));
            else
                onceCommissionPercent.setText("0");
            if(control(monthlyCommissionPercent))
                offer.setMonthlyCommissionPercent(new BigDecimal(monthlyCommissionPercent.getText()).divide(new BigDecimal(100)));
            else
                monthlyCommissionPercent.setText("0");
            if(control(monthlyCommissionAmount))
                offer.setMonthlyCommissionAmount(new BigDecimal(monthlyCommissionAmount.getText()));
            else
                monthlyCommissionAmount.setText("0");

            initData();
        }

    }
    @FXML
    public void onExit(){
        System.exit(0);
    }

    @FXML
    public void onClickHelp() throws Exception {
        new HelpDialog();
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

    private void printProposal(CreditProposal proposal){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        List<CreditPayment> payments = proposal.getPayments();
        text_res.setText("Всего: "+decimalFormat.format(proposal.getTotalPayment())+" денежных единиц\n"
                        +"Эффективная процентная ставка: "+(decimalFormat.format(proposal.getEffectiveRate().doubleValue() * 100)) + "%\n"
                        +"Комиссия: " + decimalFormat.format(proposal.getInitialCreditCommission()));
        int i = 1;
        for (CreditPayment payment: payments) {
            creditData.add(new CreditData(
                    (i++),
                    "" + dateFormat.format(payment.getDate()),
                    "" + decimalFormat.format(payment.getAmount()),
                    "" + decimalFormat.format(payment.getDebt()),
                    "" + decimalFormat.format(payment.getInterest()),
                    "" + decimalFormat.format(payment.getCommission()),
                    "" + decimalFormat.format(payment.getTotalLeft())));
        }

    }

}
