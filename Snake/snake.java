import java.awt.*;
import  java.awt.event.*;
import javax.swing.*;
public class snake extends JApplet implements KeyListener,Runnable{
    int x,y;
    Container c;
    JLabel base[],head;
    ImageIcon h,b,f,t;
    char movement,dummymovement;
    Thread t1;
    Boolean condition=true,threadcondition=false;
    int headi,dummyheadi,foodi,taili[],snakelength=1,i,score=0;
    JPanel game,endgame;
    Font font;
    Graphics g;
    public void init(){
        Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
        y=(int)screenSize.getHeight();
        y-=118;
        x=y;
        font=new Font("Arial",Font.BOLD,x/10);
        resize(x,y);
        t1=new Thread(this);
        c=getContentPane();
        c.setLayout(new CardLayout());
        Frame title=(Frame)this.getParent().getParent();
        title.setTitle("Snake");
        game=new JPanel();
        game.setLayout(new GridLayout(20,20));
        c.addKeyListener(this);
        base=new JLabel[400];
        taili=new int[400];
        h=new ImageIcon("head.png");
        b=new ImageIcon("base.png");
        f=new ImageIcon("food.png");
        t=new ImageIcon("tail.png");
        for(int i=0;i<400;i++){
            base[i]=new JLabel(b);
            game.add(base[i]);
        }
        game.setSize(x,y);
        game.setVisible(true);
        endgame=new JPanel();
        endgame.setLayout(new FlowLayout());
        endgame.setVisible(false);
        c.add(game);
        c.add(endgame);
        t1.start();
    }
    public void keyPressed(KeyEvent ke){
        movement=ke.getKeyChar();
        movement=Character.toLowerCase(movement);
        if((movement=='w')||(movement=='s')||(movement=='a')||(movement=='d')){
            dummymovement=movement;
            threadcondition=true;
        }
       else{
           movement=dummymovement;
       }
    }
    public void keyReleased(KeyEvent ke){
     }
    public void keyTyped(KeyEvent ke){ }
    public void run(){
        try{
            foodi=(int)(Math.random()*400+1);
            headi=(int)(Math.random()*400+1);
            base[headi].setIcon(h);
        while(true){
           resize(x,y);
            c.requestFocusInWindow();        
            taili[0]=headi;
            dummyheadi=headi;
            calfood();
             base[foodi].setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("food.png")).getImage().getScaledInstance(x/20, x/20, Image.SCALE_SMOOTH)));
             nextmovement();
            dummymovement=movement;
                showStatus("| Snake Length: "+snakelength+" | Score: "+score+" |");
                border();
                tail();
               snakehead();
                t1.sleep(100);
                if(threadcondition){
                base[headi].setIcon(b);    
                } 
        }
    }catch(Exception e){}
    }
    public void calfood(){
        if(headi==foodi){ 
            foodi=(int)(Math.random()*400+1);
            for(i=0;i<snakelength-1;i++){
                if(taili[i+1]==foodi){
                    condition=true;
                    break;
                }
                else{
                    condition=false;
                }
             }
             if(condition){
                 calfood();
             }
             else{
                 condition=true;
             }
             snakelength++;
             score=score+(int)(Math.random()*150+1);
           }
         }
         public void border(){
            if((dummyheadi-1==headi)&&(dummyheadi%20==0)){
                headi++;
                dead();
                score();
                t1.stop();
            }
            if((dummyheadi==headi-1)&&((dummyheadi+1)%20==0)){
                headi--;
                dead();
                score();
                t1.stop();
            }
            if(headi>399){
                headi-=20;
                dead();
                score();
                t1.stop();
            }
            if(headi<0){
                headi+=20;
                dead();
                score();
                t1.stop();
            }
         }
         public void snakehead(){
            if(movement=='w'){
                base[headi].setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("shu.png")).getImage().getScaledInstance(x/20, x/20, Image.SCALE_SMOOTH)));
               }
             if(movement=='s'){
              base[headi].setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("shd.png")).getImage().getScaledInstance(x/20, x/20, Image.SCALE_SMOOTH)));
               }
            if(movement=='a'){
              base[headi].setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("shl.png")).getImage().getScaledInstance(x/20, x/20, Image.SCALE_SMOOTH)));
                }
            if(movement=='d'){
                base[headi].setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("shr.png")).getImage().getScaledInstance(x/20, x/20, Image.SCALE_SMOOTH)));
                }
                //head bite
                for(i=0;i<snakelength-1;i++){
                    if(taili[i+1]==headi){
                        dead();
                        score();
                        t1.stop();  
                    }
                }
         }
         public void tail(){
            if(snakelength>1){
                for(i=snakelength;i>1;i--){
                    base[taili[i-1]].setIcon(b);
                    taili[i-1]=taili[i-2];
                    }
                for(i=0;i<snakelength-1;i++){
                    base[taili[i+1]].setIcon(t);
                 }
           }
         }
         public void dead(){
            if(movement=='w'){
                base[headi].setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("shub.png")).getImage().getScaledInstance(x/20, x/20, Image.SCALE_SMOOTH)));
               }
             if(movement=='s'){
              base[headi].setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("shdb.png")).getImage().getScaledInstance(x/20, x/20, Image.SCALE_SMOOTH)));
               }
            if(movement=='a'){
              base[headi].setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("shlb.png")).getImage().getScaledInstance(x/20, x/20, Image.SCALE_SMOOTH)));
                }
            if(movement=='d'){
                base[headi].setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("shrb.png")).getImage().getScaledInstance(x/20, x/20, Image.SCALE_SMOOTH)));
                }
                try{
                t1.sleep(1000);
                }catch(Exception e){}
         }
         public void nextmovement(){
            if(movement=='w'){
                headi-=20;
               }
             if(movement=='s'){
                headi+=20;
               }
            if(movement=='a'){
                headi--;
                }
            if(movement=='d'){
                headi++; 
                }
         }
         public void score(){
             game.setVisible(false);
             endgame.setVisible(true);
             g=endgame.getGraphics();
             g.setFont(font);
             g.setColor(Color.GREEN);
             g.drawString("Score: "+score,0,y/3);
             g.setColor(Color.BLUE);
             g.drawString("Snake Length: "+snakelength,0,y/2);
             g.setColor(Color.RED);
             g.drawString("Game Over",0,(y/1)-(y/3));
             showStatus("Game Over");
         }
}
/*<applet code="snake.class" height=0 width=0></applet>*/