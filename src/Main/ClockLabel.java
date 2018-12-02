//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.Timer;

public class ClockLabel extends JLabel implements ActionListener {
    String type;
    SimpleDateFormat sdf;

    public ClockLabel(String type) {
        this.type = type;
        this.setForeground(Color.BLACK);
        byte var3 = -1;
        switch(type.hashCode()) {
            case 99228:
                if (type.equals("day")) {
                    var3 = 2;
                }
                break;
            case 3076014:
                if (type.equals("date")) {
                    var3 = 0;
                }
                break;
            case 3560141:
                if (type.equals("time")) {
                    var3 = 1;
                }
        }

        switch(var3) {
            case 0:
                this.sdf = new SimpleDateFormat("  MMMM dd yyyy");
                this.setFont(new Font("sans-serif", 0, 12));
                this.setHorizontalAlignment(2);
                break;
            case 1:
                this.sdf = new SimpleDateFormat("hh:mm:ss a");
                this.setFont(new Font("sans-serif", 0, 40));
                this.setHorizontalAlignment(0);
                break;
            case 2:
                this.sdf = new SimpleDateFormat("EEEE  ");
                this.setFont(new Font("sans-serif", 0, 16));
                this.setHorizontalAlignment(4);
                break;
            default:
                this.sdf = new SimpleDateFormat();
        }

        Timer t = new Timer(1000, this);
        t.start();
    }

    public void actionPerformed(ActionEvent ae) {
        Date d = new Date();
        this.setText(this.sdf.format(d));
    }
}
