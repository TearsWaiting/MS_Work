package 带锁记事本;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import java.io.*;
import javax.swing.undo.*;
import javax.swing.border.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import java.awt.datatransfer.*;

public class Test extends JFrame implements ActionListener, DocumentListener {
	// 声明变量
	// 菜单条
	JMenuBar menuBar;
	// 菜单（用户，文件，编辑，格式，查看，帮助）
	JMenu userMenu, fileMenu, editMenu, formatMenu, viewMenu, helpMenu;
	// “用户”的菜单项
	JMenuItem userMenu_register, userMenu_changePassword;
	// “文件”的菜单项
	JMenuItem fileMenu_New, fileMenu_Open, fileMenu_Save, fileMenu_SaveAs, fileMenu_PageSetUp, fileMenu_Print,
			fileMenu_Exit;
	// “编辑”的菜单项
	JMenuItem editMenu_Undo, editMenu_Cut, editMenu_Copy, editMenu_Paste, editMenu_Delete, editMenu_Find,
			editMenu_FindNext, editMenu_Replace, editMenu_Goto, editMenu_SelectAll, editMenu_TimeDate;
	// “格式”的菜单项
	JCheckBoxMenuItem formatMenu_State, formatMenu_LineWrap;
	JMenuItem formatMenu_Font;
	// “查看”的菜单项
	JCheckBoxMenuItem viewMenu_Status;
	// “帮助”的菜单项
	JMenuItem helpMenu_ViewHelp, helpMenu_AboutNotepad;
	// 文本编辑区域
	JTextArea editArea;
	// 状态栏标签
	JLabel statusBar;
	// 右键出现菜单项
	JPopupMenu popupMenu;
	JMenuItem popupMenu_Undo, popupMenu_Cut, popupMenu_Copy, popupMenu_Paste, popupMenu_Delete, popupMenu_SelectAll;

	// 存放文本副本，用于比较，判断文本是否有改动
	String oldValue;
	// 判断文件是否保存过
	boolean isNewFile = true;
	boolean isSave = false;
	// 当前文件名
	File currentFile;

//系统剪切板
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Clipboard clipBoard = toolkit.getSystemClipboard();
//撤销操作管理器
	UndoManager undomanager = new UndoManager();
	UndoableEditListener undoHandler = new UndoHandler();

