import java.util.ArrayList;

public class Result {

	ArrayList<Integer> strategy;
	double metric;
	
	public Result(ArrayList<Integer> st,double weighed_std){
		strategy = st;
		metric = weighed_std;
	}
	/*
	public int compareTo(Result o) {
		if(metric >= o.metric)
		return 1;
		return -1;
	}*/



}
