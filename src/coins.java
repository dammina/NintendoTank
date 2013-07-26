import org.newdawn.slick.Animation;
import org.newdawn.slick.tests.xml.Entity;


public class coins extends Entity{
	private int amount;
	private int life;
	private int x;
	private int y;
	private Animation anim;
	public coins(int amount, int life, int x, int y, Animation anim) {
		super();
		this.amount = amount;
		this.life = life;
		this.x = x;
		this.y = y;
		this.anim=anim;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Animation getAnim() {
		return anim;
	}
	public void setAnim(Animation anim) {
		this.anim = anim;
	}
	
	
}
