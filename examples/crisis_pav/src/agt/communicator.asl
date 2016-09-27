/*   */
{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }


/*The agent is supposed to know all the tables in the application*/
known_table(table_fire_brigade).
known_table(table_logistic_cell).
known_table(table_mayor).

/* The agent knows the right imagibles to signal the tangibles 
   corresponding_imagible(T,I) means that the imagible I corresponds to the tangible T   
*/
corresponding_imagible(launch_tangible_object,launch_imagible).
corresponding_imagible(alert_tangible_object,alert_imagible).
corresponding_imagible(to3,img3).
corresponding_imagible(to4,img4).
corresponding_imagible(to5,img5).



!start.



+!start : true <- 
   .print("hello world.");
   .

 


+table(X)[artifact_id(Art)] <- 
   .println("Found table ", X);
   +known_table(X,Art).



/* Handling SAI default norms */
+obliged(Me,informTangibleInteraction(SourceTable,TangibleObject,X,Y),Cd) : .my_name(Me) & corresponding_imagible(TangibleObject,Imagible) & .term2string(SourceTable, STable)
   <- .print("SAI informs I am obliged to inform that the tangible object ", TangibleObject, " is in the ", SourceTable, "(", X, ",", Y, ")");
      for ( known_table(TargetTable,ArtId) & TargetTable \== STable ){
	    putImagible(Imagible,X,Y,Me)[artifact_id(ArtId)];   
     }
	.
	
/* Handling PAV norms */	
+as(Me,Fa,Fm,informTangibleInteraction(SourceTable,TangibleObject,X,Y),Fr,Ft) : .my_name(Me) & corresponding_imagible(TangibleObject,Imagible) & .term2string(SourceTable, STable)
   <- .print("SAI informs I am obliged to inform that the tangible object ", TangibleObject, " is in the ", SourceTable, "(", X, ",", Y, ")");
      for ( known_table(TargetTable,ArtId) & TargetTable \== STable ){
	    putImagible(Imagible,X,Y,Me)[artifact_id(ArtId)];   
     }
	.
	
