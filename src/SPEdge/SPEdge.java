package SPEdge;

import java.util.Date;

import AbstractEdge.AbsEdge;
import Application_Spatial.TimeInterval;
import SpatialVertex.SPVertex;

public class SPEdge extends AbsEdge<String, Double, SPVertex> {
	private TimeInterval TI;
	


	public SPEdge() {
		
		super();
	}
	
public SPEdge(SPVertex v1, SPVertex v2) {
		
		super( v1, v2);
	}
	

public void setTimeInterval(TimeInterval ti) {
	this.TI=ti;
	
}

public TimeInterval getTimeInterval() {
	return TI;

	}
}
