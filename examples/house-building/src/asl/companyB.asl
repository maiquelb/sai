// This company bids for site preparation
// Strategy: decreasing its price by 150 until its minimal value
{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }
{ include("common.asl") }
{ include("org_code.asl") }
{ include("sai_code.asl") }


my_price(1500). // initial belief

!in_ora4mas.
//!discover_art("auction_for_SitePreparation").



+currentBid(V)[artifact_id(Art)]        // there is a new value for current bid
    : not i_am_winning(Art) &           // I am not the winner
      my_price(P) & P < V               // I can offer a better bid
   <- //.print("my bid in auction artifact ", Art, " is ",math.max(V-150,P));
      .wait(500);
      bid( math.max(V-150,P) ).         // place my bid offering a cheaper service

/* plans for execution phase */

  
+!site_prepared 
   <- prepareSite. // simulates the action (in GUI artifact)
      
