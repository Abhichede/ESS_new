//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Sprays;

import DBUtil.DatabaseConnection;
import Main.Printsupport;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class SprayPrintJob implements Printable {
    JTable itemTable = new JTable();
    Object[][] itemTableData;
    static String[] title;
    static String[] customerTitles = new String[]{"Date", "Acres", "Amount", "Sign"};
    static String[] villageTitles = new String[]{"Customer", "Acres", "Amount", "Paid", "Balance", "Sign"};
    int[] pageBreaks;
    String printFor;
    String forName;
    DefaultTableModel defaultTableModel;
    String village_name = "";

    public SprayPrintJob() {
    }

    public SprayPrintJob(String printFor, String forName) {
        this.printFor = printFor;
        this.forName = forName;
        this.defaultTableModel = new DefaultTableModel(0, 0);
        if (printFor.equals("customer")) {
            this.defaultTableModel.setColumnIdentifiers(customerTitles);
            title = customerTitles;
        } else {
            this.defaultTableModel.setColumnIdentifiers(villageTitles);
            title = villageTitles;
            this.village_name = forName;
        }

        this.itemTable.setModel(this.defaultTableModel);
        this.fillTable();
    }

    public Object[][] getTableData(JTable table) {
        int itemcount = table.getRowCount();
        DefaultTableModel dtm = (DefaultTableModel)table.getModel();
        int nRow = dtm.getRowCount();
        int nCol = dtm.getColumnCount() - 1;
        Object[][] tableData = new Object[nRow][nCol];
        if (itemcount == nRow) {
            for(int i = 0; i < nRow; ++i) {
                for(int j = 0; j < nCol; ++j) {
                    tableData[i][j] = dtm.getValueAt(i, j);
                }
            }

            if (tableData.length != itemcount) {
                this.getTableData(table);
            }
        } else {
            this.getTableData(table);
        }

        return tableData;
    }

    public void fillTable() {
        Double total_acres;
        ResultSet customers;
        String[] data;
        if (this.printFor.equals("customer")) {
            try {
                total_acres = 0.0D;
                ResultSet sprays = DatabaseConnection.getFilteredSprays("customer", this.forName);

                String[] totalAcres;
                while(sprays.next()) {
                    totalAcres = new String[]{sprays.getString("date"), sprays.getString("acres"), sprays.getString("total_amount")};
                    total_acres = total_acres + Double.parseDouble(sprays.getString("acres"));
                    this.defaultTableModel.addRow(totalAcres);
                }

                totalAcres = new String[]{"", "Acres: ", "" + total_acres};
                this.defaultTableModel.addRow(totalAcres);
                customers = DatabaseConnection.getCustomersByNameSingle(this.forName);
                if (customers.next()) {
                    data = new String[]{"", "Total: ", "" + customers.getString("payable_amount")};
                    this.defaultTableModel.addRow(data);
                    String[] totalPaid = new String[]{"", "Paid: ", "" + customers.getString("paid_amount")};
                    this.defaultTableModel.addRow(totalPaid);
                    data = new String[]{"", "Balance: ", "" + (Double.parseDouble(customers.getString("payable_amount")) - Double.parseDouble(customers.getString("paid_amount")))};
                    this.defaultTableModel.addRow(data);
                    this.village_name = customers.getString("cust_village");
                }
            } catch (Exception var8) {
                var8.printStackTrace();
            }
        } else if (this.printFor.equals("village_name")) {
            try {
                total_acres = 0.0D;
                Double total_amount = 0.0D;
                Double total_paid = 0.0D;

                for(customers = DatabaseConnection.getCustomersByVillageName(this.forName); customers.next(); total_paid = total_paid + Double.parseDouble(customers.getString("paid_amount"))) {
                    Double acres = 0.0D;

                    for(ResultSet sprays = DatabaseConnection.getFilteredSprays("customer", customers.getString("cust_name")); sprays.next(); acres = acres + Double.parseDouble(sprays.getString("acres"))) {
                        ;
                    }

                    data = new String[]{customers.getString("cust_name"), acres + "", customers.getString("payable_amount"), customers.getString("paid_amount"), Double.parseDouble(customers.getString("payable_amount")) - Double.parseDouble(customers.getString("paid_amount")) + ""};
                    this.defaultTableModel.addRow(data);
                    total_acres = total_acres + acres;
                    total_amount = total_amount + Double.parseDouble(customers.getString("payable_amount"));
                }

                data = new String[]{"Totals: ", "" + total_acres, "" + total_amount, "" + total_paid, "" + (total_amount - total_paid)};
                this.defaultTableModel.addRow(data);
            } catch (Exception var9) {
                var9.printStackTrace();
            }
        }

    }

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        Graphics2D g2d = (Graphics2D)graphics;
        Font font = new Font("Monospaced", 1, 12);
        double width = pageFormat.getImageableWidth();
        double imageableHeight = pageFormat.getImageableHeight();
        g2d.setFont(font);
        FontMetrics fontMetrics = g2d.getFontMetrics(font);
        int fontHeight = fontMetrics.getHeight();
        int currentHeight = 0;
        int y;
        int start;
        int end;
        if (this.pageBreaks == null) {
            y = (int)(imageableHeight / (double)fontHeight) / 2 - 4;
            start = (this.itemTable.getRowCount() + 5) / y;
            System.out.println("Imageable Height: " + imageableHeight + " Font Height: " + fontHeight);
            this.pageBreaks = new int[start];

            for(end = 0; end < start; ++end) {
                this.pageBreaks[end] = (end + 1) * y;
            }
        }

        if (pageIndex > this.pageBreaks.length) {
            return 1;
        } else {
            y = fontHeight + 5;
            start = pageIndex == 0 ? 0 : this.pageBreaks[pageIndex - 1];
            end = pageIndex == this.pageBreaks.length ? this.itemTable.getRowCount() + 2 : this.pageBreaks[pageIndex];
            this.itemTableData = this.getTableData(this.itemTable);

            try {
                g2d.drawString("SANMAN ESS Spraying Unit", 15, y);
                g2d.drawString(Printsupport.now(), (int)(width / 3.0D) * 2, y);
                y += fontHeight;
                font = new Font("Monospaced", 0, 8);
                g2d.setFont(font);
                g2d.drawString("SANMAN DOODH DAIRY, NASHIK-KALWAN ROAD, DINDORI", 15, y);
                y += fontHeight;
                font = new Font("Monospaced", 1, 12);
                g2d.setFont(font);
                if (this.printFor.equals("customer")) {
                    g2d.drawString("Customer: " + this.forName, 15, y);
                    y += fontHeight;
                }

                g2d.drawString("Village: " + this.village_name, 15, y);
                y += fontHeight;
                g2d.drawLine(0, y, (int)width, y);
                y += fontHeight;
                int itemsX = 25;

                int cellX;
                for(cellX = 0; cellX < title.length; ++cellX) {
                    g2d.drawString(title[cellX], itemsX, y);
                    if (cellX != 0 && cellX != title.length - 1) {
                        itemsX += 70;
                    } else {
                        itemsX += 160;
                    }
                }

                y += fontHeight;
                g2d.drawLine(0, y, (int)width, y);
                font = new Font("Monospaced", 0, 12);
                g2d.setFont(font);
                cellX = 25;
                int cellY = y + fontHeight;

                for(int tblRow = start; tblRow < (end > this.itemTable.getRowCount() ? this.itemTable.getRowCount() : end); ++tblRow) {
                    for(int tblCol = 0; tblCol < this.itemTable.getColumnCount() - 1; ++tblCol) {
                        if ((!this.printFor.equals("customer") || tblRow <= this.itemTable.getRowCount() - 5) && (!this.printFor.equals("village_name") || tblRow != this.itemTable.getRowCount() - 1)) {
                            font = new Font("Monospaced", 0, 12);
                            g2d.setFont(font);
                            g2d.drawString(this.itemTableData[tblRow][tblCol].toString(), cellX, cellY);
                        } else {
                            font = new Font("Monospaced", 1, 12);
                            g2d.setFont(font);
                            g2d.drawString(this.itemTableData[tblRow][tblCol].toString(), cellX, cellY);
                        }

                        if (tblCol == 0) {
                            cellX += 160;
                        } else {
                            cellX += 70;
                        }
                    }

                    cellY += fontHeight;
                    g2d.drawLine(0, cellY, (int)width, cellY);
                    cellY += fontHeight;
                    cellX = 25;
                }
            } catch (Exception var22) {
                var22.printStackTrace();
            }

            int result = 0;
            return result;
        }
    }
}
