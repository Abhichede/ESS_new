//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JFormattedTextField.AbstractFormatter;

public class DateLabelFormatter extends AbstractFormatter {
    private String datePattern = "yyyy-MM-dd";
    private SimpleDateFormat dateFormatter;

    public DateLabelFormatter() {
        this.dateFormatter = new SimpleDateFormat(this.datePattern);
    }

    public Object stringToValue(String text) throws ParseException {
        return this.dateFormatter.parseObject(text);
    }

    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar)value;
            return this.dateFormatter.format(cal.getTime());
        } else {
            return "";
        }
    }
}
