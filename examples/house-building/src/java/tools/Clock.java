package tools;

import cartago.*;

public class Clock extends Artifact {

	  boolean working;
	  final static long TICK_TIME = 1000;

	      
	  void init(){
	    working = false;
            defineObsProperty("currentTime", 0);
            defineObsProperty("working", false);                     
	  }
	    
	  @OPERATION void start(){
	    if (!working){
	      working = true;
              getObsProperty("working").updateValue(true);
	      execInternalOp("work");              
	    } else {
	      failed("already_working");
	    }
	  }
	  
	  @OPERATION void stop_clock(){
	    working = false;
            getObsProperty("working").updateValue(false);
	  }

	  @INTERNAL_OPERATION void work(){
	      while (working){
	        signal("tick");
	        await_time(TICK_TIME);
	        getObsProperty("currentTime").updateValue(getObsProperty("currentTime").intValue()+1);
	      }
	  }
}
