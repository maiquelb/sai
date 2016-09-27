// Agent giacomo, who wants to build a house
{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }



/* Initial beliefs and rules */

// counts the number of tasks based on the observable properties of the auction artifacts
number_of_tasks(NS) :- .findall( S, task(S), L) & .length(L,NS).
      

/* Initial goals */

!have_a_house.


/* Plans */

+!have_a_house 
   <- !setup_sai;
      ?jcm__art("clock", Clock);
      setFrequency(5)[artifact_id(Clock)];
      start[artifact_id(Clock)]; //while the clock is counting the bidders can bid       
      !contract; // hire the companies that will build the house
      !execute.  // (simulates) the execution of the construction
      
+!setup_sai
   <- !setup_sai_wsp_ora4mas; //each plan "setup_sai_X" sets links the workspace X to the SAI engine    
      !setup_sai_wsp_auction; 
      !setup_sai_wsp_house;
      //load the normative program
      ?jcm__ws("wsp_sai",WspSai); //look to the SAI workspace
   	  cartago.set_current_wsp(WspSai);
      lookupArtifact("bhsch",Npl2Sai);   
      focus(Npl2Sai);       
      getNormativeEngine(NEs)[artifact_id(Npl2Sai)];
      addNormativeEngine(NEs)[artifact_id(ArtSai)]; //adds the normative engine from the scheme artifact to the sai engine. Thus, the normative engine is fed with the constitutive state
      //loadNplProgram("/home/maiquel/temp/bhsch.txt")[artifact_id(Npl2Sai)];
      loadNplProgram("src/org/bhsch.txt")[artifact_id(Npl2Sai)];
      //set up norms for groups
      lookupArtifact("hsh_group",Group);   
      focus(Group);       
      getNormativeEngine(NEg)[artifact_id(Group)];
      addNormativeEngine(NEg)[artifact_id(ArtSai)]; //adds the normative engine from the scheme artifact to the sai engine. Thus, the normative engine is fed with the constitutive state
      loadNplProgram("src/org/hsh_group.txt")[artifact_id(Group)];            
      .
    
      
+!setup_sai_wsp_auction
   <- ?jcm__ws("wsp_sai",WspSai); //look to the SAI workspace
   	  cartago.set_current_wsp(WspSai);
      lookupArtifact("sai",ArtSai);   
      focus(ArtSai);   
      getRuleEngine(RE)[artifact_id(ArtSai)];
      
      ?jcm__ws("wsp_auction",WspAuction);
      cartago.set_current_wsp(WspAuction);   
      setWSPRuleEngine(RE)[artifact_id(Wsp_Auction)]; //links the woskspace "wsp_auction" to SAI. Thus, the SAI engine is fed with the environmental facts from that workspace
                       
      .      
      
-!setup_sai_wsp_auction
   <- .wait(500);
      !setup_sai_wsp_auction.      
      
+!setup_sai_wsp_house
   <- ?jcm__ws("wsp_sai",WspSai); //look to the SAI workspace
   	  cartago.set_current_wsp(WspSai);
      lookupArtifact("sai",ArtSai);   
      focus(ArtSai);   
      getRuleEngine(RE)[artifact_id(ArtSai)];
      
      ?jcm__ws("wsp_house",WspHouse);
      cartago.set_current_wsp(WspHouse);   
      setWSPRuleEngine(RE)[artifact_id(Wsp_House)]; //links the woskspace "wsp_house" to SAI. Thus, the SAI engine is fed with the environmental facts from that workspace           
      .
      
      
-!setup_sai_wsp_house
   <- .wait(500);
      !setup_sai_wsp_house.      
      
+!setup_sai_wsp_ora4mas
   <-?jcm__ws("wsp_sai",WspSai); //look to the SAI workspace
   	  cartago.set_current_wsp(WspSai);
      lookupArtifact("sai",ArtSai);   
      focus(ArtSai);          
	  
	  /*
	  ?jcm__ws("wsp_ora4mas",WspOra4mas); 
   	  cartago.set_current_wsp(WspOra4mas);
   	  lookupArtifact("bhsch",SchArtId);  
      getNormativeEngine(NEs)[artifact_id(SchArtId)];
      addNormativeEngine(NEs)[artifact_id(ArtSai)]; //adds the normative engine from the scheme artifact to the sai engine. Thus, the normative engine is fed with the constitutive state      
      lookupArtifact("hsh_group",GrpArtId);  
      getNormativeEngine(NEg)[artifact_id(GrpArtId)];
      addNormativeEngine(NEg)[artifact_id(ArtSai)]; //adds the normative engine from the group artifact to the sai engine. Thus, the normative engine is fed with the constitutive state
      */
      .
      
      
-!setup_sai_wsp_house
   <- .wait(500);
      !setup_sai_wsp_ora4mas.       
      
-!have_a_house[error(E),error_msg(Msg),code(Cmd),code_src(Src),code_line(Line)]
   <- .print("Failed to build a house due to: ",Msg," (",E,"). Command: ",Cmd, " on ",Src,":", Line).

   
/* Plans for Contracting */

+!contract
  <- !wait_for_bids.
   
   
+!wait_for_bids:nticks(X)&X>5000 // use an internal deadline of 5 seconds to close the auctions
   <- ?jcm__art("clock",Clock);
      println("I am stopping the clock. Time: " , X);
      stop[artifact_id(Clock)];
      !show_winners;
      .
      
+!wait_for_bids
   <- ?nticks(X);
      println("Waiting the bids ", 5000-X," seconds...");
      .wait(500); 
      !wait_for_bids;
      .
      
      
+!show_winners     
   <- for ( currentWinner(Ag)[artifact_id(ArtId)] ) {
         ?currentBid(Price)[artifact_id(ArtId)]; // check the current bid
         ?task(Task)[artifact_id(ArtId)];          // and the task it is for
         println("Winner of task ", Task," is ", Ag, " for ", Price)
      }. 

      
/* Plans for managing the execution of the house construction */

+!execute
   <- println;
      println("*** Execution Phase ***");
      println;	  
	      
      /*?jcm__ws("wsp_ora4mas",WspOra4mas); 
   	  cartago.set_current_wsp(WspOra4mas);
      */
    
   	 .
  

+!house_built // I have an obligation towards the top-level goal of the scheme: finished!
   <- println("*** Finished ***").
