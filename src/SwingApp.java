//package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.UIManager;

public class SwingApp {

	private JFrame SwingApp;
	private JTextField AccountIDField;
	private JTextField PasswordField;

	private String LName;
	private String FName;
	private String ID;
	private String EmailAddress;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SwingApp window = new SwingApp();
					window.SwingApp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SwingApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		HashMap<String, String> accounts = new HashMap<String, String>();
		getAccounts(accounts);
		
		SwingApp = new JFrame();
		SwingApp.setBounds(100, 100, 450, 300);
		SwingApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SwingApp.getContentPane().setLayout(null);
		
		JPanel TitlePanel = new JPanel();
		TitlePanel.setBounds(10, 11, 414, 24);
		SwingApp.getContentPane().add(TitlePanel);
		
		JLabel Class = new JLabel("Account Login");
		Class.setBackground(Color.BLACK);
		Class.setForeground(Color.WHITE);
		Class.setOpaque(true);
		Class.repaint();
		TitlePanel.add(Class);
		
		JPanel DetailPanel = new JPanel();
		DetailPanel.setBounds(10, 67, 414, 86);
		SwingApp.getContentPane().add(DetailPanel);
		DetailPanel.setLayout(null);
		
		JLabel AccountID = new JLabel("Account ID");
		AccountID.setBounds(10, 11, 74, 21);
		DetailPanel.add(AccountID);
		
		AccountIDField = new JTextField();
		AccountIDField.setBounds(106, 11, 207, 20);
		DetailPanel.add(AccountIDField);
		AccountIDField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 43, 74, 21);
		DetailPanel.add(lblPassword);
		
		PasswordField = new JPasswordField();
		PasswordField.setFont(UIManager.getFont("PasswordField.font"));
		PasswordField.setColumns(10);
		PasswordField.setBounds(106, 43, 207, 20);
		DetailPanel.add(PasswordField);
		
		JButton btnClicktoEnd = new JButton("Login");
		btnClicktoEnd.setBounds(172, 164, 83, 23);
		SwingApp.getContentPane().add(btnClicktoEnd);
		btnClicktoEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent end) {
				String acc = AccountIDField.getText();
				String pass = PasswordField.getText();
				
				if (accounts.containsKey(acc)) {
					String test = accounts.get(acc);
					if (test.equals(pass)) {
						AccountMenu menu = new AccountMenu(acc);
						menu.setVisible(true);
					} else {
						JFrame frame = new JFrame();
						JOptionPane.showMessageDialog(frame, "Incorrect ID/password.");
					}
				} else {
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "Incorrect ID/password.");
				}
			}
		});
	}

	private void getAccounts(HashMap<String, String> accounts) {
		String line = null;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader("Password.txt"));
			
			while ((line = reader.readLine()) != null)	{
				String[] words = line.split(" ");
				accounts.put(words[0], words[1]);
			}
			
			reader.close();
		}
		catch (FileNotFoundException ex) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "Password file was not found.");
		}
		catch (IOException ex) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "Error reading password file.");
		}
		
		
	}
}
