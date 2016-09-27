// Common code for the organisational part of the system

/*  **** Disabled to SAI ****
task_roles("SitePreparation",  [site_prep_contractor]).
task_roles("Floors",           [bricklayer]).
task_roles("Walls",            [bricklayer]).
task_roles("Roof",             [roofer]).
task_roles("WindowsDoors",     [window_fitter, door_fitter]).
task_roles("Plumbing",         [plumber]).
task_roles("ElectricalSystem", [electrician]).
task_roles("Painting",         [painter]).

+!contract(Task,GroupName)
    : task_roles(Task,Roles)
   <- //!in_ora4mas;
      lookupArtifact(GroupName, GroupId);
      for ( .member( Role, Roles) ) {
         adoptRole(Role)[artifact_id(GroupId)];
         focus(GroupId)
      }. 
    
-!contract(Service,GroupName)[error(E),error_msg(Msg),code(Cmd),code_src(Src),code_line(Line)]
   <- .print("Failed to sign the contract for ",Service,"/",GroupName,": ",Msg," (",E,"). command: ",Cmd, " on ",Src,":", Line).


*/
