package gui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class ComboBoxAutoComplete<T extends Comparable<T>> extends JComboBox<T>{
	public ComboBoxAutoComplete(Collection<T> collection) {
		super();
		setPreferredSize(new Dimension(200, getPreferredSize().height));
		ArrayList<T> list = new ArrayList<T>(collection.stream().distinct().collect(Collectors.toCollection(ArrayList::new)));
		java.util.Collections.sort(list);
		list.forEach(country -> addItem(country));
		AutoCompletion.enable(this);	
	}
	public ComboBoxAutoComplete() {
		super();
		setPreferredSize(new Dimension(200, getPreferredSize().height));
	}
	public void setItems(Collection<T> collection) {
		if(getItemCount() != 0)
			removeAllItems();
		if(collection != null) {
			ArrayList<T> list = new ArrayList<T>(collection.stream().distinct().collect(Collectors.toCollection(ArrayList::new)));
			java.util.Collections.sort(list);
			list.forEach(country -> addItem(country));
		}
	}
}
