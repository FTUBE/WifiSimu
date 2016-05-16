import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;

public class MyPanel extends JPanel {
	
	ArrayList<Circle> clist;
	ArrayList<STA> slist;
	ArrayList<Set<Integer>> avail = new ArrayList<Set<Integer>>();
	int[] total = null;
	
	double mindiv = Double.MAX_VALUE;
	ArrayList<Integer> strategy = new ArrayList<Integer>();
	//Set<ArrayList<Integer>> strategy = new HashSet<ArrayList<Integer>>();
	
	static enum state{AP,STA,LINE,ALL,NONE};
	
	state cur = state.NONE;
	int[][] matrix = new int[99][99];
	int[][] conn = new int[99][99];
	int[][] prev = new int[99][99];
	
    MyPanel()
    {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(250,250));
        clist = new ArrayList<Circle>();
        slist = new ArrayList<STA>();
    }

    public void paintComponent(Graphics page)
    {
        super.paintComponent(page);
        
        	for(int i = 0; i < slist.size();i++){
        		for(int j = 0; j < clist.size();j++){
        			if(matrix[i][j] == 1){
        				STA s = slist.get(i);
        				Circle c = clist.get(j);
        				page.setColor(Color.RED);
        				page.drawLine(s.x+5, s.y+5, c.x+c.radius, c.y+c.radius);
        			}
        		}
        	}

        page.setColor(Color.BLACK);
        for(Circle c : clist){
        	page.drawString("AP"+c.no, c.x+c.radius-10, c.y+c.radius+3);
        	page.drawOval(c.x, c.y,2*c.radius, 2*c.radius);
        }
        
        for(STA s : slist){
        	Color p = new Color((int)s.bw+80,57,0);
        	page.drawString(""+s.no, s.x+7, s.y-1);
        	page.setColor(p);
        	page.fillOval(s.x, s.y, 10, 10);
        }

    }

    
	public void doshit() {
		//cleanmatrix();

		for(STA s : slist){
			//ArrayList<Integer> res = new ArrayList<Integer>();
			//System.out.print("STA "+ s.no + " can connect to AP");
			for(Circle c : clist){
				if(checkyoupriv(c, s)) {
				conn[s.no-1][c.no-1] = 1;
				}
				else{
				conn[s.no-1][c.no-1] = 0;
				}
			}
		}
		
		if(isstateChanged()) recalculate();
		
		repaint();
}

	private void recalculate() {
		
		initTotal();
		cleanmatrix();
		mindiv = Double.MAX_VALUE;
		strategy.clear();
		
		//strategy.clear();
		int[] residue= new int[clist.size()];
		//System.out.println("Residue : ");
		for(Circle c : clist) {
			residue[c.no-1] = c.capa;
			//System.out.print(c.capa+" ");
		}
		//System.out.println("");
		availUpdate();
		traverse(0,residue,new ArrayList<Integer>());
		
		if(strategy.isEmpty()) {
			System.out.println("No solution.");
			return;
		}
		
		System.out.println("Best Strategy gives a minimum div = "+mindiv);
		for(int i = 0; i < strategy.size();i++){
			matrix[i][strategy.get(i)] = 1;
		}
		/*System.out.println("=====Strategy=====");
		for(ArrayList<Integer> l : strategy){
			System.out.println("=====Some=====");
			for(int i : l){
				System.out.print(i+" ");
			}
			System.out.println("\n==============");
		}
		System.out.println("==================");*/
	}

	private void initTotal() {
		total = new int[clist.size()];
		for(int i = 0; i < clist.size();i++){
			total[i] = clist.get(i).capa;
		}
	}

	private void availUpdate(){
		avail.clear();
		for(int i = 0; i < slist.size();i++){
			Set<Integer> s = new HashSet<Integer>();
			for(int j = 0; j < clist.size();j++){
				if(conn[i][j] != 0)s.add(j);
			}
			avail.add(s);
		}
	}
	private boolean isstateChanged() {
		boolean changed = false;
		for(int i = 0 ; i < slist.size();i++){
			for(int j = 0; j < clist.size();j++){
				if(conn[i][j] != prev[i][j]) changed = true;
				prev[i][j] = conn[i][j];
			}
		}
		return changed;
	}

	private void cleanmatrix() {
		for(int i = 0; i < slist.size();i++){
			for(int j = 0; j < clist.size();j++) matrix[i][j] = 0;
		}
		
	}

	private boolean checkyoupriv(Circle c, STA s){
		int cx = c.x + c.radius;
		int cy = c.y + c.radius;
		int px = s.x;
		int py = s.y;
		if((Math.pow(py-cy,2) + Math.pow(px-cx,2)) > Math.pow(c.radius,2)) return false;
		return true;
	}
	
	public void traverse(int sta,int[] residue,ArrayList<Integer> result){
		
		if(sta == slist.size()){
			
			int[] use = new int[clist.size()];

			for(int i = 0; i < residue.length;i++){
				use[i] = total[i] - residue[i];
			}
			
			double cur_div = calculatediv(use);
			if(cur_div > mindiv) return;
			mindiv = cur_div;
			strategy = (ArrayList<Integer>)result.clone();
			//strategy.add((ArrayList<Integer>)result.clone());
			return;
		}
		
		for(int ap : avail.get(sta)){
			
			if(residue[ap] - slist.get(sta).bw<0)//Threshold limit goes here.
				continue;
			residue[ap] -= slist.get(sta).bw;
			result.add(ap);
			traverse(sta+1,residue,result);
			residue[ap] += slist.get(sta).bw;
			result.remove(result.size()-1);
			
		}
		
	}

	private double calculatediv(int[] result) {
		
		double avg = 0, sum = 0, size = result.length,toret = 0;

		for(int i : result){
			sum +=i;
		}
		
		avg = sum/size;
		
		for(int i : result){
			toret += Math.pow(i-avg,2);
		}
		
		toret = Math.sqrt(toret/size);
		
		return toret;
	}
}
