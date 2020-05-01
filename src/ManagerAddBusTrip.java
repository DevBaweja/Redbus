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
import java.sql.Time;

import javax.swing.*;

public class ManagerAddBusTrip extends JPanel implements ActionListener, ItemListener {
	JLabel lbpnr, lbbusname, lbbusno, lbsource, lbdestination, lbdeparture, lbarrival;
	JTextField txpnr, txsource, txdestination, txdeparture, txarrival;
	JLabel lbtype;
	JPanel ptype;
	JRadioButton cbac, cbnonac, cbhidden;
	ButtonGroup gr;
	JPanel pmain, p;

	JComboBox cbbusname, cbbusno;
	JButton btsubmit, btrefresh;

	int bus_code;
	String travel;
	int travel_code;
	String manager;

	public ManagerAddBusTrip() {

		manager = ManagerTabbedBar.manager;
		travel = ManagerHome.travel;
		travel_code = ManagerHome.travel_code;

		setPreferredSize(new Dimension(1000, 1000));
		setSize(new Dimension(1000, 1000));
		setVisible(true);

		lbpnr = new JLabel("PNR");
		lbbusname = new JLabel("Bus Name");
		lbbusno = new JLabel("Bus Number");

		cbbusname = new JComboBox();
		cbbusname.addItem("Select Bus Name");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
			Statement stmt = con.createStatement();
			stmt.executeUpdate("create database if not exists RedBusDb");
			stmt.execute("use RedBusDb");
			stmt.executeUpdate("create table if not exists Travel" + travel_code
					+ "(bus_code int(5),bus_name varchar(50),bus_number varchar(20) unique,primary key(bus_code))");

			PreparedStatement pstmt = con.prepareStatement("select distinct bus_name from Travel" + travel_code + "");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				cbbusname.addItem(rs.getString(1));
			}

		} catch (SQLException ae) {
			// TODO Auto-generated catch block
			ae.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		cbbusno = new JComboBox();
		cbbusno.addItem("Select Bus Number");
		cbbusno.setEnabled(false);
		lbsource = new JLabel("Source");
		lbdestination = new JLabel("Destination");
		lbtype = new JLabel("Type");
		lbdeparture = new JLabel("Departure time");
		lbarrival = new JLabel("Arrival Time");

		txpnr = new JTextField(5);
		int pnr;
		while (true) 
		{
			pnr = (int) (Math.random() * 100000);
			if(pnr>10000)
				break;
		}
		txpnr.setText(Integer.toString(pnr));
		txpnr.setEditable(false);
		
		txsource = new JTextField(50);
		txdestination = new JTextField(50);
		txdeparture = new JTextField(10);
		txarrival = new JTextField(10);

		cbac = new JRadioButton("AC");
		cbnonac = new JRadioButton("Non-AC");
		cbhidden = new JRadioButton();
		gr = new ButtonGroup();
		gr.add(cbac);
		gr.add(cbnonac);
		gr.add(cbhidden);

		ptype = new JPanel();
		ptype.setLayout(new GridLayout(1, 2));
		ptype.add(cbac);
		ptype.add(cbnonac);
		btsubmit = new JButton("Add Trip");
		btrefresh = new JButton("Refresh");

		setLayout(new BorderLayout());
		pmain = new JPanel();
		p = new JPanel();

		pmain.setLayout(new GridLayout(8, 2, 50, 50));
		p.setLayout(new FlowLayout());

		pmain.add(lbbusname);
		pmain.add(cbbusname);
		pmain.add(lbbusno);
		pmain.add(cbbusno);
		pmain.add(lbpnr);
		pmain.add(txpnr);
		pmain.add(lbsource);
		pmain.add(txsource);
		pmain.add(lbdestination);
		pmain.add(txdestination);
		pmain.add(lbtype);
		pmain.add(ptype);
		pmain.add(lbdeparture);
		pmain.add(txdeparture);
		pmain.add(lbarrival);
		pmain.add(txarrival);
		p.add(btsubmit);
		p.add(btrefresh);

		add(pmain, BorderLayout.CENTER);
		add(p, BorderLayout.SOUTH);

		btrefresh.addActionListener(this);
		btsubmit.addActionListener(this);
		cbbusname.addItemListener(this);
		validate();
		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		if (src == btrefresh) {

			cbbusname.setSelectedIndex(0);
			cbbusno.setSelectedIndex(0);
			cbbusno.setEnabled(false);
			txsource.setText("");
			txdestination.setText("");
			cbhidden.setSelected(true);
			txdeparture.setText("");
			txarrival.setText("");
			int pnr;
			while (true) 
			{
				pnr = (int) (Math.random() * 100000);
				if(pnr>10000)
					break;
			}
			txpnr.setText(Integer.toString(pnr));

		} else if (src == btsubmit) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
				Statement stmt = con.createStatement();
				PreparedStatement pstmt;
				stmt.executeUpdate("create database if not exists RedBusDb");
				stmt.execute("use RedBusDb");

				// Finding bus code
				stmt.executeUpdate("create table if not exists Travel" + travel_code
						+ "(bus_code int(5),bus_name varchar(50),bus_number varchar(20) unique,primary key(bus_code))");

				pstmt = con.prepareStatement(
						"select bus_code from Travel" + travel_code + " where bus_name=? and bus_number=?");
				pstmt.setString(1, cbbusname.getSelectedItem().toString());
				pstmt.setString(2, cbbusno.getSelectedItem().toString());
				ResultSet rs = pstmt.executeQuery();
				rs.next();
				bus_code = rs.getInt("bus_code");

				stmt.executeUpdate(
						"create table if not exists TripBasicTb (pnr int(5),travel_code int(3),bus_code int(5),source varchar(50),destination varchar(50),type boolean,departure_time time,arrival_time time,primary key(pnr) )");
				pstmt = con.prepareStatement(
						"insert into TripBasicTb(pnr,travel_code,bus_code,source,destination,type,departure_time,arrival_time) values(?,?,?,?,?,?,?,?) ");
				pstmt.setInt(1, Integer.parseInt(txpnr.getText()));
				pstmt.setInt(2, travel_code);
				pstmt.setInt(3, bus_code);
				pstmt.setString(4, txsource.getText());
				pstmt.setString(5, txdestination.getText());
				if (cbac.isSelected())
					pstmt.setBoolean(6, true);
				else if (cbnonac.isSelected())
					pstmt.setBoolean(6, false);
				
				String td = txdeparture.getText();
				String ta = txarrival.getText();
				Time t_deparutre = new Time(Integer.parseInt(td.charAt(0)+""+td.charAt(1)+""),Integer.parseInt(td.charAt(2)+""+td.charAt(3)+""),00);
				pstmt.setTime(7,t_deparutre);
				Time t_arrival = new Time(Integer.parseInt(ta.charAt(0)+""+ta.charAt(1)+""),Integer.parseInt(ta.charAt(2)+""+ta.charAt(3)+""),00);
				pstmt.setTime(8,t_arrival);
				
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

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		if (src == cbbusname) {

			cbbusno.setEnabled(true);
			cbbusno.removeAllItems();
			cbbusno.addItem("Select Bus Number");
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
				Statement stmt = con.createStatement();
				stmt.executeUpdate("create database if not exists RedBusDb");
				stmt.execute("use RedBusDb");
				stmt.executeUpdate("create table if not exists Travel" + travel_code
						+ "(bus_code int(5),bus_name varchar(50),bus_number varchar(20) unique,primary key(bus_code))");

				PreparedStatement pstmt = con
						.prepareStatement("select bus_number from Travel" + travel_code + " where bus_name = ?");
				pstmt.setString(1, cbbusname.getSelectedItem().toString());
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					cbbusno.addItem(rs.getString(1));
				}

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
