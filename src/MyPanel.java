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
	
	double[][] alloc1 = new double[MAXSTA][MAXAP],alloc2 = new double[MAXSTA][MAXAP];
	int[][] conn = new int[MAXSTA][MAXAP], prev = new int[MAXSTA][MAXAP];
	
	ArrayList<Set<Integer>> avail = new ArrayList<Set<Integer>>();
	
	double[] residue1 = new double[MAXAP];
	double[] residue2 = new double[MAXAP];
	
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
        			if(alloc2[i][j] != 0){
        				STA s = slist.get(i);
        				Circle c = clist.get(j);
        				page.setColor(Color.BLUE);
        				page.drawLine(s.x+3, s.y+3, c.x+c.radius-1, c.y+c.radius-1);
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

	public void trigger(boolean isBwChanged,int staI) {
		
		initConn();
		initResidue();
		
		if(isBwChanged){
			if(staI<0 || staI >= slist.size()) return;
			updateUse(staI);
			if(!isOvrld(alloc2)){
				return;
			}
			/*initAvail() function WAS supposed to be needed here, but due to the fact that
			 * it is only the bandwidth that changes, the avail should remain the same as before.
			 */
			loadBalance();
			repaint();
			return;
		}
		
		ArrayList<Integer> changedList = staChanged();
		if(!changedList.isEmpty()) {
			initAvail();
			apAlloc(changedList);
			if(isOvrld(alloc2)){
				loadBalance();
			}
		}
		repaint();
}

	private void apAlloc(ArrayList<Integer> changedList) {
		for(int sta : changedList){
			double need = slist.get(sta).bw;
			int whichap1 = -1,whichap2 = -1;
			double maxratio1 = Double.MIN_VALUE;
			double maxratio2 = Double.MIN_VALUE;
			for(int ap : avail.get(sta)){
				if(residue1[ap] >= need){
					double ratio = residue1[ap]/(double)clist.get(ap).capa;
					if(ratio > maxratio1){
						maxratio1 = ratio;
						whichap1 = ap;
					}
				}
				if(residue2[ap] >= need){
					double ratio = residue2[ap]/(double)clist.get(ap).capa;
					if(ratio > maxratio2){
						maxratio2 = ratio;
						whichap2 = ap;
					}
				}
			}
			//Then sta should connect to whichap1 in 1, whichap2 in 2.
			associate(1,sta,whichap1);
			associate(2,sta,whichap2);
		}
	}

	private void associate(int alloc,int sta,int ap) {
		if(alloc == 1){
			for(int j = 0; j < clist.size();j++) alloc1[sta][j] =0;
			if(ap != -1){
				alloc1[sta][ap] = slist.get(sta).bw;
			}
		}
		if(alloc == 2){
			for(int j = 0; j < clist.size();j++) alloc2[sta][j] =0;
			if(ap != -1){
				alloc2[sta][ap] = slist.get(sta).bw;
			}
		}
	}

	private void loadBalance() {
		System.out.println("Overload");
		
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
			double sum1 = 0;
			double sum2 = 0;
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
	
	private void updateUse(int staI) {
		for(int j = 0; j < clist.size();j++){
			if(alloc1[staI][j] != 0){
				alloc1[staI][j] = slist.get(staI).bw;
			}
			if(alloc2[staI][j] != 0){
				alloc2[staI][j] = slist.get(staI).bw;
			}
		}
	}
	
	private boolean inCircle(Circle c, STA s){
		int cx = c.x + c.radius;
		int cy = c.y + c.radius;
		int px = s.x;
		int py = s.y;
		if((Math.pow(py-cy,2) + Math.pow(px-cx,2)) > Math.pow(c.radius,2)) return false;
		return true;
	}
	

	private boolean isOvrld(double[][] matrix){
		for(int j = 0; j < clist.size();j++){
			int sum = 0;
			for(int i = 0; i < slist.size();i++){
				sum += matrix[i][j];
			}
			if(sum > clist.get(j).capa) return true;
		}
		return false;
	}
	
}
