package ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import model.Book;
import model.User;
import service.UserService;
import utils.Tools;

/**
 * Width：800
 * Height：500
 * @author Administrator
 *
 */
public class UserView{
	private JFrame jf;
	User user = null;
	UserService userService = new UserService();
	private JFrame userView = new JFrame("Library Management System");
	private Container c = userView.getContentPane();
	private JPanel topPanel = new JPanel(); 
	private JLabel userMsg = new JLabel(); 
	private JLabel time = new JLabel();
	private JLabel title = new JLabel("Book Information");
	private JScrollPane mainPanel = new JScrollPane(); 
	private JTable table = new JTable();
	private JComboBox<String> findBy = new JComboBox<>();
	private String items[] = {"Search by Book Name", "Search by Author", "Search by Book Number", "Search All Books"};
	private JTextField input = new JTextField();
	private JButton submit = new JButton("Search");
	private JButton borrowBooks = new JButton("Borrow");
	private JButton returnBooks = new JButton("Return");
	
	private List<Book> books = null;
	String[] tableTitle = new String[]{"Book Name", "Book Author", "Book Number","Borrowing Info","Quantity of Books","Location"};//表格头字段
	private String[][] tableData = new String[1][6];
	
	
	public UserView(User loginUser) {
		this.user = loginUser;
		init();
		userView.setVisible(true);
	}
	
	/*
	 *  initial window
	 */
	private void init() {
		c.setLayout(null);
		userView.setSize(800,500);
		userView.setLocationRelativeTo(null);
		userView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		userView.setResizable(false);
		
		topPanel();
		mainPanel();
		
		createListener();
	}
	
	/**
	 *  - topPanel
	 */
	private void topPanel() {
		topPanel.setLayout(null);
		topPanel.setBounds(0,0,800,100);
		topPanel.setBackground(Color.GRAY);
		
		
		userMsg.setBounds(10,5,200,20);
		userMsg.setFont(new Font("Arial",Font.PLAIN,15));
		userMsg.setText("Welcome: "+user.getUserName());
		
		
		ComboBoxModel<String> model = new DefaultComboBoxModel<>(items);
		findBy.setModel(model);
		findBy.setFont(new Font("Verdana", Font.PLAIN, 9));
		findBy.setBounds(50,40,160,25);
		

		input.setBounds(230, 40, 280, 25);
		

		submit.setBounds(550, 40, 80, 25);
		borrowBooks.setBounds(630,40,80,25);
		returnBooks.setBounds(710,40, 80,25);

		

		time.setBounds(600, 70, 200, 25);
		time.setFont(new Font("Times New Roman", Font.ITALIC, 11));
		time.setText("Login Time: "+Tools.getTime());
		
		topPanel.add(userMsg);
		topPanel.add(findBy);
		topPanel.add(input);
		topPanel.add(submit);
		topPanel.add(borrowBooks);
		topPanel.add(returnBooks);
		topPanel.add(time);
		c.add(topPanel);
		topPanel.setVisible(true);
	}
	
	/**
	 * - Information display panel
	 */
	private void mainPanel() {
	
		
		
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false); 
		//table.setEnabled(false);							
		table.setRowHeight(20);
		mainPanel.setBounds(45, 140, 700, 300);
		mainPanel.setViewportView(table);
		
		
		title.setBounds(350, 110, 120, 25);
		
