package sai.bridges.jacamo;

import static jason.asSyntax.ASSyntax.parseFormula;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cartago.LINK;
import cartago.OPERATION;
import cartago.manual.syntax.Literal;
import jason.asSemantics.Unifier;
import jason.util.Config;
import npl.NPLInterpreter;
import ora4mas.nopl.NormativeBoard;
import ora4mas.nopl.WebInterface;
import sai.main.institution.INormativeEngine;
import sai.main.institution.SaiEngine;
import sai.norms.npl.nopl2sai.INormativeBoard2SaiListener;
import sai.norms.npl.nopl2sai.NOpl2Sai;

public class NormativeBoardSai extends NormativeBoard implements INormativeBoard2SaiListener {
	
	private NOpl2Sai npl2sai;
	private SaiEngine institution;
	private List<Commitment> commitmentsList = new ArrayList<Commitment>();
	private List<Goal> achievementsList = new ArrayList<Goal>();
	private CommitmentChecker commitmentChecker = new CommitmentChecker();
	
	public void init() {

		
		
				
        oeId = getId().getWorkspaceId().getName();
        String nbId = getId().getName();
        
        

        nengine = new NPLInterpreter();
        nengine.init();
        installNormativeSignaler();

        if (! "false".equals(Config.get().getProperty(Config.START_WEB_OI))) {
            WebInterface w = WebInterface.get();
            try {
                w.registerOEBrowserView(oeId, "/norm/", nbId, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        this.npl2sai = new NOpl2Sai(getNPLInterpreter());
		this.npl2sai.addNormListener(this);

    }

	
	 
	
    
	public INormativeEngine getNormEngine() {	
		return this.npl2sai;
	}
	
	
	
	@LINK
	public void setInstitution(SaiEngine institution){
		this.institution = institution;
		institution.addNormativeEngine(this.getNormEngine());
	}





	@Override
	public void test() {
		Iterator<jason.asSyntax.Literal> it = nengine.getAg().getBB().iterator();
		while(it.hasNext()){
			log("testing - " + it.next().toString());
		}
		
	}





	@Override
	public void sai_committed(String agent, String mission, String scheme) {
		synchronized (commitmentsList) {
			commitmentsList.add(new Commitment(agent, mission, scheme)); //adds to the list to be consumed by a thread
			log("added " + commitmentsList.get(commitmentsList.size()-1));
		}			
		commitmentChecker.interrupt();	
	}
	

	
	private class Commitment{

		private String agent;
		private String mission;
		private String scheme;

		public Commitment(String agent, String mission, String scheme) {
			super();
			this.agent = agent;
			this.mission = mission;
			this.scheme = scheme;
		}

		public String getAgent() {
			return agent;
		}

		public String getMission() {
			return mission;
		}
		
		public String getScheme(){
			return scheme;
		}

		@Override
		public String toString() {
			return "Commitment [agent=" + agent + ", mission=" + mission + ", scheme="+scheme+"]";
		}

	}

	private class Goal{
		private String agent;
		private String goal;
		private String scheme;

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
		
		public String getScheme(){
			return scheme;
		}

		@Override

		public String toString() {
			return "Goal [agent=" + agent + ", goal=" + goal + ", scheme="+ scheme +"]";
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
								//log("commitment checker - " + c.getAgent()+","+c.getMission());
								toCommit = nengine.getAg().believes(parseFormula("active(obligation("+c.getAgent()+",R,committed("+c.getAgent()+","+c.getMission()+",\""+c.scheme+"\"),D)[created(_)])"), new Unifier());
								//toCommit = nengine.getAg().believes(parseFormula("active(obligation("+c.getAgent()+",R,committed("+c.getAgent()+","+c.getMission()+","+c.scheme+"),D)[created(_)])"), new Unifier());
								if(toCommit){
									//execInternalOp("internal_commitMission",c.getAgent(),c.getMission());
									//added.add(c);
									log(">>>>>>>>>>>>>>> To commit " + c.toString());
								}else{
									log("do not believe " + parseFormula("active(obligation("+c.getAgent()+",R,committed("+c.getAgent()+","+c.getMission()+",\""+c.scheme+"\"),D)[created(_)])"));
									//Iterator<Literal> it =   nengine.getAg().getBB().iterator();
									//while(it.hasNext()){
									//	log("iterator: " + it.next());
									//}
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
								if(nengine.getAg().believes(parseFormula("fulfilled(obligation("+c.getAgent()+",_,achieved(\""+c.scheme+"\","+c.getGoal()+","+c.getAgent()+"),_))"), new Unifier())){
									addedAchievement.add(c);
								}
								else{
									toAchieve = nengine.getAg().believes(parseFormula("enabled("+c.scheme+","+c.getGoal()+")"), new Unifier());
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
