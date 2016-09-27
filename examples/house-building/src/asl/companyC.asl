// This company can bid for all types tasks,
// but can commit to at most 2 of them
// Strategy: fixed price
{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }
{ include("common.asl") }
{ include("sai_code.asl") }

// a rule to help the agent infer whether it can commit to another task
can_commit :- 
   .my_name(Me) & .term2string(Me,MeS) &
   .findall( ArtId, currentWinner(MeS)[artifact_id(ArtId)], L) &
   .length(L,S) & S < 2.

// initial beliefs about valuations for the auction
my_price("SitePreparation", 1900).
my_price("Floors",           900). 
my_price("Walls",            900). 
my_price("Roof",            1100). 
my_price("WindowsDoors",    2000). 
my_price("Plumbing",         600). 
my_price("ElectricalSystem", 300). 
my_price("Painting",        1100). 

!in_ora4mas.
/*
!discover_art("auction_for_SitePreparation").
!discover_art("auction_for_Floors").
!discover_art("auction_for_Walls").
!discover_art("auction_for_Roof").
!discover_art("auction_for_WindowsDoors").
!discover_art("auction_for_Plumbing").
!discover_art("auction_for_ElectricalSystem").
!discover_art("auction_for_Painting").
 */

@lbo[atomic] // atomic to ensure it still winning less than two when the bid is placed
+currentBid(V)[artifact_id(Art)]        // there is a new value for current bid
    : task(S)[artifact_id(Art)] &
      my_price(S,P) &                   // get my valuation for this service
      not i_am_winning(Art) &           // I am not the winner
      P < V &                           // I can offer a better bid
      can_commit                        // I can still commit to another task
   <- //.print("my bid in auction artifact ", Art, ", Task ", S, ", is ", P);
      .wait(500);
      bid( P )[ artifact_id(Art) ].     // place my bid offering a cheaper service

/* plans for execution phase */

{ include("org_code.asl") }
{ include("org_goals.asl") }
