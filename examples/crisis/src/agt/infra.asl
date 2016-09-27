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
   
   lookupArtifact("sai_norms",ArtNorms);   
   focus(ArtNorms);   
   getNormativeEngine(NE)[artifact_id(ArtNorms)]; //get the normative engine enclosed in the normative artifact
   addNormativeEngine(NE)[artifact_id(ArtSai)];   //include the normative engine in the constitutive engine to make it an observer of changes in the constitutive state
   
   /*lookupArtifact("npl2sai",Npl2ai);   
   focus(Npl2ai);   
   getNormativeEngine(NE)[artifact_id(Npl2ai)];
   addNormativeEngine(NE)[artifact_id(ArtSai)];
   */
   
   
   /*Setup the workspace wsp_mayor*/
   ?jcm__ws("wsp_mayor",WsMayor);
   cartago.set_current_wsp(WsMayor);   
   setWSPRuleEngine(RE)[artifact_id(WsMayor)];
   
   
   /*Setup the workspace wsp_fire_brigade*/
   ?jcm__ws("wsp_fire_brigade",WsFB);
   cartago.set_current_wsp(WsFB);   
   setWSPRuleEngine(RE)[artifact_id(WsFB)];
   
   /*Setup the workspace wsp_gis*/
   ?jcm__ws("wsp_gis",WsGis);
   cartago.set_current_wsp(WsGis);   
   setWSPRuleEngine(RE)[artifact_id(Ws1)];   
   updateInhabitants(downtown, 200)[artifact_name("crisis_parameters")];	
   updatePhase(downtown, preventive)[artifact_name("crisis_parameters")];
   
   
   
   .print("hello world.").


+table(X) <- .println("Found table ", X).
