// CArtAgO artifact code for project test_OpFeedback

package test;

import sai4jacamo.RuleEngine;
import cartago.*;

public class TestArt extends Artifact {
	private AuxiliarClass aux;
	private RuleEngine engine;
	
	void init() {
		this.aux = new AuxiliarClass();
		this.engine = new RuleEngine();
	}
	
	@OPERATION
	void getString(OpFeedbackParam<String> result){
	    result.set(this.aux.toString());
	  }
	
	@OPERATION
	void getObject(OpFeedbackParam<AuxiliarClass> result){
	    result.set(this.aux);
	  }
	
	@OPERATION
	void getRuleEngine(OpFeedbackParam<RuleEngine> result){
	    result.set(this.engine);
	  }

	
	
	
}

