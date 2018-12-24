package Application_Spatial;


import java.util.List;
import java.util.ListIterator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import AbstractEdge.AbsEdge;
import SPEdge.SPEdge;
import SPGraph.SPGraph;
import SpatialVertex.SPVertex;



public class App_main {

	public static SPGraph<SPVertex, SPEdge> g=new SPGraph<SPVertex, SPEdge>();
	public static HashMap<Integer, SPGraph<SPVertex, SPEdge>> listGraphs= new HashMap<Integer, SPGraph<SPVertex, SPEdge>>();  
	public static File f;
	public static PrintWriter pw;
	
	public static void main(String[] args) throws IOException, ParseException {
		// TODO Auto-generated method stub
	
		
		Read_Data();
    }
		
		
	

public void test() {
	
	SPGraph<SPVertex, SPEdge> g=new SPGraph<SPVertex, SPEdge>();
	
	SPVertex v1=new SPVertex();
	v1.addpoint(1.1, 2.4);
	
	SPVertex v2=new SPVertex();
	v2.addpoint(1.5, 3.4);
	
	g.addVertex(v1);
	g.addVertex(v2);
	
	SPEdge e1=new SPEdge(v1, v2);
    e1.addLabel("first");
	g.addEdge(v1, e1);
	
	System.out.println(g.getNeigbours(v1).get(0).getLabel()+" "+ g.getVertex(0).getPoint().getx());
	
	
}

public static void Read_Data() throws IOException, ParseException {
	
	
	String target_dir = "C:\\Users\\zohre\\Documents\\Term6\\code_spatial\\sample_data";
	File dir = new File(target_dir);
    File[] files = dir.listFiles();
    String [] data_line;
    int lines=0, numFiles=0,  i=0;
    for (File f : files) {
        if(f.isFile()) {
            BufferedReader inputStream = null;

            try {
                inputStream = new BufferedReader(new FileReader(f));
                inputStream.lines();
               
                String line;
               lines=0;

                while ((line = inputStream.readLine()) != null ) {
                 //   System.out.println(line);
                    SPVertex v1=new SPVertex();
                    data_line =line.split(",");
                   
                    SimpleDateFormat formatter=new SimpleDateFormat("yyy-MM-dd HH:mm:ss"); 
                    Date date1= formatter.parse(data_line[1]); 
                    if (!g.getlistVertex().contains(v1)) {
                    v1.addTime(date1);
                    v1.addLabel(data_line[0]+"_"+lines);
                     v1.addid(lines);
                    v1.addpoint(Double.parseDouble(data_line[2]), Double.parseDouble(data_line[3]));
                 //   System.out.println(v1.getNodeTime());
                    g.addVertex(v1); 
                    
                }
                    lines++;
                   
                }
             
            /*    
               int j=0;
               
                while(j<(lines-1)) {
                	if(g.getVertex(i).getLabel().equals(g.getVertex(i+1).getLabel())) {
                SPEdge e1=new SPEdge(g.getVertex(i), g.getVertex(i+1));
                e1.addWeight(1.0); 
                g.addEdge(g.getVertex(i), e1);
                	}
                j++;
                i++;
                   
                }*/
                numFiles++;
                
            }
            finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        }
    }
    
    
     int h=0;
    SPVertex k1=g.getVertex(0);
    SPVertex k2=g.getVertex(1);
    while(h<g.getlistVertex().size()-1) {
    	 k1= g.getVertex(h);
         k2= g.getVertex(h+1);
    	
    	if(k1.getLabel().equals(k2.getLabel())) {
            SPEdge e1=new SPEdge(k1, k2);
            e1.addWeight(1.0); 
            g.addEdge(k1, e1);
            
            	}
    	h=h+1;
    	
    	
    	
    }
    
  

	//  for(SPVertex Element:g.getlistVertex()) {
	   
//	   System.out.println("node:::"+Element.getLabel()+" edges:::"+g.getNeigbours(Element));
 //  }
	
//////////Calculate Time Interval, create a snapshot every 177 seconds 
int  time_interval=177, numInterval=0;
Date Min_date=get_Min_Time();
Date k=Min_date;
//List TimeInterval = new ArrayList<>();

while (k.before(get_Max_Time())) {
	 
	 
	 Calendar calendar = Calendar.getInstance();
	 calendar.setTime(k);
	 calendar.add(Calendar.SECOND, time_interval);
	 calculate_betweenEdges(k, calendar.getTime(),numInterval);
	// System.out.println(k +" "+ calendar.getTime() );
	 k=calendar.getTime();
	 numInterval++;

  
   }
	System.out.println("num_of_Intervals "+ numInterval+" Number of Vertices: "+g.getNumberVertices()+" Number of Edges: "+g.getNumberEdges());

	Addtofile( numInterval);
	
}
//write to file
	public static void Addtofile(int numInterval) throws IOException {
	
		File f;
		PrintWriter pw;
	f = new File("C:\\Users\\zohre\\Documents\\Term6\\code_spatial\\graph_spatial\\sample_graph.txt");
     f.createNewFile();
     pw = new PrintWriter(f);
     ArrayList<SPVertex> temp= new ArrayList<SPVertex>();  
     Set<Integer> s=listGraphs.keySet();
     for (int i=0; i<listGraphs.size();i++) {
			//	    System.out.println("Key = " + entry1.getKey() + ", Value = " + entry1.getValue());
    	SPGraph<SPVertex,SPEdge> g2= (SPGraph<SPVertex,SPEdge>)listGraphs.get(i);
    	
    	
    	temp=(ArrayList<SPVertex>) g2.getlistVertex();
    	pw.write("Time Interval "+i+"\n");
        for(int d=0; d<temp.size();d++) {
       	 pw.write(temp.get(d).getLabel()+": ");
     // 	  System.out.print(" vertex "+temp.get(d).getLabel());
      	  for(int e=0; e<g2.getNeigbours(temp.get(d)).size(); e++) {
      	//	  System.out.println(" : "+((AbsEdge<String, Double, SPVertex>) g2.getNeigbours(temp.get(d)).get(e)).getdestination().getid());
      	   		  pw.write(((AbsEdge<String, Double, SPVertex>) g2.getNeigbours(temp.get(d)).get(e)).getdestination().getLabel()+" ");

      	  }
      	  
      	  pw.write("\n");
     	
        }
        pw.write("--");
  
    	
     }
  	pw.close();
     
 /* for entire Graph   
     ArrayList<SPVertex> temp= new ArrayList<SPVertex>();
 	  temp=(ArrayList<SPVertex>) g.getlistVertex();
     for(int d=0; d<g.getlistVertex().size();d++) {
    	 pw.write(temp.get(d).getLabel()+": ");
   	  System.out.print(" vertex "+temp.get(d).getLabel());
   	  for(int e=0; e<g.getNeigbours(temp.get(d)).size(); e++) {
   		  System.out.println(" : "+g.getNeigbours(temp.get(d)).get(e).getdestination().getid());
   		  if(g.getNeigbours(temp.get(d)).get(e).getTimeInterval()!=null)
   		  pw.write(g.getNeigbours(temp.get(d)).get(e).getTimeInterval().getMin()+" - "+g.getNeigbours(temp.get(d)).get(e).getTimeInterval().getMax()+", "+g.getNeigbours(temp.get(d)).get(e).getdestination().getLabel()+" ");
   		  else 
   	   		  pw.write(g.getNeigbours(temp.get(d)).get(e).getdestination().getLabel()+" ");

   	  }
   	  pw.write("\n");
  	System.out.println(" ");
     }
	pw.close();
*/

}
	
public static void calculate_betweenEdges(Date MinTime, Date maxTime, Integer numInterval) {
	
	double dist=0;
    SPGraph<SPVertex, SPEdge> g1=new SPGraph<SPVertex, SPEdge>();
	TimeInterval ti=new TimeInterval(MinTime,maxTime);
	ArrayList<SPVertex> check= new ArrayList<SPVertex>();
	check=(ArrayList<SPVertex>) g.getlistVertex();
	Map<Integer, String> NodesinInterval= new HashMap<Integer,String>();
	NodesinInterval=getNodesInterval(MinTime,maxTime);
	
	boolean fig=false;
	if ( NodesinInterval.size()>1) {
//	System.out.println(" Nodes in the interval "+ "("+MinTime+" , "+ maxTime+")" + NodesinInterval.size());
	 fig=true;
	 
	}
	
	//for (Map.Entry<Integer, String> entry1 : NodesinInterval.entrySet()

	
	 
		   Set<Integer> key_nodes = (NodesinInterval.keySet());
		   Integer [] a=key_nodes.toArray(new Integer[0]);
	       
	   for(int y=0; y<a.length;y++ ) {
		   g1.addVertex(g.getVertex(a[y]));
		   for (int w=y+1; w<a.length;w++)
		{
	//		System.out.println(" check "+check.get(a[y]).getid()+" check2"+check.get(a[w]).getid());
		     
		dist=distance(check.get(a[y]).getPoint().getx(), check.get(a[y]).getPoint().gety(), check.get(a[w]).getPoint().getx(), check.get(a[w]).getPoint().gety(), 'K');
        //	System.out.println( " distance= "+dist);
		if(fig) {
    	//	System.out.println("dist "+dist+" n1= "+ a[y]+ " n2= "+ a[w]);
    		
    		}
        	if (dist<30 && dist!=0 ) {
        		if(check.get(a[y])!= check.get(a[w])) {
        		SPEdge e2= new SPEdge(check.get(a[y]),check.get(a[w]));
        		e2.setTimeInterval(ti);
        		g1.addEdge(check.get(a[y]), e2);
        	     
        		
        		
        		}
        		
        		

		
		
	}
	   
	   }
	   } 
	   
	   listGraphs.put(numInterval, g1); 
	 /*  
	   if(!NodesinInterval.isEmpty()) {
    			for (Map.Entry<Integer, String> entry1 : NodesinInterval.entrySet()) {
    			//	    System.out.println("Key = " + entry1.getKey() + ", Value = " + entry1.getValue());
    				    for (Map.Entry<Integer, String> entry2 : NodesinInterval.entrySet()) {
    	    				dist=distance(check.get(entry1.getKey()).getPoint().getx(), check.get(entry1.getKey()).getPoint().gety(), check.get(entry2.getKey()).getPoint().getx(), check.get(entry2.getKey()).getPoint().gety(), 'K');
                            //	System.out.println( " distance= "+dist);
    	    				if(fig) {
                        		System.out.println("dist "+dist+" n1= "+ entry1.getKey()+ " n2= "+ entry2.getKey());
                        		
                        		}
                            	if (dist<30 && dist!=0 ) {
                            		if(check.get(entry1.getKey())!= check.get(entry2.getKey())) {
                            		SPEdge e2= new SPEdge(check.get(entry1.getKey()),check.get(entry2.getKey()));
                            		if(!g.getNeigbours(check.get(entry1.getKey())).contains(e2)) {
                            		g.addEdge(check.get(entry1.getKey()), e2);
                            	
                            		}
                            		
                            		}
                            		
                            		
                            		
    				    } // if
    			} // second for
    		} // first for
    				
    		} // if
    */
    	
	

    		
 }



public static Map getNodesInterval(Date min_time, Date max_time) {
ArrayList<SPVertex> check= new ArrayList<SPVertex>();
Map<Integer, String> temp= new HashMap<Integer,String>();
check=(ArrayList<SPVertex>) g.getlistVertex();

for(int k=0; k<check.size();k++) {
	 
  	if(check.get(k).getNodeTime().before(max_time) && check.get(k).getNodeTime().after(min_time) || check.get(k).getNodeTime().equals(min_time) || check.get(k).getNodeTime().equals(max_time) ) {
  		
  		if(!temp.containsValue(check.get(k).getLabel())) {
  			
  		temp.put(k,check.get(k).getLabel());
  		
  	    }
  	
     }
}	
return temp;
}

     
public static double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
    double theta = lon1 - lon2;
    double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
    dist = Math.acos(dist);
    dist = rad2deg(dist);
    dist = dist * 60 * 1.1515;
    if (unit == 'K') {
      dist = dist * 1.609344;
    } else if (unit == 'N') {
      dist = dist * 0.8684;
      }
    return (dist);
  }	


