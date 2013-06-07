package Battleship;

import static Battleship.Player.CARRIER;
import static Battleship.Player.ships;
import javax.swing.*;
import java.awt.*;	
	
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import javax.swing.border.*;	
public class Ship
{		
	private String name;
	private int dir=5,
			   length,				   
			   i,
			   j,				   
			   x1,
			   y1,
			   x2,
			   y2;
	private int hitsleft;
	private boolean invalid;
	
	public Ship(String n, int d, int ln, int x, int y)
	{
		name=n;
		length=ln;
		dir=d;
		x1=x;
		y1=y;
		invalid=false;
		hitsleft=ln;			
	}
	
	public Ship(String n, int d, int ln, int x, int y, int ex, int ey)
	{
		name=n;
		length=ln;
		dir=d;
		x1=x;
		y1=y;
		x2=ex;
		y2=ey;
		invalid=false;
		hitsleft=ln;			
	}
	
	public String getName()
	{
		return this.name;
	}		
	
	public int getLength()
	{
		return this.length;
	}
	
	public int getDirect()
	{
		return this.dir;
	}
	
	public int getX()
	{
		return this.x1;
	}
	
	public int getY()
	{
		return this.y1;
	}
	
	//returns the end x-point for this ship 
	public int getEndX()
	{
		return this.x2;
	}
	
	//returns the end y-point for this ship 
	public int getEndY()
	{
		return this.y2;
	}

	public void setInvalid(boolean c)
	{				
		this.invalid=c;
	}
			
	public void setHitsLeft()
	{				
		this.hitsleft-=1;
	}
	
	public int getHitsLeft()
	{				
		return this.hitsleft;
	}
	
	public void clearship ()
	{				
		switch (this.dir)
		{
			case 0:	{													
						if  (!this.invalid)
							for (j=this.y1;j<this.y2;j++)
							{
								Battleship.getPlayers(Battleship.getYou()).setBboard(this.x1,j,null);
								Battleship.getPlayers(Battleship.getYou()).setHitOrMiss(this.x1,j,false);
								Battleship.getPlayers(Battleship.getYou()).setWhatShip(this.x1,j," ");	
							}
					}
			break;
			case 1:	{	
						if (!this.invalid)	
							for (i=this.x1;i<this.x2;i++)
							{
								Battleship.getPlayers(Battleship.getYou()).setBboard(i,this.y1,null);
								Battleship.getPlayers(Battleship.getYou()).setHitOrMiss(i,this.y1,false);
								Battleship.getPlayers(Battleship.getYou()).setWhatShip(i,this.y1," ");	
							}								
					}
			break;				
		}
	}
	
