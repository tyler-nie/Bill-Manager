package ui.renderers;

import model.Bill;

import javax.swing.*;
import java.awt.*;

public class BillRenderer extends DefaultListCellRenderer {

    public BillRenderer(){   }

    public Component getListCellRendererComponent(JList list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value,index,isSelected,cellHasFocus);
        Bill b = (Bill)value;
        setText("Bill " + Integer.toString(b.getID()));
        return this;
    }
}
