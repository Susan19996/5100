package ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import model.Admin;
import model.Book;
import service.AdminService;
import utils.Tools;

/**
 * 
 * @author Administrator
 *
 */
public class AdminView {
	//service和admin
	AdminService adminService = new AdminService();
	private Admin admin = new Admin();
	
	private JFrame adminView = new JFrame("Library Management System(Admin)");
	private Container c = adminView.getContentPane();
	//Top panel components
	private JPanel topPanel = new JPanel(); 
	private JLabel topAdminMsg = new JLabel(); 
	private JComboBox<String> topFindBy = new JComboBox<String>(); 
	private String items[] = {"Search by Book Name", "Search by Author", "Search by Book Number", "Search All Books"};
	private JTextField topInput = new JTextField(); 
	private JLabel topTime = new JLabel(); 
	//side panels and components
	private JPanel sidePanel = new JPanel(); 
	private JButton sideSubmit = new JButton("Search"); 
	private JButton sideAddBtn = new JButton("  Add Book  "); 
	private JButton sideDeleteBtn = new JButton("Delete Book");
	private JButton sideUpdateBtn = new JButton("Update Book");
	//function panel
	private JPanel addPanel = new JPanel();
	private JPanel deletePanel = new JPanel();
	
	private JScrollPane findPanel = new JScrollPane(); 
	private JPanel updataPanel = new JPanel();
	private JLabel functionMsg = new JLabel(); 
	//add function component
	private JLabel addName = new JLabel("Book Name");
	private JLabel addAuthor = new JLabel("Book Author");
	private JLabel addNumber = new JLabel("Book Number");
	private JLabel addLocation = new JLabel("Book Location");
	private JLabel addBorrow = new JLabel("Borrowing Information");
	private JLabel addQuantity = new JLabel("Quantity of Books");
	private JComboBox<String> addBorrowText = new JComboBox<String>();
	private String[] addBorrowList = {"Y","N"};
	private JTextField addNameText = new JTextField(30);
	private JTextField addAuthorText = new JTextField(30);
	private JTextField addNumberText = new JTextField(30);
	private JTextField addLocationText = new JTextField(30);
	private JTextField addQuantityText = new JTextField(30);
	private JButton addCommitBtn = new JButton("Add"); 
	private JButton addCleanBtn = new JButton("Clear"); 
	//search function component
	private JLabel findTitle = new JLabel("Book Information"); 
	private JTable findTable = new JTable();
	String[] findTableTitle = new String[]{"Book Name", "Book Author", "Book Number", "Borrowing Info", "Location", "Quantity of Books"};//表格头字段
	
	private String[][] findTableData;;
	//delete function component
	private JLabel deletenum = new JLabel("Please enter the number of the book to be deleted:");
	private JTextField deletenumText = new JTextField(30); 
	private JButton deletenumBtn = new JButton("Delete");
	//modify function component
	private JLabel updateNum = new JLabel("Please enter the number of the book to be updated:");
	private JTextField updateNumText0 = new JTextField(30);
	private JButton updateFindBtn = new JButton("Search");
	private JButton updateBtn = new JButton("Update");
	private JTextField updateNameText = new JTextField(30);
	private JTextField updateAuthorText = new JTextField(30);
	private JTextField updateNumText = new JTextField(30);
	private JTextField updateLocationText = new JTextField(30);
	private JComboBox<String> updateBorrowText = new JComboBox<String>();
	private JTextField updateQuantityText = new JTextField(30);
	private int id;
	//instance
	private Book book = null;
	private List<Book> books = new ArrayList<Book>();
	//delete dialog
	private JDialog bookDialog = new JDialog(adminView, "Book Information", true); 
	private JButton sure = new JButton("Confirm Deletion");
	private JButton nosure = new JButton("Cancel");
	//wrong message
	private String msg = "";
	
	public AdminView() {
		init();
		adminView.setVisible(true);
	}
	
