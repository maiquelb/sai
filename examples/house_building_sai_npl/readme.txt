This is a version of the classical house_built example without Moise/ORA4MAS. 
The NPL programs produced by the ORA4MAS artifacts in the original example are loaded by the NPL engine. These programs are in /src/org/bhsch.txt (functional specification) and /src/org/hsh_group.txt (structural specification).
The facts provided to the NPL engine come from the SAI constitutive state. The constitutive specification is in /src/org/constitutive.sai




To run this example:
   Type ant.




While the example is running:

 To check the normative state, go to
   - http://localhost:3275/bh

To check the normative state, go to
   - http://localhost:3273/npl/bhsch 
   - http://localhost:3273/npl/hsh_group

To check the history of the normative state, go to 
   - http://localhost:3273/npl/bhsch/history 
   - http://localhost:3273/npl/hsh_group/history
