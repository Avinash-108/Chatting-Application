package chatting.application;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;

public class Client  implements ActionListener{
    JTextField text;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static DataOutputStream dout;
    static JFrame f = new JFrame();
    Client(){
        f.setLayout(null);
        JPanel p1 = new JPanel();
        p1.setBackground(Color.BLUE);
        p1.setBounds(0, 0, 450, 60);
        p1.setLayout(null);
        f.add(p1);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/Arrow.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);
        
        back.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }
    });
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/saitama2.jpg"));
        Image i5 = i4.getImage().getScaledInstance(85, 85, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(370,5,65,55);
        p1.add(profile);
        
        JLabel name = new JLabel("Pesron2");
        name.setBounds(45, 8, 100, 50);
        name.setForeground(Color.white);
        name.setFont(new Font("Raleway",Font.BOLD,18));
        p1.add(name);
        
        a1 = new JPanel();
        a1.setBounds(5, 75, 440, 570);
        f.add(a1);
        
        text = new JTextField();
        text.setBounds(5, 655, 310, 40);
        text.setFont(new Font("Raleway",Font.PLAIN, 16));
        text.setBackground(Color.gray);
        text.setForeground(Color.white);
        f.add(text);
        
        JButton send = new JButton("Send");
        send.setBounds(320, 655, 123, 40);
        send.setBackground(Color.blue);
        send.setForeground(Color.white);
        send.setFont(new Font("Raleway", Font.PLAIN, 18));
        send.addActionListener(this);
        f.add(send);
        
        f.setSize(450,700);
        f.setLocation(800,50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.BLACK);
        f.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
     try{ 
        String out = text.getText();
        JPanel p2 = formatLabel(out); 
        a1.setLayout(new BorderLayout());
        
        JPanel right = new JPanel(new BorderLayout());
        right.add(p2, BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));
        a1.add(vertical, BorderLayout.PAGE_START);
        dout.writeUTF(out);
        text.setText("");
        f.repaint();
        f.invalidate();
        f.validate();
        }catch(Exception e){
          e.printStackTrace();
      }
    }
    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        
        JLabel output = new JLabel("<html><p style=\"width: 150px\">"+ out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN,16));
       
        output.setBackground(new Color(138, 236, 255));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15,50));
        
        panel.add(output);
        return panel;
    }
    
    public static void main(String[] args){
        new Client();
        try{
            Socket s = new Socket("127.0.0.1",6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            
            while(true){
                a1.setLayout(new BorderLayout());
                String msg = din.readUTF();
                JPanel panel = formatLabel(msg);
                
                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);
                
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical, BorderLayout.PAGE_START);
                f.validate();
            }
            
        }catch(Exception e){
            
        }
    }  
}
