import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class Deposit extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			HashMap<String, String[]> empty = new HashMap<String, String[]>();
			HashMap<String, String> empty2 = new HashMap<String, String>();
			String[] emptyArr = null;
			Deposit dialog = new Deposit("empty", emptyArr, empty, empty2);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Deposit(String acc, String[] current, HashMap<String, String[]> accountInfo, HashMap<String, String> accounts) {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		{
			JLabel lblPutInAn = new JLabel("Put in an amount to deposit");
			contentPanel.add(lblPutInAn);
		}
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.CENTER);
			{
				textField = new JTextField();
				panel.add(textField);
				textField.setColumns(10);
			}
		}
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.SOUTH);
			{
				JButton btnOK = new JButton("OK");
				btnOK.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						depositMoney();
					}

					private void depositMoney() {
						int change;
						try {
						    change = Integer.parseInt(textField.getText());
						} catch (NumberFormatException e1) {
						    System.out.println("Wrong number");
						    change = 0;
						}
						if (change <= 0) {
							JFrame frame = new JFrame();
							JOptionPane.showMessageDialog(frame, "Please enter an integer greater than 0.");
						} else {
							int balance = Integer.parseInt(current[2]);
							int newBalance = balance + change;
							System.out.println(newBalance);
							current[2] = Integer.toString(newBalance);
							accountInfo.put(acc, current);
							overwriteAccountInfo(accountInfo, accounts, current, balance);
						}
						
					}

					private void overwriteAccountInfo(HashMap<String, String[]> accountInfo, HashMap<String, String> accounts, String[] current, int balance) {
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
				panel.add(btnOK);
			}
			{
				JButton btnCancel = new JButton("Cancel");
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				panel.add(btnCancel);
			}
		}
	}

}
