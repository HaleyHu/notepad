package org.hbin.notepad.bar;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;

import org.hbin.notepad.actionListener.HelpActionListener;
import org.hbin.notepad.bean.MainFrame;
import org.hbin.notepad.filefilter.TxtFileFilter;
import org.hbin.notepad.util.Close;

/**
 * 菜单栏
 * @author 
 */
public class BarMenu extends JMenuBar {
	private static final long serialVersionUID = 8903541843706994961L;

	private JMenu fileMenu, editMenu, formatMenu, viewMenu, helpMenu;
	private JMenuItem newItem, openItem, saveItem, saveAsItem, printItem, exitItem;
	private JMenuItem undoItem, redoItem, cutItem, copyItem, pasteItem, delItem,
			findItem, findNextItem, replaceItem, jumpItem, selectAllItem,
			dateItem;
	private JCheckBoxMenuItem wrapItem, lineNumberItem, statusItem, fullScreenItem;
	private JMenuItem fontItem;
	private JMenuItem helpItem, aboutItem;
	
	private MainFrame frame;
	
	/** 状态栏菜单是否被选中 */
	private boolean isStatusSelected;
	
	private MenuActionListener actionListener = new MenuActionListener();
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	Icon newImage = new ImageIcon(this.getClass().getResource("/images/new.gif"));
	Icon openImage = new ImageIcon(this.getClass().getResource("/images/open.gif"));
	Icon saveImage = new ImageIcon(this.getClass().getResource("/images/save.gif"));
	
	Icon cutImage = new ImageIcon(this.getClass().getResource("/images/cut.gif"));
	Icon copyImage = new ImageIcon(this.getClass().getResource("/images/copy.gif"));
	Icon pasteImage = new ImageIcon(this.getClass().getResource("/images/paste.gif"));

	public BarMenu(MainFrame frame) {
		this.frame = frame;
		
		init();
	}

	/**
	 * 初始菜单项
	 * @author
	 */
	private void init() {
		initFile();
		initEdit();
		initFormat();
		initView();
		initHelp();
	}
	
