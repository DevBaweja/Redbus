import java.awt.BorderLayout;
import java.awt.Color;
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

public class User_View_Bus extends JFrame implements ActionListener{
	JLabel lbname,lbtravel,lbbusname,lbbusnumber,lbsource,lbdestination,lbdeparturetime,lbarrivaltime,lbseatprice,lbsleeperprice,lbtype,lbpnr;
	JTextField txname,txtravel,txbusname,txbusnumber,txsource,txdestination,txdeparturetime,txarrivaltime,txseatprice,txsleeperprice,txtype,txpnr;
	
	JPanel pbusname,pbusnumber,psource,pdestination,pdeparturetime,parrivaltime,pseatprice,psleeperprice,ptype,ppnr;

	JPanel pmain, p;

	JButton btproceed, btcancel;
	static String user,pnr,seatprice,sleeperprice,name;

	String travel_code,bus_code;
	public User_View_Bus() 
	{

		user = UserTabbedBar.user;
		//user = "dev";
		
		pnr = (String)User_Searching_Bus.selectedpnr;
		//pnr = "70407";
		
		setPreferredSize(new Dimension(1500, 900));
		setSize(new Dimension(1500, 900));
		setVisible(true);
		set();
		fillvalues();
		btproceed.addActionListener(this);
		btcancel.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		if(src == btproceed)
		{
			new User_Book_Bus();
			this.dispose();
		}
		else if(src == btcancel)
		{
			this.dispose();
		}
		
	}
	
	public void fillvalues()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
			Statement stmt = con.createStatement();
			stmt.executeUpdate("create database if not exists RedBusDb");
			stmt.execute("use RedBusDb");
			PreparedStatement pstmt;
			ResultSet rs;
			
			pstmt = con
					.prepareStatement("select name from UserTb where username=?");
			pstmt.setString(1,user);
			rs = pstmt.executeQuery();
			rs.next();
			txname.setText(rs.getString("name"));
			name = rs.getString("name");
			
			
			pstmt = con
					.prepareStatement("select * from TripBasicTb where pnr=?");
			pstmt.setString(1,pnr);
			rs = pstmt.executeQuery();
			rs.next();
			txsource.setText(rs.getString("source"));
			txdestination.setText(rs.getString("destination"));
			txdeparturetime.setText(rs.getString("departure_time"));
			txarrivaltime.setText(rs.getString("arrival_time"));
			if(rs.getBoolean("type"))
			txtype.setText("AC");
			else
			txtype.setText("Non-AC");
			
			travel_code = rs.getString("travel_code");
			bus_code = rs.getString("bus_code");
			
			pstmt = con
					.prepareStatement("select travel from ManagerTb where travel_code=?");
			pstmt.setString(1,travel_code);
			rs = pstmt.executeQuery();
			rs.next();
			txtravel.setText(rs.getString("travel"));
			
			
			pstmt = con
					.prepareStatement("select bus_name,bus_number from travel"+travel_code+" where bus_code=?");
			pstmt.setString(1,bus_code);
			rs = pstmt.executeQuery();
			rs.next();
			txbusname.setText(rs.getString("bus_name"));
			txbusnumber.setText(rs.getString("bus_number"));
			
			pstmt = con
					.prepareStatement("select seat_price,sleeper_price from TripDetailTb where pnr=?");
			pstmt.setString(1,pnr);
			rs = pstmt.executeQuery();
			rs.next();
			txseatprice.setText(rs.getString("seat_price"));
			txsleeperprice.setText(rs.getString("sleeper_price"));
			seatprice = rs.getString("seat_price");
			sleeperprice = rs.getString("sleeper_price");
			
