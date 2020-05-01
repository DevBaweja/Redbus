import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class ManagerAddBus extends JPanel implements ActionListener {
	JLabel lbbuscode, lbbusname, lbbusno;
	JTextField txbuscode, txbusname, txbusno;
	JButton btsubmit, btrefresh;
	JPanel pmain,p;

	String travel;
	int travel_code;
	String manager;

	public ManagerAddBus() {

		manager = ManagerTabbedBar.manager;
		travel = ManagerHome.travel;
		travel_code = ManagerHome.travel_code;

		setPreferredSize(new Dimension(1000, 1000));
		setSize(new Dimension(1000, 1000));
		setVisible(true);

		setLayout( new BorderLayout());

		lbbuscode = new JLabel("Bus Code");
		lbbusname = new JLabel("Bus Name");
		lbbusno = new JLabel("Bus Number");

		txbuscode = new JTextField(5);
		txbuscode.setEditable(false);
		int code;
		while (true) {
			code = (int) (Math.random() * 100000);
			if (code > 10000) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
					Statement stmt = con.createStatement();
					stmt.executeUpdate("create database if not exists RedBusDb");
					stmt.execute("use RedBusDb");
					stmt.executeUpdate("create table if not exists Travel" + travel_code
							+ "(bus_code int(5),bus_name varchar(50),bus_number varchar(20) unique,primary key(bus_code))");

					PreparedStatement pstmt = con
							.prepareStatement("select count(*) from Travel" + travel_code + " where bus_code=? ");
					pstmt.setInt(1, code);
					ResultSet rs = pstmt.executeQuery();
					rs.next();
					int c = rs.getInt(1);
					if (c == 0)
						break;
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
		txbuscode.setText(Integer.toString(code));
		txbusname = new JTextField(10);
		txbusno = new JTextField(10);

		btsubmit = new JButton("Add Bus");
		btrefresh = new JButton("Refresh");

		pmain = new JPanel();
		p = new JPanel();
		pmain.setLayout(new GridLayout(4, 2, 200, 200));
		p.setLayout(new FlowLayout());
		pmain.add(lbbuscode);
		pmain.add(txbuscode);
		pmain.add(lbbusname);
		pmain.add(txbusname);
		pmain.add(lbbusno);
		pmain.add(txbusno);
		pmain.add(new JPanel());
		pmain.add(new JPanel());
		p.add(btsubmit);
		p.add(btrefresh);
		
		add(pmain,BorderLayout.CENTER);
		add(p,BorderLayout.SOUTH);

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
			int code;
			while (true) {
				code = (int) (Math.random() * 100000);
				if (code > 10000) {
					try {
						Class.forName("com.mysql.jdbc.Driver");
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
						Statement stmt = con.createStatement();
						stmt.executeUpdate("create database if not exists RedBusDb");
						stmt.execute("use RedBusDb");
						stmt.executeUpdate("create table if not exists Travel" + travel_code
								+ "(bus_code int(5),bus_name varchar(50),bus_number varchar(20) unique,primary key(bus_code))");

						PreparedStatement pstmt = con
								.prepareStatement("select count(*) from Travel" + travel_code + " where bus_code=? ");
						pstmt.setInt(1, code);
						ResultSet rs = pstmt.executeQuery();
						rs.next();
						int c = rs.getInt(1);
						if (c == 0)
							break;
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

			txbuscode.setText(Integer.toString(code));
			txbusname.setText("");
			txbusno.setText("");
		} else if (src == btsubmit) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
				Statement stmt = con.createStatement();
				stmt.executeUpdate("create database if not exists RedBusDb");
				stmt.execute("use RedBusDb");
				stmt.executeUpdate("create table if not exists Travel" + travel_code
						+ "(bus_code int(5),bus_name varchar(50),bus_number varchar(20) unique,primary key(bus_code))");

				PreparedStatement pstmt = con.prepareStatement(
						"insert into Travel"+travel_code+"(bus_code,bus_name,bus_number) values(?,?,?)");
				pstmt.setInt(1, Integer.parseInt(txbuscode.getText()));
				pstmt.setString(2, txbusname.getText());
				pstmt.setString(3, txbusno.getText());
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
