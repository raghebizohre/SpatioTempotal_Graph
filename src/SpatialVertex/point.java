package SpatialVertex;

public class point {

	private double x;
	private double y;
	
	public point(double x, double y) {
		
		 this.x=x;
		 this.y=y;
		
	}
	
	public void set_pointx(double x, double y) {
		
		this.x=x;
		this.y=y;
	}
	
	public double getx() {
		
		return x;
	}
	
	public double gety() {
		
		return y;
	}
}
