package gui;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class ComboBoxAutoComplete<T extends Comparable<T>> extends JComboBox<T>{
	public ComboBoxAutoComplete(Collection<T> collection) {
		super();		
		ArrayList<T> list = new ArrayList<T>(collection);
		java.util.Collections.sort(list);
		list.forEach(country -> addItem(country));
		AutoCompletion.enable(this);	
	}
}
