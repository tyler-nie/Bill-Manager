package ui.renderers;

import model.Person;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import java.awt.Component;

public class PersonRender extends DefaultListCellRenderer {
    public PersonRender(){   }

    public Component getListCellRendererComponent(JList list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value,index,isSelected,cellHasFocus);
        Person p = (Person)value;
        setText(p.getName());
        return this;
    }
}
