package Model;

public class PixelGrey {
	
	private int x;
	private int y;
	private int color;
	
	public PixelGrey (int x, int y, int color){
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public int getColor() {
		return color;
	}
	
	public void setColor(int color) {
		if(color>255) this.color = 255;
		else if (color<0) this.color = 0;
		else this.color = color;
		this.color = color;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x= x;
	}
	
}
