import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.awt.event.ActionEvent;

public class MakeATransfer extends JDialog {
	private JTextField accountIDText;
	private JTextField amountText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HashMap<String, String[]> empty = new HashMap<String, String[]>();
					String[] emptyArr = null;
					MakeATransfer dialog = new MakeATransfer("", emptyArr, empty);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public MakeATransfer(String acc, String[] current, HashMap<String, String[]> accountInfo) {
		setBounds(100, 100, 450, 300);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel lblEnterAnAccount = new JLabel("Enter an Account ID to send money to");
		panel.add(lblEnterAnAccount);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String recipient = accountIDText.getText();
				if (accountInfo.containsKey(recipient)) {
					transferMoney(recipient);
				} else {
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "Account doesn't exist.");
				}
				
				
			}


			private void transferMoney(String recipient) {
				int change;
				try {
				    change = Integer.parseInt(amountText.getText());
				} catch (NumberFormatException e1) {
				    System.out.println("Wrong number");
				    change = 0;
				}
				if (change <= 0) {
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "Please enter an integer greater than 0.");
				} else {
					int balance = Integer.parseInt(current[2]);
					int newBalance = balance - change;
					if (newBalance < 0) {
						JFrame frame = new JFrame();
						JOptionPane.showMessageDialog(frame, "You don't have that much money to withdraw.");
					} else {
						System.out.println(newBalance);
						current[2] = Integer.toString(newBalance);
						accountInfo.put(acc, current);
						overwriteAccountInfo(accountInfo, current, balance);
						
						String[] transferAcc = accountInfo.get(recipient);
						int transferBalance = Integer.parseInt(transferAcc[2]);
						int newTransferBalance = transferBalance + change;
						transferAcc[2] = Integer.toString(newTransferBalance);
						accountInfo.put(acc, transferAcc);
						overwriteAccountInfo(accountInfo, transferAcc, transferBalance);
						
					}
				}
				
			}

			private void overwriteAccountInfo(HashMap<String, String[]> accountInfo, String[] current, int balance) {
				String line = "", oldText = "";
				
				try {
					BufferedReader reader = new BufferedReader(new FileReader("AccountInformation.txt"));
					
					while ((line = reader.readLine()) != null)	{
						oldText += line + "\r\n";
					}
					reader.close();
					System.out.println(oldText);
					String replacedText = oldText.replaceAll(current[0] + "\r\n" + current[1] + "\r\n" + balance + "\r\n" + current[3] + "\r\n" , current[0] + "\r\n" + current[1] + "\r\n" + current[2] + "\r\n" + current[3] + "\r\n");
					FileWriter writer = new FileWriter("AccountInformation.txt");
					writer.write(replacedText);
					writer.close();
					dispose();
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
		});
		panel_1.add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel_1.add(btnCancel);
		
		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2, BorderLayout.CENTER);
		
		JLabel lblAccountid = new JLabel("AccountID");
		panel_2.add(lblAccountid);
		
		accountIDText = new JTextField();
		panel_2.add(accountIDText);
		accountIDText.setColumns(10);
		
		JSeparator separator = new JSeparator();
		panel_2.add(separator);
		
		JLabel lblMoney = new JLabel("Amount");
		panel_2.add(lblMoney);
		
		amountText = new JTextField();
		panel_2.add(amountText);
		amountText.setColumns(10);

	}

}