	// 构造方法LockedText开始
	public Test() {
		// 调用父类的构造方法，为窗口设标题
		super("带锁记事本");

// 改变系统默认字体
		Font font = new Font("Dialog", Font.PLAIN, 13);

		java.util.Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put(key, font);
			}
		}

		// 创建菜单条
		menuBar = new JMenuBar();

		// 创建用户菜单及菜单项并注册事件监听
		userMenu = new JMenu("用户(U)");
		// 设置快捷键ALT+U
		userMenu.setMnemonic('U');

		userMenu_register = new JMenuItem("注册");
		userMenu_register.addActionListener(this);

		userMenu_changePassword = new JMenuItem("更改密码");
		userMenu_changePassword.addActionListener(this);

		// 创建文件菜单及菜单项并注册事件监听
		fileMenu = new JMenu("文件(F)");
		// 设置快捷键ALT+F
		fileMenu.setMnemonic('F');

		fileMenu_New = new JMenuItem("新建(N)");
		fileMenu_New.addActionListener(this);
		// 设置快捷键Ctrl+N
		fileMenu_New.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));

		fileMenu_Open = new JMenuItem("打开(O)...");
		fileMenu_Open.addActionListener(this);
		// 设置快捷键Ctrl+O
		fileMenu_Open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));

		fileMenu_Save = new JMenuItem("保存(S)");
		fileMenu_Save.addActionListener(this);
		// 设置快捷键Ctrl+S
		fileMenu_Save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));

		fileMenu_SaveAs = new JMenuItem("另存为(A)...");
		fileMenu_SaveAs.addActionListener(this);

		fileMenu_PageSetUp = new JMenuItem("页面设置(U)...");
		fileMenu_PageSetUp.addActionListener(this);

		fileMenu_Print = new JMenuItem("打印(P)...");
		fileMenu_Print.addActionListener(this);
		// 设置快捷键Ctrl+P
		fileMenu_Print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));

		fileMenu_Exit = new JMenuItem("退出(X)");
		fileMenu_Exit.addActionListener(this);

		// 创建编辑菜单及菜单项并注册事件监听
		editMenu = new JMenu("编辑(E)");
		// 设置快捷键ALT+E
		editMenu.setMnemonic('E');

		// 当选择编辑菜单时，设置剪切、复制、粘贴、删除等功能的可用性
		editMenu.addMenuListener(new MenuListener() {
			public void menuCanceled(MenuEvent e)// 取消菜单时调用
			{
				checkMenuItemEnabled();// 设置剪切、复制、粘贴、删除等功能的可用性
			}

			public void menuDeselected(MenuEvent e)// 取消选择某个菜单时调用
			{
				checkMenuItemEnabled();// 设置剪切、复制、粘贴、删除等功能的可用性
			}

			public void menuSelected(MenuEvent e)// 选择某个菜单时调用
			{
				checkMenuItemEnabled();// 设置剪切、复制、粘贴、删除等功能的可用性
			}
		});

		editMenu_Undo = new JMenuItem("撤销(U)");
		editMenu_Undo.addActionListener(this);

		// 设置快捷键Ctrl+Z
		editMenu_Undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
		editMenu_Undo.setEnabled(false);

		editMenu_Cut = new JMenuItem("剪切(T)");
		editMenu_Cut.addActionListener(this);
		// 设置快捷键Ctrl+X
		editMenu_Cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));

		editMenu_Copy = new JMenuItem("复制(C)");
		editMenu_Copy.addActionListener(this);
		// 设置快捷键Ctrl+C
		editMenu_Copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));

		editMenu_Paste = new JMenuItem("粘贴(P)");
		editMenu_Paste.addActionListener(this);
		// 设置快捷键Ctrl+V
		editMenu_Paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));

		editMenu_Delete = new JMenuItem("删除(D)");
		editMenu_Delete.addActionListener(this);
		// 设置快捷键Delete
		editMenu_Delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));

		editMenu_Find = new JMenuItem("查找(F)...");
		editMenu_Find.addActionListener(this);
		// 设置快捷键Ctrl+F
		editMenu_Find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));

		editMenu_FindNext = new JMenuItem("查找下一个(N)");
		editMenu_FindNext.addActionListener(this);
		// 设置快捷键F3
		editMenu_FindNext.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));

		editMenu_Replace = new JMenuItem("替换(R)...", 'R');
		editMenu_Replace.addActionListener(this);
		// 设置快捷键Ctrl+H
		editMenu_Replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));

		editMenu_Goto = new JMenuItem("转到(G)...", 'G');
		editMenu_Goto.addActionListener(this);
		// 设置快捷键Ctrl+G
		editMenu_Goto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));

		editMenu_SelectAll = new JMenuItem("全选", 'A');
		editMenu_SelectAll.addActionListener(this);
		// 设置快捷键Ctrl+A
		editMenu_SelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));

		editMenu_TimeDate = new JMenuItem("时间/日期(D)", 'D');
		editMenu_TimeDate.addActionListener(this);
		// 设置快捷键F5
		editMenu_TimeDate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));

		// 创建格式菜单及菜单项并注册事件监听
		formatMenu = new JMenu("格式(O)");
		// 设置快捷键ALT+O
		formatMenu.setMnemonic('O');

		formatMenu_LineWrap = new JCheckBoxMenuItem("自动换行(W)");
		formatMenu_LineWrap.addActionListener(this);
		// 设置快捷键ALT+W
		formatMenu_LineWrap.setMnemonic('W');
		formatMenu_LineWrap.setState(true);

		formatMenu_Font = new JMenuItem("字体(F)...");
		formatMenu_Font.addActionListener(this);

		// 创建查看菜单及菜单项并注册事件监听
		viewMenu = new JMenu("查看(V)");
		// 设置快捷键ALT+V
		viewMenu.setMnemonic('V');

		viewMenu_Status = new JCheckBoxMenuItem("状态栏(S)");
		viewMenu_Status.addActionListener(this);
		// 设置快捷键ALT+S
		viewMenu_Status.setMnemonic('S');
		viewMenu_Status.setState(true);

		// 创建帮助菜单及菜单项并注册事件监听
		helpMenu = new JMenu("帮助(H)");
		// 设置快捷键ALT+H
		helpMenu.setMnemonic('H');

		helpMenu_ViewHelp = new JMenuItem("查看帮助(H)");
		helpMenu_ViewHelp.addActionListener(this);
		// 设置快捷键F1
		helpMenu_ViewHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));

		helpMenu_AboutNotepad = new JMenuItem("关于记事本(A)");
		helpMenu_AboutNotepad.addActionListener(this);

		// 向窗口添加菜单条
		this.setJMenuBar(menuBar);

		// 向菜单条添加"文件"菜单及菜单项
		menuBar.add(userMenu);
		userMenu.add(userMenu_register);
		userMenu.add(userMenu_changePassword);

		// 向菜单条添加"文件"菜单及菜单项
		menuBar.add(fileMenu);
		fileMenu.add(fileMenu_New);
		fileMenu.add(fileMenu_Open);
		fileMenu.add(fileMenu_Save);
		fileMenu.add(fileMenu_SaveAs);
		fileMenu.addSeparator(); // 分隔线
		fileMenu.add(fileMenu_PageSetUp);
		fileMenu.add(fileMenu_Print);
		fileMenu.addSeparator(); // 分隔线
		fileMenu.add(fileMenu_Exit);

		// 向菜单条添加"编辑"菜单及菜单项
		menuBar.add(editMenu);
		editMenu.add(editMenu_Undo);
		editMenu.addSeparator(); // 分隔线
		editMenu.add(editMenu_Cut);
		editMenu.add(editMenu_Copy);
		editMenu.add(editMenu_Paste);
		editMenu.add(editMenu_Delete);
		editMenu.addSeparator(); // 分隔线
		editMenu.add(editMenu_Find);
		editMenu.add(editMenu_FindNext);
		editMenu.add(editMenu_Replace);
		editMenu.add(editMenu_Goto);
		editMenu.addSeparator(); // 分隔线
		editMenu.add(editMenu_SelectAll);
		editMenu.add(editMenu_TimeDate);

		// 向菜单条添加"格式"菜单及菜单项
		menuBar.add(formatMenu);
		formatMenu.add(formatMenu_LineWrap);
		formatMenu.add(formatMenu_Font);

		// 向菜单条添加"查看"菜单及菜单项
		menuBar.add(viewMenu);
		viewMenu.add(viewMenu_Status);

		// 向菜单条添加"帮助"菜单及菜单项
		menuBar.add(helpMenu);
		helpMenu.add(helpMenu_ViewHelp);
		helpMenu.addSeparator();
		helpMenu.add(helpMenu_AboutNotepad);

		// 创建文本编辑区并添加滚动条
		editArea = new JTextArea();
		this.add(editArea, BorderLayout.CENTER);
		editArea.setWrapStyleWord(true);// 设置单词在一行不足容纳时换行
		editArea.setLineWrap(true);// 设置文本编辑区自动换行默认为true,即会"自动换行"
		oldValue = editArea.getText();// 获取原文本编辑区的内容

		JScrollPane scroller = new JScrollPane(editArea);
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(scroller, BorderLayout.CENTER);