	/**
	 * 初始化文件菜单项
	 * @author 
	 */
	private void initFile(){
		fileMenu = new JMenu("文件(F)");
		newItem = new JMenuItem("新建(N)", newImage);
		openItem = new JMenuItem("打开(O)…", openImage);
		saveItem = new JMenuItem("保存(S)", saveImage);
		saveAsItem = new JMenuItem("另存为(A)…");
		printItem = new JMenuItem("打印(P)…");
		exitItem = new JMenuItem("退出(X)");
		
		//设置快捷键和助记符
		fileMenu.setMnemonic(KeyEvent.VK_F);
		newItem.setMnemonic(KeyEvent.VK_N);
		newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,  
                KeyEvent.CTRL_MASK));
		openItem.setMnemonic(KeyEvent.VK_O);
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,  
                KeyEvent.CTRL_MASK));
		saveItem.setMnemonic(KeyEvent.VK_S);
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,  
                KeyEvent.CTRL_MASK));
		saveAsItem.setMnemonic(KeyEvent.VK_A);
		printItem.setMnemonic(KeyEvent.VK_P);
		printItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, 
				KeyEvent.CTRL_MASK));
		exitItem.setMnemonic(KeyEvent.VK_X);
		
		//绑定事件
		newItem.addActionListener(actionListener);
		openItem.addActionListener(actionListener);
		saveItem.addActionListener(actionListener);
		saveAsItem.addActionListener(actionListener);
		printItem.addActionListener(actionListener);
		exitItem.addActionListener(actionListener);
		
		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(saveAsItem);
		fileMenu.addSeparator();
		fileMenu.add(printItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		
		add(fileMenu);
	}

	/**
	 * 初始化编辑菜单项
	 * @author 
	 */
	private void initEdit(){
		editMenu = new JMenu("编辑(E)");
		
		undoItem = new JMenuItem("撤消(U)");
		redoItem = new JMenuItem("恢复(Y)");
		cutItem = new JMenuItem("剪切(T)", cutImage);
		copyItem = new JMenuItem("复制(C)", copyImage);
		pasteItem = new JMenuItem("粘贴(P)", pasteImage);
		delItem = new JMenuItem("删除(L)");

		findItem = new JMenuItem("查找(F)…");
		findNextItem = new JMenuItem("查找下一个(N)");
		replaceItem = new JMenuItem("替换(R)…");
		jumpItem = new JMenuItem("转到(G)…");
		selectAllItem = new JMenuItem("全选(A)");
		dateItem = new JMenuItem("日期/时间(D)");

		//设置快捷键和助记符
		editMenu.setMnemonic(KeyEvent.VK_E);
		undoItem.setMnemonic(KeyEvent.VK_U);
		undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,  
                KeyEvent.CTRL_MASK));
		redoItem.setMnemonic(KeyEvent.VK_Y);
		redoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,  
                KeyEvent.CTRL_MASK));
		cutItem.setMnemonic(KeyEvent.VK_T);
		cutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,  
                KeyEvent.CTRL_MASK));
		copyItem.setMnemonic(KeyEvent.VK_C);
		copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,  
                KeyEvent.CTRL_MASK));
		pasteItem.setMnemonic(KeyEvent.VK_P);
		pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,  
                KeyEvent.CTRL_MASK));
		delItem.setMnemonic(KeyEvent.VK_L);
		delItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		
		findItem.setMnemonic(KeyEvent.VK_F);
		findItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,  
                KeyEvent.CTRL_MASK));
		findNextItem.setMnemonic(KeyEvent.VK_N);
		findNextItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
		replaceItem.setMnemonic(KeyEvent.VK_R);
		replaceItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,  
                KeyEvent.CTRL_MASK));
		jumpItem.setMnemonic(KeyEvent.VK_G);
		jumpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,  
                KeyEvent.CTRL_MASK));
		selectAllItem.setMnemonic(KeyEvent.VK_A);
		selectAllItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,  
                KeyEvent.CTRL_MASK));
		dateItem.setMnemonic(KeyEvent.VK_D);
		dateItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));

		//绑定事件
		undoItem.addActionListener(actionListener);
		redoItem.addActionListener(actionListener);
		cutItem.addActionListener(actionListener);
		copyItem.addActionListener(actionListener);
		pasteItem.addActionListener(actionListener);
		delItem.addActionListener(actionListener);
		
		findItem.addActionListener(actionListener);
		findNextItem.addActionListener(actionListener);
		replaceItem.addActionListener(actionListener);
		jumpItem.addActionListener(actionListener);
		selectAllItem.addActionListener(actionListener);
		dateItem.addActionListener(actionListener);

		editMenu.add(undoItem);
		editMenu.add(redoItem);
		editMenu.addSeparator();
		
		editMenu.add(cutItem);
		editMenu.add(copyItem);
		editMenu.add(pasteItem);
		editMenu.add(delItem);
		editMenu.addSeparator();

		editMenu.add(findItem);
		editMenu.add(findNextItem);
		editMenu.add(replaceItem);
		editMenu.add(jumpItem);
		editMenu.addSeparator();
		
		editMenu.add(selectAllItem);
		editMenu.add(dateItem);
		
		add(editMenu);
	}

	/**
	 * 初始化格式菜单项
	 * @author 
	 */
	private void initFormat(){
		formatMenu = new JMenu("格式(O)");
		
		wrapItem = new JCheckBoxMenuItem("自动换行(W)");
		fontItem = new JMenuItem("字体(F)…");

		//设置快捷键和助记符
		formatMenu.setMnemonic(KeyEvent.VK_O);
		wrapItem.setMnemonic(KeyEvent.VK_W);
		fontItem.setMnemonic(KeyEvent.VK_F);

		//绑定事件
		wrapItem.addActionListener(actionListener);
		fontItem.addActionListener(actionListener);
		
		formatMenu.add(wrapItem);
		formatMenu.add(fontItem);
		
		add(formatMenu);
	}

	/**
	 * 初始化查看菜单项
	 * @author 
	 */
	private void initView(){
		viewMenu = new JMenu("查看(V)");
		lineNumberItem = new JCheckBoxMenuItem("行号(L)");
		statusItem = new JCheckBoxMenuItem("状态栏(S)");
		fullScreenItem = new JCheckBoxMenuItem("全屏显示");
		
		//设置快捷键和助记符
		viewMenu.setMnemonic(KeyEvent.VK_V);
		statusItem.setMnemonic(KeyEvent.VK_S);
		lineNumberItem.setMnemonic(KeyEvent.VK_L);
		fullScreenItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0));
		
		//绑定事件
		lineNumberItem.addActionListener(actionListener);
		statusItem.addActionListener(actionListener);
		fullScreenItem.addActionListener(actionListener);

		viewMenu.add(lineNumberItem);
		viewMenu.add(statusItem);
		viewMenu.add(fullScreenItem);
		add(viewMenu);
	}

	/**
	 * 初始化帮助菜单项
	 * @author 
	 */
	private void initHelp(){
		helpMenu = new JMenu("帮助(H)");

		helpItem = new JMenuItem("查看帮助(H)");
		aboutItem = new JMenuItem("关于记事本(A)");

		//设置快捷键和助记符
		helpMenu.setMnemonic(KeyEvent.VK_H);
		helpItem.setMnemonic(KeyEvent.VK_H);
		aboutItem.setMnemonic(KeyEvent.VK_A);

		String title = "帮助";
		String content = "<html><div>感谢您使用Notepad！<br/><br/>如果您在使用过程中遇到任何问题" +
				"<br/>请联系作者！<br/><br/></div>";
		int messageType = JOptionPane.INFORMATION_MESSAGE;
		helpItem.addActionListener(new HelpActionListener(this, title,
				new JLabel(content), messageType));
		
		title = "关于记事本";
		content = "<html><div>Notepad 1.0<br/><br/><span style='color:red;font-size:20px'>简易记事本</span><br/>" +
				"<br/>版本：<i>1.0</i><br/>作者：<i>斌斌</i><br/>邮箱：<i>cn.binbin@qq.com</i><br/><br/></div></html>";
		aboutItem.addActionListener(new HelpActionListener(this, title,
				new JLabel(content), messageType));
		
		helpMenu.add(helpItem);
		helpMenu.addSeparator();
		helpMenu.add(aboutItem);
		
		add(helpMenu);
	}

	public MainFrame getFrame() {
		return frame;
	}
	
	class MenuActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea textArea = frame.getTextArea();
