// This company bids for Plumbing only
// Strategy: fixed price


{ include("common.asl") }
{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

my_price(300). // initial belief


+currentBid(V)[artifact_id(Art)]         // there is a new value for current bid
    : not i_am_winning(Art)  &           // I am not the current winner
      my_price(P) & P < V                // I can offer a better bid
   <- .wait(4000); //agents wait a time before to bid to ensure that all the infrastructure, namely, the link between SAI and CArtAgO, is ready
      //?jcm__ws("wsp_auction",WspAuction); //look to the SAI workspace
   //	  cartago.set_current_wsp(WspAuction);
      bid( P ).                          // place my bid offering a cheaper service
   
/* plans for execution phase */

{ include("org_code.asl") }

// plan to execute organisational goals (not implemented)

+!plumbing_installed   // the organisational goal (created from an obligation)
   <- //?jcm__ws("wsp_house",WspHouse); 
   //	  cartago.set_current_wsp(WspHouse);
      installPlumbing. // simulates the action (in GUI artifact)
      

// obligation to achieve a goal      
+obligation(Ag,Norm,What,Deadline)[artifact_id(ArtId)]
    : .my_name(Ag) & (What=satisfied(Scheme,Goal) | What = done(Scheme,Goal,Ag))
   <- .print(" ---> working to achieve ",Goal," in scheme ",Scheme);
      !Goal[scheme(Scheme)];
      //.print(" <--- done");
      goalAchieved(Goal)[artifact_id(ArtId)].
      

