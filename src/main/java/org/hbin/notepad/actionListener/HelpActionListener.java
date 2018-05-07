package org.hbin.notepad.actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.hbin.notepad.bar.BarMenu;

public class HelpActionListener implements ActionListener {
	private BarMenu barMenu;
	private String title;
	private Object message;
	private int messageType;
	
	public HelpActionListener(BarMenu barMenu, String title, Object message,
			int messageType) {
		this.barMenu = barMenu;
		this.title = title;
		this.message = message;
		this.messageType = messageType;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(barMenu.getFrame(), message, title,
				messageType);
	}
}