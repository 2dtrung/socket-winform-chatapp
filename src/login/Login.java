package login;


import java.awt.EventQueue;
import java.util.Random;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import client.MainGui;
import tags.Encode;
import tags.Tags;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.awt.Font;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import java.awt.event.*;
import javax.swing.*;

public class Login {
 private static String NAME_FAILED = "INVALID CHARACTER";
 private static String NAME_EXSIST = "ALREADY USED  USER";
 private static String ERROR_NAME = "EMAIL IS NOT VALID";
 private static String SERVER_NOT_START = "TURN ON SERVER BEFORE START";

 private Pattern checkName = Pattern.compile("[_a-zA-Z][_a-zA-Z0-9]*");

 private JFrame frameLoginForm;
 private JTextField txtPort;
 private JLabel lblError;
 private String name = "", mail="", IP = ""; 
 private JTextField txtIP;	
 private JTextField txtUsername;
 private JButton btnLogin;
 private JTextField textMail;

 public static void main(String[] args) {
  EventQueue.invokeLater(new Runnable() {
   public void run() {
    try {
     Login window = new Login();
     window.frameLoginForm.setVisible(true);
    } catch (Exception e) {
     e.printStackTrace();
    }
   }
  });
 }

 public Login() {
  initialize();
 }

 private void initialize() {
  frameLoginForm = new JFrame();
  frameLoginForm.getContentPane().setBackground(Color.WHITE);
  frameLoginForm.setForeground(Color.LIGHT_GRAY);
  frameLoginForm.setTitle("Login Form");
  frameLoginForm.setResizable(false);
  frameLoginForm.setBounds(100, 100, 627, 343);
  frameLoginForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  frameLoginForm.getContentPane().setLayout(null);

  JLabel lblWelcome = new JLabel("CONNECT TO SERVER\r\n and LOGIN");
  lblWelcome.setBackground(Color.LIGHT_GRAY);
  lblWelcome.setForeground(Color.BLACK);
  lblWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 18));
  lblWelcome.setBounds(170, 11, 271, 48);
  frameLoginForm.getContentPane().add(lblWelcome);

  JLabel lblHostServer = new JLabel("IP Server");
  lblHostServer.setIcon(new ImageIcon(Login.class.getResource("/image1/IP.png")));
  lblHostServer.setFont(new Font("Segoe UI", Font.PLAIN, 13));
  lblHostServer.setBounds(3, 70, 101, 29);
  frameLoginForm.getContentPane().add(lblHostServer);

  JLabel lblPortServer = new JLabel("Port Server");
  lblPortServer.setIcon(new ImageIcon(Login.class.getResource("/image1/port.png")));
  lblPortServer.setFont(new Font("Segoe UI", Font.PLAIN, 13));
  lblPortServer.setBounds(3, 110, 106, 30);
  frameLoginForm.getContentPane().add(lblPortServer);

  txtPort = new JTextField();
  txtPort.setFont(new Font("Segoe UI", Font.PLAIN, 13));
  txtPort.setText("3456");
  txtPort.setEditable(false);
  txtPort.setColumns(10);
  txtPort.setBounds(114, 110, 43, 31);
  frameLoginForm.getContentPane().add(txtPort);

  

  lblError = new JLabel("");
  lblError.setBounds(66, 287, 399, 20);
  frameLoginForm.getContentPane().add(lblError);

  txtIP = new JTextField();
  txtIP.setBounds(114, 71, 166, 28);
  frameLoginForm.getContentPane().add(txtIP);
  txtIP.setColumns(10);
  
  JLabel lblUserName = new JLabel("User Name");
  lblUserName.setIcon(new ImageIcon(Login.class.getResource("/image1/user.png")));
  lblUserName.setFont(new Font("Segoe UI", Font.PLAIN, 13));
  lblUserName.setBounds(297, 65, 106, 38);
  frameLoginForm.getContentPane().add(lblUserName);

  txtUsername = new JTextField();
  txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 13));
  txtUsername.setColumns(10);
  txtUsername.setBounds(413, 69, 185, 30);
  frameLoginForm.getContentPane().add(txtUsername);
  
  JLabel lblPassword = new JLabel("Mail");
  lblPassword.setIcon(new ImageIcon(Login.class.getResource("/image1/status.png")));
  lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
  lblPassword.setBounds(297, 114, 106, 38);
  frameLoginForm.getContentPane().add(lblPassword);
  
  textMail = new JTextField();
  textMail.setFont(new Font("Segoe UI", Font.PLAIN, 13));
  textMail.setColumns(10);
  textMail.setBounds(413, 118, 185, 30);
  frameLoginForm.getContentPane().add(textMail);
  
 
  btnLogin = new JButton("");
  btnLogin.setBackground(Color.GRAY);
  btnLogin.setFont(new Font("Segoe UI", Font.PLAIN, 13));
  btnLogin.setIcon(new ImageIcon(Login.class.getResource("/image1/login.png")));
  btnLogin.addActionListener(new ActionListener() {

   public void actionPerformed(ActionEvent arg0) {
    name = txtUsername.getText();
    mail = textMail.getText();
    lblError.setVisible(false);
    IP = txtIP.getText();


    //must edit here
    if (checkName.matcher(name).matches() && !IP.equals("")&& !mail.equals("") && mail.contains("@")) {
     try {
      Random rd = new Random();
      int portPeer = 10000 + rd.nextInt() % 1000;
      InetAddress ipServer = InetAddress.getByName(IP);
      int portServer = Integer.parseInt("3456");
      Socket socketClient = new Socket(ipServer, portServer);

      String msg = Encode.getCreateAccount(name, Integer.toString(portPeer));
      
      ObjectOutputStream serverOutputStream = new ObjectOutputStream(socketClient.getOutputStream());
      serverOutputStream.writeObject(msg);
      serverOutputStream.flush();
      ObjectInputStream serverInputStream = new ObjectInputStream(socketClient.getInputStream());
      msg = (String) serverInputStream.readObject();

      socketClient.close();
      if (msg.equals(Tags.SESSION_DENY_TAG)) {
       lblError.setText(NAME_EXSIST);
       lblError.setVisible(true);
       return;
      }
      new MainGui(IP, portPeer, name, msg);
     
      frameLoginForm.dispose();
     } catch (Exception e) {
      lblError.setText(SERVER_NOT_START);
      lblError.setVisible(true);
      e.printStackTrace();
     }
    }
    
    else if (!(mail.contains("@"))) {
    	lblError.setText(ERROR_NAME);
    	lblError.setVisible(true);
    	lblError.setText(ERROR_NAME);
    }
    
    else {
     lblError.setText(NAME_FAILED);
     lblError.setVisible(true);
     lblError.setText(NAME_FAILED);
    }
   }
  });
  
  btnLogin.setBounds(245, 213, 79, 63);
  frameLoginForm.getContentPane().add(btnLogin);
  
  JTextPane textPane = new JTextPane();
  textPane.setBounds(448, 197, 7, 20);
  frameLoginForm.getContentPane().add(textPane);
  lblError.setVisible(false);
 }
}