/* auxiliary rules for interacting with SAI */


/*  To check the constitutive rules that the agent knows 
+constitutive_rule(X,Y,T,M): .my_name(companyA) 
   <- println("Constitutive rule ", X, " count as ", Y, " when ", T, " while ", M).

*/

+sai_obligation(StatusFunction,ToAchieve): .my_name(Me) & sai_is(Me,StatusFunction) & not(i_am_obliged(ToAchieve))
   <- +i_am_obliged(ToAchieve);
      println("SAI informs that i am  obliged to achieve ", ToAchieve, " because I carry the status function of ", StatusFunction).
   
+sai_is(Me,StatusFunction): .my_name(Me) & sai_obligation(StatusFunction,ToAchieve)& not(i_am_obliged(ToAchieve))  
   <- +i_am_obliged(ToAchieve); 
      println("SAI informs that i am  obliged to achieve ", ToAchieve, " because I carry the status function of ", StatusFunction).

      
      
/* if the aim is an event */      
/*+obliged(A,sai__event(I,A),Cd): .my_name(A) & constitutive_rule(X[sai__agent(Agent)],Y,T,M) & Y==I    */
+obliged(A,sai__event(I,A),Cd): .my_name(A) & constitutive_rule(X[sai__agent(Agent)],I,T,M)   
   <- println("SAI informs that i am  obliged produce the event ", I," until the deadline ", Cd,". To do that, I need to perform ", X);
      println(" ---> working to achieve ",I);
      !I;
      println(I, " done thanks to SAI.");
      println(" ").


+obliged(A,sai__event(I,A),Cd): .my_name(A)    
   <- println("SAI informs that i am  obliged produce the event ", I," until the deadline ", Cd,". But I do not know how...").   


/* if the aim is a state */   
+obliged(A,I,Cd): .my_name(A)    
   <- //println("SAI informs that i am  obliged to achieve ", A,",",I,",",Ca,",",Cd).
      println("SAI informs that i am  obliged to achieve ", I, " until ", Cd);
      !I.
    
/*-obliged(A,I,Ca,Cd): .my_name(A)   
<- println("SAI informs that i am NO MORE  obliged to achieve ", A,",",I,",",Ca,",",Cd).
* /
*/
