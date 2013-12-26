package su.ugatu.moodle.is.credit_calc.component;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class MyModel extends AbstractTableModel{
    //Шапка таблицы
    private final String[] head = {"№ Платежа","Дата платежа","Сумма платежа","Основной долг","Начисленные проценты","Ежемесячные комиссии","Остаток задолженности"};
    private ArrayList<String[]> data;

    public MyModel(){
        data = new ArrayList<String[]>();
        for (int i=0; i< data.size(); i++){
            data.add(new String[getColumnCount()]);
        }
   }

    @Override
    public int getRowCount() {
        return data.size(); // количество строк
    }

    @Override
    public int getColumnCount() {
        return head.length; // количество столбцов
    }

    // значении строк
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String[] rows = data.get(rowIndex);
        return rows[columnIndex];
    }

    @Override
    public String getColumnName(int column){
        return head[column]; // значении шапки таблицы
    }

    // Добавление строки
    public void addData(String[] row){
        String[] rowTable = new String[getColumnCount()];
        rowTable = row;
        data.add(rowTable);
    }


}