//编辑区注册事件监听(与撤销操作有关)
		editArea.getDocument().addUndoableEditListener(undoHandler);
		editArea.getDocument().addDocumentListener(this);

		// 创建右键弹出菜单
		popupMenu = new JPopupMenu();
		popupMenu_Undo = new JMenuItem("撤销(U)");
		popupMenu_Cut = new JMenuItem("剪切(T)");
		popupMenu_Copy = new JMenuItem("复制(C)");
		popupMenu_Paste = new JMenuItem("粘帖(P)");
		popupMenu_Delete = new JMenuItem("删除(D)");
		popupMenu_SelectAll = new JMenuItem("全选(A)");

		popupMenu_Undo.setEnabled(false);

		// 向右键菜单添加菜单项和分隔符
		popupMenu.add(popupMenu_Undo);
		popupMenu.addSeparator();
		popupMenu.add(popupMenu_Cut);
		popupMenu.add(popupMenu_Copy);
		popupMenu.add(popupMenu_Paste);
		popupMenu.add(popupMenu_Delete);
		popupMenu.addSeparator();
		popupMenu.add(popupMenu_SelectAll);

		// 文本编辑区注册右键菜单事件
		popupMenu_Undo.addActionListener(this);
		popupMenu_Cut.addActionListener(this);
		popupMenu_Copy.addActionListener(this);
		popupMenu_Paste.addActionListener(this);
		popupMenu_Delete.addActionListener(this);
		popupMenu_SelectAll.addActionListener(this);

		// 文本编辑区注册右键菜单事件
		editArea.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger())// 返回此鼠标事件是否为该平台的弹出菜单触发事件
				{
					popupMenu.show(e.getComponent(), e.getX(), e.getY());// 在组件调用者的坐标空间中的位置 X、Y 显示弹出菜单
				}
				checkMenuItemEnabled();// 设置剪切，复制，粘帖，删除等功能的可用性
				editArea.requestFocus();// 编辑区获取焦点
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger())// 返回此鼠标事件是否为该平台的弹出菜单触发事件
				{
					popupMenu.show(e.getComponent(), e.getX(), e.getY());// 在组件调用者的坐标空间中的位置 X、Y 显示弹出菜单
				}
				checkMenuItemEnabled();// 设置剪切，复制，粘帖，删除等功能的可用性
				editArea.requestFocus();// 编辑区获取焦点
			}
		});// 文本编辑区注册右键菜单事件结束

		// 创建和添加状态栏
		statusBar = new JLabel("第X行，第Y列");
		// 向窗口添加状态栏标签
		this.add(statusBar, BorderLayout.SOUTH);

		// 设置窗口在屏幕上的位置、大小和可见性
		this.setLocation(400, 125);
		this.setSize(650, 550);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//添加窗口监听器
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) { // exitWindowChoose();
			}
		});
		checkMenuItemEnabled();
		editArea.requestFocus();
	}

	// 构造方法LockedText结束

	// 设置菜单项的可用性：剪切，复制，粘帖，删除功能
	public void checkMenuItemEnabled() {
		String selectText = editArea.getSelectedText();
		if (selectText == null) {
			editMenu_Cut.setEnabled(false);
			popupMenu_Cut.setEnabled(false);
			editMenu_Copy.setEnabled(false);
			popupMenu_Copy.setEnabled(false);
			editMenu_Delete.setEnabled(false);
			popupMenu_Delete.setEnabled(false);
		} else {
			editMenu_Cut.setEnabled(true);
			popupMenu_Cut.setEnabled(true);
			editMenu_Copy.setEnabled(true);
			popupMenu_Copy.setEnabled(true);
			editMenu_Delete.setEnabled(true);
			popupMenu_Delete.setEnabled(true);
		}
		// 粘帖功能可用性判断
		Transferable contents = clipBoard.getContents(this);
		if (contents == null) {
			editMenu_Paste.setEnabled(false);
			popupMenu_Paste.setEnabled(false);
		} else {
			editMenu_Paste.setEnabled(true);
			popupMenu_Paste.setEnabled(true);
		}
	}
	// 方法checkMenuItemEnabled()结束

	// 事件响应(实现接口ActionListener的方法（只有一个）)
	public void actionPerformed(ActionEvent e) {
		// 新建
		if (e.getSource() == fileMenu_New) {
			editArea.requestFocus();
			String currentValue = editArea.getText();
			boolean isTextChange = (currentValue.equals(oldValue)) ? false : true;
			if(isTextChange)
			{
				isSave = false;
			}
			else
			{
				isSave = true;
			}
			if (isSave) {
				New();
			} else {
				int saveChoose = JOptionPane.showConfirmDialog(this, "文件尚未保存，是否保存？", "提醒",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if (saveChoose == JOptionPane.YES_OPTION) {
					if(isNewFile)
					{
						saveAs();
						New();					}
					else
					{
						save();
						New();	
					}
				} else if (saveChoose == JOptionPane.NO_OPTION) {
					New();
				} else {
					statusBar.setText("打开文件失败");
					return;
				}
			}
		}
		//新建结束

		// 打开
		if (e.getSource() == fileMenu_Open) {
			editArea.requestFocus();
			String currentValue = editArea.getText();
			boolean isTextChange = (currentValue.equals(oldValue)) ? false : true;
			if(isTextChange)
			{
				isSave = false;
			}
			else
			{
				isSave = true;
			}
			if (isSave) {
				open();
			} else {
				int saveChoose = JOptionPane.showConfirmDialog(this, "文件尚未保存，是否保存？", "提醒",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if (saveChoose == JOptionPane.YES_OPTION) {
					saveAs();
					open();
				} else if (saveChoose == JOptionPane.NO_OPTION) {
					open();
				} else {
					return;
				}
			}
		}
		// 打开结束

		// 保存
		if (e.getSource() == fileMenu_Save) {
			editArea.requestFocus();
			if (isNewFile) {
				saveAs();
			} else {
				save();
			}
		}
		// 保存结束

		// 另存为
		if (e.getSource() == fileMenu_SaveAs) {
			editArea.requestFocus();
			saveAs();
		}
		// 另存为结束

	}

	// 实现DocumentListener接口中的方法（一共三个）(与撤销操作有关)
	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		editMenu_Undo.setEnabled(true);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		editMenu_Undo.setEnabled(true);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		editMenu_Undo.setEnabled(true);
	}

	// 实现接口UndoableEditListener的类UndoHandler(与撤销操作有关)
	class UndoHandler implements UndoableEditListener {
		@Override
		public void undoableEditHappened(UndoableEditEvent uee) {
			// TODO Auto-generated method stub
			undomanager.addEdit(uee.getEdit());
		}
	}

	// 功能实现方法
	public void New() {
		editArea.replaceRange("", 0, editArea.getText().length());
		statusBar.setText("此为新建文件");
		this.setTitle("无标题 - 记事本");
		isNewFile = true;
		isNewFile = false;
		undomanager.discardAllEdits();
		editMenu_Undo.setEnabled(false);
		oldValue = editArea.getText();
	}
	
	public void save() {
		FileWriter fw = null;
		BufferedWriter bfw = null;
		try {
			fw = new FileWriter(currentFile);
			bfw = new BufferedWriter(fw);
			bfw.write(editArea.getText(), 0, editArea.getText().length());
			isSave = true;
			statusBar.setText("保存成功"+" 当前文件路径：" + currentFile.getAbsoluteFile());
		} catch (IOException ioException) {
			ioException.getStackTrace();
		} finally {
			try {
				if (fw != null) {
					fw.close();
				}
			} catch (IOException ioException) {
				ioException.getStackTrace();
			}
			try {
				if (bfw != null) {
					bfw.close();
				}
			} catch (IOException ioException) {
				ioException.getStackTrace();
			}
		}
	}

	public void saveAs() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setDialogTitle("另存为");
		int result = fileChooser.showSaveDialog(this);
		File saveAsFileName = fileChooser.getSelectedFile();
		if (result == JFileChooser.CANCEL_OPTION) {
			statusBar.setText("您没有选择任何文件，另存为失败");
			return;
		}
		if (saveAsFileName == null || saveAsFileName.getName().equals("")) {
			JOptionPane.showMessageDialog(this, "不合法的文件名", "警告", JOptionPane.ERROR_MESSAGE);
			statusBar.setText("不合法的文件名，另存为失败");
		} else {
			FileWriter fw = null;
			BufferedWriter bfw = null;
			try {
				fw = new FileWriter(saveAsFileName);
				bfw = new BufferedWriter(fw);
				bfw.write(editArea.getText(), 0, editArea.getText().length());
				isNewFile = false;
				isSave = true;
				currentFile = saveAsFileName;
				oldValue = editArea.getText();
				this.setTitle(saveAsFileName.getName() + " - 记事本");
				statusBar.setText("另存为成功"+" 当前文件路径：" + saveAsFileName.getAbsoluteFile());
			} catch (IOException ioexception) {
				ioexception.getStackTrace();
			} finally {
				try {
					if (fw != null) {
						fw.close();
					}
				} catch (IOException ioException) {
					ioException.getStackTrace();
				}
				try {
					if (bfw != null) {
						bfw.close();
					}
				} catch (IOException ioexception) {
					ioexception.getStackTrace();
				}
			}
		}
	}

	public void open() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setDialogTitle("打开文件");
		int result = fileChooser.showOpenDialog(this);
		File fileName = fileChooser.getSelectedFile();
		if (result == JFileChooser.CANCEL_OPTION) {
			statusBar.setText("您没有选择任何文件，打开失败");
			return;
		}
		if (fileName == null || fileName.getName().equals("")) {
			JOptionPane.showMessageDialog(this, "不合法的文件名", "警告", JOptionPane.ERROR_MESSAGE);
			statusBar.setText("不合法的文件名，打开失败 ");
		} else {
			FileReader fr = null;
			BufferedReader bfr = null;
			try {
				fr = new FileReader(fileName);
				bfr = new BufferedReader(fr);
				editArea.setText("");
				String str = bfr.readLine();
				while (str != null) {
					editArea.append(str);
				}
				isNewFile = false;
				isSave = false;
				currentFile = fileName;
				oldValue = editArea.getText();
				this.setTitle(fileName.getName() + " - 记事本");
				statusBar.setText("打开成功"+"当前打开文件：" + fileName.getAbsoluteFile());
			} catch (IOException ioException) {
				ioException.getStackTrace();
			} finally {
				try {
					if (fr != null) {
						fr.close();
					}
				} catch (IOException ioexception) {
					ioexception.getStackTrace();
				}
				try {
					if (bfr != null) {
						bfr.close();
					}
				} catch (IOException ioexception) {
					ioexception.getStackTrace();
				}
			}
		}
	}

	
	// 主方法
	public static void main(String args[]) {
		new LockedText();
	}
}

