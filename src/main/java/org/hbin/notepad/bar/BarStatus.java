package org.hbin.notepad.bar;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * 状态栏
 * @author 
 */
public class BarStatus extends JPanel {
	private static final long serialVersionUID = 4569926427876706337L;
	
	private static final String positionInfo = "第R行，第C列 ";
	private JLabel infoLabel;
	
	public BarStatus() {
		init();
	}

	private void init() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(10, 23));

		infoLabel = new JLabel(positionInfo.replace("R", "1").replace("C", "1"), SwingConstants.RIGHT);
		add(infoLabel);
	}
	
	/**
	 * 设置光标位置信息
	 * @param row
	 * @param column
	 */
	public void setCaretPositionInfo(int row, int column) {
		String info = positionInfo.replace("R", row + "").replace("C",
				column + "");
		infoLabel.setText(info);
	}
	
	public void setText(String info) {
		if (info != null) {
			infoLabel.setText(info);
		}
	}
}
