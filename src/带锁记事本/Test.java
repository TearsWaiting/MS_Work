package �������±�;

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
	// ��������
	// �˵���
	JMenuBar menuBar;
	// �˵����û����ļ����༭����ʽ���鿴��������
	JMenu userMenu, fileMenu, editMenu, formatMenu, viewMenu, helpMenu;
	// ���û����Ĳ˵���
	JMenuItem userMenu_register, userMenu_changePassword;
	// ���ļ����Ĳ˵���
	JMenuItem fileMenu_New, fileMenu_Open, fileMenu_Save, fileMenu_SaveAs, fileMenu_PageSetUp, fileMenu_Print,
			fileMenu_Exit;
	// ���༭���Ĳ˵���
	JMenuItem editMenu_Undo, editMenu_Cut, editMenu_Copy, editMenu_Paste, editMenu_Delete, editMenu_Find,
			editMenu_FindNext, editMenu_Replace, editMenu_Goto, editMenu_SelectAll, editMenu_TimeDate;
	// ����ʽ���Ĳ˵���
	JCheckBoxMenuItem formatMenu_State, formatMenu_LineWrap;
	JMenuItem formatMenu_Font;
	// ���鿴���Ĳ˵���
	JCheckBoxMenuItem viewMenu_Status;
	// ���������Ĳ˵���
	JMenuItem helpMenu_ViewHelp, helpMenu_AboutNotepad;
	// �ı��༭����
	JTextArea editArea;
	// ״̬����ǩ
	JLabel statusBar;
	// �Ҽ����ֲ˵���
	JPopupMenu popupMenu;
	JMenuItem popupMenu_Undo, popupMenu_Cut, popupMenu_Copy, popupMenu_Paste, popupMenu_Delete, popupMenu_SelectAll;

	// ����ı����������ڱȽϣ��ж��ı��Ƿ��иĶ�
	String oldValue;
	// �ж��ļ��Ƿ񱣴��
	boolean isNewFile = true;
	boolean isSave = false;
	// ��ǰ�ļ���
	File currentFile;

