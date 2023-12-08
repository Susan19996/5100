package ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.Register;
import service.RegistService;
import utils.Tools;

/**
 * 
 * @author Administrator
 *
 */
public class RegisterDialog {
	private RegistService registService = new RegistService();
	private Register register = null;
	private JDialog registerView;
	private Container c;
	private JFrame jf;
	
	//create label
	private JLabel registerName = new JLabel("Username:");
	private JLabel nameNotice = new JLabel("Username must be at least 2 characters!");
	private JLabel registerPassword = new JLabel("Password:");
	private JLabel passwordNotice = new JLabel("Password must be at least 5 characters!");
	private JLabel registerPassword2 = new JLabel("Repeat Password:");
	private JLabel registerAdminCode = new JLabel("Administrator Key:"); 
	private JLabel adminCodeNotice = new JLabel("Note: The admin key is only valid when registering as an administrator.");
	private JLabel registMold = new JLabel("Registration Type:");

	//create text field
	private JTextField nameField = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	private JPasswordField passwordField2 = new JPasswordField();
	private JTextField adminCodeField = new JTextField();
	private JComboBox<String> registMoldField = new JComboBox<String>();
	private String[] registMoldList = {"Student", "Administrator"};
	private ComboBoxModel<String> model = new DefaultComboBoxModel<String>(registMoldList);
	
	//button
	private JButton registBtn = new JButton("Register");
	private JButton resetBtn = new JButton("Reset");
	
	public RegisterDialog(JFrame jf, String title) {
		this.jf = jf;
		init(title);
		registerView.setVisible(true);
	}
	
	
	private void init(String title) {
		
		registerView = new JDialog(jf, title, true);
		c = registerView.getContentPane();
		c.setLayout(null);
		registerView.setSize(600, 400);
		registerView.setLocationRelativeTo(null);
		
		
		registerName.setBounds(120, 30, 130, 30);
		nameNotice.setBounds(120, 50, 500, 30);
		nameNotice.setForeground(Color.RED);
		registerPassword.setBounds(120,70, 130, 30);
		passwordNotice.setBounds(120, 90, 500, 30);
		passwordNotice.setForeground(Color.RED);
		
		registerPassword2.setBounds(120, 110, 130, 30);
		registerAdminCode.setBounds(120, 140, 130, 30);
		adminCodeNotice.setBounds(120, 160, 500, 30);
		adminCodeNotice.setForeground(Color.RED);
		registMold.setBounds(120, 190, 130, 30); 
		
		nameField.setBounds(300, 30, 200, 25);
		passwordField.setBounds(300, 70, 200, 25);
		passwordField2.setBounds(300, 112, 200, 25);
		adminCodeField.setBounds(300, 142, 200, 25); 
		registMoldField.setBounds(300, 195, 200, 25);
		
		registBtn.setBounds(180, 230, 100, 25);
		resetBtn.setBounds(360, 230, 100, 25);
		
		registMoldField.setModel(model);
		
		c.add(registerName);
		c.add(nameNotice);
		c.add(registerPassword);
		c.add(passwordNotice);
		c.add(registerPassword2);
		c.add(registerAdminCode);
		c.add(registMold);
		c.add(adminCodeNotice);
		c.add(nameField);
		c.add(passwordField);
		c.add(passwordField2);
		c.add(adminCodeField);
		c.add(registMoldField);
		c.add(registBtn);
		c.add(resetBtn);
		
	
		createListener();
	}

	/**
	 * - Listener
	 */
	
	private void createListener() {
		
		registBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Start registration");
				String msg = "";
				
				if (Arrays.equals(passwordField.getPassword(),passwordField2.getPassword())) {
					
					String str = new String(passwordField.getPassword());
					register = new Register(nameField.getText(),str,
							adminCodeField.getText(),registMoldField.getSelectedItem().toString());
					
					msg = registService.regist(register);
				}else {
					
					msg = "The two password entries are not the same!";
					passwordField.setText("");
					passwordField2.setText("");
				}
				Tools.createMsgDialog(jf, msg);
			}
		});
		
		
		resetBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nameField.setText("");
				passwordField.setText("");
				passwordField2.setText("");
				registMoldField.setSelectedIndex(0); 
			}
		});
	}
}
