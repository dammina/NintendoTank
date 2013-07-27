import org.newdawn.slick.Animation;


public class lifes {
	private int x;
	private int y;
	private int life;
	private Animation anim;
	public lifes(int x, int y, int life, Animation anim) {
		super();
		this.x = x;
		this.y = y;
		this.life = life;
		this.anim = anim;
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
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public Animation getAnim() {
		return anim;
	}
	public void setAnim(Animation anim) {
		this.anim = anim;
	}
	
}
