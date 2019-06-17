package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Models.NewUser;
import Models.Transaction;
import sosaBank.Customer;
import sosaBank.Game;
import util.ConnectionUtil;

public class BankOracle implements BankDao {

	private static final Logger log = LogManager.getLogger(BankOracle.class);



	@SuppressWarnings("null")
	public int deposit(int accountID, int amount) {
		// this one adds to the account
		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			log.error("Connection was null");
		}

		try {
	
			String sql = "call create_banktrans(?, ?, ?)";
			CallableStatement ps = con.prepareCall(sql);
			ps.setInt(1, accountID);
			ps.setInt(2, amount);
			ps.registerOutParameter(3, Types.INTEGER);
			ps.executeQuery();
			 int tid = ps.getInt(3);
			if (tid == 5) {
				
			
			int balance = lookUp(accountID); 
			int newbalance = balance + amount;
			if (newbalance >0 ) {
			//this is just a little check for withdraw, ideally these would be the same method	
			
			String sql2 = "call update_Balance(?,?)";
			CallableStatement ps2 = con.prepareCall(sql2);
			ps2.setInt(1, accountID);
			ps2.setInt(2, newbalance);
			
			ps2.executeUpdate();
		
			System.out.println("great you your new balance is " + newbalance + " dollars.");
			return newbalance;
			}}else {
				System.out.println("Realizing that you can't CHEAT the system you decided to LEAVE");
				Game.leave();
				return 0;
			}
			
		} catch (SQLException e) {
			log.error("Unable to execute sql query", e);
		}
		return 0;

		
	
	}

	@SuppressWarnings("null")
	public int withdraw(int accountID, int amount) {
		// this one subtracts from the account.
		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			log.error("Connection was null");
		}

		try {
	
			String sql = "call create_banktrans(?, ?, ?)";
			CallableStatement ps = con.prepareCall(sql);
			ps.setInt(1, accountID);
			ps.setInt(2, amount);
			ps.registerOutParameter(3, Types.INTEGER);
			ps.executeQuery();
			 int tid = ps.getInt(3);
			if (tid == 5) {
				
			
			int balance = lookUp(accountID); 
			int newbalance = balance - amount;
			if (newbalance >0 ) {
				
			
			String sql2 = "call update_Balance(?,?)";
			CallableStatement ps2 = con.prepareCall(sql2);
			ps2.setInt(1, accountID);
			ps2.setInt(2, newbalance);
			
			ps2.executeUpdate();
			
			System.out.println("great you your new balance is " + newbalance + " dollars.");
			return newbalance;
			}}else {
				System.out.println("Realizing that you can't CHEAT the system you decided to LEAVE");
				Game.leave();
				return 0;
			}
			
		} catch (SQLException e) {
			log.error("Unable to execute sql query", e);
		}
		return 0;

	
 
	}


	@Override
	public int ValidateAccountPass(int accountid, String accountpass) {
		// this one returns a 3 if the user was validated. 
		Connection con = ConnectionUtil.getConnection();
		int a = 1;
		if (con == null) {
			log.error("Connection was null");
		}
		
		try {
			String sql = "Select ID FROM BANKUSER WHERE ID =? and password=?";
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setInt(1, 29);
			ps.setString(2, "password");
		
			ps.executeQuery();
			a = 3;
			System.out.println("validating account with password worked!");
			
			
		} catch (SQLException e) {
			System.out.println(e);
			log.error("Unable to execute sql query", e);
			System.out.println("no no no, try chatting with the TELLER to create an account");
			System.out.println("you get frustrated and LEAVE.");
			Game.leave();
		}
		return a;	
	}

	@Override
	public int lookUp(int AccountID) {
	//this method returns the account balance
		Connection con = ConnectionUtil.getConnection();
		if (con == null) {
			log.error("Connection was null");
		}
		
		try {
			String sql = "call find_bankBALANCE(?, ?)";
			CallableStatement ps = con.prepareCall(sql);
			ps.setInt(1, AccountID);
			ps.registerOutParameter(2, Types.INTEGER);
			ps.executeQuery();
			int balance = ps.getInt(2);
			
			return balance;
			
		} catch (SQLException e) {
			log.error("Unable to execute sql query", e);
		}
		return -35;	

	}

	



	public Object createUser(String AccountName, String AccountPass) {
		
		int id =0;
		
		Connection con = ConnectionUtil.getConnection();
try {	
	
	String sql = "call create_bankuser(?, ?, ?)";
	CallableStatement ps = con.prepareCall(sql);
	ps.setString(1, AccountName);
	ps.setString(2, AccountPass);
	ps.registerOutParameter(3, Types.INTEGER);
	ps.execute();
	 id = ps.getInt(3);
	System.out.println("your account id is " +id + " remember that im not going to tell you again");

	
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}



}

