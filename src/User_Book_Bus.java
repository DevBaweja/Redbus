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

public class User_Book_Bus extends JFrame implements ActionListener, ItemListener {
	JLabel lbname, lbplace, lbdate, lbseatno, lbsleeperno, lbseattag, lbsleepertag, lbfinalprice;
	JTextField txname, txdate, txseatno, txsleeperno, txseattag, txsleepertag, txfinalprice;
	JButton btplace;

	JPanel pplace, pdate, pseatno, psleeperno, pseattag, psleepertag;
	String seatprice, sleeperprice, name;

	static String seatno, sleeperno, seattag, sleepertag;

	JButton btshow;
	JPanel pmain, p, Pdob;

	JButton btbook, btcancel;
	String user;
	String pnr;
	static String date;

	JComboBox chyy, chmm, chdd;

	public User_Book_Bus() {

		user = User_View_Bus.user;
		pnr = User_View_Bus.pnr;
		seatprice = User_View_Bus.seatprice;
		sleeperprice = User_View_Bus.sleeperprice;
		name = User_View_Bus.name;

		setPreferredSize(new Dimension(1500, 900));
		setSize(new Dimension(1500, 900));
		setVisible(true);

		set();

		btbook.setEnabled(false);
		btplace.setEnabled(false);
		btshow.addActionListener(this);
		btbook.addActionListener(this);
		btplace.addActionListener(this);
		btcancel.addActionListener(this);
		chyy.addItemListener(this);
		chmm.addItemListener(this);
		chdd.addItemListener(this);

		// do it after item listener
		chyy.setSelectedItem("2019");
		chyy.setEnabled(false);

		chmm.setSelectedItem("7"); // for july
		chmm.setEnabled(false);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		if (src == btbook) {
			
			bookticket();
		//	System.out.println("Booking");
		} else if (src == btplace) {
			date  = chdd.getSelectedItem().toString();
			new User_Select_Place();
			btshow.setEnabled(true);
			btbook.setEnabled(false);
		} else if (src == btshow) {
			
			if(seatno!=null || sleeperno!=null)
			{
				txseatno.setText(seatno);
				txsleeperno.setText(sleeperno);
				txseattag.setText(seattag);
				txsleepertag.setText(sleepertag);
				int total = Integer.parseInt(seatno) * Integer.parseInt(seatprice)
						+ Integer.parseInt(sleeperno) * Integer.parseInt(sleeperprice);
				txfinalprice.setText(Integer.toString(total));
				
				// also set total
				if (txfinalprice.getText().equals("0"))
				btbook.setEnabled(false);
				else
				btbook.setEnabled(true);
			}
			
		} else if (src == btcancel) {
			this.dispose();
		}

	}

