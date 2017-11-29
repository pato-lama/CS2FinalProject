import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class AccountMenu extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AccountMenu dialog = new AccountMenu("test");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * @param acc 
	 */
	public AccountMenu(String acc) {
		HashMap<String, String> accounts = new HashMap<String, String>();
		getAccounts(accounts);
		
		HashMap<String, String[]> accountInfo = new HashMap<String, String[]>();
		getInformation(accountInfo, acc);
		
		String[] current = accountInfo.get(acc);
		for (String str : current) {
			System.out.println(str);
		}
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(23, 27, 129, 41);
		contentPanel.add(panel);
		
		JButton btnNewButton = new JButton("Deposit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Deposit newDeposit = new Deposit();
				newDeposit.setVisible(true);
			}
		});
		panel.add(btnNewButton);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(23, 91, 129, 41);
		contentPanel.add(panel_1);
		
		JButton btnWithdraw = new JButton("Withdraw");
		panel_1.add(btnWithdraw);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(23, 158, 129, 41);
		contentPanel.add(panel_2);
		
		JButton btnChangePassword = new JButton("Change Password");
		panel_2.add(btnChangePassword);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(162, 27, 129, 41);
		contentPanel.add(panel_3);
		
		JButton btnCheckBalance = new JButton("Check Balance");
		panel_3.add(btnCheckBalance);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(162, 91, 129, 41);
		contentPanel.add(panel_4);
		
		JButton btnMakeATransfer = new JButton("Make A Transfer");
		panel_4.add(btnMakeATransfer);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(162, 158, 129, 41);
		contentPanel.add(panel_5);
		
		JButton btnLogOff = new JButton("Log Off");
		panel_5.add(btnLogOff);
	}

	private void getInformation(HashMap<String, String[]> accountInfo, String acc) {
		String line = null;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader("AccountInformation.txt"));
			
			while ((line = reader.readLine()) != null)	{
				String account = line;
				int count = 0;
				String[] words = new String[4];
				while (count < 4) {
					words[count] = (line = reader.readLine());
					count++;
				}
				accountInfo.put(account, words);
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
