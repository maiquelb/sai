package sai.bridges.jacamo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import moise.common.MoiseException;
import npl.NormativeFailureException;
import npl.parser.ParseException;
import ora4mas.nopl.GroupBoard;
import ora4mas.nopl.JasonTermWrapper;
import ora4mas.nopl.oe.CollectiveOE;
import ora4mas.nopl.oe.Group;
import sai.main.institution.INormativeEngine;
import sai.norms.npl.nopl2sai.IGroup2SaiListener;
import sai.norms.npl.nopl2sai.NOpl2Sai;
import cartago.ArtifactId;
import cartago.CartagoException;
import cartago.INTERNAL_OPERATION;
import cartago.OPERATION;
import cartago.OpFeedbackParam;
import cartago.OperationException;

public class GroupBoardSai extends GroupBoard implements IGroup2SaiListener {

	private NOpl2Sai npl2sai;

	private Set<ArtifactId>    schemes     = new HashSet<ArtifactId>();//from superclass - should be protected there
	private Set<ArtifactId>    listeners   = new HashSet<ArtifactId>();//from superclass - should be protected there
	private ArtifactId         parentGroup = null; //from superclass - should be protected there

	private List<String>       futureSchemes = new LinkedList<String>(); // schemes to be responsible to when well formed - from superclass - should be protected there

	@Override
	public void init(final String osFile, final String grType) throws ParseException, MoiseException, OperationException {
		super.init(osFile, grType);

		this.npl2sai = new NOpl2Sai(getNPLInterpreter());
		this.npl2sai.addGroupListener(this);
	}


	@OPERATION
	public void getNormativeEngine(OpFeedbackParam<INormativeEngine> nEngine){
		nEngine.set(this.npl2sai);
	}


	@Override
	public void sai_play(String agent, String role, String group) {
		execInternalOp("internal_adoptRole", agent, role);
	}


	@INTERNAL_OPERATION
	private void internal_adoptRole(String ag, String role){
		adoptRole(ag, role);
	}

	@INTERNAL_OPERATION
	private void adoptRole(String ag, String role) {
		if (!running) return;
		boolean oldStatus = isWellFormed();
		CollectiveOE bak = orgState.clone();
		orgState.addPlayer(ag, role);
		try {
			nengine.verifyNorms();

			boolean status = isWellFormed();
			if (parentGroup != null) {
				execLinkedOp(parentGroup, "updateSubgroupPlayers", orgState.getId(), orgState.getPlayers());
				if (status != oldStatus) {
					logger.fine(orgState.getId()+": informing parent group that now my formation is "+status);
					execLinkedOp(parentGroup, "updateSubgroupFormationStatus", orgState.getId(), status);
				}
			}
			notifyObservers();

			defineObsProperty(obsPropPlay, 
					new JasonTermWrapper(ag), 
					new JasonTermWrapper(role), 
					this.getId().getName());            
			if (status != oldStatus) { 
				getObsProperty(obsWellFormed).updateValue(new JasonTermWrapper(status ? "ok" : "nok"));

				while (!futureSchemes.isEmpty()) {
					String sch = futureSchemes.remove(0);
					//logger.info("Since the group "+orgState.getId()+" is now well formed, adding scheme "+sch);
					addScheme(sch);
				}
			}
			updateGuiOE();            
		} catch (NormativeFailureException e) {
			e.printStackTrace();
			orgState = bak; // takes the backup as the current model since the action failed
			failed("Error adopting role "+role, "reason", new JasonTermWrapper(e.getFail()));
		} catch (Exception e) {
			orgState = bak; // takes the backup as the current model since the action failed
			e.printStackTrace();
			failed(e.toString());
		}   
	}


	//from the superclass - should be protected there
	private void notifyObservers() throws CartagoException {
		for (ArtifactId a: schemes) {
			//log("schemes " + a.getName() + " - " + schemes.size() );
			//log("schemes getId: " + orgState.getId());
			//log("schemes getPlayers: " + orgState.getPlayers());
			execLinkedOp(a, "updateRolePlayers", orgState.getId(), orgState.getPlayers());
		}
		for (ArtifactId a: listeners) {
			//log("listeners " + a.getName() + " - " + listeners.size());
			execLinkedOp(a, "updateRolePlayers", orgState.getId(), orgState.getPlayers());
		}
	}


	@Override
	public void sai_responsible(String group, String scheme) {		
		if(group.replaceAll("\"", "").equals(this.getId().getName())){
			while(!this.isWellFormed()){ //TODO: put this in a thread
			}
			execInternalOp("internal_addScheme", scheme.replaceAll("\"", ""));
		}
	}

	@INTERNAL_OPERATION //implemented in the superclass as the operation addScheme
	private void internal_addScheme(String schId) {
		if (!running) return;
		CollectiveOE bak = orgState.clone();
		try {
			ArtifactId schAr = lookupArtifact(schId);
			getGrpState().addResponsibleForScheme(schId);			
			nengine.verifyNorms();
			

			getObsProperty(obsPropSchemes).updateValue(getGrpState().getResponsibleForAsProlog());

			
			schemes.add(schAr);
			notifyObservers();
			updateGuiOE();

			// update in subgroups
			for (Group sg: getGrpState().getSubgroups()) {
				ArtifactId sgid = lookupArtifact(sg.getId());
				execLinkedOp(sgid, "addScheme", schId);                
			}

		} catch (NormativeFailureException e) {
			orgState = bak; // takes the backup as the current model since the action failed
			failed("Error adding scheme "+schId, "reason", new JasonTermWrapper(e.getFail()));
		} catch (Exception e) {
			orgState = bak; // takes the backup as the current model since the action failed
			failed(e.toString());
		}   
	}



	//the same in the superclass. Should be protected there
	private Group getGrpState() {
		return (Group)orgState;
	}


}
