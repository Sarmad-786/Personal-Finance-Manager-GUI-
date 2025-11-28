package PersonalFinanceManagerWeek2;
import javax.swing.*;
import java.awt.*;
public abstract class Splash extends JFrame implements Runnable{
    Thread thread;
    
    Splash(){
      //setSize(1200, 600);
     // setLocation(200, 100);
     ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/splash.jpg")); // load splash image from icon folder
      Image i2 = i1.getImage().getScaledInstance(1200, 600, Image.SCALE_DEFAULT);
      ImageIcon i3 = new ImageIcon(i2);
      JLabel image = new JLabel(i3);
      add(image);
     setVisible(true);// to visible the frame 
     thread = new Thread(this);
     thread.start();
     
    }
    
    @Override
    public void run(){
        
        // this try cath is used for 7 sec delays on first window
        try{
            Thread.sleep(5000);
             new WelcomeScreen();
            setVisible(false);
        }catch(Exception e){}
        
    }
    
    public static void main(String[]args){
       Splash frame =  new Splash() {};
       
       // follwing block of code handle delay on openeing screen
       int x=1;
       for(int i=1; i<=500; x=7, i+=6){
           frame.setLocation(750-(x+i)/2, 400-(i/2));
           frame.setSize(x = i, i);
           
           try{
              Thread.sleep(10);  // 10 sec delay  
           } catch(Exception e){}
    }
    
}
}