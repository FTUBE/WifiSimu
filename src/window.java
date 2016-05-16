import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JRadioButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.JTextArea;

public class window {
	
	static boolean state = true;
	private JFrame frame;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window w = new window();
					w.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public window() {
		initialize();
	}
	//Circle shit.
	
	/**
	 * Initialize the contents of the frame.
	 */
	public static void list(MyPanel mypanel,JTextArea apst){
		
		String Text = "";
		for(int i = 0; i < mypanel.clist.size();i++){
			int cp = mypanel.clist.get(i).capa;
			double used = 0;
			for(int j = 0; j < mypanel.slist.size();j++){
				//if(mypanel.[j][i] == 1) used += mypanel.slist.get(j).bw;
			}
			DecimalFormat    df   = new DecimalFormat("######0.00");   
			Text += "AP"+(i+1)+" - "+cp+","+used+","+df.format((used/(double)cp)*100.0)+"%\n";
		}

		apst.setText(Text);
	}
	private void initialize() {
		
		//True means STA;
		
		frame = new JFrame();
		frame.setBounds(100, 100, 1024, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblAp = new JLabel("AP");
		MyPanel mypanel = new MyPanel();
		
		JSpinner ApNo = new JSpinner();
		
		JLabel stano = new JLabel("STA_No.:");
		
		JSpinner STANo = new JSpinner();
		
		JSpinner APx = new JSpinner();
		APx.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int index = (int)ApNo.getValue();
 				if(mypanel.clist.isEmpty() || index > mypanel.clist.size() || index <= 0) return;
 				Circle c = mypanel.clist.get(index-1);
				c.x = (int)APx.getValue();
				mypanel.trigger(false, 0);
			}
		});
		
		JSpinner APy = new JSpinner();
		APy.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int index = (int)ApNo.getValue();
 				if(mypanel.clist.isEmpty() || index > mypanel.clist.size() || index <= 0) return;
 				Circle c = mypanel.clist.get(index-1);
				c.y = (int)APy.getValue();
				mypanel.trigger(false, 0);
			}
		});
		
		JSpinner apcapa = new JSpinner();
		apcapa.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int index = (int)ApNo.getValue();
 				if(mypanel.clist.isEmpty() || index > mypanel.clist.size() || index <= 0) return;
 				Circle c = mypanel.clist.get(index-1);
				c.capa = (int)apcapa.getValue();
				mypanel.trigger(false, 0);
			}
		});
		
		JSpinner APrad = new JSpinner();
		APrad.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int index = (int)ApNo.getValue();
 				if(mypanel.clist.isEmpty() || index > mypanel.clist.size() || index <= 0) return;
 				Circle c = mypanel.clist.get(index-1);
				c.radius = (int)APrad.getValue();
				mypanel.trigger(false, 0);
			}
		});
		
		JButton btnNewButton = new JButton("AP");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mypanel.clist.add(new Circle(0,0,50,mypanel.clist.size()+1,0));
				mypanel.trigger(false, 0);
			}
		});
		
		JLabel lblApx = new JLabel("APx:");
		
		JLabel lblApy = new JLabel("APy:");
		
		JLabel lblAprad = new JLabel("APrad:");
		
		JSpinner STAbw = new JSpinner();
		STAbw.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int index = (int) STANo.getValue();
				if(mypanel.slist.isEmpty() || index > mypanel.slist.size() || index <= 0) return;
				STA s = mypanel.slist.get((int)STANo.getValue()-1);
				s.bw = (int) STAbw.getValue();
				mypanel.trigger(true, index-1);
			}
		});
		
		JSpinner STAy = new JSpinner();
		STAy.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int index = (int) STANo.getValue();
				if(mypanel.slist.isEmpty() || index > mypanel.slist.size() || index <= 0) return;
				STA s = mypanel.slist.get((int)STANo.getValue()-1);
				s.y = (int) STAy.getValue();
				mypanel.trigger(false, 0);
			}
		});
		
		JSpinner STAx = new JSpinner();
		STAx.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int index = (int) STANo.getValue();
				if(mypanel.slist.isEmpty() || index > mypanel.slist.size() || index <= 0) return;
				STA s = mypanel.slist.get((int)STANo.getValue()-1);
				s.x = (int) STAx.getValue();
				mypanel.trigger(false, 0);
			}
		});
		
		JButton btnSta = new JButton("STA");
		btnSta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mypanel.slist.add(new STA(mypanel.slist.size()+1, 0, 0, 0));
				mypanel.trigger(false, 0);
			}
		});
		
		JLabel lblStax = new JLabel("STAx:");
		
		JLabel lblStay = new JLabel("STAy:");
		
		JLabel lblStabw = new JLabel("STAbw:");
		
		JLabel lblApno = new JLabel("AP_No.:");
		
		JRadioButton statick = new JRadioButton("STA");		
		JRadioButton aptick = new JRadioButton("AP");
		aptick.setSelected(true);
		
		aptick.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				state = true;
				statick.setSelected(false);
				aptick.setSelected(true);
				lblAp.setText("AP");
			}
		});
		
		statick.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				state = false;
				aptick.setSelected(false);
				statick.setSelected(true);
				lblAp.setText("STA");
			}
		});
		//
		mypanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				if(state){
					int index = (int) ApNo.getValue();
					if(index <= 0 || index > mypanel.clist.size()) return;
					Circle s = mypanel.clist.get(index-1);
					s.x = x-s.radius;
					s.y = y-s.radius;
					APx.setValue(s.x);
					APy.setValue(s.y);
					//System.out.println(x + " "+ y);
					mypanel.trigger(false, 0);
					return;
				}
				int index = (int) STANo.getValue();
				if(index <= 0 || index > mypanel.slist.size()) return;
				STA c = mypanel.slist.get(index-1);
				c.x = x-5;
				c.y = y-5;
				STAx.setValue(c.x);
				STAy.setValue(c.y);
				
				mypanel.trigger(false, 0);
			}
		});
		JTextArea apst = new JTextArea();
		boolean rocking = false;
		
		JLabel APcapa = new JLabel("APcapa:");
		
		JSpinner SHINKA = new JSpinner();
		SHINKA.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(STA s : mypanel.slist){
					int deltax = (int)(2*Math.random())*2-1;
					int deltay = (int)(2*Math.random())*2-1;
					s.x += deltax;
					s.y += deltay;
					//System.out.println(deltax);
					mypanel.trigger(false, 0);
					window.list(mypanel, apst);
				}
			}
		});
		
		
		
		
		
		//Layout shit.
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(40)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(mypanel, GroupLayout.PREFERRED_SIZE, 450, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblAp))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(50)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(APcapa, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(lblApx)
										.addComponent(lblApy, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblAprad, GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
										.addComponent(lblApno, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(aptick))
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(47)
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
												.addComponent(APrad, GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
												.addComponent(APx, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(APy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(ApNo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(apcapa, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE))))
									.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(33)
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(lblStax, Alignment.TRAILING)
												.addComponent(lblStay, Alignment.TRAILING)
												.addComponent(lblStabw, Alignment.TRAILING)
												.addComponent(stano, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)))
										.addComponent(SHINKA, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(18)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(STAbw, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
											.addGap(153))
										.addGroup(groupLayout.createSequentialGroup()
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(STAy, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
												.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
													.addComponent(STAx, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
													.addComponent(STANo, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)))
											.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(btnSta)
												.addComponent(btnNewButton))
											.addGap(15))))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(57)
									.addComponent(statick))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(40)
							.addComponent(apst, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)))
					.addGap(1))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(15)
					.addComponent(mypanel, GroupLayout.PREFERRED_SIZE, 450, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
					.addComponent(lblAp)
					.addGap(35))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnNewButton)
							.addGap(18)
							.addComponent(btnSta))
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblApno)
							.addComponent(stano)
							.addComponent(STANo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(ApNo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(19)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblApx)
						.addComponent(APx, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(STAx, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblStax))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblApy)
						.addComponent(APy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(STAy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblStay))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAprad)
						.addComponent(APrad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(STAbw, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblStabw))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(APcapa)
						.addComponent(apcapa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(SHINKA, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(39)
					.addComponent(aptick)
					.addGap(33)
					.addComponent(statick)
					.addGap(18)
					.addComponent(apst, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
