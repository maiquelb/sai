/* */

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }






!start.


+!start : true <- .print("hello world.").



 
//TODO: make a generic plan to deal with this obligation 
+obliged(Me,inform_invalid_evacuation(Zone,Actor),_) : .my_name(Me)  & Zone==downtown & Actor==firefighter
   <- .print("SAI informs I am obliged to inform an inconsistent evacuation of ", Zone, " to ", Actor);   
      ?jcm__ws("wsp_fire_brigade",WsFB);
      cartago.set_current_wsp(WsFB);       
      lookupArtifact("table_fire_brigade", ArtId);
      .print("The table is ", ArtId);
      putImagible(wrong_action_imagible,1,2, Me)[artifact_id(ArtId)];           
      .
      
+obliged(Me,inform_invalid_evacuation(Zone,Actor),_) : .my_name(Me)  & Zone==downtown & Actor==mayor
   <- .print("SAI informs I am obliged to inform an inconsistent evacuation of ", Zone, " to ", Actor);
      ?jcm__ws("wsp_mayor",WsMayor);
      cartago.set_current_wsp(WsMayor);          
      lookupArtifact("table_mayor", ArtId);
      .print("The table is ", ArtId);
      putImagible(wrong_action_imagible,1,2, Me)[artifact_id(ArtId)];           
      . 