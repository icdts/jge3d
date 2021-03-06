package editor.window;

import java.util.ArrayList;

import editor.action_listener.ActionEvent;
import editor.action_listener.ActionListener;
import engine.window.components.ComboBox;
import engine.window.components.Window;

public class MirrorMenu extends Window implements ActionListener {
	private ComboBox<String> mirror_cb;
	private ArrayList<ActionListener> action_listeners;

	public MirrorMenu() {
		action_listeners = new ArrayList<ActionListener>();
		setTitle("Axis Mirror Menu");

		mirror_cb = new ComboBox<String>();
		mirror_cb.setTheme("mirror_cb");
		mirror_cb.addActionListener(this);

		add(mirror_cb);
	}

	public void populateCB() {
		mirror_cb.addItem("X");
		mirror_cb.addItem("Y");
		mirror_cb.addItem("Z");
		mirror_cb.addItem("XY");
		mirror_cb.addItem("XZ");
		mirror_cb.addItem("YZ");
		mirror_cb.addItem("XYZ");
		
		mirror_cb.setSelected(0);
	}

	public Integer getSelection() {
		return mirror_cb.getSelected();
	}

	public void addActionListener(ActionListener listener) {
		action_listeners.add(listener);
	}

	private void fireActionEvent() {
		for (ActionListener ae : action_listeners) {
			ae.actionPerformed(new ActionEvent(this));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		fireActionEvent();
	}
}