/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
/*::  This function converts decimal degrees to radians             :*/
/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
private static double deg2rad(double deg) {
  return (deg * Math.PI / 180.0);
}

/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
/*::  This function converts radians to decimal degrees             :*/
/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
private static double rad2deg(double rad) {
  return (rad * 180.0 / Math.PI);
}


private static Date  get_Min_Time() {
	

	ArrayList <SPVertex> s= new ArrayList<SPVertex>();
			s=(ArrayList<SPVertex>) g.getlistVertex();
			
	Date temp=s.get(0).getNodeTime();
	Date min=s.get(0).getNodeTime();
    for (int i=0; i<s.size();i++) {
    	
    	if(s.get(i).getNodeTime().before(temp)) {
    		min=s.get(i).getNodeTime();
    		temp=s.get(i).getNodeTime();
    	}
    	
    } 
    return min;
}

private static Date  get_Max_Time() {
	

	ArrayList <SPVertex> s= new ArrayList<SPVertex>();
			s=(ArrayList<SPVertex>) g.getlistVertex();
			
	Date temp=s.get(0).getNodeTime();
	Date max=s.get(0).getNodeTime();
    for (int i=0; i<s.size();i++) {
    	
    	if(s.get(i).getNodeTime().after(temp)) {
    		max=s.get(i).getNodeTime();
    		temp=s.get(i).getNodeTime();
    	}
    	
    } 
    return max;
}


 //   System.out.println(g.getNumberVertices()+" "+g.getNumberEdges()+ " "+ g.getNeigbours(g.getVertex(0)).get(0));
	



}
