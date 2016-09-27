The original example available at jacamo.sf.net

This example is modified to exemplify Situated Artificial Institutions model. It is similar to the original but during its running, the SAI engine is fed with facts occurring in the environment (CArtAgO artifacts). Based on these facts, the SAI engine calculates the current constitutive and normative states. These states can be inspected at http://localhost:8001 and at the file sai.log.


To run this example just type in a terminal, under the bin folder : ant run
 
IMPORTANT: This example is configured to use modified versions of CArtAgO and JaCaMo. They are stored at /lib folder. In order to keep the proper configuration, do not run it from JEdit/JaCaMo plugin.



About sai.log
============= 
The Sai Engine runs in cycles. A cycle is triggered by an environmental fact. In a cycle, the SAI state is update in n subcicles (for n>= 1). A subcycle n_x (for x > 1) updates the SAI state based on the updates occurred in the subcycle n_(x-1). The end of a cycle is reached when a subcycle does not raise any update in the SAI state. Sai log records these elements as [saiLogger] [cycle] #subcycle fact.

