import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class ManagerHome extends JPanel {
	JLabel lbname,lbtravel;
	static String travel;
	static int travel_code;
	String manager;
	public ManagerHome() {
		
		manager = ManagerTabbedBar.manager;
		
		setPreferredSize(new Dimension(1000, 1000));
		setSize(new Dimension(1000, 1000));
		setVisible(true);

		setLayout(new BorderLayout());
		
		lbname = new JLabel("Welcome Manager, "+manager);
		add(lbname,BorderLayout.NORTH);
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
			Statement stmt = con.createStatement();
			stmt.executeUpdate("create database if not exists RedBusDb");
			stmt.execute("use RedBusDb");
			PreparedStatement pstmt = con
					.prepareStatement("select travel_code,travel from ManagerTb where username=?");
			pstmt.setString(1,manager);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			travel = rs.getString("travel");
			travel_code = rs.getInt("travel_code");
			con.close();

		} catch (SQLException ae) {
			// TODO Auto-generated catch block
			ae.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
		lbtravel = new JLabel("Travels :"+travel);
		add(lbtravel,BorderLayout.CENTER);
		validate();
		revalidate();
	}
}
