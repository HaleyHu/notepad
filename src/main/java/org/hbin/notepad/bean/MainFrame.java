package org.hbin.notepad.bean;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.undo.UndoManager;

import org.hbin.notepad.bar.BarMenu;
import org.hbin.notepad.bar.BarStatus;
import org.hbin.notepad.util.Close;
import org.hbin.notepad.util.FileUtil;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1941381476012544512L;
	/** 窗口默认宽度 */
	private static final int DEFAULT_WIDTH = 800;
	/** 窗口默认高度 */
	private static final int DEFAULT_HEIGHT = 600;
	private JPopupMenu popup;
	private JMenuItem undoItem, redoItem, cutItem, copyItem, pasteItem, delItem, selectAllItem; //功能菜单
	
	private JScrollPane scrollPane;
    private JTextArea textArea;
    private BarStatus barStatus = new BarStatus();
    private UndoManager undoManager = new UndoManager();
    private LineNumberHeaderView lineNumberHeader = new LineNumberHeaderView();
    
    /** 当前文件名称 */
    private String fileName;
//    /** 查找的字符串 */
//    private String query;
//    /** 是否向上查找 */
//    private boolean queryUp;
//    /** 查询是否大小写敏感 */
//    private boolean queryCaseSensitive;
	
	public MainFrame() {
		init();
	}
	
	/**
	 * 初始化
	 * @author
	 */
	private void init(){
		setTitle("记事本 - New File.txt");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setWindowIcon();
		
		//设置关闭窗口时弹出确认提示
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Close.close(MainFrame.this);
			}
		});
		
		initMain();
		initMenu();
		initPopMenu();
//		initStatus();
		
		//窗体居中
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * 设置窗口图标
	 */
	public void setWindowIcon()
	{
		ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/logo.png"));
		this.setIconImage(imageIcon.getImage());
	}
	

	/**
	 * 初始化主界面
	 * @author
	 */
	private void initMain(){
		textArea = new JTextArea();
		scrollPane = new JScrollPane(textArea);
		add(scrollPane,BorderLayout.CENTER);
		textArea.setMargin(new Insets(5, 5, 5, 5));
		textArea.getDocument().addUndoableEditListener(undoManager);
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				update(e);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				update(e);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				update(e);
			}

			public void update(DocumentEvent e) {
				if (fileName != null) {
					setTitle("记事本 - " + fileName + "*");
				} else {
					setTitle("记事本 - New File.txt*");
				}
			}
		});
		textArea.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				try {
					int pos = textArea.getCaretPosition();
					// 获取行数
					int row = textArea.getLineOfOffset(pos) + 1;
					// 获取列数
					int column = pos - textArea.getLineStartOffset(row - 1) + 1;
					barStatus.setCaretPositionInfo(row, column);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	/**
	 * 初始化菜单栏
	 * @author
	 */
	private void initMenu(){
		setJMenuBar(new BarMenu(this));
	}

	/**
	 * 初始化状态栏
	 * @author
	 */
	public void initStatus(){
        add(barStatus, BorderLayout.SOUTH);
	}
	
	/**
	 * 初始化弹出菜单
	 */
	private void initPopMenu(){
		popup = new JPopupMenu();

		popup.add(undoItem = new JMenuItem("撤消(U)"));
		popup.add(redoItem = new JMenuItem("恢复(Y)"));
		popup.addSeparator();
		popup.add(cutItem = new JMenuItem("剪切(T)"));
		popup.add(copyItem = new JMenuItem("复制(C)"));
		popup.add(pasteItem = new JMenuItem("粘贴(V)"));
		popup.add(delItem = new JMenuItem("删除(D)"));
		popup.addSeparator();
		popup.add(selectAllItem = new JMenuItem("全选(A)"));

		undoItem.setMnemonic(KeyEvent.VK_U);
		redoItem.setMnemonic(KeyEvent.VK_Y);
		cutItem.setMnemonic(KeyEvent.VK_T);
		copyItem.setMnemonic(KeyEvent.VK_C);
		pasteItem.setMnemonic(KeyEvent.VK_V);
		delItem.setMnemonic(KeyEvent.VK_D);
		selectAllItem.setMnemonic(KeyEvent.VK_A);
		
		OperateActionListener actionListener = new OperateActionListener();
		undoItem.addActionListener(actionListener);
		redoItem.addActionListener(actionListener);
		cutItem.addActionListener(actionListener);
		copyItem.addActionListener(actionListener);
		pasteItem.addActionListener(actionListener);
		delItem.addActionListener(actionListener);
		selectAllItem.addActionListener(actionListener);
		
		textArea.add(popup);
		textArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// 右键单击时弹出菜单
				if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0
						&& e.getClickCount() == 1) {
					int x = e.getX() + 15;
					int y = e.getY() + 65;
					if(e.getX() + popup.getWidth() + 15 > MainFrame.this.getWidth()) {
						x = MainFrame.this.getWidth() - popup.getWidth() - 15;
					}
					if(e.getY() + popup.getHeight() + 65 > MainFrame.this.getHeight()) {
						y = MainFrame.this.getHeight() - popup.getHeight() - 45;
					}
					popup.show(MainFrame.this, x , y);
				}
			}
		});
	}
	
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	
	public String getFileName() {
		return fileName;
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

	public BarStatus getBarStatus() {
		return barStatus;
	}

	public void setBarStatus(BarStatus barStatus) {
		this.barStatus = barStatus;
	}

	public UndoManager getUndoManager() {
		return undoManager;
	}

	public void setUndoManager(UndoManager undoManager) {
		this.undoManager = undoManager;
	}
	
	/**
	 * 显示/隐藏行号
	 * @param v true-显示行号,false-隐藏行号
	 */
	public void setRowHeaderVisible(boolean v){
		scrollPane.setRowHeaderView(v ? lineNumberHeader : null);
	}
	
	/**
	 * 显示/隐藏状态栏
	 * @param v true-显示状态栏,false-隐藏状态栏
	 */
	public void setStatusVisible(boolean v) {
		if (v) {
			initStatus();
		} else {
			remove(getBarStatus());
		}
		validate();
	}

	public boolean openFile(String fileName){
		String content = FileUtil.readFile(fileName);
		if(content!=null){
			this.fileName = fileName;
			textArea.setText(content);
			setTitle("记事本 - " + fileName);
			return true;
		}
		return false;
	}
	
	/**
	 * 保存文件
	 * @author 
	 */
	public boolean saveFile(){
		setTitle("记事本 - " + fileName);
		return FileUtil.saveFile(fileName, textArea.getText());
	}
	
	class OperateActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			if(undoItem.equals(obj)){
				if(undoManager.canUndo()){
					undoManager.undo();
				}
			}else if(redoItem.equals(obj)){
				if(undoManager.canRedo()){
					undoManager.redo();
				}
			}else if(cutItem.equals(obj)){
				textArea.cut();
			}else if(copyItem.equals(obj)){
				textArea.copy();
			}else if(pasteItem.equals(obj)){
				textArea.paste();
			}else if(delItem.equals(obj)){
				textArea.replaceSelection(null);
			}else if(selectAllItem.equals(obj)){
				textArea.selectAll();
			}
		}
	}
}
