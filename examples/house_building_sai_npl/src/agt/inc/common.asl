/* auxiliary rules for agents */

i_am_winning(Art)   // check if I placed the current best bid on auction artifact Art
   :- currentWinner(W)[artifact_id(Art)] &
      .my_name(Me) & .term2string(Me,MeS) & W == MeS.



/* Organisational Plans Required by all agents */

// plans to handle obligations
// obligation to commit to a mission
+obligation(Ag,Norm,committed(Ag,Mission,Scheme),Deadline)
    : .my_name(Ag)
   <- println("I am obliged to commit to ",Mission," on ",Scheme,"... Waiting for SAI to set the commitment...").
   
// obligation to achieve a goal      
+obligation(Ag,Norm,achieved(Scheme,Goal,Ag),Deadline)
    : .my_name(Ag)
   <-       println(" ---> working to achieve ",Goal);
      ?jcm__ws("wsp_house",WspHouse); //look to the SAI workspace
   	  cartago.set_current_wsp(WspHouse);      
      !Goal[scheme(Scheme)];
      println(" <--- done").
      
// an unknown type of obligation was received
+obligation(Ag,Norm,What,DeadLine)  
   : .my_name(Ag)
   <- println("I am obliged to ",What,", but I don't know what to do!").