		c.add(mainPanel);
		c.add(title);
		mainPanel.setVisible(true);
	}


	private void mouseListener() {
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					String bookname = (String) table.getValueAt(selectedRow, 0);
					userService.setSelectedBook(bookname);
					input.setText(bookname);
					System.out.println("Selected book: " + bookname);
				}
			}
		});
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
	}


	/**
	 * - button listener
	 */
	private void createListener() {
		
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				Tools.cleanTwoArray(tableData); 
				String findMsg = ""; 
				findMsg = input.getText();

			
				if (findBy.getSelectedItem().equals(items[0])) {
					
					books = userService.findBooks(findMsg, 0);
					System.out.println("Search by Book Name");
				}
				if (findBy.getSelectedItem().equals(items[1])) {
					
					books = userService.findBooks(findMsg, 1);
					System.out.println("Search by Author");
				}
				if (findBy.getSelectedItem().equals(items[2])) {
					
					books = userService.findBooks(findMsg, 2);
					System.out.println("Search by Book Number");
				}
				if (findBy.getSelectedItem().equals(items[3])) {
					
					books = userService.findBooks(findMsg, 3);
					System.out.println("Search for all books");
				}

				if (books != null) {
					
					tableData = new String[books.size()][6];
					for (int i = 0; i < books.size(); i++) {
						Book book = books.get(i);
						if (book != null) {
							
							tableData[i][0] = book.getBookname();
							tableData[i][1] = book.getAuthor();
							tableData[i][2] = book.getNum().toString(); 
							tableData[i][3] = book.getBorrow();
							tableData[i][4] = book.getQuantity().toString();
							tableData[i][5] = book.getLocation();
						}
					}
				}
				
				TableModel data = new DefaultTableModel(tableData, tableTitle);
				table.setModel(data);
				mouseListener();
				//input.setText("");
			}
		});


		borrowBooks.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == borrowBooks) {
					String findMsg = input.getText();
					String msg = "";

					if (findMsg.isEmpty()) {
						
						Tools.createMsgDialog(jf, "Please enter the information of the book");
						return; 
					}

					if (findBy.getSelectedItem().equals(items[0])) {
						
						books = userService.borrowBooks(findMsg, 0);
						System.out.println("Search by Book Name");
					} else if (findBy.getSelectedItem().equals(items[1])) {
						
						books = userService.borrowBooks(findMsg, 1);
						System.out.println("Search by Author");
					} else if (findBy.getSelectedItem().equals(items[2])) {
						
						books = userService.borrowBooks(findMsg, 2);
						System.out.println("Search by Book Number");
					} else if (findBy.getSelectedItem().equals(items[3])) {
						
						books = userService.borrowBooks(findMsg, 0);
						System.out.println("Search by Book Name");
					}

					if (books != null) {
						
						tableData = new String[books.size()][6];
						for (int i = 0; i < books.size(); i++) {
							Book book = books.get(i);
							if (book != null) {
								tableData[i][0] = book.getBookname();
								tableData[i][1] = book.getAuthor();
								tableData[i][2] = book.getNum().toString();
								tableData[i][3] = book.getBorrow();
								tableData[i][4] = book.getQuantity().toString();
								tableData[i][5] = book.getLocation();
							}
						}
						msg = "Books borrowed successfully!";
						TableModel data = new DefaultTableModel(tableData, tableTitle);
						table.setModel(data);
						input.setText("");
					} else {
						msg = "The book isn't available now!";
					}
					Tools.createMsgDialog(jf, msg);
				}
			}
		});


		returnBooks.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == returnBooks) {
					String findMsg = input.getText();
					String msg = "";

					if (findMsg.isEmpty()) {
						
						Tools.createMsgDialog(jf, "Please enter the information of the book");
						return; 
					}

					
					if (findBy.getSelectedItem().equals(items[0])) {
						
						books = userService.returnBooks(findMsg, 0);//qaq我也不知道这种模式用的对不对
						System.out.println("Search by Book Name");
					}
					if (findBy.getSelectedItem().equals(items[1])) {
						
						books = userService.returnBooks(findMsg, 1);
						System.out.println("Search by Author");
					}
					if (findBy.getSelectedItem().equals(items[2])) {
						
						books = userService.returnBooks(findMsg, 2);
						System.out.println("Search by Book Number");
					}
					if (findBy.getSelectedItem().equals(items[3])) {
						
						books = userService.returnBooks(findMsg, 0);
						System.out.println("Search for all books");
					}

					if (books != null) {
						
						tableData = new String[books.size()][6];
						for (int i = 0; i < books.size(); i++) {
							Book book = books.get(i);
							if (book != null) {
								
								tableData[i][0] = book.getBookname();
								tableData[i][1] = book.getAuthor();
								tableData[i][2] = book.getNum().toString(); //long类型的编号，转为String类型
								tableData[i][3] = book.getBorrow();
								tableData[i][4] = book.getQuantity().toString();
								tableData[i][5] = book.getLocation();
							}
						}
					}
					msg = "Books returned successfully!";
					TableModel data = new DefaultTableModel(tableData, tableTitle);
					table.setModel(data);
					input.setText("");
					Tools.createMsgDialog(jf, msg);
				}
			}
		});

	}
}