	//Method to place the ships	
	public void placeship()
	{			
            //Graphics2D g2 = (Graphics2D)g;
		switch (this.dir)
		{
			case 0:	{												
						if ((this.length+this.y1)>10)								
						{
							JOptionPane.showMessageDialog(null,"A "+
							this.name+" placed in a "+Battleship.getDirection(this.dir)+
							" direction will not fit at position "
							+Battleship.getCletters(this.x1+1)+Battleship.getCnumbers(this.y1+1)+".",
							"Invalid Placement",JOptionPane.ERROR_MESSAGE);
							this.invalid=true;
						}   								
						else
						{												
							j=0;
							while ((j!=this.length)&&(!Battleship.getPlayers(Battleship.getYou()).getHitOrMiss(this.x1,this.y1+j)))
								j++;
							if (j!=this.length)
							{
								JOptionPane.showMessageDialog(null,"Positio"
								+"n "+Battleship.getCletters(this.x1+1)+
								Battleship.getCnumbers(this.y1+j+1)+" is already occupied.",
								"Invalid Placement",JOptionPane.ERROR_MESSAGE);
								this.invalid=true;
							}
							else
							{
								this.x2=this.x1;
								this.y2=this.y1+this.length;								
								for (j=this.y1;j<this.y2;j++)
								{
                                                                                                     //public void setBckgrImageboard(int shipName,int diretion,int clipOrder,int i,int j, Color k)
									Battleship.getPlayers(Battleship.getYou()).setBckgrImageboard(this.name,0,j-this.y1+1,this.x1,j,Battleship.getColor());
                                                                        //(this.x1,j,Battleship.getColor());//setBboard(this.x1,j,Battleship.getColor());//color the first board here to be green
									Battleship.getPlayers(Battleship.getYou()).setHitOrMiss(this.x1,j,true);
									Battleship.getPlayers(Battleship.getYou()).setWhatShip(this.x1,j,this.name);
                                                                        //very important
                                                                        //this.paintShip(this);
                                                                        
                                                                        //JButton tem =  Battleship.getPlayers(Battleship.getYou()).getBboard(this.x1,this.y1);
                                                                       // Battleship.getPlayers(Battleship.getYou()).getBboard(this.x1,this.y1).prepareImage(Toolkit.getDefaultToolkit().getImage("graphics/carrier.gif"),tem.getWidth(),tem.getHeight(), (ImageObserver) tem);//.add(new ImageIcon("graphics/carrier.gif"));//.], i, i, i);.drawImage(Player.ships[CARRIER][1], 25*x, 25*y, this);
                                                                       // Battleship.getPlayers(Battleship.getYou()).getBboard(this.x1,this.y1).prepareImage(Toolkit.getDefaultToolkit().getImage("graphics/carrier.gif"),tem.getWidth(),tem.getHeight(), (ImageObserver) Battleship.getWindows()[1]);
                                                                        
								}
//                                                                Graphics2D g2 = (Graphics2D)Battleship.getPlayers(Battleship.getYou()).getMyBoard().getGraphics();
//                                                                        JButton tem =  Battleship.getPlayers(Battleship.getYou()).getBboard(this.x1,this.y1);
//                                                                        g2.drawImage(Toolkit.getDefaultToolkit().getImage("graphics/carrier.gif"), tem.getX(), tem.getY(), tem);
								this.invalid=false;
							}
						}
					}
			break;
			case 1:	{		
						if ((this.x1+this.length)>10)								
						{
							JOptionPane.showMessageDialog(null,"A "+
							this.name+" placed in a "+Battleship.getDirection(this.dir)+
							" direction will not fit at position "
							+Battleship.getCletters(this.x1+1)+Battleship.getCnumbers(this.y1+1)+".",
							"Invalid Placement",JOptionPane.ERROR_MESSAGE);
							this.invalid=true;
						}
						else
						{							
							j=0;
							while ((j!=this.length)
								&&(!Battleship.getPlayers(Battleship.getYou()).getHitOrMiss(this.x1+j,this.y1)))
								j++;
							if (j!=this.length)
							{
								JOptionPane.showMessageDialog(null,"Positio"
								+"n "+Battleship.getCletters(this.x1+j+1)+
								Battleship.getCnumbers(this.y1+1)+" is already occupied.",
								"Invalid Placement",JOptionPane.ERROR_MESSAGE);
								this.invalid=true;
							}
							else
							{
								this.y2=this.y1;
								this.x2=this.x1+this.length;										
								for (i=this.x1;i<this.x2;i++)
								{
									//Battleship.getPlayers(Battleship.getYou()).setBckgrImageboard(i,this.y1,Battleship.getColor());
                                                                        Battleship.getPlayers(Battleship.getYou()).setBckgrImageboard(this.name,1,i-this.x1+1,i,this.y1,Battleship.getColor());//(this.x1,j,Battleship.getColor());//setBboard(this.x1,j,Battleship.getColor());//color the first board here to be green
                                                                        Battleship.getPlayers(Battleship.getYou()).setHitOrMiss(i,this.y1,true);
									Battleship.getPlayers(Battleship.getYou()).setWhatShip(i,this.y1,this.name);				
								}
								this.invalid=false;
//                                                                 Graphics2D g2 = (Graphics2D)Battleship.getPlayers(Battleship.getYou()).getMyBoard().getGraphics();
//                                                                        JButton tem =  Battleship.getPlayers(Battleship.getYou()).getBboard(this.x1,this.y1);
//                                                                        g2.drawImage(Toolkit.getDefaultToolkit().getImage("graphics/carrier.gif"), tem.getX(), tem.getY(), tem);
								
							}
						}
					}
			break;							
		}			
		if ((Battleship.getW()>0)&&(Battleship.getA()>0)
			&&(Battleship.getS()>0)&&(Battleship.getT()>0)
		&&(Battleship.getE()>0)&&(!this.invalid))				
		{	
			if ((!Battleship.getPlayers(Battleship.getYou()).getBoats(0).invalid)&&(!Battleship.getPlayers(Battleship.getYou()).getBoats(1).invalid)&&(!Battleship.getPlayers(Battleship.getYou()).getBoats(2).invalid)
				&&(!Battleship.getPlayers(Battleship.getYou()).getBoats(3).invalid)&&(!Battleship.getPlayers(Battleship.getYou()).getBoats(4).invalid))
				Battleship.setDeploy(true);
			else
				Battleship.setDeploy(false);
		}
		else
			Battleship.setDeploy(false);
	}
	public void paintShip(Ship uers)
        {
            //Graphic2D g = new 
            Graphics2D g2 = (Graphics2D)Battleship.getPlayers(Battleship.getYou()).getBboard(uers.getX(),uers.getY()).getGraphics();
            
            //g.
            g2.drawImage(Player.ships[0][3], uers.getX(), uers.getY(),Battleship.getPlayers(Battleship.getYou()).getMyBoard());
        
        }
	public Ship compinput(int u, int n)
	{			
		Ship boat;
		
		int i=0,
			j=0,
			x,
			y,
			shipl=0,	
			dir;
		
		switch (u)
		{	
			case 0:		shipl=5;
			break;
			case 1:		shipl=4;
			break;
			case 2:			
			case 3:		shipl=3;
			break;
			case 4:		shipl=2;
			break;							
		}		
		
		do
		{
			x=(int)(Math.random()*10);
			y=(int)(Math.random()*10);				
			dir=(int)(Math.random()*2);//generates random direction within range			
			boat=new Ship(Battleship.getShips(u),dir,shipl,x,y);				
			switch (dir)
			{
				case 0:	{												
							if (((boat.getLength()+y)>10)||(x==0)||(y==0))								
								boat.setInvalid(true);																				
							else
							{												
								j=0;									
								while ((j!=boat.getLength())&&(!Battleship.getPlayers(n).getHitOrMiss(x,y+j)))
									j++;								
								if (j!=boat.getLength())
									boat.setInvalid(true);																		
								else
								{
									boat.x2=x;
									boat.y2=y+boat.getLength();								
									for (j=y;j<boat.y2;j++)
									{										
										Battleship.getPlayers(n).setHitOrMiss(x,j,true);
										Battleship.getPlayers(n).setWhatShip(x,j,Battleship.getShips(u));				
									}
									boat.setInvalid(false);																
								}
							}
						}
				break;
				case 1:	{		
							if (((x+boat.getLength())>10)||(x==0)||(y==0))						
								boat.setInvalid(true);							
							else
							{							
								j=0;									
								while ((j!=boat.getLength())&&(!Battleship.getPlayers(n).getHitOrMiss(x+j,y)))
									j++;
								if (j!=boat.getLength())
									boat.setInvalid(true);							
								else
								{
									boat.y2=y;
									boat.x2=x+boat.getLength();										
									for (i=x;i<boat.x2;i++)
									{
										Battleship.getPlayers(n).setHitOrMiss(i,y,true);
										Battleship.getPlayers(n).setWhatShip(i,y,Battleship.getShips(u));			
									}
									boat.setInvalid(false);									
								}
							}
						}
				break;						
			}			
		}			
		while (boat.invalid);		
		return boat;
	}
}
