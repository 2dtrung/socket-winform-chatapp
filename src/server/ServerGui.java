package server;

import java.awt.EventQueue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.JButton;
import java.awt.TextArea;
import java.awt.Font;

import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.ImageIcon;

public class ServerGui {

	public static int port = 3456;
	private JFrame frmServerMangement;
	private JTextField txtIP, txtPort;
	private JLabel lblStatus;
	private static TextArea txtMessage;
	public static JLabel lblUserOnline;
	ServerCore server;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGui window = new ServerGui();
					window.frmServerMangement.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ServerGui() {
		initialize();
	}
	
	public static String getLabelUserOnline() {
		return lblUserOnline.getText();
	}
	public static void updateMessage(String msg) {
		txtMessage.append(msg + "\n");
	}

	public static void updateNumberClient() {
		int number = Integer.parseInt(lblUserOnline.getText());
		lblUserOnline.setText(Integer.toString(number + 1));
	}
	
	public static void decreaseNumberClient() {
		int number = Integer.parseInt(lblUserOnline.getText());
		lblUserOnline.setText(Integer.toString(number - 1));

	}

	private void initialize() {
		frmServerMangement = new JFrame();
		frmServerMangement.getContentPane().setBackground(Color.WHITE);
		frmServerMangement.setForeground(UIManager.getColor("RadioButtonMenuItem.foreground"));
		frmServerMangement.getContentPane().setFont(new Font("Segoe UI", Font.PLAIN, 13));
		frmServerMangement.getContentPane().setForeground(UIManager.getColor("RadioButtonMenuItem.acceleratorSelectionForeground"));
		frmServerMangement.setTitle("Server Mangement");
		frmServerMangement.setResizable(false);
		frmServerMangement.setBounds(200, 200, 730, 686);
		frmServerMangement.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmServerMangement.getContentPane().setLayout(null);
		frmServerMangement.setBackground(Color.ORANGE);

		JLabel lblIP = new JLabel("IP Address");
		lblIP.setIcon(new ImageIcon(ServerGui.class.getResource("/image1/IP.png")));
		lblIP.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblIP.setBounds(26, 5, 111, 28);					////// Vi tri lbl IP
		frmServerMangement.getContentPane().add(lblIP);

		txtIP = new JTextField();
		txtIP.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		txtIP.setEditable(false);
		txtIP.setBounds(147, 5, 107, 28);				////// Vi tri text Ip
		frmServerMangement.getContentPane().add(txtIP);
		txtIP.setColumns(10);
		try {
			txtIP.setText(Inet4Address.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		JLabel lblNewLabel = new JLabel("Port Server");
		lblNewLabel.setIcon(new ImageIcon(ServerGui.class.getResource("/image1/port.png")));
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblNewLabel.setBounds(26, 47, 111, 22);			////// Vi tri lbl Port
		frmServerMangement.getContentPane().add(lblNewLabel);

		txtPort = new JTextField();
		txtPort.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		txtPort.setEditable(false);
		txtPort.setColumns(10);
		txtPort.setBounds(147, 41, 48, 28);
		frmServerMangement.getContentPane().add(txtPort);			///// Vi tri cua text Port
		txtPort.setText("3456");

		JButton btnStart = new JButton("");
		btnStart.setBackground(UIManager.getColor("RadioButtonMenuItem.selectionBackground"));
		btnStart.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		
		btnStart.setBounds(222, 155, 48, 43);			/////// Vi tri button START
		frmServerMangement.getContentPane().add(btnStart);
		btnStart.setIcon(new ImageIcon(ServerGui.class.getResource("/image1/start.png")));

		txtMessage = new TextArea();					
		txtMessage.setBackground(Color.BLACK);
		txtMessage.setForeground(Color.GREEN);
		txtMessage.setFont(new Font("Consolas", Font.PLAIN, 14));
		txtMessage.setEditable(false);
		txtMessage.setBounds(0, 267, 714, 358);		////// Vi tri textArea
		frmServerMangement.getContentPane().add(txtMessage);

		JButton btnStop = new JButton("");
		btnStop.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		btnStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				lblUserOnline.setText("0");
				try {
					server.stopserver();
					ServerGui.updateMessage("STOP SERVER");
					lblStatus.setText("<html><font color='red'>OFF</font></html>");
				} catch (Exception e) {
					e.printStackTrace();
					ServerGui.updateMessage("STOP SERVER");
					lblStatus.setText("<html><font color='red'>OFF</font></html>");
				}
			}
		});
		btnStop.setBounds(471, 155, 48, 43);						//// Vi tri button Stop
		frmServerMangement.getContentPane().add(btnStop);
		btnStop.setIcon(new ImageIcon(ServerGui.class.getResource("/image1/stop.png")));
		
		JLabel lblnew111 = new JLabel("ACTIVE STATUS");
		lblnew111.setIcon(new ImageIcon(ServerGui.class.getResource("/image1/status.png")));
		lblnew111.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblnew111.setBounds(422, 8, 133, 25);
		frmServerMangement.getContentPane().add(lblnew111);
		
		lblStatus = new JLabel("New label");
		lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblStatus.setBounds(565, 14, 79, 16);


		frmServerMangement.getContentPane().add(lblStatus);
		lblStatus.setText("<html><font color='red'>OFF</font></html>");
		
		JLabel lblRecord = new JLabel("LOG");
		lblRecord.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblRecord.setBounds(422, 88, 89, 16);
		frmServerMangement.getContentPane().add(lblRecord);
		
		JLabel lbllabelUserOnline = new JLabel("USER ONLINE");
		lbllabelUserOnline.setIcon(new ImageIcon(ServerGui.class.getResource("/image1/userOnline.png")));
		lbllabelUserOnline.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lbllabelUserOnline.setBounds(422, 45, 111, 27);
		frmServerMangement.getContentPane().add(lbllabelUserOnline);
		
		lblUserOnline = new JLabel("0");
		lblUserOnline.setForeground(Color.BLUE);
		lblUserOnline.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblUserOnline.setBounds(565, 50, 56, 16);
		frmServerMangement.getContentPane().add(lblUserOnline);
		
		JLabel lblStart = new JLabel("START");
		lblStart.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblStart.setBounds(232, 199, 89, 16);
		frmServerMangement.getContentPane().add(lblStart);
		
		JLabel lblStop = new JLabel("STOP");
		lblStop.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblStop.setBounds(481, 199, 89, 16);
		frmServerMangement.getContentPane().add(lblStop);
		
		JMenuBar menuBar = new JMenuBar();
		frmServerMangement.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Help");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmMe = new JMenuItem("Contact");
		mnNewMenu.add(mntmMe);
		
		JMenuItem mntmContribute = new JMenuItem("Contribute");
		mnNewMenu.add(mntmContribute);
		
		JMenuItem mntmAboutServer = new JMenuItem("About Server");
		mnNewMenu.add(mntmAboutServer);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnNewMenu.add(mntmExit);
		
		
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					server = new ServerCore(3456);
					ServerGui.updateMessage("START SERVER");
					lblStatus.setText("<html><font color='green'>ON</font></html>");
				} catch (Exception e) {
					ServerGui.updateMessage("START ERROR");
					e.printStackTrace();
				}
			}
		});
	}
}