//ϵͳ���а�
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Clipboard clipBoard = toolkit.getSystemClipboard();
//��������������
	UndoManager undomanager = new UndoManager();
	UndoableEditListener undoHandler = new UndoHandler();

	// ���췽��LockedText��ʼ
	public Test() {
		// ���ø���Ĺ��췽����Ϊ���������
		super("�������±�");

// �ı�ϵͳĬ������
		Font font = new Font("Dialog", Font.PLAIN, 13);

		java.util.Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put(key, font);
			}
		}

		// �����˵���
		menuBar = new JMenuBar();

		// �����û��˵����˵��ע���¼�����
		userMenu = new JMenu("�û�(U)");
		// ���ÿ�ݼ�ALT+U
		userMenu.setMnemonic('U');

		userMenu_register = new JMenuItem("ע��");
		userMenu_register.addActionListener(this);

		userMenu_changePassword = new JMenuItem("��������");
		userMenu_changePassword.addActionListener(this);

		// �����ļ��˵����˵��ע���¼�����
		fileMenu = new JMenu("�ļ�(F)");
		// ���ÿ�ݼ�ALT+F
		fileMenu.setMnemonic('F');

		fileMenu_New = new JMenuItem("�½�(N)");
		fileMenu_New.addActionListener(this);
		// ���ÿ�ݼ�Ctrl+N
		fileMenu_New.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));

		fileMenu_Open = new JMenuItem("��(O)...");
		fileMenu_Open.addActionListener(this);
		// ���ÿ�ݼ�Ctrl+O
		fileMenu_Open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));

		fileMenu_Save = new JMenuItem("����(S)");
		fileMenu_Save.addActionListener(this);
		// ���ÿ�ݼ�Ctrl+S
		fileMenu_Save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));

		fileMenu_SaveAs = new JMenuItem("���Ϊ(A)...");
		fileMenu_SaveAs.addActionListener(this);

		fileMenu_PageSetUp = new JMenuItem("ҳ������(U)...");
		fileMenu_PageSetUp.addActionListener(this);

		fileMenu_Print = new JMenuItem("��ӡ(P)...");
		fileMenu_Print.addActionListener(this);
		// ���ÿ�ݼ�Ctrl+P
		fileMenu_Print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));

		fileMenu_Exit = new JMenuItem("�˳�(X)");
		fileMenu_Exit.addActionListener(this);

		// �����༭�˵����˵��ע���¼�����
		editMenu = new JMenu("�༭(E)");
		// ���ÿ�ݼ�ALT+E
		editMenu.setMnemonic('E');

		// ��ѡ��༭�˵�ʱ�����ü��С����ơ�ճ����ɾ���ȹ��ܵĿ�����
		editMenu.addMenuListener(new MenuListener() {
			public void menuCanceled(MenuEvent e)// ȡ���˵�ʱ����
			{
				checkMenuItemEnabled();// ���ü��С����ơ�ճ����ɾ���ȹ��ܵĿ�����
			}

			public void menuDeselected(MenuEvent e)// ȡ��ѡ��ĳ���˵�ʱ����
			{
				checkMenuItemEnabled();// ���ü��С����ơ�ճ����ɾ���ȹ��ܵĿ�����
			}

			public void menuSelected(MenuEvent e)// ѡ��ĳ���˵�ʱ����
			{
				checkMenuItemEnabled();// ���ü��С����ơ�ճ����ɾ���ȹ��ܵĿ�����
			}
		});

		editMenu_Undo = new JMenuItem("����(U)");
		editMenu_Undo.addActionListener(this);

		// ���ÿ�ݼ�Ctrl+Z
		editMenu_Undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
		editMenu_Undo.setEnabled(false);

		editMenu_Cut = new JMenuItem("����(T)");
		editMenu_Cut.addActionListener(this);
		// ���ÿ�ݼ�Ctrl+X
		editMenu_Cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));

		editMenu_Copy = new JMenuItem("����(C)");
		editMenu_Copy.addActionListener(this);
		// ���ÿ�ݼ�Ctrl+C
		editMenu_Copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));

		editMenu_Paste = new JMenuItem("ճ��(P)");
		editMenu_Paste.addActionListener(this);
		// ���ÿ�ݼ�Ctrl+V
		editMenu_Paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));

		editMenu_Delete = new JMenuItem("ɾ��(D)");
		editMenu_Delete.addActionListener(this);
		// ���ÿ�ݼ�Delete
		editMenu_Delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));

		editMenu_Find = new JMenuItem("����(F)...");
		editMenu_Find.addActionListener(this);
		// ���ÿ�ݼ�Ctrl+F
		editMenu_Find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));

		editMenu_FindNext = new JMenuItem("������һ��(N)");
		editMenu_FindNext.addActionListener(this);
		// ���ÿ�ݼ�F3
		editMenu_FindNext.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));

		editMenu_Replace = new JMenuItem("�滻(R)...", 'R');
		editMenu_Replace.addActionListener(this);
		// ���ÿ�ݼ�Ctrl+H
		editMenu_Replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));

		editMenu_Goto = new JMenuItem("ת��(G)...", 'G');
		editMenu_Goto.addActionListener(this);
		// ���ÿ�ݼ�Ctrl+G
		editMenu_Goto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));

		editMenu_SelectAll = new JMenuItem("ȫѡ", 'A');
		editMenu_SelectAll.addActionListener(this);
		// ���ÿ�ݼ�Ctrl+A
		editMenu_SelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));

		editMenu_TimeDate = new JMenuItem("ʱ��/����(D)", 'D');
		editMenu_TimeDate.addActionListener(this);
		// ���ÿ�ݼ�F5
		editMenu_TimeDate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));

		// ������ʽ�˵����˵��ע���¼�����
		formatMenu = new JMenu("��ʽ(O)");
		// ���ÿ�ݼ�ALT+O
		formatMenu.setMnemonic('O');

		formatMenu_LineWrap = new JCheckBoxMenuItem("�Զ�����(W)");
		formatMenu_LineWrap.addActionListener(this);
		// ���ÿ�ݼ�ALT+W
		formatMenu_LineWrap.setMnemonic('W');
		formatMenu_LineWrap.setState(true);

		formatMenu_Font = new JMenuItem("����(F)...");
		formatMenu_Font.addActionListener(this);

		// �����鿴�˵����˵��ע���¼�����
		viewMenu = new JMenu("�鿴(V)");
		// ���ÿ�ݼ�ALT+V
		viewMenu.setMnemonic('V');

		viewMenu_Status = new JCheckBoxMenuItem("״̬��(S)");
		viewMenu_Status.addActionListener(this);
		// ���ÿ�ݼ�ALT+S
		viewMenu_Status.setMnemonic('S');
		viewMenu_Status.setState(true);

		// ���������˵����˵��ע���¼�����
		helpMenu = new JMenu("����(H)");
		// ���ÿ�ݼ�ALT+H
		helpMenu.setMnemonic('H');

		helpMenu_ViewHelp = new JMenuItem("�鿴����(H)");
		helpMenu_ViewHelp.addActionListener(this);
		// ���ÿ�ݼ�F1
		helpMenu_ViewHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));

		helpMenu_AboutNotepad = new JMenuItem("���ڼ��±�(A)");
		helpMenu_AboutNotepad.addActionListener(this);

		// �򴰿���Ӳ˵���
		this.setJMenuBar(menuBar);

		// ��˵������"�ļ�"�˵����˵���
		menuBar.add(userMenu);
		userMenu.add(userMenu_register);
		userMenu.add(userMenu_changePassword);

		// ��˵������"�ļ�"�˵����˵���
		menuBar.add(fileMenu);
		fileMenu.add(fileMenu_New);
		fileMenu.add(fileMenu_Open);
		fileMenu.add(fileMenu_Save);
		fileMenu.add(fileMenu_SaveAs);
		fileMenu.addSeparator(); // �ָ���
		fileMenu.add(fileMenu_PageSetUp);
		fileMenu.add(fileMenu_Print);
		fileMenu.addSeparator(); // �ָ���
		fileMenu.add(fileMenu_Exit);

		// ��˵������"�༭"�˵����˵���
		menuBar.add(editMenu);
		editMenu.add(editMenu_Undo);
		editMenu.addSeparator(); // �ָ���
		editMenu.add(editMenu_Cut);
		editMenu.add(editMenu_Copy);
		editMenu.add(editMenu_Paste);
		editMenu.add(editMenu_Delete);
		editMenu.addSeparator(); // �ָ���
		editMenu.add(editMenu_Find);
		editMenu.add(editMenu_FindNext);
		editMenu.add(editMenu_Replace);
		editMenu.add(editMenu_Goto);
		editMenu.addSeparator(); // �ָ���
		editMenu.add(editMenu_SelectAll);
		editMenu.add(editMenu_TimeDate);

		// ��˵������"��ʽ"�˵����˵���
		menuBar.add(formatMenu);
		formatMenu.add(formatMenu_LineWrap);
		formatMenu.add(formatMenu_Font);

		// ��˵������"�鿴"�˵����˵���
		menuBar.add(viewMenu);
		viewMenu.add(viewMenu_Status);

		// ��˵������"����"�˵����˵���
		menuBar.add(helpMenu);
		helpMenu.add(helpMenu_ViewHelp);
		helpMenu.addSeparator();
		helpMenu.add(helpMenu_AboutNotepad);

		// �����ı��༭������ӹ�����
		editArea = new JTextArea();
		this.add(editArea, BorderLayout.CENTER);
		editArea.setWrapStyleWord(true);// ���õ�����һ�в�������ʱ����
		editArea.setLineWrap(true);// �����ı��༭���Զ�����Ĭ��Ϊtrue,����"�Զ�����"
		oldValue = editArea.getText();// ��ȡԭ�ı��༭��������

		JScrollPane scroller = new JScrollPane(editArea);
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(scroller, BorderLayout.CENTER);

