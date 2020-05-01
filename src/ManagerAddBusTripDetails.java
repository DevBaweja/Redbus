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

public class ManagerAddBusTripDetails extends JPanel implements ActionListener {
	JLabel lbpnr, lbseat, lbseatprice, lbsleeper, lbsleeperprice;
	JTextField txseat, txseatprice, txsleeper, txsleeperprice;
	JComboBox cbpnr;
	JPanel pmain, p;
	JButton btsubmit, btrefresh;

	int bus_code;
	String travel;
	int travel_code;
	String manager;

	public ManagerAddBusTripDetails() {

		manager = ManagerTabbedBar.manager;
		travel = ManagerHome.travel;
		travel_code = ManagerHome.travel_code;

		setPreferredSize(new Dimension(1000, 1000));
		setSize(new Dimension(1000, 1000));
		setVisible(true);
		lbpnr = new JLabel("PNR");
		cbpnr = new JComboBox();
		cbpnr.addItem("Select PNR");
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

			pstmt = con.prepareStatement("select pnr from TripBasicTb where travel_code=?");
			pstmt.setInt(1, travel_code);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				cbpnr.addItem(rs.getString(1));
			}
			
			stmt.executeUpdate(
					"create table if not exists TripDetailTb (pnr int(5),seat int(3),seat_price int(6),sleeper int(3),sleeper_price int(6) )");

			pstmt = con.prepareStatement("select pnr from TripDetailTb");
		
			rs = pstmt.executeQuery();
			while (rs.next()) {
				cbpnr.removeItem(rs.getString(1));
			}
			
		} catch (SQLException ae) {
			// TODO Auto-generated catch block
			ae.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		lbseat = new JLabel("Seats Available");
		lbseatprice = new JLabel("Seats Price");
		lbsleeper = new JLabel("Sleeper Available");
		lbsleeperprice = new JLabel("Sleeper Price");

		txseat = new JTextField(3);
		txseatprice = new JTextField(6);
		txsleeper = new JTextField(3);
		txsleeperprice = new JTextField(6);

		btsubmit = new JButton("Add Trip Details");
		btrefresh = new JButton("Refresh");

		setLayout(new BorderLayout());
		pmain = new JPanel();
		p = new JPanel();

		pmain.setLayout(new GridLayout(5, 2, 50, 50));
		p.setLayout(new FlowLayout());

		pmain.add(lbpnr);
		pmain.add(cbpnr);
		pmain.add(lbseat);
		pmain.add(txseat);
		pmain.add(lbseatprice);
		pmain.add(txseatprice);
		pmain.add(lbsleeper);
		pmain.add(txsleeper);
		pmain.add(lbsleeperprice);
		pmain.add(txsleeperprice);
		p.add(btsubmit);
		p.add(btrefresh);

		add(pmain, BorderLayout.CENTER);
		add(p, BorderLayout.SOUTH);

		btrefresh.addActionListener(this);
		btsubmit.addActionListener(this);
		validate();
		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		if (src == btrefresh) {

			cbpnr.removeAllItems();
			cbpnr.addItem("Select PNR");
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

				pstmt = con.prepareStatement("select pnr from TripBasicTb where travel_code=?");
				pstmt.setInt(1, travel_code);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					cbpnr.addItem(rs.getString(1));
				}
				
				stmt.executeUpdate(
						"create table if not exists TripDetailTb (pnr int(5),seat int(3),seat_price int(6),sleeper int(3),sleeper_price int(6) )");

				pstmt = con.prepareStatement("select pnr from TripDetailTb");
			
				rs = pstmt.executeQuery();
				while (rs.next()) {
					cbpnr.removeItem(rs.getString(1));
				}
				
			} catch (SQLException ae) {
				// TODO Auto-generated catch block
				ae.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			txseat.setText("");
			txseatprice.setText("");
			txsleeper.setText("");
			txsleeperprice.setText("");

		} else if (src == btsubmit) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
				Statement stmt = con.createStatement();
				PreparedStatement pstmt;
				stmt.executeUpdate("create database if not exists RedBusDb");
				stmt.execute("use RedBusDb");
				stmt.executeUpdate(
						"create table if not exists TripDetailTb (pnr int(5),seat int(3),seat_price int(6),sleeper int(3),sleeper_price int(6) )");

				pstmt = con.prepareStatement(
						"insert into TripDetailTb(pnr,seat,seat_price,sleeper,sleeper_price) values(?,?,?,?,?) ");
				pstmt.setString(1, cbpnr.getSelectedItem().toString());
				pstmt.setInt(2, Integer.parseInt(txseat.getText()));
				pstmt.setInt(3, Integer.parseInt(txseatprice.getText()));
				pstmt.setInt(4, Integer.parseInt(txsleeper.getText()));
				pstmt.setInt(5, Integer.parseInt(txsleeperprice.getText()));

				pstmt.executeUpdate();
				con.close();

			} catch (SQLException ae) {
				// TODO Auto-generated catch block
				ae.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
		}
	}
}
