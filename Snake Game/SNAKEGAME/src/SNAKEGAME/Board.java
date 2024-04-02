package SNAKEGAME;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board  extends JPanel implements ActionListener {
    private Image apple;
    private Image dot;
    private Image head;
    private final int All_dots=900;
    private final int Dot_Size=10;
    private final int Random_Position=30;
    private int apple_x;
    private int apple_y;
    public boolean In_Game=true;

private final int x[]=new int[All_dots];

private final int y[]=new int[All_dots];
private boolean leftdirection=false;
private boolean rightdirection=true;
private boolean updirection=false;
private boolean downdirection=false;


    private int dots;
    Timer timer1;

    Board(){
        setPreferredSize(new Dimension(300,300));
        addKeyListener(new movement());


        setBackground(Color.black);

        setFocusable(true);
        initgame();
        loadimages();


    }
    public void loadimages(){
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("SNAKEGAME/icons/apple.png"));
        apple=i1.getImage();
        ImageIcon i2=new ImageIcon(ClassLoader.getSystemResource("SNAKEGAME/icons/dot.png"));
        dot=i2.getImage();
        ImageIcon i3=new ImageIcon(ClassLoader.getSystemResource("SNAKEGAME/icons/head.png"));
        head= i3.getImage();
    }
    public void initgame(){
        dots=3;
        for (int i=0;i<dots;i++){
            y[i]=50;
            x[i]=50-i*Dot_Size;

        }
        locateApple();
        Timer timer =new Timer(130,this);
        timer.start();

    }
    public void locateApple(){
       int r = (int) (Math.random()*Random_Position);
       apple_x=r*Dot_Size;

r=(int) (Math.random()*Random_Position);
apple_y=r*Dot_Size;

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if (In_Game) {
            g.drawImage(apple, apple_x, apple_y, this);
            for (int i = 0; i < dots; i++) {
                if (i == 0) {
                    g.drawImage(head, x[i], y[i], this);
                } else {
                    g.drawImage(dot, x[i], y[i], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        } else {
            GameOver(g);
        }
    }
    public void GameOver(Graphics g){
        String message="GAME OVER!!";
        Font font=new Font("SAN_SERIF",Font.BOLD,14);
        FontMetrics metrics=getFontMetrics(font);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(message, (300 - metrics.stringWidth(message))/2,300/2);
    }
    public void move(){
        for (int i =dots;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if (leftdirection){
            x[0]=x[0]-Dot_Size;
        }
        if (rightdirection){
            x[0]=x[0]+Dot_Size;
        }
        if (updirection){
            y[0]=y[0]-Dot_Size;
        }
        if (downdirection){
            y[0]=y[0]+Dot_Size;
        }

        //x[0]+=Dot_Size;
        //y[0]+=Dot_Size;
    }
    public void checkapple(){
        if (x[0]==apple_x&&y[0]==apple_y){
            dots++;
            locateApple();
        }
    }
    public void collide() {
        for (int i = dots; i > 0; i--) {
            if ((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
                In_Game = false;
            }
        }
            if(y[0]>=300){
                In_Game=false;
            }

        if(x[0]>=300){
            In_Game=false;
        }

        if(y[0]<0){
            In_Game=false;
        }

        if(x[0]<0){
            In_Game=false;
        }
        if (!In_Game){
            timer1.stop();
        }


    }

    public  void actionPerformed(ActionEvent ae){
        if (In_Game) {
            checkapple();
            move();
            collide();
        }
        repaint();
    }
    public class movement extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent e) {
            int key=e.getKeyCode();

            if (key==KeyEvent.VK_LEFT && (!rightdirection)){
                leftdirection=true;
                updirection=false;
                downdirection=false;
            }
            if (key==KeyEvent.VK_RIGHT && (!leftdirection)){
                rightdirection=true;
                updirection=false;
                downdirection=false;
            }
            if (key==KeyEvent.VK_UP && (!downdirection)){
                updirection=true;
                rightdirection=false;
                leftdirection=false;
            }
            if (key==KeyEvent.VK_DOWN && (!updirection)){
                downdirection=true;
                rightdirection=false;
                leftdirection=false;
            }
        }
    }


}
