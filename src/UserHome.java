import java.awt.Dimension;

import javax.swing.*;

public class UserHome extends JPanel {
	JLabel lbname;
	String user;
	public UserHome() {
	
		user = UserTabbedBar.user;
		lbname = new JLabel("Welcome user, "+user);
		add(lbname);
	}
}
