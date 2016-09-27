// Agent giacomo, who wants to build a house

{ include("common.asl") }

/* Initial beliefs and rules */

// counts the number of tasks based on the observable properties of the auction artifacts
number_of_tasks(NS) :- .findall( S, task(S), L) & .length(L,NS).
      

/* Initial goals */

!setup.


/* Plans */

+!setup
   <- 
       .my_name(Me);
      createWorkspace("ora4mas");
      joinWorkspace("ora4mas",_);        
      .wait(5000);
      jacamo.infra.jacamo_new_obj("sai4jacamo.RuleEngine",[],Id);
      setWSPRuleEngine(Id);	
      makeArtifact("sai","sai4jacamo.Sai4JacamoArt",[Id],SaiArt);
      focus(SaiArt);
      makeArtifact("table_ccp","simulator.TangiTable",["table_ccp"],TbCcp);
      focus(TbCcp);
      makeArtifact("table_fire_brigade","simulator.TangiTable",["table_fire_brigade"],TbFf);
      focus(TbFf);
      makeArtifact("table_logistic_cell","simulator.TangiTable",["table_logistic_cell"],TbLc);
      focus(TbLc);
      addNormativeProgram("/home/maiquel/Dropbox/ufsc/doutorado/emse/crisis/app_paper/normative_running.sai")[artifact_id(SaiArt)];
      addConstitutiveProgram("/home/maiquel/Dropbox/ufsc/doutorado/emse/crisis/app_paper/constitutive_running.sai")[artifact_id(SaiArt)].