	public AdminView(Admin admin) {
		this.admin = admin;
		init();
		adminView.setVisible(true);
	}
	
	private void init() {
		c.setLayout(null);
		adminView.setSize(800, 500);
		adminView.setLocationRelativeTo(null);
		adminView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		adminView.setResizable(false);
		
		createTopPanel(); 
		createSidePanel(); 
		selectFunctionListener(); 
		createListener(); 
	}
	
	/**
	 * @param msg
	 */
	private void setFunctionMsg(String msg) {
		
		functionMsg.setBounds(200, 10, 700, 30);
		functionMsg.setText(msg);
		functionMsg.setFont(new Font("Arial", Font.BOLD, 25));
		functionMsg.setForeground(Color.orange);
	}
	
	/**
	 * top pannel：800*100
	 */
	private void createTopPanel() {
		topPanel.setLayout(null);
		topPanel.setBounds(0,0,800,100);
		topPanel.setBackground(Color.GRAY);
		
		
		topAdminMsg.setBounds(10,5,500,20);
		topAdminMsg.setFont(new Font("Arial",Font.PLAIN,15));
		topAdminMsg.setText("Welcome (Administrator): "+admin.getUsername());
		
		
		ComboBoxModel<String> model = new DefaultComboBoxModel<>(items);
		topFindBy.setModel(model);
		topFindBy.setFont(new Font("Verdana", Font.PLAIN, 9));
		topFindBy.setBounds(120,40,150,25);
		
		
		topInput.setBounds(280, 40, 280, 25);
		
		
		sideSubmit.setBounds(580, 40, 80, 25);
		
		
		topTime.setBounds(600, 70, 200, 25);
		topTime.setFont(new Font("Times New Roman", Font.ITALIC, 11));
		topTime.setText("Login Time: "+Tools.getTime());
		
		topPanel.add(topAdminMsg);
		topPanel.add(topFindBy);
		topPanel.add(topInput);
		topPanel.add(sideSubmit);
		topPanel.add(topTime);
		c.add(topPanel);
		topPanel.setVisible(true);
	}
	
	/**
	 * side panel：(100*400)
	 */
	private void createSidePanel() {
		sidePanel.setLayout(new FlowLayout());
		sidePanel.setBounds(0, 100, 100, 400);
		sidePanel.setBackground(Color.lightGray);
		
		sidePanel.add(sideAddBtn);
		sidePanel.add(sideDeleteBtn);
		sidePanel.add(sideUpdateBtn);
		c.add(sidePanel);
		sidePanel.setVisible(true);
	}
	
	/**
	 * add function panel：700*700
	 */
	private JPanel createAddPanel() {
		addPanel.setLayout(null);
		addPanel.setBounds(100, 100, 700, 700);
		
		
		setFunctionMsg("You are using the add function.");
		
		
		addName.setBounds(80, 60, 150, 30);
		addAuthor.setBounds(80, 90, 150, 30);
		addNumber.setBounds(80, 120, 150, 30);
		addLocation.setBounds(80, 150, 150, 30);
		addQuantity.setBounds(80, 180, 150, 30);
		addBorrow.setBounds(80, 210, 150, 30);
		
		addNameText.setBounds(220, 60, 300, 25);
		addAuthorText.setBounds(220, 90, 300, 25);
		addNumberText.setBounds(220, 120, 300, 25);
		addLocationText.setBounds(220, 150, 300, 25);
		addQuantityText.setBounds(220, 180, 300, 25);
		ComboBoxModel<String> model = new DefaultComboBoxModel<String>(addBorrowList);
		addBorrowText.setModel(model);
		addBorrowText.setBounds(220, 210, 80, 25);
		
		
		
		addCommitBtn.setBounds(280, 280, 60, 25);
		addCleanBtn.setBounds(350, 280, 60, 25);
		
		addPanel.add(functionMsg);
		addPanel.add(addName);
		addPanel.add(addAuthor);
		addPanel.add(addNumber);
		addPanel.add(addBorrow);
		addPanel.add(addQuantity);
		addPanel.add(addLocation);
		addPanel.add(addNameText);
		addPanel.add(addAuthorText);
		addPanel.add(addNumberText);
		addPanel.add(addBorrowText);
		addPanel.add(addQuantityText);
		addPanel.add(addLocationText);
		addPanel.add(addCommitBtn);
		addPanel.add(addCleanBtn);
		return addPanel;
	}