//			JMenuItem newItem, openItem, saveItem, saveAsItem, exitItem;
			Object obj = e.getSource();
			if(newItem.equals(obj)){
				newFile();
			} else if(openItem.equals(obj)){
				open();
			} else if(saveItem.equals(obj)){
				save();
			} else if(saveAsItem.equals(obj)){
				saveAs();
			} else if(printItem.equals(obj)) {
				print();
			} else if(exitItem.equals(obj)){
				exit();
//				undoItem, redoItem, cutItem, copyItem, pasteItem, delItem,
			} else if (undoItem.equals(obj)) {
				if(frame.getUndoManager().canUndo()){
					frame.getUndoManager().undo();
				}
			} else if (redoItem.equals(obj)) {
				if(frame.getUndoManager().canRedo()){
					frame.getUndoManager().redo();
				}
			} else if (cutItem.equals(obj)) {
				textArea.cut();
			} else if (copyItem.equals(obj)) {
				textArea.copy();
			} else if (pasteItem.equals(obj)) {
				textArea.paste();
			} else if (delItem.equals(obj)) {
				textArea.replaceSelection(null);
//				findItem, findNextItem, replaceItem, jumpItem, selectAllItem,
//				dateItem;
			} else if (findItem.equals(obj)) {
				JOptionPane.showMessageDialog(frame, "功能开发中，请稍后再试", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			} else if (findNextItem.equals(obj)) {
				JOptionPane.showMessageDialog(frame, "功能开发中，请稍后再试", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			} else if (replaceItem.equals(obj)) {
				JOptionPane.showMessageDialog(frame, "功能开发中，请稍后再试", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			} else if (jumpItem.equals(obj)) {
				int lineCount = textArea.getLineCount();
				String result = JOptionPane.showInputDialog(frame, "行号（1..."
						+ lineCount + "）：", "转到指定行", JOptionPane.PLAIN_MESSAGE);
				if (result!=null) {
					int line = 0;
					try {
						line = Integer.parseInt(result);
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(frame, "输入的行号有误！",
								"错误提示", JOptionPane.ERROR_MESSAGE);
					}
					int position = 0;
					try {
						if (line > 0 && line <= lineCount) {
							position = textArea.getLineStartOffset(line - 1);
						}else{
							JOptionPane.showMessageDialog(frame, "输入的行号有误！",
									"错误提示", JOptionPane.ERROR_MESSAGE);
						}
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
					textArea.setCaretPosition(position);
				}
			} else if (selectAllItem.equals(obj)) {
				textArea.selectAll();
			} else if (dateItem.equals(obj)) {
				String time = df.format(new Date());
				textArea.replaceSelection(time);
				//JCheckBoxMenuItem wrapItem, lineNumberItem, statusItem, fullScreenItem;
				//JMenuItem fontItem;
			} else if (wrapItem.equals(obj)) {
				boolean flag = wrapItem.isSelected();
				//换行策略：是否自动换行
				textArea.setLineWrap(flag);
				//换行方式：true-在单词边界换行false-在字符边界换行
				textArea.setWrapStyleWord(flag);
				//如果选择自动换行，则保存状态栏是否显示的信息，并将状态栏菜单项置灰处理；否则设置状态栏菜单项为可用状态并还原是否显示信息
				if (flag) {
					isStatusSelected = statusItem.isSelected();
					jumpItem.setEnabled(false);
					statusItem.setEnabled(false);
					if(isStatusSelected){
						statusItem.setSelected(false);
						frame.setStatusVisible(false);
					}
				} else {
					jumpItem.setEnabled(true);
					statusItem.setEnabled(true);
					statusItem.setSelected(isStatusSelected);
					if(isStatusSelected){
						frame.setStatusVisible(true);
					}
				}
			}else if(lineNumberItem.equals(obj)){
				frame.setRowHeaderVisible(lineNumberItem.isSelected());
			} else if (statusItem.equals(obj)) {
				frame.setStatusVisible(statusItem.isSelected());
			} else if (fullScreenItem.equals(obj)) {
				GraphicsDevice gd = GraphicsEnvironment
						.getLocalGraphicsEnvironment().getDefaultScreenDevice();
				if (gd == null || !gd.isFullScreenSupported()) {
					return;// 设备不支持
				}
				// 全屏模式
				if (fullScreenItem.isSelected()) {
					gd.setFullScreenWindow(frame);
				} else {
					gd.setFullScreenWindow(null);
				}
			} else if (fontItem.equals(obj)) {
				JOptionPane.showMessageDialog(frame, "功能开发中，请稍后再试", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		private void newFile(){
			frame.setFileName(null);
			frame.getTextArea().setText("");
			frame.setTitle("记事本 - New File.txt");
		}
		
		private void open(){
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(new FileNameExtensionFilter("*.txt(文本文件)", "txt"));
			int index = chooser.showOpenDialog(frame);
			if (index == JFileChooser.APPROVE_OPTION) {
				File f = chooser.getSelectedFile();
				frame.openFile(f.getAbsolutePath());
			}
		}
		
		private void save(){
			if(frame.getFileName() == null){
				saveFile("保存");
			} else{
				frame.saveFile();
			}
		}
		
		private void saveAs(){
			saveFile("另存为");
		}
		
		private void print() {
			PrinterJob.getPrinterJob().printDialog();
		}
		
		private void saveFile(String dialogTitle){
			JFileChooser chooser = new JFileChooser();
			// //文件默认目录
			// String defaultPath = System.getProperty("user.home");
			// fileChooser.setCurrentDirectory(new File(defaultPath));
			chooser.setDialogTitle(dialogTitle);
			chooser.setDialogType(JFileChooser.SAVE_DIALOG);
			chooser.setFileFilter(new FileNameExtensionFilter("*.txt(文本文件)", "txt"));
			chooser.setSelectedFile(new File("New File.txt"));
			chooser.addChoosableFileFilter(new TxtFileFilter());
			int index = chooser.showSaveDialog(frame);
			if (index == JFileChooser.APPROVE_OPTION) {
				File f = chooser.getSelectedFile();
				System.out.println("[SaveActionListener actionPerformed()]" + f.getAbsolutePath());

				if (f.exists()) {
					String msg = f.getName() + "已存在，要替换它吗？";
					int result = JOptionPane.showConfirmDialog(frame, msg, "保存文件",
							JOptionPane.WARNING_MESSAGE);
					if (result == JOptionPane.NO_OPTION) {
						return;
					}
				} else {
					try {
						f.createNewFile();
					} catch (IOException e1) {
						e1.printStackTrace();
						return;
					}
				}

				frame.setFileName(f.getAbsolutePath());
				boolean saveResult = frame.saveFile();

				frame.setTitle("记事本 - " + f.getAbsolutePath());
				String msg = saveResult ? "保存成功" : "保存失败";
				int messageType = saveResult ? JOptionPane.INFORMATION_MESSAGE
						: JOptionPane.WARNING_MESSAGE;
				JOptionPane.showMessageDialog(frame, msg, "保存文件", messageType);
			}
		}
		
		private void exit(){
			Close.close(frame);
		}
	}
}
