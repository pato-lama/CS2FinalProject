import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.UIManager;

public class ChangePassword extends JDialog {
	private JTextField currentPasswordText;
	private JPasswordField newPasswordText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HashMap<String, String> empty = new HashMap<String, String>();
					
					ChangePassword dialog = new ChangePassword("", empty);
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
	public ChangePassword(String acc, HashMap<String, String> accounts) {
		setBounds(100, 100, 450, 300);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel lblChangeYourPassword = new JLabel("Change your password (restart is required for change to take effect)");
		panel.add(lblChangeYourPassword);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String currPass = currentPasswordText.getText();
				if (currPass.equals(accounts.get(acc))) {
					String newPass = newPasswordText.getText();
					if (newPass.equals("")) {
						JFrame frame = new JFrame();
						JOptionPane.showMessageDialog(frame, "Please enter a new password.");
					} else {
						accounts.put(acc, newPass);
						overwritePassword(acc, currPass, newPass);
					}
				}
			}

			private void overwritePassword(String acc, String currPass, String newPass) {
				String line = "", oldText = "";
				
				try {
					BufferedReader reader = new BufferedReader(new FileReader("Password.txt"));
					int count = 0;
					while ((line = reader.readLine()) != null)	{
						oldText += line + "\r\n";
					}
					reader.close();
					System.out.println(oldText);
					String replacedText = oldText.replaceAll(acc + " " + currPass, acc + " " + newPass );
					FileWriter writer = new FileWriter("Password.txt");
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
		panel_2.setLayout(null);
		getContentPane().add(panel_2, BorderLayout.CENTER);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 11, 74, 21);
		panel_2.add(lblPassword);
		
		currentPasswordText = new JTextField();
		currentPasswordText.setColumns(10);
		currentPasswordText.setBounds(106, 11, 207, 20);
		panel_2.add(currentPasswordText);
		
		JLabel lblNewPassword = new JLabel("New Password");
		lblNewPassword.setBounds(10, 43, 74, 21);
		panel_2.add(lblNewPassword);
		
		newPasswordText = new JPasswordField();
		newPasswordText.setFont(UIManager.getFont("PasswordField.font"));
		newPasswordText.setColumns(10);
		newPasswordText.setBounds(106, 43, 207, 20);
		panel_2.add(newPasswordText);

	}

}
