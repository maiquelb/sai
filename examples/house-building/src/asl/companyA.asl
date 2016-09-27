// This company bids for Plumbing only
// Strategy: fixed price

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }
{ include("common.asl") }
//{ include("org_code.asl") }
{ include("sai_code.asl") }

my_price(300). // initial belief

!in_ora4mas.
//!discover_art("auction_for_Plumbing").


+currentBid(V)[artifact_id(Art)]         // there is a new value for current bid
    : not i_am_winning(Art)  &           // I am not the current winner
      my_price(P) & P < V                // I can offer a better bid
   <- .wait(500);
     // .print("my bid in auction artifact ", Art, " is ",P);
      bid( P )[artifact_name("auction_for_Plumbing")].                          // place my bid offering a cheaper service
   
/* plans for execution phase */



// plan to execute organisational goals (not implemented)

+!plumbing_installed   // the organisational goal (created from an obligation)
   <- installPlumbing. // simulates the action (in GUI artifact)
      
