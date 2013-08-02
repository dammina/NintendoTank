/*  
 * To change this template, choose Tools | Templates  
 * and open the template in the editor.  
 */  



import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
/**
 * @author Dammina
 */

public class WizardGame extends BasicGame
{
	private TiledMap grassMap;
	public static ArrayList<coins> coins;
	public static ArrayList<lifes> lifes;
	private static ArrayList<bricks> bricks_destroyed;
	private Animation [] bricks;
	private Animation [] water;
	private Animation [] stones;
	private Animation [] coin;
	private Animation [] life;
	public static ArrayList<Integer> blocked;

	public static int playerNo;
	private static int cl;

	private static Animation [] sprite;
	private static Animation [] up;
	private static Animation [] down;
	private static Animation [] left;
	private static Animation [] right;
	private float x = 34f, y = 34f;
	private String msg; 
	private static Server server;
	private static String[] brickCods;
	private static String[] waterCods;
	private static String[] stoneCods;

	private static String[] players;
	public static String[] xc,yc,dir,health,cns,pts;
	private coins coinPile;
	private lifes lifePack;
	private bricks destroyed_brick;

	private Client myTank;
	private AI intelli;

	private boolean temp;


	public WizardGame()
	{
		super("Wizard game");
	}

	public static void main(String[] arguments) throws InterruptedException
	{
		try
		{

			new Play().start();
			server=new Server();
			AppGameContainer app = new AppGameContainer(new WizardGame());
			app.setDisplayMode(1280, 640, false);

			app.start();
		}
		catch (SlickException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void init(GameContainer container) throws SlickException
	{
		grassMap = new TiledMap("Images/map16.tmx");  

		Image [] movementUp1 = {new Image("Images/wmg1_bk1.png"), new Image("Images/wmg1_bk2.png")};
		Image [] movementDown1 = {new Image("Images/wmg1_fr1.png"), new Image("Images/wmg1_fr2.png")};
		Image [] movementLeft1 = {new Image("Images/wmg1_lf1.png"), new Image("Images/wmg1_lf2.png")};
		Image [] movementRight1 = {new Image("Images/wmg1_rt1.png"), new Image("Images/wmg1_rt2.png")};

		Image [] movementUp2 = {new Image("Images/wmg2_bk1.png"), new Image("Images/wmg2_bk2.png")};
		Image [] movementDown2 = {new Image("Images/wmg2_fr1.png"), new Image("Images/wmg2_fr2.png")};
		Image [] movementLeft2 = {new Image("Images/wmg2_lf1.png"), new Image("Images/wmg2_lf2.png")};
		Image [] movementRight2 = {new Image("Images/wmg2_rt1.png"), new Image("Images/wmg2_rt2.png")};

		Image [] movementUp3 = {new Image("Images/wmg3_bk1.png"), new Image("Images/wmg3_bk2.png")};
		Image [] movementDown3 = {new Image("Images/wmg3_fr1.png"), new Image("Images/wmg3_fr2.png")};
		Image [] movementLeft3 = {new Image("Images/wmg3_lf1.png"), new Image("Images/wmg3_lf2.png")};
		Image [] movementRight3 = {new Image("Images/wmg3_rt1.png"), new Image("Images/wmg3_rt2.png")};

		Image [] movementUp4 = {new Image("Images/wmg4_bk1.png"), new Image("Images/wmg4_bk2.png")};
		Image [] movementDown4 = {new Image("Images/wmg4_fr1.png"), new Image("Images/wmg4_fr2.png")};
		Image [] movementLeft4 = {new Image("Images/wmg4_lf1.png"), new Image("Images/wmg4_lf2.png")};
		Image [] movementRight4 = {new Image("Images/wmg4_rt1.png"), new Image("Images/wmg4_rt2.png")};

		Image [] movementUp5 = {new Image("Images/wmg5_bk1.png"), new Image("Images/wmg5_bk2.png")};
		Image [] movementDown5 = {new Image("Images/wmg5_fr1.png"), new Image("Images/wmg5_fr2.png")};
		Image [] movementLeft5 = {new Image("Images/wmg5_lf1.png"), new Image("Images/wmg5_lf2.png")};
		Image [] movementRight5 = {new Image("Images/wmg5_rt1.png"), new Image("Images/wmg5_rt2.png")};

		Image [] placeBricks= {new Image("Images/bricks.png"), new Image("Images/bricks.png")};
		Image [] placeWater= {new Image("Images/water.png"), new Image("Images/water.png")};
		Image [] placeStones= {new Image("Images/stones.png"), new Image("Images/stones.png")};



		int [] duration = {300, 300};
		up=new Animation[5];
		down=new Animation[5];
		left=new Animation[5];
		right=new Animation[5];

		coins=new ArrayList<coins>();
		lifes=new ArrayList<lifes>();
		bricks_destroyed=new ArrayList<bricks>();
		blocked=new ArrayList<Integer>();

		up[0] = new Animation(movementUp1, duration, false);
		down[0] = new Animation(movementDown1, duration, false);
		left[0] = new Animation(movementLeft1, duration, false);
		right[0] = new Animation(movementRight1, duration, false); 

		up[1] = new Animation(movementUp2, duration, false);
		down[1] = new Animation(movementDown2, duration, false);
		left[1] = new Animation(movementLeft2, duration, false);
		right[1] = new Animation(movementRight2, duration, false); 

		up[2] = new Animation(movementUp3, duration, false);
		down[2] = new Animation(movementDown3, duration, false);
		left[2] = new Animation(movementLeft3, duration, false);
		right[2] = new Animation(movementRight3, duration, false); 

		up[3] = new Animation(movementUp4, duration, false);
		down[3] = new Animation(movementDown4, duration, false);
		left[3] = new Animation(movementLeft4, duration, false);
		right[3] = new Animation(movementRight4, duration, false); 

		up[4] = new Animation(movementUp5, duration, false);
		down[4] = new Animation(movementDown5, duration, false);
		left[4] = new Animation(movementLeft5, duration, false);
		right[4] = new Animation(movementRight5, duration, false); 

		bricks=new Animation[400];
		water=new Animation[400];
		stones=new Animation[400];
		coin=new Animation[400];
		life=new Animation[400];

		try {
			msg = server.readMessage();
			System.out.println(msg);
			String [] brockenMsg = msg.split(":");
			playerNo=Integer.parseInt(brockenMsg[1].charAt(1)+"");
			brickCods=brockenMsg[2].split(";");
			stoneCods=brockenMsg[3].split(";");
			waterCods=brockenMsg[4].split(";");
			for(int i=0;i<brickCods.length;i++){
				bricks[i]=new Animation(placeBricks, duration, false);
			}
			for(int i=0;i<waterCods.length;i++){
				water[i]=new Animation(placeWater, duration, false);
			}
			for(int i=0;i<stoneCods.length;i++){
				stones[i]=new Animation(placeStones, duration, false);
			}

			msg=server.readMessage();//S:P0;0,0;0:P1;0,9;0:P2;9,0;0#
			System.out.println(msg);
			String[] brokenMsg2=msg.split("#");
			players=brokenMsg2[0].split(":");

			////////////////////////////////////////////////////////
			sprite = new Animation[players.length-1];
			dir=new String[players.length-1];
			xc=new String[players.length-1];
			yc=new String[players.length-1];
			health=new String[players.length-1];
			cns=new String[players.length-1];
			pts=new String[players.length-1];
			for(int i=1;i<players.length;i++){//S:P0;0,0;0:P1;0,9;0:P2;9,0;0#
				String[] position=players[i].split(";");
				//	    		System.out.println("checkkkkk "+position[1]+"  : "+position[2]);
				dir[i-1]=position[2];
				xc[i-1]=position[1].split(",")[0];
				yc[i-1]=position[1].split(",")[1];
				
				//	    		System.out.println(xc[i-1]+"^^^^^^^^^^^^^^^^^^^^^^^^^^"+yc[i-1]+"<<<<<<<<<<<<<<<"+dir[i-1]);

				//	    		sprite[i-1].draw(Integer.parseInt(xc)*32, Integer.parseInt(yc)*32, 32, 32);
			}

			////////////////////////////////////////////////////////


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//    	sprite = right;

	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException
	{
		Image [] placeCoins= {new Image("Images/Coins.png"), new Image("Images/Coins.png")};
		Image [] placeLifes= {new Image("Images/Lifes.png"), new Image("Images/Lifes.png")};
		myTank=new Client();
		intelli=new AI();

		//    	myTank.sendMessage("LEFT#");
		//    	myTank.sendMessage("UP#");

		try {

			msg=server.readMessage();

			//			System.out.println("updateeeeeee:::::::::::::"+msg);
			String[] brockenMsg3=msg.split("#");
			String[] brockenMsg4=brockenMsg3[0].split(":");

			//			System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx::::::::::::       "+brockenMsg5.length);

			if(brockenMsg4[0].equals("G")){
				//				intelli.AIfunc();
				String[] brockenMsg5=brockenMsg4[players.length].split(";");
				for(int i=1;i<players.length;i++){
					xc[i-1]=brockenMsg4[i].split(";")[1].split(",")[0];
					yc[i-1]=brockenMsg4[i].split(";")[1].split(",")[1];
					dir[i-1]=brockenMsg4[i].split(";")[2];
					health[i-1]=brockenMsg4[i].split(";")[4];
					cns[i-1]=brockenMsg4[i].split(";")[5];
					pts[i-1]=brockenMsg4[i].split(";")[6];
					
					for(int j=0;j<coins.size();j++){
						if(coins.get(j).getX()==Integer.parseInt(xc[i-1]) && coins.get(j).getY()==Integer.parseInt(yc[i-1])){
							coins.remove(j);
							j--;
						}
					}
					for(int j=0;j<lifes.size();j++){
						if(lifes.get(j).getX()==Integer.parseInt(xc[i-1]) && lifes.get(j).getY()==Integer.parseInt(yc[i-1])){
							lifes.remove(j);
							j--;
						}
					}					
					
				}
				for(int i=0;i<brockenMsg5.length;i++){
					if(Integer.parseInt(brockenMsg5[i].split(",")[2])==4){
						destroyed_brick=new bricks(Integer.parseInt(brockenMsg5[i].split(",")[0]), Integer.parseInt(brockenMsg5[i].split(",")[1]), 4);
						if(!bricks_destroyed.contains(destroyed_brick));
						bricks_destroyed.add(destroyed_brick);
					}
				}
				for(int i=0;i<coins.size();i++){
					coins.get(i).setLife(coins.get(i).getLife()-delta);
					if(coins.get(i).getLife()/1000<=0){
						coins.remove(i);
						i--;
					}
				}
				for(int i=0;i<lifes.size();i++){
					lifes.get(i).setLife(lifes.get(i).getLife()-delta);
					if(lifes.get(i).getLife()/1000<=0){
						lifes.remove(i);
						i--;
					}
				}
				intelli.AIfunc();
			}else if(brockenMsg4[0].equals("C")){
				coinPile=new coins(Integer.parseInt(brockenMsg4[3]), Integer.parseInt(brockenMsg4[2]), Integer.parseInt(brockenMsg4[1].split(",")[0]), Integer.parseInt(brockenMsg4[1].split(",")[1]),new Animation(placeCoins, 300));
				coins.add(coinPile);
			}else if(brockenMsg4[0].equals("L")){
				lifePack=new lifes(Integer.parseInt(brockenMsg4[1].split(",")[0]), Integer.parseInt(brockenMsg4[1].split(",")[1]), Integer.parseInt(brockenMsg4[2]), new Animation(placeLifes, 300));
				lifes.add(lifePack);
			}


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*   	Input input = container.getInput();
    	if (input.isKeyDown(Input.KEY_UP))
    	{
    	    sprite = up;
    	    sprite.update(delta);
    	    // The lower the delta the slowest the sprite will animate.
    	    y -= delta * 0.1f;
    	}
    	else if (input.isKeyDown(Input.KEY_DOWN))
    	{
    	    sprite = down;
    	    sprite.update(delta);
    	    y += delta * 0.1f;
    	}
    	else if (input.isKeyDown(Input.KEY_LEFT))
    	{
    	    sprite = left;
    	    sprite.update(delta);
    	    x -= delta * 0.1f;
    	}
    	else if (input.isKeyDown(Input.KEY_RIGHT))
    	{
    	    sprite = right;
    	    sprite.update(delta);
    	    x += delta * 0.1f;
    	}*/
	}

	public void render(GameContainer container, Graphics g) throws SlickException
	{

		grassMap.render(0, 0);
		for(int i=1;i<players.length;i++){
			//    		System.out.println(xc[i-1]+"|||||||||||||||||||||||||||"+yc[i-1]+"//////////////////"+dir[i-1]);
			if(dir[i-1].equals("0"))
				sprite[i-1]=up[i-1];
			else if(dir[i-1].equals("1"))
				sprite[i-1]=right[i-1];
			else if(dir[i-1].equals("2"))
				sprite[i-1]=down[i-1];
			else if(dir[i-1].equals("3"))
				sprite[i-1]=left[i-1];

			sprite[i-1].draw(Integer.parseInt(xc[i-1])*32, Integer.parseInt(yc[i-1])*32, 32, 32);

		}

		//    	sprite.draw((int)x, (int)y,32,32);

		//    	bricks[0].draw((int)x,(int)y,32,32);

		for(int i=0;i<brickCods.length;i++){
			temp=false;
			x=Integer.parseInt(brickCods[i].split(",")[0]);
			y=Integer.parseInt(brickCods[i].split(",")[1]);
			for(int j=0;j<bricks_destroyed.size();j++){
				if(bricks_destroyed.get(j).getX()==x && bricks_destroyed.get(j).getY()==y)
					temp=true;    			
			}
			if(!temp){
				bricks[i].draw((int)x*32,(int)y*32,32,32);
				cl=(int)x*20+(int)y;
			}
			if(!blocked.contains(cl))
				blocked.add(cl);
			//    		System.out.println("#####B "+x+" "+y);
		}

		for(int i=0;i<stoneCods.length;i++){

			x=Integer.parseInt(stoneCods[i].split(",")[0]);
			y=Integer.parseInt(stoneCods[i].split(",")[1]);
			//    		System.out.println("#####S "+x+" "+y);
			stones[i].draw((int)x*32,(int)y*32,32,32);
			cl=(int)x*20+(int)y;
			if(!blocked.contains(cl))
				blocked.add(cl);
		}

		for(int i=0;i<waterCods.length;i++){
			x=Integer.parseInt(waterCods[i].split(",")[0]);
			if(waterCods[i].split(",")[1].contains("#"))
				y=Integer.parseInt(waterCods[i].split(",")[1].split("#")[0]);
			else
				y=Integer.parseInt(waterCods[i].split(",")[1]);
			//    		System.out.println("#####W "+x+" "+y);
			water[i].draw((int)x*32,(int)y*32,32,32);
			cl=(int)x*20+(int)y;
			if(!blocked.contains(cl))
				blocked.add(cl);
		}

		for(int i=0;i<coins.size();i++){
			coins.get(i).getAnim().draw(coins.get(i).getX()*32,coins.get(i).getY()*32,32,32);
		}
		for(int i=0;i<lifes.size();i++){
			lifes.get(i).getAnim().draw(lifes.get(i).getX()*32,lifes.get(i).getY()*32,32,32);
		}
		g.drawLine(22*32, 2*32, 38*32, 2*32);
		g.drawLine(22*32, 3*32, 38*32, 3*32);
		g.drawLine(22*32, 4*32, 38*32, 4*32);
		g.drawLine(22*32, 5*32, 38*32, 5*32);
		g.drawLine(22*32, 6*32, 38*32, 6*32);
		g.drawLine(22*32, 7*32, 38*32, 7*32);
		g.drawLine(22*32, 8*32, 38*32, 8*32);
		g.drawLine(22*32, 2*32, 22*32, 8*32);
		g.drawLine(26*32, 2*32, 26*32, 8*32);
		g.drawLine(30*32, 2*32, 30*32, 8*32);
		g.drawLine(34*32, 2*32, 34*32, 8*32);
		g.drawLine(38*32, 2*32, 38*32, 8*32);
		
		if(pts[0]!=null){
			g.drawString("Player ID", (int)(22.5*32), (int)(2.25*32));
			g.drawString("Points", (int)(26.5*32), (int)(2.25*32));
			g.drawString("Coins", (int)(30.5*32), (int)(2.25*32));
			g.drawString("Health", (int)(34.5*32), (int)(2.25*32));

			g.drawString("Player 1", (int)(22.5*32), (int)(3.25*32));
			g.drawString(pts[0], (int)(26.5*32), (int)(3.25*32));
			g.drawString(cns[0], (int)(30.5*32), (int)(3.25*32));
			g.drawString(health[0], (int)(34.5*32), (int)(3.25*32));

			g.drawString("Player 2", (int)(22.5*32), (int)(4.25*32));
			g.drawString(pts[1], (int)(26.5*32), (int)(4.25*32));
			g.drawString(cns[1], (int)(30.5*32), (int)(4.25*32));
			g.drawString(health[1], (int)(34.5*32), (int)(4.25*32));

			g.drawString("Player 3", (int)(22.5*32), (int)(5.25*32));
			g.drawString(pts[2], (int)(26.5*32), (int)(5.25*32));
			g.drawString(cns[2], (int)(30.5*32), (int)(5.25*32));
			g.drawString(health[2], (int)(34.5*32), (int)(5.25*32));

			g.drawString("Player 4", (int)(22.5*32), (int)(6.25*32));
			g.drawString(pts[3], (int)(26.5*32), (int)(6.25*32));
			g.drawString(cns[3], (int)(30.5*32), (int)(6.25*32));
			g.drawString(health[3], (int)(34.5*32), (int)(6.25*32));

			g.drawString("Player 5", (int)(22.5*32), (int)(7.25*32));
			g.drawString(pts[4], (int)(26.5*32), (int)(7.25*32));
			g.drawString(cns[4], (int)(30.5*32), (int)(7.25*32));
			g.drawString(health[4], (int)(34.5*32), (int)(7.25*32));
		}
		//    	System.out.println("crappppppppppppppppp");
	}
}