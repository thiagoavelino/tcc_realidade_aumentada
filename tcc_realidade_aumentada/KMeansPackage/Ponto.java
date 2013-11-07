package KMeansPackage;


public class Ponto{
	private float X;
	private float Y;
	private int centroid;
	public Ponto(float x, float y){
		this.X=x;
		this.Y=y;
		this.centroid=0;
	}
	public Ponto(float x, float y, int c){
		this.X = x;
		this.Y = y;
		this.centroid = c;
	}
	public Ponto(Ponto p){
		this.X = p.getX();
		this.Y = p.getY();
		this.centroid = p.getCentroid();
	}
	public float getX(){
		return this.X;
	}
	public float getY(){
		return this.Y;
	}
	public void setX(float a){
		this.X=a;
	}
	public void setY(float b){
		this.Y=b;
	}
	public int getCentroid(){
		return centroid;
	}
	public void setCentroid(int c){
		centroid = c;
	}
	public void add(Ponto p){
		this.X += p.getX();
		this.Y += p.getY();
	}
	public static Ponto add(Ponto a, Ponto b){
		return new Ponto(a.getX()+b.getX(),a.getY()+b.getY());
	}
	public void addX(float a){
		this.X+=a;
	}
	public void addY(float a){
		this.Y+=a;
	}
	public void divN(int n){
		this.X/=n;
		this.Y/=n;
	}
	public String toString(){
		return "("+this.X+","+this.Y+"):"+this.centroid;
	}
	public static boolean equalCoordinates(Ponto A, Ponto B){
		if(A.getX()==B.getX()&&A.getY()==B.getY())
			return true;
		else
			return false;
	}
	public static float getDistance(Ponto A, Ponto B){
		float d = (float)
			Math.sqrt(Math.pow((A.getX()-B.getX()),2)+Math.pow((A.getY()-B.getY()),2));
		return d;
	}
}

