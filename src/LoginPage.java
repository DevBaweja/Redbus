import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class LoginPage extends JFrame implements ActionListener {
	JPanel Pbutton, Pmain;
	JButton btFirst, btLast;
	CardLayout CLO;

	public LoginPage() {
		setSize(new Dimension(1500,1000));
		setPreferredSize(new Dimension(1500,1000));
		setVisible(true);
		setLayout(new BorderLayout());

		Pbutton = new JPanel();
		Pbutton.setLayout(new GridLayout(1, 2,40,0));
		btFirst = new JButton("FOR USER");
		btLast = new JButton("FOR MANAGER");

		Pbutton.add(btFirst);
		Pbutton.add(btLast);
		add(Pbutton, BorderLayout.WEST);

		CLO = new CardLayout();
		Pmain = new JPanel();
		Pmain.setLayout(CLO);
		Pmain.add(new LoginUser());
		Pmain.add(new LoginManager());

		add(Pmain, BorderLayout.CENTER);

		btFirst.addActionListener(this);
		btLast.addActionListener(this);

		style();
		validate();
		revalidate();
		
	}

	public void style() {

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		Object src = ae.getSource();
		if (src == btFirst) {
			CLO.first(Pmain);
		} else if (src == btLast) {
			CLO.last(Pmain);
		}

	}

	public static void main(String[] args) {
		new LoginPage();

	}

}
