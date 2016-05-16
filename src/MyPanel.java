import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

public class MyPanel extends JPanel {
	
	ArrayList<Circle> clist;
	ArrayList<STA> slist;
	static int MAXSTA = 99;
	static int MAXAP = 99;
	
	double mindiv = Double.MAX_VALUE;
	ArrayList<Integer> strategy = new ArrayList<Integer>();
	
	int[][] alloc1,alloc2,conn,prev = new int[MAXSTA][MAXAP];
	
	ArrayList<Set<Integer>> avail = new ArrayList<Set<Integer>>();
	
	int[] residue1,residue2 = new int[MAXAP];
	
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
        			if(alloc1[i][j] != 0){
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

	public void trigger(boolean isCapaChanged) {
		
		initConn();
		initResidue();
		if(isCapaChanged){
			if(!isOvrld(alloc2)){
				return;
			}
			loadBalance();
			repaint();
			return;
		}
		
		ArrayList<Integer> changedList = staChanged();
		if(!changedList.isEmpty()) {
			apAlloc(changedList);
		}
		
		repaint();
}

	private void apAlloc(ArrayList<Integer> changedList) {
		
	}

	private void loadBalance() {
		
	}

	private void recalculate_futsu(ArrayList<Integer> staChanged) {
		for(int sta : staChanged){
			for(int ap : avail.get(sta)){
				
			}
		}
	}
	
	private void initConn() {
		for(STA s : slist){
			for(Circle c : clist){
				if(inCircle(c, s)) {
				conn[s.no-1][c.no-1] = 1;
				}
				else{
				conn[s.no-1][c.no-1] = 0;
				}
			}
		}
	}
	
	private void initResidue(){
		for(int j = 0; j < clist.size();j++){
			int sum1 = 0;
			int sum2 = 0;
			for(int i = 0; i < slist.size();i++){
				sum1 += alloc1[i][j];
				sum2 += alloc2[i][j];
			}
			residue1[j] = clist.get(j).capa - sum1;
			residue2[j] = clist.get(j).capa - sum2;
		}
	}

	private void initAvail(){
		avail.clear();
		for(int i = 0; i < slist.size();i++){
			Set<Integer> s = new HashSet<Integer>();
			for(int j = 0; j < clist.size();j++){
				if(conn[i][j] != 0)s.add(j);
			}
			avail.add(s);
		}
	}
	
	private ArrayList<Integer> staChanged() {
		ArrayList<Integer> changed = new ArrayList<Integer>();
		for(int i = 0 ; i < slist.size();i++){
			boolean isC = false;
			for(int j = 0; j < clist.size();j++){
				if(conn[i][j] != prev[i][j]) isC = true;
				prev[i][j] = conn[i][j];
			}
			if(isC) changed.add(i);
		}
		return changed;
	}

	private boolean inCircle(Circle c, STA s){
		int cx = c.x + c.radius;
		int cy = c.y + c.radius;
		int px = s.x;
		int py = s.y;
		if((Math.pow(py-cy,2) + Math.pow(px-cx,2)) > Math.pow(c.radius,2)) return false;
		return true;
	}
	

	private boolean isOvrld(int[][] matrix){
		for(int j = 0; j < clist.size();j++){
			int sum = 0;
			for(int i = 0; i < slist.size();i++){
				sum += matrix[i][j];
			}
			if(sum >= clist.get(j).capa) return true;
		}
		return false;
	}
	
}
