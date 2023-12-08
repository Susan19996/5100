package service;

import java.util.ArrayList;
import java.util.List;

import dao.AdminDao;
import model.Book;

public class AdminService extends UserService {
	AdminDao adminDao = new AdminDao();

	/**
	 *
	 * @param book
	 * @return
	 */
	public String addBook(Book book) {
		List<Book> books = new ArrayList<Book>();
		String msg = "";
		if(book.getAuthor().trim().equals("") || book.getBookname().trim().equals("") ||
				book.getBorrow().trim().equals("") || book.getLocation().trim().equals("") ||
				book.getNum()<0 || book.getNum().toString().trim().equals("")|| book.getQuantity()<0 || book.getQuantity().toString().trim().equals("")) {
			msg = "Please enter correct information!";
		}else {
			books = adminDao.findBookByNum(book.getNum().toString());
			if (books.isEmpty()) {
				msg = adminDao.addBook(book);
			}else {
				msg = "This book already exists!";
			}
		}
		return msg;
	}
	
	/**
	 * - use number to find book
	 */
	public Book findByNumber(String number) {
		List<Book> books = new ArrayList<Book>();
		if (number.trim().equals("")) {
			return null;
		}else {
			books = adminDao.findBookByNum(number);
			if (books.isEmpty()) {
				return null;
			}
		}
		return books.get(0);
	}

	/**
	 * - use number to delete book
	 * @param string
	 */
	public String deleteByNum(String num) {
		return adminDao.deleteByNum(num);
	}

	/**
	 * - Update book
	 * @param book
	 */
	public String update(Book book) {
		String msg = "";
		if(book.getAuthor().trim().equals("") || book.getBookname().trim().equals("") ||
				book.getBorrow().trim().equals("") || book.getLocation().trim().equals("") ||
				book.getNum()<0 || book.getNum().toString().trim().equals("")|| book.getQuantity()<0 || book.getQuantity().toString().trim().equals("")) {
			msg = "Please enter correct information!";
		}else {
			msg = adminDao.updateBook(book);
		}
		return msg;
	}

}
