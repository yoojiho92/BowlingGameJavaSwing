package GUI;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;


public class ScorePanel extends JPanel {
    private String columnNames[];

    private  Object rowData[][];
    JTable jTable;

    public ScorePanel(){
        columnNames = new String[10];
        for(int i=0;i < 10; i++){
            columnNames[i] = java.lang.String.valueOf(i);
        }
        rowData = new String[3][10];
        for(int i = 0 ; i < 10 ; i++){
            rowData[0][i] = java.lang.String.valueOf(i);
            if(i != 9){
                rowData[1][i] = " | ";
            }else {
                rowData[1][i] = " | | ";
            }
            rowData[2][i] = "-";

        }
        setSize(500,200);
        jTable = new JTable(rowData,columnNames);
        resizeTable();
        jTable.setEnabled(false);
        add(jTable);
    }

    public void resizeTable(){
        // DefaultTableCellHeaderRenderer 생성 (가운데 정렬을 위한)
        DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
        // DefaultTableCellHeaderRenderer의 정렬을 가운데 정렬로 지정
        tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i =0 ; i <  jTable.getColumnModel().getColumnCount(); i++){
            jTable.getColumnModel().getColumn(i).setPreferredWidth(50);
            jTable.getColumnModel().getColumn(i).setCellRenderer(tScheduleCellRenderer);
        }
    }

    public void setScore(int round, int spare, String value){
        String[] token = String.valueOf(rowData[1][round]).split("|");
        if(spare == 0){
            rowData[1][round] = value + " | ";
        }else if(spare == 1){
            rowData[1][round] = token[0] + " | "+ value;
        }else if(spare == 2){
            rowData[1][round] = token[0] + " | "+ token[1] + " | "+ value;
        }
        remove(jTable);
        jTable = new JTable(rowData,columnNames);
        resizeTable();
        jTable.setEnabled(false);
        add(jTable);
    }
}