	public void bookticket() {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
			Statement stmt = con.createStatement();
			stmt.executeUpdate("create database if not exists RedBusDb");
			stmt.execute("use RedBusDb");			
			stmt.executeUpdate(
					"create table if not exists Ticket"+pnr+"_"+date+" (username varchar(50),seat int(3),seat_tag varchar(50),sleeper int(3),sleeper_tag varchar(50) )");
			
		
			PreparedStatement pstmt = con.prepareStatement("insert into Ticket"+pnr+"_"+date+"(username,seat,seat_tag,sleeper,sleeper_tag) values(?,?,?,?,?)");
			pstmt.setString(1, user);
			pstmt.setInt(2, Integer.parseInt(txseatno.getText()));
			pstmt.setString(3, txseattag.getText());
			pstmt.setInt(4, Integer.parseInt(txsleeperno.getText()));
			pstmt.setString(5, txsleepertag.getText());
			
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

	public void set() {
		lbname = new JLabel("Name");
		lbplace = new JLabel("Place");
		lbdate = new JLabel("Date");
		lbseatno = new JLabel("Seat Numbers");
		lbsleeperno = new JLabel("Sleeper Numbers");
		lbseattag = new JLabel("Seat Tags");
		lbsleepertag = new JLabel("Sleeper Tags");
		lbfinalprice = new JLabel("Final Price");

		txname = new JTextField();
		txname.setText(name);
		btplace = new JButton("Select");
		txdate = new JTextField();
		txseatno = new JTextField();
		txsleeperno = new JTextField();
		txseattag = new JTextField();
		txsleepertag = new JTextField();
		txfinalprice = new JTextField();

		txseatno.setForeground(Color.green);
		txsleeperno.setForeground(Color.green);
		txseattag.setForeground(Color.green);
		txsleepertag.setForeground(Color.green);
		txfinalprice.setForeground(Color.green);
		txname.setBackground(Color.black);
		txseatno.setBackground(Color.black);
		txsleeperno.setBackground(Color.black);
		txseattag.setBackground(Color.black);
		txsleepertag.setBackground(Color.black);
		txfinalprice.setBackground(Color.black);

		chyy = new JComboBox();
		chmm = new JComboBox();
		chdd = new JComboBox();
		chyy.addItem("Year");
		for (int i = 2010; i <= 2030; i++)
			chyy.addItem(i + "");

		chmm.addItem("Month");
		for (int i = 1; i <= 12; i++)
			chmm.addItem(i + "");

		chdd.addItem("Date");
		Pdob = new JPanel();
		Pdob.setLayout(new GridLayout(1, 3));
		// according to month and year date must change
		Pdob.add(chyy);
		Pdob.add(chmm);
		Pdob.add(chdd);

		txname.setEnabled(false);
		txseatno.setEnabled(false);
		txsleeperno.setEnabled(false);
		txseattag.setEnabled(false);
		txsleepertag.setEnabled(false);
		txfinalprice.setEnabled(false);

		pplace = new JPanel();
		pdate = new JPanel();
		pseatno = new JPanel();
		psleeperno = new JPanel();
		pseattag = new JPanel();
		psleepertag = new JPanel();

		pplace.setLayout(new GridLayout(1, 2));
		pdate.setLayout(new GridLayout(1, 2));
		pseatno.setLayout(new GridLayout(1, 2));
		psleeperno.setLayout(new GridLayout(1, 2));
		pseattag.setLayout(new GridLayout(1, 2));
		psleepertag.setLayout(new GridLayout(1, 2));

		pplace.add(lbplace);
		pdate.add(lbdate);
		pseatno.add(lbseatno);
		psleeperno.add(lbsleeperno);
		pseattag.add(lbseattag);
		psleepertag.add(lbsleepertag);

		pplace.add(btplace);
		pdate.add(Pdob);
		pseatno.add(txseatno);
		psleeperno.add(txsleeperno);
		pseattag.add(txseattag);
		psleepertag.add(txsleepertag);

		btshow = new JButton("Show");
		btshow.setEnabled(false);
		btbook = new JButton("Book");
		btcancel = new JButton("Cancel");

		setLayout(new BorderLayout());
		pmain = new JPanel();
		p = new JPanel();

		pmain.setLayout(new GridLayout(5, 2, 50, 50));
		p.setLayout(new FlowLayout());

		pmain.add(lbname);
		pmain.add(txname);
		pmain.add(pdate);
		pmain.add(pplace);
		pmain.add(pseatno);
		pmain.add(psleeperno);
		pmain.add(pseattag);
		pmain.add(psleepertag);
		pmain.add(lbfinalprice);
		pmain.add(txfinalprice);

		p.add(btshow);
		p.add(btbook);
		p.add(btcancel);
		add(pmain, BorderLayout.CENTER);
		add(p, BorderLayout.SOUTH);
		validate();
		revalidate();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		if (e.getStateChange() == ItemEvent.SELECTED) {

			if (src == chyy || src == chmm) {
				chdd.removeItemListener(this);
				if (chyy.getSelectedIndex() != 0 && chmm.getSelectedIndex() != 0) {

					int yy = Integer.parseInt(chyy.getSelectedItem().toString());
					int mm = Integer.parseInt(chmm.getSelectedItem().toString());

					// every time it come here we will remove previous entered item
					// and then it will add automatically

					// for(int i=1;i<chdd.getItemCount();i++)
					// chdd.remove(1);

					chdd.removeAllItems();
					chdd.addItem("Date");

					int days = 0;

					if (mm == 2) {
						if (yy % 4 == 0)

							days = 29;
						else
							days = 28;
					}
					else {
						if (mm == 4 || mm == 6 || mm == 9 || mm == 11)
							days = 30;
						else
							days = 31;
					}

					for (int i = 1; i <= days; i++)
						{
							if(i<10)
								chdd.addItem("0"+i);
							else 
							chdd.addItem(Integer.toString(i));
						}

				}
				chdd.addItemListener(this);
				btplace.setEnabled(false);
			} else if (src == chdd) {
				btplace.setEnabled(true);
			}
		}
	}
}