	/**
	 * search panel function：700*300
	 */
	private JScrollPane createFindPanel() {
		
		setFunctionMsg("You are using the search function.");
		functionMsg.setBounds(300, 110, 700, 30);
		
		findTitle.setBounds(400, 150, 150, 25);
		
		
		findTable.getTableHeader().setReorderingAllowed(false);
		findTable.getTableHeader().setResizingAllowed(false); 	
		findTable.setEnabled(false);							
		findTable.setRowHeight(20);
		findPanel.setBounds(150, 190, 600, 250);
		findPanel.setViewportView(findTable);
		
		c.add(functionMsg);
		c.add(findTitle);
		
		return findPanel;
	}
	
	/**
	 * delete function panel：700*700
	 */
	private JPanel createDeletePanel() {
		deletePanel.setLayout(null);
		deletePanel.setBounds(80, 100, 700, 700);
		
		
		setFunctionMsg("You are using the delete function.");
		
		
		deletenum.setBounds(50, 70, 400, 30);
		deletenumText.setBounds(150, 100, 330, 25);
		deletenumBtn.setBounds(520, 100, 80, 25);
		
		deletePanel.add(functionMsg);
		deletePanel.add(deletenum);
		deletePanel.add(deletenumText);
		deletePanel.add(deletenumBtn);
		return deletePanel;
	}
	
