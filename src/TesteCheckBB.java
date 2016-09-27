import jason.RevisionFailedException;
import jason.asSemantics.Agent;
import jason.asSyntax.parser.ParseException;


public class TesteCheckBB {
	private static Agent agent = new Agent();
	
	public static void main(String args[]) throws RevisionFailedException, ParseException{
		System.out.println(handleIs(" teste sss bob is auctioneer dsdsdsds tom is bidder dsds"));
	}	
	
	
	private static String handleIs(String text){
		if(text.indexOf(" is ") == -1){
			return text;
		}
		else{ 
			String initial = ""; 
			if(text.substring(0,text.indexOf(" is ")).lastIndexOf(" ")>0)				
			   initial = text.substring(0,text.substring(0,text.indexOf(" is ")).lastIndexOf(" "));
			String preIs = text.substring(text.substring(0,text.indexOf(" is ")).lastIndexOf(" ")+1,(text.indexOf(" is ")));
			String posIs = text.substring(text.indexOf(" is ")+4);
			String sFinal = "";
			if(text.substring(text.indexOf(" is ")+4).indexOf(" ")==-1)
				sFinal = text.substring(text.indexOf(" is ")+4) + ")";
			else{
				sFinal = text.substring(text.indexOf(" is ")+4,
						 text.substring(text.indexOf(" is ")+4).indexOf(" ") +  text.indexOf(" is ")+4) + ")" +
						 text.substring(text.substring(text.indexOf(" is ")+4).indexOf(" ") +  text.indexOf(" is ")+4)
						 
						 ;
			}
				
			return  handleIs(initial + " is("+preIs+","+sFinal);
		}
			/*return text.substring(0,text.substring(0,text.indexOf(" is ")).lastIndexOf(" ")) +" "+										
				   "is(" + text.substring(text.substring(0,text.indexOf(" is ")).lastIndexOf(" ")+1,(text.indexOf(" is ")-1))+ "," + 
			         text.substring(text.indexOf(" is ")+4)+
				     text.substring(text.indexOf(" is ")+4);
*/
	}

}