//整体思路----------------------------------------------------------------------

//在LocketText主类下
//LocketText extends JFrame implements ActionListener,DocumentListener
//
//声明变量：
//菜单条
//菜单
//菜单组件
//文本编辑区域
//状态栏标签
//右击菜单栏
//其他功能变量
//
//构造方法：
//
//设置菜单项的可用性：剪切，复制，粘帖，删除功能
//
//关闭窗口时提示
//
//查找方法
//
//替换方法
//
//"字体"方法
//
//事件响应
//
//实现DocumentListener接口中的方法(与撤销操作有关)
//
//实现接口UndoableEditListener的类UndoHandler(与撤销操作有关)
//
//main函数开始

//具体细节-------------------------------------------------------------------
// 关闭窗口时调用

// 关闭窗口时调用方法结束

// 查找方法

/*
 * ButtonGroup此类用于为一组按钮创建一个多斥（multiple-exclusion）作用域。 使用相同的 ButtonGroup
 * 对象创建一组按钮意味着“开启”其中一个按钮时，将关闭组中的其他所有按钮。
 */
/*
 * JRadioButton此类实现一个单选按钮，此按钮项可被选择或取消选择，并可为用户显示其状态。 与 ButtonGroup
 * 对象配合使用可创建一组按钮，一次只能选择其中的一个按钮。 （创建一个 ButtonGroup 对象并用其 add 方法将 JRadioButton
 * 对象包含在此组中。）
 */

