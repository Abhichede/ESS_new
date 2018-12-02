//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Sprays;

import Main.Printsupport;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class SprayPrintJob implements Printable {
    JTable itemTable;
    Object[][] itemTableData;
    static String[] title = new String[]{"Date", "Customer", "Driver", "Village", "Acres", "Rate", "Total Amount", "Sign"};
    int[] pageBreaks;

    public SprayPrintJob() {
    }

    public SprayPrintJob(JTable itemsTable) {
        this.itemTable = itemsTable;
    }

    public Object[][] getTableData(JTable table) {
        int itemcount = table.getRowCount();
        DefaultTableModel dtm = (DefaultTableModel)table.getModel();
        int nRow = dtm.getRowCount();
        int nCol = dtm.getColumnCount();
        Object[][] tableData = new Object[nRow][nCol];
        if (itemcount == nRow) {
            for(int i = 0; i < nRow; ++i) {
                for(int j = 1; j < nCol; ++j) {
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

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        int result = 1;
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
            y = (int)((imageableHeight - (double)(fontHeight * 7)) / (double)fontHeight);
            start = (this.itemTable.getRowCount() + 5) / y;
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
                g2d.drawString(Printsupport.now(), (int)(width / 3.0D) * 2, y);
                y += fontHeight;
                g2d.drawLine(0, y, (int)width, y);
                y += fontHeight;
                int itemsX = 25;

                int cellX;
                for(cellX = 0; cellX < title.length; ++cellX) {
                    g2d.drawString(title[cellX], itemsX, y);
                    if (cellX != 1 && cellX != 2) {
                        if (cellX != 0 && cellX != title.length - 2) {
                            itemsX += 70;
                        } else {
                            itemsX += 100;
                        }
                    } else {
                        itemsX += 150;
                    }
                }

                y += fontHeight;
                g2d.drawLine(0, y, (int)width, y);
                font = new Font("Monospaced", 0, 12);
                g2d.setFont(font);
                cellX = 25;
                int cellY = y + fontHeight;

                for(int tblRow = start; tblRow < (end > this.itemTable.getRowCount() ? this.itemTable.getRowCount() : end); ++tblRow) {
                    for(int tblCol = 1; tblCol < this.itemTable.getColumnCount(); ++tblCol) {
                        g2d.drawString(this.itemTableData[tblRow][tblCol].toString(), cellX, cellY);
                        if (tblCol != 2 && tblCol != 3) {
                            if (tblCol != 1 && tblCol != 4) {
                                if (tblCol == this.itemTable.getColumnCount() - 2) {
                                    cellX += 100;
                                } else {
                                    cellX += 60;
                                }
                            } else {
                                cellX += 85;
                            }
                        } else {
                            cellX += 150;
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

            result = 0;
            return result;
        }
    }
}
