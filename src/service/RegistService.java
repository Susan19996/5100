package service;

import dao.RegistDao;
import model.Admin;
import model.AdminCode;
import model.Register;
import model.User;

public class RegistService {
	
	private RegistDao registDao = new RegistDao();
	
	/**
	 * @param register
	 * @return
	 */
	public String regist(Register register) {
		String msg = "";
		if(register.getUsername().contains(" ") || register.getUsername().equals("") ||  
				register.getPassword().contains(" ") || register.getPassword().equals("")) {
			msg = "Input cannot contain spaces or be left empty.";
		}else if(register.getUsername().length() < 2 ||  register.getPassword().length()<5){
			
			msg = "Username and password must be at least 5 characters,name at least 2 characters.";
		}else if (isExist(register)) {
			
			msg = "This account already exists.";
		}else if(register.getMold().equals("Student")) {
			
			msg = registDao.userRegist(register);
		}else if(checkAdminCode(register.getAdminCode())){
			
			msg = registDao.adminRegist(register);
		}else {
		
			msg = "The key is incorrect or has been used too many times.";
		}
		return msg;
	}

	/**
	 * 
	 * @param adminCode
	 * @return
	 */
	private boolean checkAdminCode(String adminCode) {
		AdminCode adminCodeMold = new AdminCode();
		adminCodeMold = registDao.checkAdminCode(adminCode);
		
		if (adminCodeMold != null) {
			
			if (adminCodeMold.getCount() != 0) {
				
				registDao.updateAdminCode(adminCodeMold);
				return true;
			}else {
			
				return false;
			}
		}else {
		
			return false;
		}
	}
	
	/**
	 * @param register
	 * @return
	 */
	private boolean isExist(Register register) {
		User user = registDao.findUserByCode(register.getUsername());
		if (user != null) {
			
			return true;
		}else {
		
			Admin admin = registDao.findAdminByCode(register.getUsername());
			if (admin != null) {
				
				return true;
			}
		}
		
		return false;
	}
}
