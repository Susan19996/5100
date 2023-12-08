package service;

import java.util.ArrayList;
import java.util.List;

import dao.AdminDao;
import dao.UserDao;
import model.Admin;
import model.Book;
import model.User;

/**
 * 
 * @author Administrator
 *
 */
public class UserService{
	UserDao userDao = new UserDao();
	AdminDao adminDao = new AdminDao();
	
	
	private boolean checkCode(String userName, String userPassword) {
		
		if(userName.length()<2 || userName.contains(" ")) {
		
			return false;
		}else if(userPassword.length()<5 || userPassword.contains(" ")){
			
			return false;
		}else {
			return true;
		}
	}
	
	
	public User login(String userName, String userPassword) {
		boolean flag = checkCode(userName, userPassword);
		if (flag) {
			
			User user = userDao.findAllByUserAndPasswd(userName, userPassword);
			if (user!=null) {
				return user;
			}else {
				return null;
			}
		}else {
			
			return null;
		}
	}
	
	
	public Admin adminLogin(String userName, String userPassword) {
		boolean flag = checkCode(userName, userPassword);
		if (flag) {
			
			Admin admin = adminDao.findAllByAdminAndPasswd(userName, userPassword);
			if (admin!=null) {
				return admin;
			}else {
				return null;
			}
		}else {
			
			return null;
		}
	}
	
	/**
	 * Book Searching
	 */
	public List<Book> findBooks(String findMsg, int flag) {
		List<Book> books = new ArrayList<>();
		if(findMsg.equals("") && flag != 3) {
			return books;
		}else {
			switch(flag) {
			case 0:
				books = userDao.findBookByBookname(findMsg);
				break;
			case 1:
				books = userDao.findBookByAuthor(findMsg);
				break;
			case 2:
				books = userDao.findBookByNum(findMsg);
				break;
			case 3:
				books = userDao.findAllBook();
				break;
			}
		}
		
		return books;
	}

	public List<Book> borrowBooks(String findMsg, int flag){
		List<Book> books = new ArrayList<>();

			if(findMsg.equals("") && flag != 2) {
			return books;
		}else {
			switch(flag) {
			case 0:
				books = userDao.borrowBookByBookname(findMsg);
				break;
			case 1:
				books = userDao.borrowBookByAuthor(findMsg);
				break;
			case 2:
				books = userDao.borrowBookByNum(findMsg);
				break;
		}
	}
	return books;
   }

   public List<Book> returnBooks(String findMsg, int flag){
		List<Book> books = new ArrayList<>();

		if(findMsg.equals("") && flag != 2) {
			return books;
		}else {
			switch(flag) {
			case 0:
				books = userDao.returnBookByBookname(findMsg);
				break;
			case 1:
				books = userDao.returnBookByAuthor(findMsg);
				break;
			case 2:
				books = userDao.returnBookByNum(findMsg);
				break;
		}
	}
	return books;
   }


	private String selectedbook;

	public void setSelectedBook(String bookname) {
		this.selectedbook = bookname;
	}
    
}

