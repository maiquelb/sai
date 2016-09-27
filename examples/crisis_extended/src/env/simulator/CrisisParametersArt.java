// CArtAgO artifact code for project demoCrisis

/**
 * Parameters for the crisis management, setted as observable properties:
 * security_phase(Zone,Phase): phase of the crisis management in the Zone - e.g. security_phase(downtown,preventive)
 * nbInhabit(Zone,X): the Zone has X inhabitants - e.g. nbInhabit(downtown,500)
 * 
 * 
 */


package simulator;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cartago.*;

public class CrisisParametersArt  extends cartago.tools.GUIArtifact{

	private MyFrame frame;
	private String zone;
	
	
	public void init(String zoneName){
		this.zone = zoneName;	
		init();
	}
	
   public void setup(){	  	   
		defineObsProperty("security_phase", this.zone,"preventive");
		defineObsProperty("nbInhabit", this.zone,300);
		frame = new MyFrame();
		linkActionEventToOp(frame.btnUpdateInhabitants, "internal_updateInhabitants");
		linkActionEventToOp(frame.btnUpdatePhase, "internal_updatePhase");
		frame.setVisible(true);
	}
	
   
    @OPERATION void updateInhabitants(int nb){    	
		frame.txtInhabitants.setText(Integer.toString(nb));
		updateObsProperty("nbInhabit",this.zone,nb);
	}
	
    
	@INTERNAL_OPERATION void internal_updateInhabitants(ActionEvent ev){			
		int nb = Integer.parseInt(frame.txtInhabitants.getText());
		getObsProperty("nbInhabit").updateValues(this.zone,nb);				
	}
		
	/*
	 * A single method accessed by internal and external operations
	 */
	private void updateNumberInhabitants(int nb){
		getObsProperty("nbInhabit").updateValues(this.zone,nb);
	}
	
	
	@OPERATION void updatePhase(String phase){
		updateCrisisPhase( phase);
	}
	
	
	
	@INTERNAL_OPERATION void internal_updatePhase(ActionEvent ev){
		String phase = frame.txtPhase.getText();
		updateCrisisPhase(phase);
	}
	
	
	/*
	 * A single method accessed by internal and external operations
	 */
	private void updateCrisisPhase(String phase){
		getObsProperty("security_phase").updateValues(this.zone,phase);
	}
	

	class MyFrame extends JFrame {    

		private JButton btnUpdateInhabitants;
		private JButton btnUpdatePhase;
		private JTextField txtInhabitants;
		private JTextField txtPhase;

		public MyFrame(){
			setTitle("Crisis parameters - " + zone);
			setSize(500,200);
			JPanel panel = new JPanel();
			
			JPanel header1= new JPanel();
			JPanel header2 = new JPanel();

			setContentPane(panel);
			
			btnUpdateInhabitants = new JButton("update");
			btnUpdateInhabitants.setSize(80,50);			
			txtInhabitants = new JTextField(20);
			txtInhabitants.setText(getObsProperty("nbInhabit").stringValue(1));
			txtInhabitants.setEditable(true);
			header1.add(new JLabel("Number Inhabitants: "));
			header1.add(txtInhabitants);
			header1.add(btnUpdateInhabitants);		
			panel.add(header1);

			
			btnUpdatePhase = new JButton("update");
			btnUpdatePhase.setSize(80,50);			
			txtPhase = new JTextField(20);
			txtPhase.setText(getObsProperty("security_phase").stringValue(1));
			txtPhase.setEditable(true);
			header2.add(new JLabel("Security phase: "));
			header2.add(txtPhase);
			header2.add(btnUpdatePhase);		
			panel.add(header2);

		}



	}



}

