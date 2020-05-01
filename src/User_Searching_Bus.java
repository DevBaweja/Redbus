import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class User_Searching_Bus extends JPanel implements ActionListener, ItemListener {
	JLabel lbsource, lbdestination, lbtravel;
	JComboBox cbsource, cbdestination, cbtravel;
	JPanel pmain, p;

	JTable tb;
	JScrollPane jsp;
	String pnr_code[];
	static Object selectedpnr;
	JButton btsearch, btbook;
	String user;

	public User_Searching_Bus() {

		user = UserTabbedBar.user;

		setPreferredSize(new Dimension(1500, 900));
		setSize(new Dimension(1500, 900));
		setVisible(true);
		lbsource = new JLabel("Source");
		cbsource = new JComboBox();
		cbsource.addItem("Select Source");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
			Statement stmt = con.createStatement();
			stmt.executeUpdate("create database if not exists RedBusDb");
			stmt.execute("use RedBusDb");
			PreparedStatement pstmt;
			ResultSet rs;
			stmt.executeUpdate(
					"create table if not exists TripBasicTb (pnr int(5),travel_code int(3),bus_code int(5),source varchar(50),destination varchar(50),type boolean,departure_time time,arrival_time time,primary key(pnr) )");

			pstmt = con.prepareStatement("select distinct source from TripBasicTb");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				cbsource.addItem(rs.getString(1));
			}

		} catch (SQLException ae) {
			// TODO Auto-generated catch block
			ae.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		lbdestination = new JLabel("Destination");
		lbtravel = new JLabel("Travel");

		cbdestination = new JComboBox();
		cbdestination.addItem("Select Destination");

		cbtravel = new JComboBox();
		cbtravel.addItem("Select Travel");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
			Statement stmt = con.createStatement();
			stmt.executeUpdate("create database if not exists RedBusDb");
			stmt.execute("use RedBusDb");
			PreparedStatement pstmt;
			ResultSet rs;
			stmt.executeUpdate(
					"create table if not exists ManagerTb(username varchar(50),password varchar(50),travel_code int(3) unique,travel varchar(50),primary key(username))");

			pstmt = con.prepareStatement("select travel from ManagerTb");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				cbtravel.addItem(rs.getString(1));
			}

		} catch (SQLException ae) {
			// TODO Auto-generated catch block
			ae.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		btsearch = new JButton("Search");
		btbook = new JButton("Book");
		btbook.setEnabled(false); // will be enabled when search

		setLayout(new BorderLayout());
		pmain = new JPanel();
		p = new JPanel();

		pmain.setLayout(new GridLayout(1, 3, 50, 50));
		p.setLayout(new FlowLayout());

		pmain.add(lbsource);
		pmain.add(cbsource);
		pmain.add(lbdestination);
		pmain.add(cbdestination);
		pmain.add(lbtravel);
		pmain.add(cbtravel);
		p.add(btsearch);
		p.add(btbook);

		add(pmain, BorderLayout.NORTH);
		add(p, BorderLayout.SOUTH);

		btbook.addActionListener(this);
		btsearch.addActionListener(this);
		cbsource.addItemListener(this);
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		if (src == btbook) {
			bookbus();

		} else if (src == btsearch) {

			if (cbsource.getSelectedIndex() == 0 || cbdestination.getSelectedIndex() == 0
					|| cbtravel.getSelectedIndex() == 0) {
				JOptionPane.showMessageDialog(null, "Kindly fill all the entries", "Information Needed",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			searchbus();
			btbook.setEnabled(true);
		}
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		if (src == cbsource) {
			fillcbdestination();
			btbook.setEnabled(false);
		}
	}

	public void bookbus() {
		selectedpnr = JOptionPane.showInputDialog(null, "Choose your PNR", "Bus Selection", JOptionPane.QUESTION_MESSAGE, null, pnr_code , "Btech");
	
		if(selectedpnr!=null)
			new User_View_Bus();
	}

	
	public void searchbus() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
			Statement stmt = con.createStatement();
			PreparedStatement pstmt;
			stmt.executeUpdate("create database if not exists RedBusDb");
			stmt.execute("use RedBusDb");
			ResultSet rs;
			// getting travel code
			stmt.executeUpdate(
					"create table if not exists ManagerTb(username varchar(50),password varchar(50),travel_code int(3) unique,travel varchar(50),primary key(username))");

			pstmt = con.prepareStatement("select travel_code from ManagerTb where travel=?");
			pstmt.setString(1, cbtravel.getSelectedItem().toString());
			rs = pstmt.executeQuery();
			rs.next();
			int travel_code = rs.getInt(1);

			stmt.executeUpdate(
					"create table if not exists TripBasicTb (pnr int(5),travel_code int(3),bus_code int(5),source varchar(50),destination varchar(50),type boolean,departure_time time,arrival_time time,primary key(pnr) )");

			pstmt = con.prepareStatement(
					"select count(*) from TripBasicTb where source=? and destination=? and travel_code = ?");
			pstmt.setString(1, cbsource.getSelectedItem().toString());
			pstmt.setString(2, cbdestination.getSelectedItem().toString());

			pstmt.setInt(3, travel_code);
			rs = pstmt.executeQuery();
			rs.next();
			int c = rs.getInt(1);
	
			
			Object[] col = { "PNR", "Bus Name", "Bus Number", "Type", "Departure Time", "Arrival Time", "Seat Price",
					"Sleeper Price" };
			Object[][] row = new Object[c][8];
			
			
			pnr_code = new String[c];

			pstmt = con
					.prepareStatement("select * from TripBasicTb where source=? and destination=? and travel_code = ?");
			pstmt.setString(1, cbsource.getSelectedItem().toString());
			pstmt.setString(2, cbdestination.getSelectedItem().toString());

			pstmt.setInt(3, travel_code);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				row[i][0] = rs.getString("pnr");
				pnr_code[i] = rs.getString("pnr");
				row[i][1] = getbusname(rs.getString("bus_code"), rs.getString("travel_code"));
				row[i][2] = getbusnumber(rs.getString("bus_code"), rs.getString("travel_code"));
				if (rs.getBoolean("type"))
					row[i][3] = "AC";
				else
					row[i][3] = "Non-AC";
				row[i][4] = rs.getTime("departure_time");
				row[i][5] = rs.getTime("arrival_time");
				row[i][6] = getseatprice(rs.getString("pnr"));
				row[i][7] = getsleeperprice(rs.getString("pnr"));

				i++;
			}
			con.close();
			
			tb = new JTable(row, col);
			jsp = new JScrollPane(tb);
			
		
			add(jsp, BorderLayout.CENTER);
			validate();
			revalidate();

		} catch (SQLException ae) {
			// TODO Auto-generated catch block
			ae.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
	}

	
	
	public void fillcbdestination() {
		cbdestination.removeAllItems();
		cbdestination.addItem("Select Destination");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
			Statement stmt = con.createStatement();
			stmt.executeUpdate("create database if not exists RedBusDb");
			stmt.execute("use RedBusDb");
			PreparedStatement pstmt;
			ResultSet rs;
			stmt.executeUpdate(
					"create table if not exists TripBasicTb (pnr int(5),travel_code int(3),bus_code int(5),source varchar(50),destination varchar(50),type boolean,departure_time time,arrival_time time,primary key(pnr) )");

			pstmt = con.prepareStatement("select distinct destination from TripBasicTb where source=?");
			pstmt.setString(1, cbsource.getSelectedItem().toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				cbdestination.addItem(rs.getString(1));
			}

		} catch (SQLException ae) {
			// TODO Auto-generated catch block
			ae.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public String getseatprice(String pnr) {
		String seatprice = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
			Statement stmt = con.createStatement();
			stmt.executeUpdate("create database if not exists RedBusDb");
			stmt.execute("use RedBusDb");
			stmt.executeUpdate(
					"create table if not exists TripDetailTb (pnr int(5),seat int(3),seat_price int(6),sleeper int(3),sleeper_price int(6) )");
			PreparedStatement pstmt = con.prepareStatement("select seat_price from TripDetailTb where pnr=? ");
			pstmt.setInt(1, Integer.parseInt(pnr));
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			seatprice = rs.getString("seat_price");
			con.close();

		} catch (SQLException ae) {
			// TODO Auto-generated catch block
			ae.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return seatprice;
	}

	public String getsleeperprice(String pnr) {
		String sleeperprice = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
			Statement stmt = con.createStatement();
			stmt.executeUpdate("create database if not exists RedBusDb");
			stmt.execute("use RedBusDb");
			stmt.executeUpdate(
					"create table if not exists TripDetailTb (pnr int(5),seat int(3),seat_price int(6),sleeper int(3),sleeper_price int(6) )");
			PreparedStatement pstmt = con.prepareStatement("select sleeper_price from TripDetailTb where pnr=? ");
			pstmt.setInt(1, Integer.parseInt(pnr));
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			sleeperprice = rs.getString("sleeper_price");
			con.close();

		} catch (SQLException ae) {
			// TODO Auto-generated catch block
			ae.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return sleeperprice;
	}

	public String getbusname(String bus_code, String travel_code) {
		String busname = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
			Statement stmt = con.createStatement();
			stmt.executeUpdate("create database if not exists RedBusDb");
			stmt.execute("use RedBusDb");
			stmt.executeUpdate("create table if not exists Travel" + travel_code
					+ "(bus_code int(5),bus_name varchar(50),bus_number varchar(20) unique,primary key(bus_code))");

			PreparedStatement pstmt = con
					.prepareStatement("select bus_name from Travel" + travel_code + " where bus_code=? ");
			pstmt.setInt(1, Integer.parseInt(bus_code));
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			busname = rs.getString("bus_name");
			con.close();

		} catch (SQLException ae) {
			// TODO Auto-generated catch block
			ae.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return busname;
	}

	public String getbusnumber(String bus_code, String travel_code) {
		String busnumber = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
			Statement stmt = con.createStatement();
			stmt.executeUpdate("create database if not exists RedBusDb");
			stmt.execute("use RedBusDb");
			stmt.executeUpdate("create table if not exists Travel" + travel_code
					+ "(bus_code int(5),bus_name varchar(50),bus_number varchar(20) unique,primary key(bus_code))");

			PreparedStatement pstmt = con
					.prepareStatement("select bus_number from Travel" + travel_code + " where bus_code=? ");
			pstmt.setInt(1, Integer.parseInt(bus_code));
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			busnumber = rs.getString("bus_number");
			con.close();

		} catch (SQLException ae) {
			// TODO Auto-generated catch block
			ae.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return busnumber;
	}

}
