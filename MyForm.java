import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.StringTokenizer;
import java.io.*;
import java.util.*;
import java.awt.event.*; 
import java.io.IOException; 
import java.util.logging.Level; 
import java.util.logging.Logger; 
import java.util.logging.*; 

public class MyForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private static User usercredentials = new User();
	public static void main(String[] args)  {
		EventQueue.invokeLater(new Runnable() {
			public void run(){
				try{
				MyForm frame = new MyForm();
				frame.setVisible(true);
				}
				catch(Exception e){
					System.out.println(e+"main");
				}
			}
		});
	}

	public MyForm() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 596, 479);
		setTitle(" Mail Sender");
		getContentPane().setLayout(null);
		try{
			fileToObj("Users.txt");
		}
		catch(Exception fil){
			System.out.println(fil);
		}
		// Label Title Send Mail
		JLabel lblSendMail = new JLabel(" Send Mail");
		lblSendMail.setFont(new Font(" Tahoma", Font.BOLD, 13));
		lblSendMail.setHorizontalAlignment(SwingConstants.CENTER);
		lblSendMail.setBounds(187, 35, 78, 14);
		getContentPane().add(lblSendMail);

		// Label From
		JLabel lblFrom = new JLabel("From :");
		lblFrom.setBounds(109, 69, 46, 14);
		getContentPane().add(lblFrom);

		// Text Field from
		final JTextField txtFrom = new JTextField();
		txtFrom.setBounds(229, 69, 200, 20);
		getContentPane().add(txtFrom);
		txtFrom.setText(usercredentials.from);
		txtFrom.setColumns(10);

		// Label Username
		JLabel lblUser = new JLabel("Username :");
		lblUser.setBounds(109, 93, 74, 14);
		getContentPane().add(lblUser);

		// Text Field Username
		final JTextField txtUser = new JTextField();
		txtUser.setBounds(229, 93, 200, 20);
		getContentPane().add(txtUser);
		txtUser.setText(usercredentials.username);			
		txtUser.setColumns(10);

		// Label password
		JLabel lblPasswd = new JLabel("Password :");
		lblPasswd.setBounds(109, 116, 74, 14);
		getContentPane().add(lblPasswd);

		// Text Field from
		final JPasswordField txtpasswd = new JPasswordField();
		txtpasswd.setBounds(229, 116, 200, 20);
		getContentPane().add(txtpasswd);
		txtpasswd.setText(usercredentials.password);	
		txtpasswd.setColumns(10);

		// Label To
		JLabel lblTo = new JLabel("To :");
		lblTo.setBounds(109, 139, 46, 14);
		getContentPane().add(lblTo);

		// Text Field To
		final JTextField txtTo = new JTextField();
		txtTo.setBounds(229, 139, 200, 20);
		getContentPane().add(txtTo);
		txtTo.setColumns(10);

		// Label Subject
		JLabel lblSubject = new JLabel("Subject :");
		lblSubject.setBounds(109, 161, 78, 14);
		getContentPane().add(lblSubject);

		// Text Field Subject
		final JTextField txtSubject = new JTextField();
		txtSubject.setColumns(10);
		txtSubject.setBounds(229, 161, 200, 20);
		getContentPane().add(txtSubject);

		// Label Description
		JLabel lblDesc = new JLabel("Description :");
		lblDesc.setBounds(109, 182, 78, 14);
		getContentPane().add(lblDesc);

		// Text Area Description
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(229, 185, 200, 115);
		getContentPane().add(scrollPane);

		final JTextArea txtDesc = new JTextArea();
		scrollPane.setViewportView(txtDesc);
		// Label Attach
		JLabel lblAttach = new JLabel("Attachment :");
		lblAttach.setBounds(109, 305, 78, 14);
		getContentPane().add(lblAttach);

		// Label File
		final JLabel lblFile = new JLabel("File");
		lblFile.setBounds(265, 305, 179, 14);
		getContentPane().add(lblFile);

		// Button JFileChooser
		JButton chooseFile = new JButton("...");
		chooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JFileChooser fileopen = new JFileChooser();
				int ret = fileopen.showDialog(null, "Choose file");

				if (ret == JFileChooser.APPROVE_OPTION) {
					lblFile.setText(fileopen.getSelectedFile().toString());
				}
			}
		});
		chooseFile.setBounds(226, 305, 27, 23);
		getContentPane().add(chooseFile);

		JCheckBox remember = new JCheckBox("remember credentials");
		remember.setBounds(109, 330, 200, 50);
		getContentPane().add(remember);
		remember.addItemListener((ItemListener) new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == 1){
					try{
					fileToObj("Users.txt");
					usercredentials.from = txtFrom.getText();
					usercredentials.username = txtUser.getText();
					usercredentials.password = new String(txtpasswd.getPassword());
					ObjToFile("Users.txt");
					}
					catch(Exception E){
						System.out.println(E);
					}
				}
			}
		});
		// Button Send
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				SendAttachmentInEmail obj = new SendAttachmentInEmail();
				obj.to=txtTo.getText();
				obj.from=txtFrom.getText();	
				obj.Subject=txtSubject.getText();
				obj.msgBody=txtDesc.getText();
				obj.username=txtUser.getText();
				obj.password=new String(txtpasswd.getPassword());
				obj.filename=lblFile.getText();
				boolean t=true;
				try{
					obj.send();
					JFrame succs = new JFrame();
					JOptionPane.showMessageDialog(succs,"Message Sent successfully!");
					t=true;
				}
				catch(Exception e){
					JFrame failu = new JFrame();
					JOptionPane.showMessageDialog(failu,"Message not Sent,Verify credentials");
					t= false;
				}
				finally{
					try{
					LogIt log = new LogIt(obj,t);
					}
					catch(Exception e){
						System.out.println(e);
					}
				}
			}
		});
		        	
		btnSend.setBounds(187, 380, 89, 23);
		getContentPane().add(btnSend);

	}
	public static void fileToObj(String s) throws Exception{
		File file = new File(s);
		if(file.createNewFile()) return;
        BufferedReader br = new BufferedReader(new FileReader(file)); 
		while ((s = br.readLine()) != null){
			StringTokenizer st = new StringTokenizer(s,",");   
			usercredentials.from=st.nextToken();
			usercredentials.password=st.nextToken();
			usercredentials.username=st.nextToken();
		}
		br.close();
	}
	public static void ObjToFile(String s) throws Exception{
		clearFile(s);
		File file = new File(s);
		file.createNewFile();
		BufferedWriter writer= new BufferedWriter(new FileWriter(s, true));  
		String v;
		v = usercredentials.from+","+usercredentials.password+","+usercredentials.username;
		writer.write(v+"\n"); 
		writer.close();
		usercredentials = new User();
	}
	public static void clearFile(String file) throws Exception{
      
		FileWriter fwOb = new FileWriter(file, false); 
		PrintWriter pwOb = new PrintWriter(fwOb, false);
		pwOb.flush();
		pwOb.close();
		fwOb.close();
	  }
}

