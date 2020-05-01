import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class User_Select_Place extends JDialog implements ActionListener {

	JPanel pmain, p;
	JPanel pseat,psleeper;
	
	JButton btset, btcancel;
	JButton btseat[],btsleeper[];
	boolean  recheckseat[],rechecksleeper[];
	int seat,sleeper;
	
	
	String user;
	String pnr;
	String date;

	public User_Select_Place()
	{

		user = User_View_Bus.user;
		pnr = User_View_Bus.pnr;
		
		date = User_Book_Bus.date;
		
		setPreferredSize(new Dimension(900, 1000));
		setSize(new Dimension(900, 1000));
		setVisible(true);
		
		getseatsleeper();
		
		btseat = new JButton[seat];
		btsleeper = new JButton[sleeper];
		
		recheckseat = new boolean[btseat.length]; 
		rechecksleeper = new boolean[btsleeper.length]; 
		
		for (int i = 0; i < btseat.length; i++) 
			btseat[i] = new JButton("A"+(i+1));
		for (int i = 0; i < btsleeper.length; i++) 
			btsleeper[i] = new JButton("B"+(i+1));
		
		
		int m=0;
		if(seat%2==0)
			m = seat/2;
		else 
			m = seat/2+1;
		int n=0;
		if(sleeper%2==0)
			n = sleeper/2;
		else 
			n = sleeper/2+1;
			
		
		pseat = new JPanel();
		psleeper = new JPanel();
		pseat.setLayout(new GridLayout(m,2,50,50));
		psleeper.setLayout(new GridLayout(n,2,50,50));
		
		for (int i = 0; i < btseat.length; i++) 
			pseat.add(btseat[i]);
		for (int i = 0; i < btsleeper.length; i++) 
			psleeper.add(btsleeper[i]);
		
		checkprevious();
		
		for (int i = 0; i < btseat.length; i++) 
			btseat[i].addActionListener(this);
		for (int i = 0; i < btsleeper.length; i++) 
			btsleeper[i].addActionListener(this);
		
		btset = new JButton("Set");
		btcancel = new JButton("Cancel");
		
		setLayout(new BorderLayout());
		p = new JPanel();
		p.setLayout(new FlowLayout());
		p.add(btset);
		p.add(btcancel);
		
		add(p,BorderLayout.SOUTH);
		
		pmain = new JPanel();
		pmain.setLayout(new GridLayout(1,3));
		pmain.add(pseat);
		pmain.add(new JPanel());
		pmain.add(psleeper);
		add(pmain,BorderLayout.CENTER);
		
		style();
		btset.addActionListener(this);
		btcancel.addActionListener(this);
		
		validate();
		revalidate();
	}
	
	public void checkprevious() {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
			Statement stmt = con.createStatement();
			stmt.executeUpdate("create database if not exists RedBusDb");
			stmt.execute("use RedBusDb");			
			stmt.executeUpdate(
					"create table if not exists Ticket"+pnr+"_"+date+" (username varchar(50),seat int(3),seat_tag varchar(50),sleeper int(3),sleeper_tag varchar(50) )");
			
		
			PreparedStatement pstmt = con.prepareStatement("select  seat,seat_tag,sleeper,sleeper_tag from Ticket"+pnr+"_"+date+" ");
			ResultSet rs = pstmt.executeQuery();
			String disableseat="",disablesleeper="";
			
			while(rs.next())
			{
				if(!rs.getString("seat_tag").equals(""))
				disableseat = disableseat+(rs.getString("seat_tag")+" ");
				if(!rs.getString("sleeper_tag").equals(""))
				disablesleeper = disablesleeper+(rs.getString("sleeper_tag")+" ");
			}
			
			disableseat = disableseat.replace(' ','_');
			disablesleeper = disablesleeper.replace(' ', '_');
		
			String dseat[],dsleeper[];
			dseat = disableseat.split("_");
			dsleeper = disablesleeper.split("_");
			
			/*or (int i = 0; i < dseat.length; i++) {
				System.out.println(dseat[i]);
			}
			for (int i = 0; i < dsleeper.length; i++) {
				System.out.println(dsleeper[i]);
			}*/
		
			for (int i = 0; i < btseat.length; i++) 
			{
				for (int j = 0; j < dseat.length; j++) 
				{
					if(!btseat[i].getText().equals(dseat[j]))
					{
						btseat[i].setBackground(Color.white);
						btseat[i].setForeground(Color.black);
					}
				}
			}
			
			for (int i = 0; i < btseat.length; i++) 
			{
				for (int j = 0; j < dseat.length; j++) 
				{
					if(btseat[i].getText().equals(dseat[j]))
					{
						btseat[i].setEnabled(false);
						btseat[i].setBackground(Color.black);
						btseat[i].setForeground(Color.white);
					}
				}
			}
			
			for (int i = 0; i < btsleeper.length; i++) 
			{
				for (int j = 0; j < dsleeper.length; j++) 
				{
					if(!btsleeper[i].getText().equals(dsleeper[j]))
					{
						btsleeper[i].setBackground(Color.white);
						btsleeper[i].setForeground(Color.black);
					}
				}
			}
			
			for (int i = 0; i < btsleeper.length; i++) 
			{
				for (int j = 0; j < dsleeper.length; j++) 
				{
					if(btsleeper[i].getText().equals(dsleeper[j]))
					{
						btsleeper[i].setEnabled(false);
						btsleeper[i].setBackground(Color.black);
						btsleeper[i].setForeground(Color.white);
					}
				}
			}
			
	
		
			con.close();

		} catch (SQLException ae) {
			// TODO Auto-generated catch block
			ae.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		for (int i = 0; i < btseat.length; i++) {
			if(src == btseat[i] && !recheckseat[i])
			{
				recheckseat[i] = true;
				btseat[i].setBackground(Color.red);
				btseat[i].setForeground(Color.white);
			}
			else if(src == btseat[i] && recheckseat[i])
			{
				recheckseat[i] = false;
				btseat[i].setBackground(Color.white);
				btseat[i].setForeground(Color.black);
			}
			
		}
		
		for (int i = 0; i < btsleeper.length; i++) {
			if(src == btsleeper[i] && !rechecksleeper[i])
			{
				rechecksleeper[i] = true;
				btsleeper[i].setBackground(Color.red);
				btsleeper[i].setForeground(Color.white);
			}
			else if(src == btsleeper[i] && rechecksleeper[i])
			{
				rechecksleeper[i] = false;
				btsleeper[i].setBackground(Color.white);
				btsleeper[i].setForeground(Color.black);
			}
			
		}
			
		 if(src == btset)
		{
		
			 int h=0,k=0;
			 String seat="",sleeper="";
			 for (int i = 0; i < recheckseat.length; i++) 
			 {
				 if(recheckseat[i])
				 {
					 if(seat.equals(""))
						 seat = "A"+(i+1);
					 else 
						 seat += " A"+(i+1);
					h++; 
				 }
			 }
				 
			 for (int i = 0; i < rechecksleeper.length; i++) 
			 {
				 if(rechecksleeper[i])
				 {
					 if(sleeper.equals(""))
						 sleeper = "B"+(i+1);
					 else 
						 sleeper += " B"+(i+1);
					k++; 
				 }
			 }
			 
			 User_Book_Bus.seatno = Integer.toString(h);
			 User_Book_Bus.sleeperno = Integer.toString(k);
			 User_Book_Bus.seattag = seat;
			 User_Book_Bus.sleepertag = sleeper;
			 
			 this.dispose();
		}
		 if(src == btcancel)
		{
			this.dispose();
		}
	}
	

	
	private void getseatsleeper() {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
			Statement stmt = con.createStatement();
			stmt.executeUpdate("create database if not exists RedBusDb");
			stmt.execute("use RedBusDb");
			
			PreparedStatement pstmt = con.prepareStatement("select seat,sleeper from TripDetailTb where pnr=? ");
			pstmt.setInt(1, Integer.parseInt(pnr));
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			seat = rs.getInt("seat");
			sleeper = rs.getInt("sleeper");
			con.close();

		} catch (SQLException ae) {
			// TODO Auto-generated catch block
			ae.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void style() {
		 Font f  = new Font("comic sans",Font.ITALIC+Font.BOLD ,25 ); 
	        Border loweredbevel = BorderFactory.createLoweredBevelBorder();

	        Border h1 = BorderFactory.createTitledBorder(loweredbevel,":: Seats ::", TitledBorder.CENTER , TitledBorder.TOP ,f,Color.green);
	        Border h2 = BorderFactory.createTitledBorder(loweredbevel,":: Sleeper ::", TitledBorder.CENTER , TitledBorder.TOP ,f,Color.green);
	        Border k1 = BorderFactory.createMatteBorder(0,10,0,0,Color.yellow);
	        Border k2 = BorderFactory.createMatteBorder(0,0,0,10,Color.yellow);
	        pseat.setBorder(BorderFactory.createCompoundBorder(h1, k1));
	        psleeper.setBorder(BorderFactory.createCompoundBorder(h2, k2));
	        
	}

}
