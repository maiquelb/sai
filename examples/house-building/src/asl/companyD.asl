// This company bids for all tasks
// Strategy: bids a random value 
{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }
{ include("common.asl") }
{ include("sai_code.asl") }

!in_ora4mas.

!discover_art("auction_for_SitePreparation").
!discover_art("auction_for_Floors").
!discover_art("auction_for_Walls").
!discover_art("auction_for_Roof").
!discover_art("auction_for_WindowsDoors").
!discover_art("auction_for_Plumbing").
!discover_art("auction_for_ElectricalSystem").
!discover_art("auction_for_Painting").

+task(S)[artifact_id(Art)]
   <- .wait(math.random(500)+50);
      Bid = math.floor(math.random(10000))+800;
      //.print("my bid in auction artifact ", Art, " is ",Bid);
      .wait(500);
      bid( Bid )[artifact_id(Art)]. // recall that the artifact ignores if this
	                                // agent places a bid that is higher than
									// the current bid

/* plans for execution phase */

{ include("org_code.asl") }
{ include("org_goals.asl") }
