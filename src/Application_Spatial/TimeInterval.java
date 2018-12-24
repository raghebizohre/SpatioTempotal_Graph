package Application_Spatial;

import java.util.Date;

public class TimeInterval {

	private Date Min, Max;
	
	
	public TimeInterval(Date Min, Date Max) {
		
		 this.Min=Min;
		 this.Max=Max;
		
	}
	
	public void setTimeInterval(Date Min, Date Max ) {
		
		this.Min=Min;
		this.Max=Max;
	}
	
	public Date getMin() {
		
		return Min;
	}
	
	public Date getMax() {
		
		return Max;
	}
}