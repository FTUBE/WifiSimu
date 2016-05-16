
public class STA {
	int no, x, y;
	double bw;
	public STA(int _n, int _x, int _y, double _bw){
		no = _n;
		x = _x;
		y = _y;
		bw = _bw;
	}
	public static void main(String[] args){
		double jpy = 172044.00;
		double rmb = 10337.37;
		double base = 100;//
		double get = 1680.59;//
		double rate_prev = jpy/rmb;
		double rate_cur = get/base;
		
		for(double delta = 0; delta < 10000;  delta += 100){
			System.out.println("PREV : "+rate_prev + "\nBuy "+(base+delta)+"\nNOW : " + getrate(jpy,rmb,base+delta,rate_cur)+"\n\n");
		}
	}
	public static double getrate(double jpy, double rmb, double buy, double rate){
		double get = buy*rate;
		jpy += get;
		rmb += buy;
		
		return jpy/rmb;
	}
}