//�༭��ע���¼�����(�볷�������й�)
		editArea.getDocument().addUndoableEditListener(undoHandler);
		editArea.getDocument().addDocumentListener(this);

		// �����Ҽ������˵�
		popupMenu = new JPopupMenu();
		popupMenu_Undo = new JMenuItem("����(U)");
		popupMenu_Cut = new JMenuItem("����(T)");
		popupMenu_Copy = new JMenuItem("����(C)");
		popupMenu_Paste = new JMenuItem("ճ��(P)");
		popupMenu_Delete = new JMenuItem("ɾ��(D)");
		popupMenu_SelectAll = new JMenuItem("ȫѡ(A)");

		popupMenu_Undo.setEnabled(false);

		// ���Ҽ��˵���Ӳ˵���ͷָ���
		popupMenu.add(popupMenu_Undo);
		popupMenu.addSeparator();
		popupMenu.add(popupMenu_Cut);
		popupMenu.add(popupMenu_Copy);
		popupMenu.add(popupMenu_Paste);
		popupMenu.add(popupMenu_Delete);
		popupMenu.addSeparator();
		popupMenu.add(popupMenu_SelectAll);

		// �ı��༭��ע���Ҽ��˵��¼�
		popupMenu_Undo.addActionListener(this);
		popupMenu_Cut.addActionListener(this);
		popupMenu_Copy.addActionListener(this);
		popupMenu_Paste.addActionListener(this);
		popupMenu_Delete.addActionListener(this);
		popupMenu_SelectAll.addActionListener(this);

		// �ı��༭��ע���Ҽ��˵��¼�
		editArea.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger())// ���ش�����¼��Ƿ�Ϊ��ƽ̨�ĵ����˵������¼�
				{
					popupMenu.show(e.getComponent(), e.getX(), e.getY());// ����������ߵ�����ռ��е�λ�� X��Y ��ʾ�����˵�
				}
				checkMenuItemEnabled();// ���ü��У����ƣ�ճ����ɾ���ȹ��ܵĿ�����
				editArea.requestFocus();// �༭����ȡ����
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger())// ���ش�����¼��Ƿ�Ϊ��ƽ̨�ĵ����˵������¼�
				{
					popupMenu.show(e.getComponent(), e.getX(), e.getY());// ����������ߵ�����ռ��е�λ�� X��Y ��ʾ�����˵�
				}
				checkMenuItemEnabled();// ���ü��У����ƣ�ճ����ɾ���ȹ��ܵĿ�����
				editArea.requestFocus();// �༭����ȡ����
			}
		});// �ı��༭��ע���Ҽ��˵��¼�����

		// ���������״̬��
		statusBar = new JLabel("��X�У���Y��");
		// �򴰿����״̬����ǩ
		this.add(statusBar, BorderLayout.SOUTH);

		// ���ô�������Ļ�ϵ�λ�á���С�Ϳɼ���
		this.setLocation(400, 125);
		this.setSize(650, 550);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//��Ӵ��ڼ�����
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) { // exitWindowChoose();
			}
		});
		checkMenuItemEnabled();
		editArea.requestFocus();
	}

	// ���췽��LockedText����

	// ���ò˵���Ŀ����ԣ����У����ƣ�ճ����ɾ������
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
		// ճ�����ܿ������ж�
		Transferable contents = clipBoard.getContents(this);
		if (contents == null) {
			editMenu_Paste.setEnabled(false);
			popupMenu_Paste.setEnabled(false);
		} else {
			editMenu_Paste.setEnabled(true);
			popupMenu_Paste.setEnabled(true);
		}
	}
	// ����checkMenuItemEnabled()����

	// �¼���Ӧ(ʵ�ֽӿ�ActionListener�ķ�����ֻ��һ����)
	public void actionPerformed(ActionEvent e) {
		// �½�
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
				int saveChoose = JOptionPane.showConfirmDialog(this, "�ļ���δ���棬�Ƿ񱣴棿", "����",
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
					statusBar.setText("���ļ�ʧ��");
					return;
				}
			}
		}
		//�½�����

		// ��
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
				int saveChoose = JOptionPane.showConfirmDialog(this, "�ļ���δ���棬�Ƿ񱣴棿", "����",
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
		// �򿪽���

		// ����
		if (e.getSource() == fileMenu_Save) {
			editArea.requestFocus();
			if (isNewFile) {
				saveAs();
			} else {
				save();
			}
		}
		// �������

		// ���Ϊ
		if (e.getSource() == fileMenu_SaveAs) {
			editArea.requestFocus();
			saveAs();
		}
		// ���Ϊ����

	}

	// ʵ��DocumentListener�ӿ��еķ�����һ��������(�볷�������й�)
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

	// ʵ�ֽӿ�UndoableEditListener����UndoHandler(�볷�������й�)
	class UndoHandler implements UndoableEditListener {
		@Override
		public void undoableEditHappened(UndoableEditEvent uee) {
			// TODO Auto-generated method stub
			undomanager.addEdit(uee.getEdit());
		}
	}

	// ����ʵ�ַ���
	public void New() {
		editArea.replaceRange("", 0, editArea.getText().length());
		statusBar.setText("��Ϊ�½��ļ�");
		this.setTitle("�ޱ��� - ���±�");
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
			statusBar.setText("����ɹ�"+" ��ǰ�ļ�·����" + currentFile.getAbsoluteFile());
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
		fileChooser.setDialogTitle("���Ϊ");
		int result = fileChooser.showSaveDialog(this);
		File saveAsFileName = fileChooser.getSelectedFile();
		if (result == JFileChooser.CANCEL_OPTION) {
			statusBar.setText("��û��ѡ���κ��ļ������Ϊʧ��");
			return;
		}
		if (saveAsFileName == null || saveAsFileName.getName().equals("")) {
			JOptionPane.showMessageDialog(this, "���Ϸ����ļ���", "����", JOptionPane.ERROR_MESSAGE);
			statusBar.setText("���Ϸ����ļ��������Ϊʧ��");
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
				this.setTitle(saveAsFileName.getName() + " - ���±�");
				statusBar.setText("���Ϊ�ɹ�"+" ��ǰ�ļ�·����" + saveAsFileName.getAbsoluteFile());
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
		fileChooser.setDialogTitle("���ļ�");
		int result = fileChooser.showOpenDialog(this);
		File fileName = fileChooser.getSelectedFile();
		if (result == JFileChooser.CANCEL_OPTION) {
			statusBar.setText("��û��ѡ���κ��ļ�����ʧ��");
			return;
		}
		if (fileName == null || fileName.getName().equals("")) {
			JOptionPane.showMessageDialog(this, "���Ϸ����ļ���", "����", JOptionPane.ERROR_MESSAGE);
			statusBar.setText("���Ϸ����ļ�������ʧ�� ");
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
				this.setTitle(fileName.getName() + " - ���±�");
				statusBar.setText("�򿪳ɹ�"+"��ǰ���ļ���" + fileName.getAbsoluteFile());
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

	
	// ������
	public static void main(String args[]) {
		new LockedText();
	}
}

//����˼·----------------------------------------------------------------------

//��LocketText������
//LocketText extends JFrame implements ActionListener,DocumentListener
//
//����������
//�˵���
//�˵�
//�˵����
//�ı��༭����
//״̬����ǩ
//�һ��˵���
//�������ܱ���
//
//���췽����
//
//���ò˵���Ŀ����ԣ����У����ƣ�ճ����ɾ������
//
//�رմ���ʱ��ʾ
//
//���ҷ���
//
//�滻����
//
//"����"����
//
//�¼���Ӧ
//
//ʵ��DocumentListener�ӿ��еķ���(�볷�������й�)
//
//ʵ�ֽӿ�UndoableEditListener����UndoHandler(�볷�������й�)
//
//main������ʼ

//����ϸ��-------------------------------------------------------------------
// �رմ���ʱ����

// �رմ���ʱ���÷�������

// ���ҷ���

/*
 * ButtonGroup��������Ϊһ�鰴ť����һ����⣨multiple-exclusion�������� ʹ����ͬ�� ButtonGroup
 * ���󴴽�һ�鰴ť��ζ�š�����������һ����ťʱ�����ر����е��������а�ť��
 */
/*
 * JRadioButton����ʵ��һ����ѡ��ť���˰�ť��ɱ�ѡ���ȡ��ѡ�񣬲���Ϊ�û���ʾ��״̬�� �� ButtonGroup
 * �������ʹ�ÿɴ���һ�鰴ť��һ��ֻ��ѡ�����е�һ����ť�� ������һ�� ButtonGroup �������� add ������ JRadioButton
 * ��������ڴ����С���
 */

// ȡ����ť�¼�����

// "������һ��"��ť����

// "���ִ�Сд(C)"��JCheckBox�Ƿ�ѡ��

// �����ִ�Сд,��ʱ����ѡ����ȫ�����ɴ�д(��Сд)���Ա��ڲ���

// k=strA.lastIndexOf(strB,editArea.getCaretPosition()-1);

// String strData=strA.subString(k,strB.getText().length()+1);

// "������һ��"��ť��������

// ����"����"�Ի���Ľ���

// ����directionPanel����ı߿�;

// BorderFactory.createTitledBorder(String title)����һ���±���߿�
// ʹ��Ĭ�ϱ߿򣨸��񻯣���Ĭ���ı�λ�ã�λ�ڶ����ϣ���Ĭ�ϵ��� (leading) �Լ��ɵ�ǰ���ȷ����Ĭ��������ı���ɫ����ָ���˱����ı���

// ���ҷ�������

// �滻����

/*
 * ButtonGroup��������Ϊһ�鰴ť����һ����⣨multiple-exclusion�������� ʹ����ͬ�� ButtonGroup
 * ���󴴽�һ�鰴ť��ζ�š�����������һ����ťʱ�����ر����е��������а�ť��
 */
/*
 * JRadioButton����ʵ��һ����ѡ��ť���˰�ť��ɱ�ѡ���ȡ��ѡ�񣬲���Ϊ�û���ʾ��״̬�� �� ButtonGroup
 * �������ʹ�ÿɴ���һ�鰴ť��һ��ֻ��ѡ�����е�һ����ť�� ������һ�� ButtonGroup �������� add ������ JRadioButton
 * ��������ڴ����С���
 */

// "������һ��"��ť����

// "������һ��"��ť��������

// "�滻"��ť����

// "ȫ���滻"��ť����

// "�滻ȫ��"��������

// ����"�滻"�Ի���Ľ���

// ����directionPanel����ı߿�;

// BorderFactory.createTitledBorder(String title)����һ���±���߿�
// ʹ��Ĭ�ϱ߿򣨸��񻯣���Ĭ���ı�λ�ã�λ�ڶ����ϣ���Ĭ�ϵ��� (leading) �Լ��ɵ�ǰ���ȷ����Ĭ��������ı���ɫ����ָ���˱����ı���

// "ȫ���滻"��ť��������

// "����"����

// �¼���Ӧ

// ��

// �򿪽���

// ����

// �������

// ���Ϊ

// ���Ϊ����

// ҳ������

// ҳ�����ý���

// ��ӡ

// ��ӡ����

// �˳�

// �˳�����
// �༭
// else if(e.getSource()==editMenu)
// { checkMenuItemEnabled();//���ü��С����ơ�ճ����ɾ���ȹ��ܵĿ�����
// }
// �༭����
// ����

// ��������

// ����

// ���н���

// ����

// ���ƽ���

// ճ��

// ճ������

// ɾ��

// ɾ������

// ����

// ���ҽ���

// ������һ��

// ������һ������

// �滻

// �滻����

// ת��

// ת������

// ʱ������

// ʱ�����ڽ���

// ȫѡ

// ȫѡ����

// �Զ�����(����ǰ������)

// �Զ����н���

// ��������

// �������ý���

// ����״̬���ɼ���

// ����״̬���ɼ��Խ���

// ��������

// �����������

// ����

// ���ڽ���

//����-------------------------------------------------------------------------
//Ϊʲô�ѱ��������ŵ�һ�������л����
//�ӿڣ����������
//
//
//
//
//Ҫѧ�����֪ʶ
//JMenu��JPopupMenu,JMenuItem,JCheckBoxMenuItem,JTextArea,Toolkit��ClipBoard
//File���ж��ĵ��Ƿ񱣴�ķ���
//����JMenuItemʱ��������ʽ�н���
//���а����
//�ı�ϵͳĬ������
//java.util.Enumeration
//�����˵���
//�����ļ��˵����˵��ע���¼�����
//���ÿ�ݼ�
//setAccelerator
//ʵ��DocumentListener�ӿ��еķ���(�볷�������й�)
//ʵ�ֽӿ�UndoableEditListener����UndoHandler(�볷�������й�)
