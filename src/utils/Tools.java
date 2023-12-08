package utils;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Tools {
	/**
	 * 
	 * @param array
	 */
	public static void cleanTwoArray(Object[][] array) {
		if (array.length == 0) {
			return;
		}
		for(int i=0; i<array.length; i++) {
			for(int j=0; j<array[i].length; j++) {
				array[i][j] = null;
			}
		}
	}
	
	/**
	 * get current timezone
	 */
	public static String getTime() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String nowTime = "";
		nowTime = df.format(new Date());
		return nowTime;
	}
	
	/**
	 * 
	 * @param jf
	 * @param msg
	 */
	public static void createMsgDialog(JFrame jf, String msg) {
		JDialog msgDialog = new JDialog(jf, "Result", true);
		Container container = msgDialog.getContentPane();
		msgDialog.setSize(450,150);
		msgDialog.setLocationRelativeTo(null);

		JTextArea textArea = new JTextArea();
		textArea.append("\n\n");
		textArea.append(msg);
		textArea.setForeground(Color.RED);
		textArea.setFont(new Font("Verdana", Font.PLAIN, 15));
        textArea.setLineWrap(true);                 
        textArea.setWrapStyleWord(true);  
		
		
		//JLabel msgLabel = new JLabel(msg);
		//msgLabel.setForeground(Color.red);
		//msgLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
		

		
		container.add(textArea);
		msgDialog.setVisible(true);
	}
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){ 
	  for (int i = str.length();--i>=0;){   
		   if (!Character.isDigit(str.charAt(i))){ 
			   return false; 
		   } 
	  } 
	  return true; 
	} 
}