	/**
	 * delete function panel：700*700
	 */
	private JPanel createUpdataPanel() {
		
		updataPanel.setLayout(null);
		updataPanel.setBounds(80, 100, 700, 700);
		
		
		setFunctionMsg("You are using the update function.");
		
		
		updateNum.setBounds(50, 70, 400, 30);
		updateNumText0.setBounds(150, 100, 330, 25);
		updateFindBtn.setBounds(520, 100, 80, 25);
		
		updataPanel.add(functionMsg);
		updataPanel.add(updateNum);
		updataPanel.add(updateNumText0);
		updataPanel.add(updateFindBtn);
		return updataPanel;
	}
	
	
	private void createMainPanel(JPanel jp) {
		c.remove(findTitle); 
		c.add(jp);
		jp.setVisible(true);
	}
	private void createMainPanel(JScrollPane jp) {
		c.add(jp);
		jp.setVisible(true);
	}
	
//------------Listener------------------------------------------------------------------
	/**
	 * - side panel
	 */
	private void selectFunctionListener() {
		
		sideAddBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				setVisibleFalse();
				
				
				createMainPanel(createAddPanel());
			}
		});
		
	
		sideDeleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				setVisibleFalse();
				
				
				createMainPanel(createDeletePanel());
			}
		});
		
		
		sideUpdateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				setVisibleFalse();
				
				
				createMainPanel(createUpdataPanel());
			}
		});
	}
	
	
	private void createListener() {
		
		sideSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				setVisibleFalse();
				
			
				createMainPanel(createFindPanel());
				

				String findMsg = ""; 
				findMsg = topInput.getText();
				
			
				if(topFindBy.getSelectedItem().equals(items[0])) {
					
					books = adminService.findBooks(findMsg, 0);
					System.out.println("Search by Book Name");
				}
				if(topFindBy.getSelectedItem().equals(items[1])) {
					
					books = adminService.findBooks(findMsg, 1);
					System.out.println("Search by Author");
				}
				if(topFindBy.getSelectedItem().equals(items[2])) {
				
					books = adminService.findBooks(findMsg, 2);
					System.out.println("Search by Book Number");
				}
				if (topFindBy.getSelectedItem().equals(items[3])) {
					
					books = adminService.findBooks(findMsg, 3);
					System.out.println("Search All Books");
				}
				
				if(books != null) {
					
					findTableData = new String[books.size()][6];
					for(int i=0; i<books.size(); i++) {
						Book book = books.get(i);
						if(book != null) {
							
							findTableData[i][0] = book.getBookname();
							findTableData[i][1] = book.getAuthor();
							findTableData[i][2] = book.getNum().toString(); 
							findTableData[i][3] = book.getBorrow();
							findTableData[i][4] = book.getLocation();
							findTableData[i][5] = book.getQuantity().toString();
						}
					}
				}
				
				TableModel data = new DefaultTableModel(findTableData,findTableTitle);
				findTable.setModel(data);
				topInput.setText("");
			}
		});
	
	
		addCommitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String bookname = "";
				String author = "";
				Long number = -1l;
				String location = "";
				Integer quantity = 0;
				String borrow = "";
				
				if (addNumberText.getText().contains(" ") || addNumberText.getText().equals("")) {
					msg = "Book Number cannot contain spaces or be empty!";
				}else if(!Tools.isNumeric(addNumberText.getText())) {
					
					msg = "The Number must be numeric.";
				}else {
					bookname = addNameText.getText();
					author = addAuthorText.getText();
					number = Long.parseLong(addNumberText.getText());
					borrow = addBorrowText.getSelectedItem().toString();
					quantity = Integer.parseInt(addQuantityText.getText());
					location = addLocationText.getText();
					
					book = new Book(bookname, author, number, borrow, quantity,location);
					
					msg = adminService.addBook(book);
				}
				
				
				Tools.createMsgDialog(adminView, msg);
			}
		});
	
		addCleanBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addNameText.setText("");
				addAuthorText.setText("");
				addNumberText.setText("");
				addLocationText.setText("");
				addBorrowText.setSelectedIndex(0);
				addQuantityText.setText("");
			}
		});
	
	
		deletenumBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				book = adminService.findByNumber(deletenumText.getText());
				if (book!=null) {
					showBookMsgDialog(book);
				}else {
					Tools.createMsgDialog(adminView, "This book does not exist!");
					deletenumText.setText("");
				}
			}
		});
		
		
		sure.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String msg = adminService.deleteByNum(book.getNum().toString());
				Tools.createMsgDialog(adminView, msg);
				bookDialog.dispose();
			}
		});
		
		
		nosure.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bookDialog.dispose();
			}
		});
		
		
	
		updateFindBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				book = adminService.findByNumber(updateNumText0.getText());
				if (book != null) {
					showBookMsgOnUpdataPanel(book);
				}else {
					Tools.createMsgDialog(adminView, "This book does not exist!");
				}
			}
		});
		
	
		updateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String msg = "";
				if (updateNumText.getText().equals("") || updateNumText.getText().contains(" ")) {
					Tools.createMsgDialog(adminView, "Incorrect format for quantity!");
				}else {
					
					System.out.println(updateBorrowText.getSelectedItem().toString());
					book = new Book(id ,updateNameText.getText(), updateAuthorText.getText(),
							Long.parseLong(updateNumText.getText()), updateBorrowText.getSelectedItem().toString(),Integer.parseInt(updateQuantityText.getText()),
							updateLocationText.getText());
					msg = adminService.update(book);
					Tools.createMsgDialog(adminView, msg);
					cleanBookMsgOnUppdataPanel(); 
				}
			}
		});
	}
	

	private void setVisibleFalse() {
		addPanel.setVisible(false);
		deletePanel.setVisible(false);
		findPanel.setVisible(false);
		updataPanel.setVisible(false);
		
		c.remove(addPanel);
		c.remove(deletePanel);
		c.remove(findPanel);
		c.remove(updataPanel);
	}
	

	private void showBookMsgDialog(Book book) {
		
		Container bookContainer = bookDialog.getContentPane();
		bookContainer.removeAll();
		bookContainer.setLayout(null);
		
		bookDialog.setSize(500, 300);
		bookDialog.setLocationRelativeTo(null);
		
		
		JLabel name = new JLabel("Name: "+book.getBookname());
		JLabel author = new JLabel("Author: "+book.getAuthor());
		JLabel num = new JLabel("Number: "+book.getNum().toString());
		JLabel borrow = new JLabel("Borrowing Info: "+book.getBorrow());
		JLabel quantity = new JLabel("Quantity of Books: "+book.getQuantity().toString());
		JLabel location = new JLabel("Location: "+book.getLocation());
	
		
		name.setBounds(100, 50, 300, 30);
		author.setBounds(100, 80, 300, 30);
		num.setBounds(100, 110, 300, 30);
		borrow.setBounds(100, 140, 300, 30);
		quantity.setBounds(100,170,300,30);
		location.setBounds(100, 200, 300, 30);
		sure.setBounds(110, 240, 150, 25);
		nosure.setBounds(260, 240, 100, 25);
		
		bookContainer.add(name);
		bookContainer.add(author);
		bookContainer.add(num);
		bookContainer.add(location);
		bookContainer.add(borrow);
		bookContainer.add(quantity);
		bookContainer.add(sure);
		bookContainer.add(nosure);
		bookDialog.setVisible(true);
	}

	/**
	 * 
	 * @param book
	 */
	private void showBookMsgOnUpdataPanel(Book book) {
		
		JLabel name = new JLabel("Name: ");
		JLabel author = new JLabel("Author: ");
		JLabel num = new JLabel("Number: ");
		JLabel location = new JLabel("Location: ");
		JLabel borrow = new JLabel("Borrowing Info: ");
		JLabel quantity = new JLabel("Quantity of Books: ");
		ComboBoxModel<String> model = new DefaultComboBoxModel<String>(addBorrowList);
		updateBorrowText.setModel(model);
		
		
		id = book.getBookid();
		updateNameText.setText(book.getBookname());
		updateAuthorText.setText(book.getAuthor());
		updateNumText.setText(book.getNum().toString());
		updateLocationText.setText(book.getLocation());
		if (book.getBorrow().equals("N")) {
			updateBorrowText.setSelectedIndex(1);
		}
		
		name.setBounds(160, 130, 120, 30);
		author.setBounds(160, 160, 120, 30);
		num.setBounds(160, 190, 120, 30);
		location.setBounds(160, 220, 120, 30);
		borrow.setBounds(160, 250, 120, 30);
		quantity.setBounds(160, 280, 150, 30);
		
		updateNameText.setBounds(300, 130, 200, 25);
		updateAuthorText.setBounds(300, 160, 200, 25);
		updateNumText.setBounds(300, 190, 200, 25);
		updateLocationText.setBounds(300, 220, 200, 25);
		updateBorrowText.setBounds(300, 250, 80, 25);
		updateQuantityText.setBounds(300, 280, 200, 25);
		updateBtn.setBounds(200, 310, 312, 25);
		
		updataPanel.add(name);
		updataPanel.add(author);
		updataPanel.add(num);
		updataPanel.add(location);
		updataPanel.add(borrow);
		updataPanel.add(quantity);
		updataPanel.add(updateNameText);
		updataPanel.add(updateAuthorText);
		updataPanel.add(updateNumText);
		updataPanel.add(updateLocationText);
		updataPanel.add(updateBorrowText);
		updataPanel.add(updateQuantityText);
		updataPanel.add(updateBtn);
	
		updataPanel.setVisible(false);
		updataPanel.setVisible(true);
	}


	private void cleanBookMsgOnUppdataPanel() {
		updateNameText.setText("");
		updateAuthorText.setText("");
		updateNumText.setText("");
		updateLocationText.setText("");
		updateBorrowText.setSelectedIndex(0);
		updateQuantityText.setText("");
	}
	
}
