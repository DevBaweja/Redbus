import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class UserTabbedBar extends JFrame implements ActionListener {

	static String user;
	JTabbedPane jtb;
	JPanel log;
	JButton logout;

	public UserTabbedBar(){

		user = LoginUser.user;
		 //user = "dev";
		
		setPreferredSize(new Dimension(1500, 900));
		setSize(new Dimension(1500, 900));
		setVisible(true);

		logout = new JButton("Logout");
		log = new JPanel();
		log.add(logout);
		
		jtb = new JTabbedPane();
		jtb.addTab("User Home", new UserHome());
		jtb.addTab("Search Bus", new User_Searching_Bus());
		jtb.addTab("Logout", log);
		
		add(jtb);
		logout.addActionListener(this);
		validate();
		revalidate();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.dispose();
	}
}