// 取消按钮事件处理

// "查找下一个"按钮监听

// "区分大小写(C)"的JCheckBox是否被选中

// 不区分大小写,此时把所选内容全部化成大写(或小写)，以便于查找

// k=strA.lastIndexOf(strB,editArea.getCaretPosition()-1);

// String strData=strA.subString(k,strB.getText().length()+1);

// "查找下一个"按钮监听结束

// 创建"查找"对话框的界面

// 设置directionPanel组件的边框;

// BorderFactory.createTitledBorder(String title)创建一个新标题边框，
// 使用默认边框（浮雕化）、默认文本位置（位于顶线上）、默认调整 (leading) 以及由当前外观确定的默认字体和文本颜色，并指定了标题文本。

// 查找方法结束

// 替换方法

/*
 * ButtonGroup此类用于为一组按钮创建一个多斥（multiple-exclusion）作用域。 使用相同的 ButtonGroup
 * 对象创建一组按钮意味着“开启”其中一个按钮时，将关闭组中的其他所有按钮。
 */
/*
 * JRadioButton此类实现一个单选按钮，此按钮项可被选择或取消选择，并可为用户显示其状态。 与 ButtonGroup
 * 对象配合使用可创建一组按钮，一次只能选择其中的一个按钮。 （创建一个 ButtonGroup 对象并用其 add 方法将 JRadioButton
 * 对象包含在此组中。）
 */

