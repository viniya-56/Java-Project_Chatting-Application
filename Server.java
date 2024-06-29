//Libraries to import
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class Server implements ActionListener
{
    //defined gloabally
    JTextField text;  
    JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;

    Server()   //Constructor
    {
        //code for panel
        f.setLayout(null);

        //green panel - p1
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        f.add(p1);

        //code for label

        //back label
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() 
        {
            public void mouseClicked(MouseEvent ae)
            {
                System.exit(0);
            }           
        });

        //profile label
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image i5 = i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40,10,50,50);
        p1.add(profile);

        //video label
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300,20,30,30);
        p1.add(video);

        //phone label
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(360,20,30,30);
        p1.add(phone);

        //menu label
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(15,30,Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel menu = new JLabel(i15);
        menu.setBounds(410,20,15,30);
        p1.add(menu);

        //name label
        JLabel name  = new JLabel("Iron-man");
        name.setBounds( 110, 15, 100, 18);//set the location and size of label
        name.setForeground(Color.WHITE); //Change the color of the label
        name.setFont(new Font("SAN_SERIF", Font.BOLD,18));//new anonymous constructor is created   fontstyle,  fontsize
        p1.add(name);

        //status label
        JLabel status  = new JLabel("Active now");
        status.setBounds( 110, 35, 100, 18);
        status.setForeground(Color.WHITE); 
        status.setFont(new Font("SAN_SERIF", Font.BOLD,14));
        p1.add(status);

        //text panel - a1
        a1 = new JPanel();
        a1.setBounds(5, 75, 440, 570);
        f.add(a1); //add panel a1 to the frame

        //Jtextfield textbox
        text = new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(text); //add textbox to frame

        //send button
        JButton send = new JButton("Send"); //constructor with String parameter
        send.setBounds(320,655,123,40);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this); //action to perform when clicked which will be defined in ActionListener interface
        send.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(send);

        //code for frame
        f.setSize(450,700);
        f.setLocation(200,30);
        f.setUndecorated(true); //to remove header
        f.getContentPane().setBackground(Color.WHITE);
 
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae)
    {

        try
        {
            String out = text.getText();
            //use sout to check the working of button
            //the msg entered here must be displayed on the right of a1

            JPanel p2 = formatLabel(out); //define your own function with return type JPanel

            a1.setLayout(new BorderLayout()); //BorderLayout() is empty costructor (default constructor) here
        
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);

            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15)); //one panel after the other panel by difference of 15

            a1.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);  //to send the string as an output

            text.setText("");  //to empty the textbox once the msg is send

            //in-built functions to refresh the frame
            f.repaint();
            f.invalidate();
            f.validate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }

    //defining function to format the label

    public static JPanel formatLabel(String out)
    {
        JPanel panel = new JPanel(); //dummy panel is created
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        JLabel output = new JLabel("<html><p style=\"width:150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102));
        output.setBorder(new EmptyBorder(15,15,15, 50));
        output.setOpaque(true);  //to make the color visible
        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);
    
        return panel;
    }
    
    public static void main(String[] args)
    {
        new Server();    
        

        //imported library is java.net.* to use ServerSocket
        //Normally, it will show error because of exception handling, so we use try and catch

        try
        {
            ServerSocket skt =  new ServerSocket(6001);

            while(true) //infinite loop
            {
                Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());    //Already declared globally

                while(true)
                {
                    String msg = din.readUTF();  //this function utilizes UTF protocol 
                    //input stream came in din is assigned to string msg

                    //creating dummy panel
                    JPanel panel = formatLabel(msg);

                    JPanel left = new JPanel(new BorderLayout()); //left panel has the property of border layout
                    left.add(panel, BorderLayout.LINE_START);

                    vertical.add(left);
                    f.validate();
                }

            }
        }       
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}