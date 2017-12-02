import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class Withdraw extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			HashMap<String, String[]> empty = new HashMap<String, String[]>();
			String[] emptyArr = null;
			Withdraw dialog = new Withdraw("", emptyArr, empty);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Withdraw(String acc, String[] current, HashMap<String, String[]> accountInfo) {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		{
			JPanel panel = new JPanel();
			panel.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPanel.add(panel);
			panel.setLayout(new FlowLayout());
			{
				JLabel lblPutInAn = new JLabel("Put in an amount to withdraw");
				panel.add(lblPutInAn);
			}
		}
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.CENTER);
			{
				textField = new JTextField();
				textField.setColumns(10);
				panel.add(textField);
			}
		}
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.SOUTH);
			{
				JButton btnOK = new JButton("OK");
				btnOK.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						withdrawMoney();
					}

					private void withdrawMoney() {
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
							int newBalance = balance - change;
							if (newBalance < 0) {
								JFrame frame = new JFrame();
								JOptionPane.showMessageDialog(frame, "You don't have that much money to withdraw.");
							} else {
								System.out.println(newBalance);
								current[2] = Integer.toString(newBalance);
								accountInfo.put(acc, current);
								overwriteAccountInfo(accountInfo, current, balance);
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
