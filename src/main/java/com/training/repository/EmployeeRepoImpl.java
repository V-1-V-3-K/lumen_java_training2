package com.training.repository;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import com.training.exception.ElementNotFoundException;
import com.training.ifaces.CrudRepository;
import com.training.model.Employee;
import com.training.utils.ConnectionFactory;

public class EmployeeRepoImpl implements CrudRepository<Employee> {

	private Connection con;
	
	public EmployeeRepoImpl() {
		super();
		this.con = ConnectionFactory.getMySqlConnection();
	}

	@Override
	public boolean save(Employee obj) {
		String sql = "insert into lumen_employee values(?,?,?,?,?,?,?,?)";
		int rowUpdated=0;
		
		try(PreparedStatement pstmt = this.con.prepareStatement(sql)){
			pstmt.setInt(1, obj.getEmployeeId());
			pstmt.setString(2, obj.getFirstName());
			pstmt.setString(3, obj.getLastName());
			pstmt.setString(4, obj.getAddress());
			pstmt.setString(5, obj.getEmail());
			pstmt.setLong(6, obj.getPhoneNumber());
			
			Date dob = Date.valueOf(obj.getDateOfBirth());
			pstmt.setDate(7, dob);
			
			Date weddingDate = null;
			if(obj.getWeddingDate()!=null) {
				weddingDate = Date.valueOf(obj.getWeddingDate());
				pstmt.setDate(8, weddingDate);
			}else {
				pstmt.setNull(8, java.sql.Types.DATE);
			}
			
			rowUpdated = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return (rowUpdated==0)?false:true;
	}

	@Override
	public Collection<Employee> findAll() {
		String sql = "Select * from lumen_employee";
		List<Employee> empList = new ArrayList<>();
		try(PreparedStatement pstmt = this.con.prepareStatement(sql)){
			
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int employeeId = rs.getInt("employee_id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String address = rs.getString("address");
				String email = rs.getString("email");
				long phoneNumber = rs.getLong("phone_number");
				Date dob = rs.getDate("date_of_birth");
				LocalDate dateOfBirth = dob.toLocalDate();
				Date wedDate = rs.getDate("wedding_date");
				LocalDate weddingDate = null;
				if(wedDate!=null) {
					weddingDate = wedDate.toLocalDate();
				}
				empList.add(new Employee(employeeId,firstName, lastName, address, email, phoneNumber, dateOfBirth, weddingDate));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return empList;
	}

	@Override
	public boolean update(Employee obj) {
		int rowUpdated= 0;
		
		String sql = "Update lumen_employee SET email=?, phone_number=? WHERE employee_id=?";
		try(PreparedStatement pstmt = this.con.prepareStatement(sql)){
			pstmt.setString(1, obj.getEmail());
			pstmt.setLong(2, obj.getPhoneNumber());
			pstmt.setInt(3, obj.getEmployeeId());
			rowUpdated = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return (rowUpdated==0)?false:true;
	}
	
	
	public boolean ifEmployeeObjExists(Employee obj) {
		boolean ret = false;
		
		String sql = "Select * from lumen_employee where first_name=? and"
				+ " last_name=? and address=? and email=? and phone_number=?"
				+ " and date_of_birth=?";
		
		if(obj.getWeddingDate()!=null) {
			sql = sql + " and wedding_date=?";
		}else {
			sql = sql+" and wedding_date is NULL";
		}
		
		try(PreparedStatement pstmt = this.con.prepareStatement(sql)){
			pstmt.setString(1, obj.getFirstName());
			pstmt.setString(2, obj.getLastName());
			pstmt.setString(3, obj.getAddress());
			pstmt.setString(4, obj.getEmail());
			pstmt.setLong(5, obj.getPhoneNumber());
			
			Date dob = Date.valueOf(obj.getDateOfBirth());
			pstmt.setDate(6, dob);
			
			Date weddingDate = null;
			if(obj.getWeddingDate()!=null) {
				weddingDate = Date.valueOf(obj.getWeddingDate());
				pstmt.setDate(7, weddingDate);
			}
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
				ret = true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public Employee giveEmpById(int id) {
		String sql = "Select * from lumen_employee where employee_id=?";
		
		Employee employee = null;
		try(PreparedStatement pstmt = this.con.prepareStatement(sql)){
			pstmt.setInt(1,id);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int employeeId = rs.getInt("employee_id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String address = rs.getString("address");
				String email = rs.getString("email");
				long phoneNumber = rs.getLong("phone_number");
				Date dob = rs.getDate("date_of_birth");
				LocalDate dateOfBirth = dob.toLocalDate();
				Date wedDate = rs.getDate("wedding_date");
				LocalDate weddingDate = null;
				if(wedDate!=null) {
					weddingDate = wedDate.toLocalDate();
				}
				employee = new Employee(employeeId,firstName, lastName, address, email, phoneNumber, dateOfBirth, weddingDate);
			}
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employee;
	}
	
	public boolean ifEmpIdExist(int id) {
		boolean ret = false;
		
		String sql = "Select * from lumen_employee where employee_id=?";
		
		try(PreparedStatement pstmt = this.con.prepareStatement(sql)){
			pstmt.setInt(1,id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
				ret = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
	}

	@Override
	public boolean delete(String name) {
		String sql = "Delete from lumen_employee where first_name=?" ;
	      
		int rowsDeleted = 0 ; 
		
		try(PreparedStatement pstmt = this.con.prepareStatement(sql)){
			pstmt.setString(1, name); 
			
			rowsDeleted += pstmt.executeUpdate() ;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return (rowsDeleted==0)?false:true;
	}
	
	

}
