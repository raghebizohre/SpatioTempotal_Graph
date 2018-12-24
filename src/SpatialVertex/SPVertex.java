package SpatialVertex;



import java.util.Date;

import AbstractVertex.AbsVertex;
import Application_Spatial.TimeInterval;

public class SPVertex extends AbsVertex{

	private double x , y;
	private point point_x;
	private Date vertexTime;
	private TimeInterval TI;
	
	
	public SPVertex() {
		super();
		vertexTime=null;
		
	} 
	
	
	public void addpoint(double x, double y) {
		point_x=new point(x,y);
	}
	
	public point getPoint() {
		return point_x;
	}
	
	public void addTime(Date vertexTime) {
		
		this.vertexTime=vertexTime;
	}
	
	public Date getNodeTime() {
		
		return vertexTime;
	}	
		
	public void setTimeInterval(TimeInterval ti) {
		this.TI=ti;
		
	}

	public TimeInterval getTimeInterval() {
		return TI;

		}
	
}
