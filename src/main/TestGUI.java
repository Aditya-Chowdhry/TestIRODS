package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.irods.jargon.core.connection.AuthScheme;

public class TestGUI extends JFrame {

	private JPanel contentPane;
	/*
	 *String Fields:
	 */
	String homeDirectory = null;
	int port = 0;
	String host = "localhost";
	String pswd = null;
	String userName = null;
	String zone = "tempZone";
	String defaultStorageResource = null;
	String initialPath=null ;
	TestConnection tc;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestGUI frame = new TestGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void submitActionPerformed(ActionEvent e){
		host=hostText.getText().trim();
		userName=userText.getText().trim();
		if(userName.equals(null)){
			status.setText("<html><body>Username empty.<br>Please try again</body><html>");
			return;
		}
		pswd=new String(password.getPassword()).trim();
		zone=zoneText.getText().trim();
		defaultStorageResource=defaultText.getText().trim();
		initialPath=initialText.getText().trim();
		port=Integer.valueOf(portText.getText());
		tc=new TestConnection(host,port,userName,pswd,initialPath,zone,defaultStorageResource);
		status.setText(tc.connectIRODS(cbAuthScheme.getSelectedItem()));
	}
	/*
	 * Initialise authentication scheme ComboBox
	 */
	 private void initAuthSchemesCombo() {
	        cbAuthScheme.setModel(new DefaultComboBoxModel(AuthScheme.values()));
	    }
	/**
	 * Create the frame.
	 * Add the LAbels and fields
	 */
	public TestGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		above=new JPanel();
		GridLayout gd= new GridLayout(8,2);
		above.setLayout(gd);
		gd.setVgap(20);
		contentPane.add(above);
		bottom=new JPanel();
		contentPane.add(bottom,BorderLayout.SOUTH);
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.LINE_AXIS));
		bottom.setPreferredSize(new Dimension(300,100));
		setContentPane(contentPane);
		
		//Adding fields
		hostLabel=new JLabel("Hostname:");
		userLabel=new JLabel("Username:");
		passwordLabel=new JLabel("Password:");
		portLabel=new JLabel("Port:");
		zoneLabel=new JLabel("Zone:");
		defaultLabel=new JLabel("DefaultResource");
		initialLabel=new JLabel("Initial Path");
		authentication=new JLabel("Authentication:");
		
		hostText=new JTextField();
		userText=new JTextField();
		password=new JPasswordField();
		portText=new JTextField();
		zoneText=new JTextField();
		defaultText=new JTextField();
		initialText=new JTextField();
		cbAuthScheme=new JComboBox<>();
		//Authentication Scheme
		 AuthScheme scheme= (AuthScheme)cbAuthScheme.getSelectedItem();
		 initAuthSchemesCombo();
		 //Adding fields in panel
		 above.add(hostLabel);
		 above.add(hostText);
		 above.add(userLabel);
		 above.add(userText);
		 above.add(passwordLabel);
		 above.add(password);
		 above.add(portLabel);
		 above.add(portText);
		 above.add(zoneLabel);
		 above.add(zoneText);
		 above.add(initialLabel);
		 above.add(initialText);
		 above.add(defaultLabel);
		 above.add(defaultText);
		 above.add(authentication);
		 above.add(cbAuthScheme);
		 //
		 //Bottom
		 submit=new JButton("Submit");
		 submit.setPreferredSize(new Dimension(50,30));
		 bottom.add(submit);
		 submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				submitActionPerformed(arg0);
				
			}
		});
		 status=new JLabel("Status:");
		 bottom.add(status);
		 
	}
/*
 * GUI components declaration
*/
	private JPanel above,bottom;
	private JLabel hostLabel,userLabel,passwordLabel,zoneLabel,initialLabel,authentication,portLabel,defaultLabel;
	private JTextField hostText,userText,zoneText,initialText,portText,defaultText;
	private JPasswordField password;
	private JButton submit;
	private JLabel status;
	private JComboBox cbAuthScheme;
}
