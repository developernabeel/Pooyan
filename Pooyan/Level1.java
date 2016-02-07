import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.sun.java.swing.plaf.nimbus.*;

class Level1 extends JFrame implements KeyListener,ActionListener
{
	JLabel lift,building,mountain,score,lives,arrow[],wolf[];
	boolean power;
	int liftY,arrowY,arrowNum,wolfX,wolfY,nextCounter=1,availableWolf[]={1,1,1,1,1,1,1,1,1},upDown[]={1,1,1,1,1,1,1,1,1},picVal[]={0,0,0,0,0,0,0,0,0};
	int scoreNum=0,livesNum=5,wolvesLeft=30;
	Point wolfPosition,arrowPosition;
	Timer tim1,tim2;
	
	Level1()
	{
		super("Pooyan");
		
		try
		{
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
			SwingUtilities.updateComponentTreeUI(this);
		}
		catch(Exception e){}
		
		lift=new JLabel(new ImageIcon("icons/piggy2.png"));
		building=new JLabel(new ImageIcon("icons/building2.png"));
		mountain=new JLabel(new ImageIcon("icons/mountain.png"));
		
		score=new JLabel("Score : 0");
		lives=new JLabel("Lives : 5");
		
		arrow=new JLabel[2];
		arrow[0]=new JLabel(new ImageIcon("icons/arrow.png"));
		arrow[1]=new JLabel(new ImageIcon("icons/redarrow.png"));
		
		wolf=new JLabel[9];
		
		for(int i=0;i<9;i++)
		{
			wolf[i]=new JLabel();
			wolf[i].setBounds(200,650,62,89);
		}
			
		lift.setBounds(550,130,56,405);
		building.setBounds(580,80,147,546);
		mountain.setBounds(-50,80,600,600);
		
		score.setBounds(520,20,150,30);
		lives.setBounds(30,20,150,30);		
		
		arrow[0].setBounds(510,175,33,8);
		arrow[1].setBounds(510,175,84,9);
		
		tim1=new Timer(10,this);
		tim2=new Timer(80,this);
		
		Font f=new Font("sansserif",Font.BOLD,15);
		score.setFont(f);
		lives.setFont(f);
		
		score.setForeground(Color.BLUE);
		lives.setForeground(Color.BLUE);
		
		Container cp=getContentPane();
		cp.setLayout(null);
		cp.setBackground(Color.WHITE);
		
		cp.add(lift);
		cp.add(building);
		cp.add(score);
		cp.add(lives);
		cp.add(arrow[0]);
		cp.add(arrow[1]);
		
		for(int i=0;i<9;i++)
			cp.add(wolf[i]);
			
		cp.add(mountain);
		
		arrow[0].setVisible(false);
		arrow[1].setVisible(false);
		
		setVisible(true);
		setResizable(false);
		setSize(700,650);
		
		Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((d.width-700)/2,(d.height-650)/2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addKeyListener(this);
		JOptionPane.showMessageDialog(this,"Start");
		tim2.start();
	}
	
	public void keyPressed(KeyEvent ke)
	{
		Point p;
		if(ke.getKeyCode()==KeyEvent.VK_UP)
		{
			p=lift.getLocation();
			liftY=p.y;
			
			if(!(liftY<=130))
			{
				liftY=liftY-10;
				lift.setLocation(550,liftY);
			}			
		}
		
		if(ke.getKeyCode()==KeyEvent.VK_DOWN)
		{
			p=lift.getLocation();
			liftY=p.y;
			
			if(!(liftY>=480))
			{
				liftY=liftY+10;
				lift.setLocation(550,liftY);
			}
		}
		
		if(ke.getKeyCode()==KeyEvent.VK_SPACE)
		{
			
			if(tim1.isRunning()==false)
			{
				p=lift.getLocation();
				arrowY=p.y;
				arrowY=arrowY+45;
				
				if(power==true)
				{
					arrow[1].setLocation(510,arrowY);
					arrow[1].setVisible(true);
					arrowNum=1;
					
				}
				else
				{
					arrow[0].setLocation(510,arrowY);
					arrow[0].setVisible(true);
					arrowNum=0;
					
				}
				
				tim1.start();
				
				// Probability of Power arrow is 2 out of 10.
				if((int)(Math.random()*10)< 3)
				{
					power=true;
					lift.setIcon(new ImageIcon("icons/piggy3.png"));
				}
				else
				{
					power=false;
					lift.setIcon(new ImageIcon("icons/piggy2.png"));	
				}
			}
		}
	}
	
	public void keyTyped(KeyEvent ke) {	}
	public void keyReleased(KeyEvent ke) { }
	
	public void actionPerformed(ActionEvent ae)
	{
		int i,x,y,randomX,randomY,randomPic;
		y=arrowY;
		Point p,wp;
		
		//-----------------------Timer 1 for Arrow-----------------------------
		
		if(ae.getSource()==tim1)
		{
			p=arrow[arrowNum].getLocation();
			x=p.x;
			
			if(x>0)
			{
				x=x-10;	
				arrow[arrowNum].setLocation(x,y);
			}
			else
			{
				tim1.stop();
				arrow[arrowNum].setVisible(false);
			}			
		}
		
		//------------------------Timer 2 for Wolves---------------------------
		//System.out.println("Action performed for "+ae.getSource());
		if(ae.getSource()==tim2)
		{
			
			for(i=0;i<9;i++)
			{
				
				//-----------Wolves going up and down-----------
				
				if(availableWolf[i]==0)
				{
					
					if(upDown[i]==1)
					{
						
						wp=wolf[i].getLocation();
						wolfY=wp.y;
						wolfX=wp.x;
						
						if(wolfY>40)
						{
							wolfY=wolfY-10;
							wolf[i].setLocation(wolfX,wolfY);
						}
						else
						{
							wolf[i].setLocation(200,650);
							availableWolf[i]=1;
							wolvesLeft--;
							livesNum--;
							lives.setText("Lives : "+livesNum);
						}
						
					}
					else if(upDown[i]==0)
					{
						
						wp=wolf[i].getLocation();
						wolfY=wp.y;
						wolfX=wp.x;
						
						if(wolfY<660)
						{
							wolfY=wolfY+10;
							wolf[i].setLocation(wolfX,wolfY);
												
						}
						else
						{
							availableWolf[i]=1;
						}						
					}
				}
				
				//----------Wolves being created------------
				
				else if(availableWolf[i]==1 && nextCounter==0)
				{
					
					randomPic=(int)(Math.random()*7+1);
					picVal[i]=randomPic;					
					wolf[i].setIcon(new ImageIcon("icons/wolf"+randomPic+".png"));
					
					randomX=(int)(Math.random()*450+1);
					wolf[i].setLocation(randomX,650);
					
					availableWolf[i]=0;
					upDown[i]=1;
					nextCounter=(int)(Math.random()*15+5);
				}
				
				//--------checking for a hit----------
				
				wolfPosition=wolf[i].getLocation();
				arrowPosition=arrow[arrowNum].getLocation();
				
				if(arrowNum==0) // for normal arrow
				{
					if((upDown[i]==1) && ((wolfPosition.y+80)>arrowPosition.y && wolfPosition.y<arrowPosition.y) && ((wolfPosition.x+60)>arrowPosition.x && wolfPosition.x<arrowPosition.x))
					{
						wolf[i].setIcon(new ImageIcon("icons/fallingWolf"+picVal[i]+".png"));
						
						upDown[i]=0;
						tim1.stop();
						arrow[arrowNum].setVisible(false);
						
						//-----Increasing Score----						
						scoreNum+=10;
						score.setText("Score : "+scoreNum);
						
						//-----Decreasing wolves left
						wolvesLeft--;
					}
				}
				else	// for red arrow
				{
					if((upDown[i]==1) && ((wolfPosition.y+120)>arrowPosition.y && (wolfPosition.y-40)<arrowPosition.y) && ((wolfPosition.x+70)>arrowPosition.x && wolfPosition.x<arrowPosition.x))
					{
						wolf[i].setIcon(new ImageIcon("icons/fallingWolf"+picVal[i]+".png"));
						
						upDown[i]=0;
						
						//-----Increasing Score----						
						scoreNum+=10;
						score.setText("Score : "+scoreNum);
						
						//-----Decreasing wolves left
						wolvesLeft--;
					}
				}
			}
			nextCounter--;
			
			//--------Checking for game to finish---------
			
			if(livesNum< 0)
			{
				tim1.stop();
				tim2.stop();
				JOptionPane.showMessageDialog(this,"You lost the game\nYour Score is "+scoreNum,"Game Over",JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
			else if(wolvesLeft<=0)
			{
				tim1.stop();
				tim2.stop();
				JOptionPane.showMessageDialog(this,"Level 1 Completed\nYour Score is "+scoreNum,"Game Over",JOptionPane.INFORMATION_MESSAGE);
				new Level2(scoreNum);
				dispose();
			}
			
		}
	}
	
	public static void main(String args[])
	{
		new Level1(); 
	}
}