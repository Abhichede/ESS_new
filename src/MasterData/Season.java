package MasterData;

import DBUtil.DatabaseConnection;
import Main.DateLabelFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class Season extends JInternalFrame{
    private JPanel panel_show_seasons;
    private JLabel lbl_strt_date;
    private JLabel lbl_end_date;
    private JDatePickerImpl txt_end_date;
    private JLabel lbl_is_active;
    private JComboBox combo_is_active;
    private JButton btn_save_eason;
    private JTable table_seasons;
    private JPanel main_panel;
    private JButton btn_edit_season;
    private JButton btn_delete_season;
    private JDatePickerImpl txt_strt_date;
    private DefaultTableModel defaultTableModel;

    public Season(){
        super("Seasons");
        String[] column = new String[]{"ID", "Start Date", "End Date", "Is Active"};
        this.defaultTableModel = new DefaultTableModel(0, 0);
        this.defaultTableModel.setColumnIdentifiers(column);
        this.table_seasons.setModel(this.defaultTableModel);
        this.add(this.$$$getRootComponent$$$());

        TableColumn sportColumn = table_seasons.getColumnModel().getColumn(3);
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("YES");
        comboBox.addItem("NO");
        sportColumn.setCellEditor(new DefaultCellEditor(comboBox));
        fillTable();
        this.btn_save_eason.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!txt_strt_date.getJFormattedTextField().getText().equals("") && !txt_end_date.getJFormattedTextField().getText().equals("")) {
                    int result = DatabaseConnection.addSeason(txt_strt_date.getJFormattedTextField().getText(), txt_end_date.getJFormattedTextField().getText(), combo_is_active.getItemAt(combo_is_active.getSelectedIndex()).toString());
                    if (result != 0) {
                        JOptionPane.showInternalMessageDialog(getContentPane(), "Season Added");
                        System.out.println(table_seasons.getRowCount());
                        fillTable();
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(getContentPane(), "Please Enter All the fields", "ERROR", 0);
                }

            }
        });

        btn_edit_season.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rowIndex = table_seasons.getSelectedRow();

                try {
                    int result = DatabaseConnection.updateSeason(Integer.parseInt(defaultTableModel.getValueAt(rowIndex, 0).toString()), defaultTableModel.getValueAt(rowIndex, 1).toString(), defaultTableModel.getValueAt(rowIndex, 2).toString(), defaultTableModel.getValueAt(rowIndex, 3).toString());
                    if (result != 0) {
                        JOptionPane.showInternalMessageDialog(getContentPane(), "success");
                        fillTable();
                    } else {
                        JOptionPane.showInternalMessageDialog(getContentPane(), "Something went wrong...", "Error", 0);
                    }
                } catch (Exception var4) {
                    var4.printStackTrace();
                }

            }
        });

        this.btn_delete_season.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rowIndex = table_seasons.getSelectedRow();

                try {
                    int result = DatabaseConnection.deleteSeason(Integer.parseInt(defaultTableModel.getValueAt(rowIndex, 0).toString()));
                    if (result != 0) {
                        JOptionPane.showInternalMessageDialog(getContentPane(), "Deleted");
                        fillTable();
                    } else {
                        JOptionPane.showInternalMessageDialog(getContentPane(), "Something went wrong...", "Error", 0);
                    }
                } catch (Exception var4) {
                    var4.printStackTrace();
                }

            }
        });
    }

    private void fillTable() {
        if (this.defaultTableModel.getRowCount() > 0) {
            for(int i = this.defaultTableModel.getRowCount() - 1; i > -1; --i) {
                this.defaultTableModel.removeRow(i);
            }
        }

        try {
            ResultSet resultSet = DatabaseConnection.getSeasons();

            while(resultSet.next()) {
                Object[] data = new Object[]{resultSet.getInt("season_id"), resultSet.getString("start_date"), resultSet.getString("end_date"), resultSet.getString("is_active")};
                this.defaultTableModel.addRow(data);
            }
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        UtilDateModel model1 = new UtilDateModel();
        UtilDateModel model2 = new UtilDateModel();
        Properties p = new Properties();

        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl toDatePanel = new JDatePanelImpl(model1, p);
        JDatePanelImpl endDatePanel = new JDatePanelImpl(model2, p);
        this.txt_strt_date = new JDatePickerImpl(toDatePanel, new DateLabelFormatter());
        this.txt_end_date = new JDatePickerImpl(endDatePanel, new DateLabelFormatter());
    }

    public JComponent $$$getRootComponent$$$() {
        return main_panel;
    }
}