class User{
	public String to;
	public String from;
	public String username;
	public String password;
	public String msgBody;
	public String Subject;
	public String filename;
}
class SendAttachmentInEmail extends User{
      
   public void send(){
      String host = "smtp.gmail.com";
      Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", host);
      props.put("mail.smtp.port", "587");
      Session session = Session.getInstance(props,
         new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(from, password);
            }
         });

      try {
         Message message = new MimeMessage(session);
         message.setFrom(new InternetAddress(from));
         message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
		 message.setSubject(Subject);
		 message.setText(msgBody);

		 if(filename.equals("File")!=true){
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(msgBody);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart(); 
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
		}
        Transport.send(message);  
      } catch (MessagingException e) {
         throw new RuntimeException(e);
      }
   }
}

//Logging code

class LogIt{ 
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public LogIt(User u,boolean sent) 
    {   
        FileHandler fh;  
    try {  
		fh = new FileHandler("MyEmail.log"); 
		logger.addHandler(fh); 
        SimpleFormatter formatter = new SimpleFormatter();  
        fh.setFormatter(formatter);   
        LogManager logmg = LogManager.getLogManager(); 
		Logger logg = logmg.getLogger(Logger.GLOBAL_LOGGER_NAME); 
        if (sent){
        	logg.log(Level.INFO," "+"From:"+u.from+" To:"+u.to+" Subject:"+u.Subject);
        }
        else{
            logg.log(Level.WARNING, " "+"From:"+u.from+" To:"+u.to+" Subject:"+u.Subject+" Message not sent!");
        }

    } catch (SecurityException e) {  
        e.printStackTrace();  
    } catch (IOException e) {  
        e.printStackTrace();  
    }  
        
    
    }
}