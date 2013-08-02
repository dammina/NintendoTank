
public class player_cods {
	private int x;
	private int y;
	private int dir;
	public int getDir() {
		return dir;
	}
	public void setDir(int dir) {
		this.dir = dir;
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
	public player_cods(int x, int y,int dir) {
		super();
		this.x = x;
		this.y = y;
		this.dir=dir;
	}
	
}
