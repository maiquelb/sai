package sai.bridges.jacamo;

import jason.asSemantics.Unifier;
import jason.asSyntax.Literal;
import static jason.asSyntax.ASSyntax.parseFormula;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import sai.main.institution.INormativeEngine;
import cartago.CartagoException;
import cartago.INTERNAL_OPERATION;
import cartago.OPERATION;
import cartago.OpFeedbackParam;
import sai.main.lang.semantics.statusFunction.AgentStatusFunction;
import sai.main.lang.semantics.statusFunction.EventStatusFunction;
import sai.main.lang.semantics.statusFunction.StateStatusFunction;
import sai.norms.npl.nopl2sai.IScheme2SaiListener;
import sai.norms.npl.nopl2sai.NOpl2Sai;
import moise.common.MoiseException;
import npl.NormativeFailureException;
import npl.parser.ParseException;
import ora4mas.nopl.JasonTermWrapper;
import ora4mas.nopl.Operation;
import ora4mas.nopl.SchemeBoard;
import ora4mas.nopl.oe.CollectiveOE;
import ora4mas.nopl.oe.Scheme;

public class SchemeBoardSai extends SchemeBoard implements IScheme2SaiListener{

	private NOpl2Sai npl2sai;
	//private List<Commitment> commitmentsList = Collections.synchronizedList(new ArrayList<Commitment>());	
	//private List<Goal> achievementsList = Collections.synchronizedList(new ArrayList<Goal>());
	private List<Commitment> commitmentsList = new ArrayList<Commitment>();	
	private List<Goal> achievementsList = new ArrayList<Goal>();
	private CommitmentChecker commitmentChecker = new CommitmentChecker();




	@Override
	public void init(final String osFile, final String schType) throws ParseException, MoiseException {
		super.init(osFile, schType);		

		this.npl2sai = new NOpl2Sai(getNPLInterpreter());
	
		this.npl2sai.addSchemeListener(this);


		commitmentChecker.start();
	}




	@OPERATION
	public void getNormativeEngine(OpFeedbackParam<INormativeEngine> nEngine){
		nEngine.set(this.npl2sai);
	}



	@Override
	public void sai_goalAchieved(String agent, String goal) {		
		synchronized (achievementsList) {
			achievementsList.add(new Goal(agent, goal)); //adds to the list to be consumed by a thread
		}
		commitmentChecker.interrupt();		
	}



	public Scheme getSchState() {
		return (Scheme)orgState;
	}

	/*private void updateMonitorScheme() throws CartagoException {
		if (monitorSchArt != null) {
			execLinkedOp(monitorSchArt, "updateMonitoredScheme", orgState);
		}
	}*/

