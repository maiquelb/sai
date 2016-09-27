/* 
 * This agent perform some tasks related to the infratructure that are not specified in the jcm file. 
 * Specifically, it attaches the Rule Engine, encolsed in the Sai4JaCaMo artifact, to each workspace in the application. 
 * The Rule Engine is a listener that captures events and changes in the observable state of the artifacts.   
 * 
 * 
 */

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

+!start : true <- 
	
   ?jcm__ws("wsp_sai",WspSai); //look to the SAI workspace
   cartago.set_current_wsp(WspSai);
   lookupArtifact("sai",ArtSai);   
   focus(ArtSai);   
   getRuleEngine(RE)[artifact_id(ArtSai)];
   
   
   /*Setup the workspace wsp_mayor*/
   ?jcm__ws("wsp_mayor",WsMayor);
   cartago.set_current_wsp(WsMayor);   
   setWSPRuleEngine(RE)[artifact_id(WsMayor)];
   
   
   /*Setup the workspace wsp_fire_brigade*/
   ?jcm__ws("wsp_fire_brigade",WsFB);
   cartago.set_current_wsp(WsFB);   
   setWSPRuleEngine(RE)[artifact_id(WsFB)];

   /*Setup the workspace wsp_lc*/
   ?jcm__ws("wsp_lc",WsLC);
   cartago.set_current_wsp(WsLC);   
   setWSPRuleEngine(RE)[artifact_id(WsLC)];
   
   /*Setup the workspace wsp_sc*/
   ?jcm__ws("wsp_sc",WsSC);
   cartago.set_current_wsp(WsSC);   
   setWSPRuleEngine(RE)[artifact_id(WsSC)];
   
   /*Setup the workspace wsp_gis*/
   ?jcm__ws("wsp_gis",WsGis);
   cartago.set_current_wsp(WsGis);   
   setWSPRuleEngine(RE)[artifact_id(Ws1)];         
   updatePhase(preventive)[artifact_name("crisis_parameters_industrial")];
   updateInhabitants(250)[artifact_name("crisis_parameters_industrial")];
   updateInhabitants(200)[artifact_name("crisis_parameters_downtown")];	   
   updatePhase(preventive)[artifact_name("crisis_parameters_downtown")];   
   setFrequency(1)[artifact_name("clock")];
   start[artifact_name("clock")];  
   
   .print("hello world.").


