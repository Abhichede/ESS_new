//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Main;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class Printsupport {
    static JTable itemsTable;
    public static int total_item_count = 0;
    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss a";

    public Printsupport() {
    }

    public PageFormat getPageFormat(PrinterJob pj, Double pageWidth, Double pageHeight, int orientation) {
        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();
        double middleHeight = (double)total_item_count * 1.0D;
        double headerHeight = 5.0D;
        double footerHeight = 5.0D;
        double width = convert_CM_To_PPI(pageWidth);
        double height = convert_CM_To_PPI(pageHeight);
        System.out.println("Paper Width: " + width);
        System.out.println("Paper Height: " + height);
        paper.setSize(width, height);
        paper.setImageableArea(convert_CM_To_PPI(0.25D), convert_CM_To_PPI(0.35D), width - convert_CM_To_PPI(0.35D), height - convert_CM_To_PPI(1.0D));
        pf.setOrientation(orientation);
        pf.setPaper(paper);
        System.out.println("Imageable Width: " + paper.getImageableWidth());
        return pf;
    }

    protected static double convert_CM_To_PPI(double cm) {
        return toPPI(cm * 0.393600787D);
    }

    protected static double toPPI(double inch) {
        return inch * 72.0D;
    }

    public static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
        return sdf.format(cal.getTime());
    }

    public static class MyPrintable implements Printable {
        public MyPrintable() {
        }

        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
            int result = 1;
            if (pageIndex == 0) {
                Graphics2D g2d = (Graphics2D)graphics;
                double width = pageFormat.getImageableWidth();
                double height = pageFormat.getImageableHeight();
                g2d.translate((int)pageFormat.getImageableX(), (int)pageFormat.getImageableY());
                Font font = new Font("Monospaced", 0, 5);
                g2d.setFont(font);

                try {
                    int y = 5;
                    g2d.drawString("ABC Shopping Complex", 40, y);
                    g2d.drawString("CopyWrite 2009-2014", 50, y + 10);
                    g2d.drawString(Printsupport.now(), 10, y + 20);
                    g2d.drawString("Cashier : admin", 10, y + 30);
                    int cH = 0;
                    TableModel mod = Printsupport.itemsTable.getModel();

                    for(int i = 0; i < mod.getRowCount(); ++i) {
                        String itemid = mod.getValueAt(i, 0).toString();
                        String itemname = mod.getValueAt(i, 1).toString();
                        cH = y + 70 + 10 * i;
                        g2d.drawString(itemid, 0, cH);
                        g2d.drawString(itemname, 50, cH);
                    }

                    font = new Font("Arial", 1, 7);
                    g2d.setFont(font);
                } catch (Exception var17) {
                    var17.printStackTrace();
                }

                result = 0;
            }

            return result;
        }
    }
}
