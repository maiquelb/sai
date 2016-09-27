// CArtAgO artifact code for project crisis

package teste;

import sai4jacamo.RuleEngine;
import cartago.*;

public class TesteFeedbackArt extends Artifact {
	void init(int initialValue) {
		defineObsProperty("count", initialValue);
		log("XXXXXXXXXXXXXXXXXX started " + initialValue);
	}
	
	@OPERATION
	void inc() {
		ObsProperty prop = getObsProperty("count");
		prop.updateValue(prop.intValue()+1);
		signal("tick");
	}
	
	@OPERATION
	  void sum(double a, double b, OpFeedbackParam sum){
		log("YYYYYYYYYYY vai somar RULE 111");
	    sum.set(new TesteClassToInstantiate().toString());
	  }
}