			con.close();

		} catch (SQLException ae) {
			// TODO Auto-generated catch block
			ae.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
	}

	
	
	public void set() {
		lbname = new JLabel("Name");
		lbtravel = new JLabel("Travel");
		lbbusname = new JLabel("Bus Name");
		lbbusnumber = new JLabel("Bus Number");
		lbsource = new JLabel("Source");
		lbdestination = new JLabel("Destination");
		lbdeparturetime = new JLabel("Departure Time");
		lbarrivaltime = new JLabel("Arrival Time");
		lbseatprice = new JLabel("Seat Price");
		lbsleeperprice = new JLabel("Sleeper Price");
		lbtype = new JLabel("Type");
		lbpnr = new JLabel("PNR");
		
		txname = new JTextField();
		txtravel = new JTextField();
		txbusname = new JTextField();
		txbusnumber = new JTextField();
		txsource = new JTextField();
		txdestination = new JTextField();
		txdeparturetime = new JTextField();
		txarrivaltime = new JTextField();
		txseatprice = new JTextField();
		txsleeperprice = new JTextField();
		txtype = new JTextField();
		txpnr = new JTextField();
		txpnr.setText(pnr);
		
		txname.setEnabled(false);
		txtravel.setEnabled(false);
		txbusname.setEnabled(false);
		txbusnumber.setEnabled(false);
		txsource.setEnabled(false);
		txdestination.setEnabled(false);
		txdeparturetime.setEnabled(false);
		txarrivaltime.setEnabled(false);
		txseatprice.setEnabled(false);
		txsleeperprice.setEnabled(false);
		txtype.setEnabled(false);
		txpnr.setEnabled(false);
	
		txname.setBackground(Color.black);
		txtravel.setBackground(Color.black);
		txbusname.setBackground(Color.black);
		txbusnumber.setBackground(Color.black);
		txsource.setBackground(Color.black);
		txdestination.setBackground(Color.black);
		txdeparturetime.setBackground(Color.black);
		txarrivaltime.setBackground(Color.black);
		txseatprice.setBackground(Color.black);
		txsleeperprice.setBackground(Color.black);
		txtype.setBackground(Color.black);
		txpnr.setBackground(Color.black);
		
		
		
		pbusname = new JPanel();
		pbusnumber  = new JPanel();
		psource  = new JPanel();
		pdestination  = new JPanel();
		pdeparturetime  = new JPanel();
		parrivaltime  = new JPanel();
		pseatprice  = new JPanel();
		psleeperprice  = new JPanel();
		ptype  = new JPanel();
		ppnr  = new JPanel();
		
		pbusname.setLayout(new GridLayout(1,2));
		pbusnumber.setLayout(new GridLayout(1,2));
		psource.setLayout(new GridLayout(1,2));
		pdestination.setLayout(new GridLayout(1,2));
		pdeparturetime.setLayout(new GridLayout(1,2));
		parrivaltime.setLayout(new GridLayout(1,2));
		pseatprice.setLayout(new GridLayout(1,2));
		psleeperprice.setLayout(new GridLayout(1,2));
		ptype.setLayout(new GridLayout(1,2));
		ppnr.setLayout(new GridLayout(1,2));
		
		pbusname.add(lbbusname);
		pbusnumber.add(lbbusnumber);
		psource.add(lbsource); 
		pdestination.add(lbdestination);  
		pdeparturetime.add(lbdeparturetime);  
		parrivaltime.add(lbarrivaltime);  
		pseatprice.add(lbseatprice);  
		psleeperprice.add(lbsleeperprice);  
		ptype.add(lbtype);  
		ppnr.add(lbpnr);
		
		pbusname.add(txbusname);
		pbusnumber.add(txbusnumber);
		psource.add(txsource); 
		pdestination.add(txdestination);  
		pdeparturetime.add(txdeparturetime);  
		parrivaltime.add(txarrivaltime);  
		pseatprice.add(txseatprice);  
		psleeperprice.add(txsleeperprice);  
		ptype.add(txtype);  
		ppnr.add(txpnr);
		
		btproceed = new JButton("Proceed");
		btcancel = new JButton("Cancel");

		setLayout(new BorderLayout());
		pmain = new JPanel();
		p = new JPanel();

		pmain.setLayout(new GridLayout(7,2, 50, 50));
		p.setLayout(new FlowLayout());

		pmain.add(lbname);
		pmain.add(txname);
		pmain.add(lbtravel);
		pmain.add(txtravel);
		pmain.add(pbusname);
		pmain.add(pbusnumber);
		pmain.add(psource);
		pmain.add(pdestination);
		pmain.add(pdeparturetime);
		pmain.add(parrivaltime);
		pmain.add(pseatprice);
		pmain.add(psleeperprice);
		pmain.add(ptype);
		pmain.add(ppnr);

		p.add(btproceed);
		p.add(btcancel);
		add(pmain, BorderLayout.CENTER);
		add(p, BorderLayout.SOUTH);
		validate();
		revalidate();
	}
}
