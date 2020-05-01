import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ManagerTabbedBar extends JFrame implements ActionListener {

	static String manager;
	JTabbedPane jtb;
	JPanel log;
	JButton logout;

	 public ManagerTabbedBar()
	{

		//manager = LoginManager.manager;
		 manager = "karni";
		
		setPreferredSize(new Dimension(1500, 900));
		setSize(new Dimension(1500, 900));
		setVisible(true);

		logout = new JButton("Logout");
		log = new JPanel();
		log.add(logout);
		
		jtb = new JTabbedPane();
		jtb.addTab("Home", new ManagerHome());
		jtb.addTab("Add Bus", new ManagerAddBus() );
		jtb.addTab("Bus Trip",new ManagerAddBusTrip());
		jtb.addTab("Bus Details", new ManagerAddBusTripDetails());
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