// "查找下一个"按钮监听

// "查找下一个"按钮监听结束

// "替换"按钮监听

// "全部替换"按钮监听

// "替换全部"方法结束

// 创建"替换"对话框的界面

// 设置directionPanel组件的边框;

// BorderFactory.createTitledBorder(String title)创建一个新标题边框，
// 使用默认边框（浮雕化）、默认文本位置（位于顶线上）、默认调整 (leading) 以及由当前外观确定的默认字体和文本颜色，并指定了标题文本。

// "全部替换"按钮监听结束

// "字体"方法

// 事件响应

// 打开

// 打开结束

// 保存

// 保存结束

// 另存为

// 另存为结束

// 页面设置

// 页面设置结束

// 打印

// 打印结束

// 退出

// 退出结束
// 编辑
// else if(e.getSource()==editMenu)
// { checkMenuItemEnabled();//设置剪切、复制、粘贴、删除等功能的可用性
// }
// 编辑结束
// 撤销

// 撤销结束

// 剪切

// 剪切结束

// 复制

// 复制结束

// 粘帖

// 粘帖结束

// 删除

// 删除结束

// 查找

// 查找结束

// 查找下一个

// 查找下一个结束

// 替换

// 替换结束

// 转到

// 转到结束

// 时间日期

// 时间日期结束

// 全选

// 全选结束

// 自动换行(已在前面设置)

// 自动换行结束

// 字体设置

// 字体设置结束

// 设置状态栏可见性

// 设置状态栏可见性结束

// 帮助主题

// 帮助主题结束

// 关于

// 关于结束

//问题-------------------------------------------------------------------------
//为什么把变量声明放到一个方法中会出错
//接口，抽象类理解
//
//
//
//
//要学会的新知识
//JMenu，JPopupMenu,JMenuItem,JCheckBoxMenuItem,JTextArea,Toolkit，ClipBoard
//File，判断文档是否保存的方法
//声明JMenuItem时，命名方式有讲究
//剪切板设计
//改变系统默认字体
//java.util.Enumeration
//创建菜单条
//创建文件菜单及菜单项并注册事件监听
//设置快捷键
//setAccelerator
//实现DocumentListener接口中的方法(与撤销操作有关)
//实现接口UndoableEditListener的类UndoHandler(与撤销操作有关)
