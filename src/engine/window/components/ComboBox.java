package engine.window.components;

import java.util.ArrayList;

import de.matthiasmann.twl.model.ListModel;
import de.matthiasmann.twl.model.SimpleChangableListModel;
import editor.action_listener.ActionListener;
import editor.action_listener.ActionEvent;

public class ComboBox<E> extends de.matthiasmann.twl.ComboBox<E> {
	private ArrayList<ActionListener> action_listeners;

	public ComboBox() {
		super();
		action_listeners = new ArrayList<ActionListener>();
		this.addCallback(new Callback(this));
		this.setModel(new SimpleChangableListModel<E>());
	}

	public void setLabel() {

	}

	public void addItem(E item) {
		System.out.println(item);
		SimpleChangableListModel<E> new_list_model = (SimpleChangableListModel<E>) this.getModel();
		new_list_model.addElement(item);
		this.setModel(new_list_model);
	}

	public void removeItem(E item) {
		ListModel<E> old_list_model = this.getModel();
		SimpleChangableListModel<E> new_list_model = new SimpleChangableListModel<E>();
		for(int i=0; i < old_list_model.getNumEntries();i++) {
			new_list_model.addElement(old_list_model.getEntry(i));
		}
		for (int i = 0; i < new_list_model.getNumEntries(); i++) {
			if (new_list_model.getEntry(i).equals(item)) new_list_model
				.removeElement(i);
		}
		this.setModel(new_list_model);
	}

	public void removeAllItems() {
		SimpleChangableListModel<E> new_list_model = new SimpleChangableListModel<E>();
		this.setModel(new_list_model);
	}

	public void fireActionEvent() {
		for (ActionListener l : action_listeners) {
			l.actionPerformed(new ActionEvent(this));
		}
	}

	public void addActionListener(ActionListener listener) {
		action_listeners.add(listener);
	}

	private class Callback implements Runnable {
		ComboBox<E> owner;

		public Callback(ComboBox<E> owner) {
			this.owner = owner;
		}

		@Override
		public void run() {
			owner.fireActionEvent();
		}
	}
}