	@INTERNAL_OPERATION
	void internal_goalAchieved(String agent, String goal){
		try {	
			this.goalDone(agent, goal);
		} catch (CartagoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/*This method is a copy from the superclass.
	 * TODO: (?) make this method protected in the superclass?
	 * */
	private void goalDone(final String agent, final String goal) throws CartagoException {
        ora4masOperationTemplate(new Operation() {
            public void exec() throws NormativeFailureException, Exception {
                getSchState().addDoneGoal(agent, goal);
                nengine.verifyNorms();
                if (getSchState().computeSatisfiedGoals()) { // add satisfied goals
                    //nengine.setDynamicFacts(orgState.transform());        
                    nengine.verifyNorms();
                }
                //updateMonitorScheme();
    
                updateGoalStateObsProp();
            }
        },"Error achieving goal "+goal);
    }

	/*private void goalAchieved(String agent, String goal) throws CartagoException {
		if (!running) return;
		CollectiveOE bak = orgState.clone();
		getSchState().addGoalAchieved(agent, goal);		
		try {
			nengine.verifyNorms();			
			if (getSchState().computeSatisfiedGoals()) { // add satisfied goals
				nengine.verifyNorms();
			}
			updateMonitorScheme();

			updateGoalStateObsProp();

			updateGuiOE();
		} catch (NormativeFailureException e) {
			orgState = bak; // takes the backup as the current model since the action failed
			failed("Error achieving goal "+goal, "reason", new JasonTermWrapper(e.getFail()));
		} catch (Exception e) {
			orgState = bak; 
			failed(e.toString());
			e.printStackTrace();
		}
	}

*/


	@Override
	public void sai_committed(String agent, String mission, String scheme) {
		if(getSchState().getId().equals(scheme.replaceAll("\"", ""))){
			synchronized (commitmentsList) {
				commitmentsList.add(new Commitment(agent, mission)); //adds to the list to be consumed by a thread
			}			
			commitmentChecker.interrupt();
		}		
	}



	@INTERNAL_OPERATION
	void internal_commitMission(String agent, String mission){
		try {
			this.commitMission(agent, mission);
		} catch (CartagoException e) {
			e.printStackTrace();
		}
	}

	private void commitMission(final String ag, final String mission) throws CartagoException {
        ora4masOperationTemplate(new Operation() {
            public void exec() throws NormativeFailureException, Exception {
                orgState.addPlayer(ag, mission);
                nengine.verifyNorms();
                
                defineObsProperty(obsPropCommitment, 
                        new JasonTermWrapper(ag), 
                        new JasonTermWrapper(mission), 
                        new JasonTermWrapper(SchemeBoardSai.this.getId().getName()));
                updateGoalStateObsProp();
                
                //updateMonitorScheme();
            }
        }, "Error committing to mission "+mission);
    }
    



	private class Commitment{

		private String agent;
		private String mission;

		public Commitment(String agent, String mission) {
			super();
			this.agent = agent;
			this.mission = mission;
		}

		public String getAgent() {
			return agent;
		}

		public String getMission() {
			return mission;
		}

		@Override
		public String toString() {
			return "Commitment [agent=" + agent + ", mission=" + mission + "]";
		}

	}

	private class Goal{
		private String agent;
		private String goal;

		public Goal(String agent, String goal) {
			super();
			this.agent = agent;
			this.goal = goal;
		}

		public String getAgent() {
			return agent;
		}

		public String getGoal() {
			return goal;
		}

		@Override

		public String toString() {
			return "Goal [agent=" + agent + ", goal=" + goal + "]";
		}


	}


	private class CommitmentChecker extends Thread{

		@Override
		public void run() {			
			ArrayList<Commitment> added = new ArrayList<Commitment>();
			ArrayList<Goal> addedAchievement = new ArrayList<Goal>();
			boolean toCommit;
			while(true){			
				if(commitmentsList.size()>0){
					added.clear();

					synchronized (commitmentsList) {						
						for(Commitment c:commitmentsList){
							try {							 
								toCommit = nengine.getAg().believes(parseFormula("active(obligation("+c.getAgent()+",R,committed("+c.getAgent()+","+c.getMission()+",\""+getSchState().getId()+"\"),D)[created(_)])"), new Unifier());
								if(toCommit){
									execInternalOp("internal_commitMission",c.getAgent(),c.getMission());
									added.add(c);
								}					
							} catch (jason.asSyntax.parser.ParseException e) {
								e.printStackTrace();
							}
						}	
					}
					commitmentsList.removeAll(added);
				}


				boolean toAchieve;
				if(achievementsList.size()>0){
					addedAchievement.clear();
					synchronized (achievementsList) {
						for(Goal c:achievementsList){
							try {																		
								if(nengine.getAg().believes(parseFormula("fulfilled(obligation("+c.getAgent()+",_,achieved(\""+getSchState().getId()+"\","+c.getGoal()+","+c.getAgent()+"),_))"), new Unifier())){
									addedAchievement.add(c);
								}
								else{
									toAchieve = nengine.getAg().believes(parseFormula("enabled("+getSchState().getId()+","+c.getGoal()+")"), new Unifier());
									if(toAchieve){
										execInternalOp("internal_goalAchieved",c.getAgent(),c.getGoal());
									}	
								}
							} catch (jason.asSyntax.parser.ParseException e) {
								e.printStackTrace();
							}
						}	
					}
					achievementsList.removeAll(addedAchievement);
				}
				try {
					Thread.sleep(1000);					
				} catch (InterruptedException e) {
				}				


			}

		}
	}

}
