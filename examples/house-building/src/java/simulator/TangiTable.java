package simulator;

import cartago.Artifact;

public class TangiTable extends Artifact {
    
    private String tableId;
    

    public void init(String id){
        this.tableId = id;
	log(this.tableId + " started");
    }
}
