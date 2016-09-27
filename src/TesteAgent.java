import static jason.asSyntax.ASSyntax.parseLiteral;
import sai.util.reasoner.jason.VerifiableConsequenceLiteral;
import jason.RevisionFailedException;
import jason.asSyntax.Literal;
import jason.asSyntax.parser.ParseException;


public class TesteAgent {
	public static void main(String args[]) throws RevisionFailedException, ParseException{

		/*JasonReasoner reasoner = new NormativeReasoner();
		Literal l1 = parseLiteral("instance(communicator,obliged,informTangibleInteraction(table_mayor,to1,1,2),(tangibleInteraction(table_fire_brigade,to1,1,2,ss) & not (((table_fire_brigade == table_mayor) & ((to1 == to1) & ((1 == 1) & (2 == 2))))))) ");
		Literal l2 = parseLiteral("instance(communicator,obliged,informTangibleInteraction(table_mayor,to1,1,2),(tangibleInteraction(VarOtherTable,VarOtherObject,VarOtherX,VarOtherY,Var) & not (((VarOtherTable == table_mayor) & ((VarOtherObject == to1) & ((VarOtherX == 1) & (VarOtherY == 2)))))))");
		System.out.println(reasoner.isLogicalConsequence(l1, l2));
		*/
		
		
		
		VerifiableConsequenceLiteral l1;
		Literal l2;
		
		l1 = new VerifiableConsequenceLiteral(parseLiteral("instance(dd,obliged,sai__event(evacuate(downtown),dd),not (secure(downtown)))"));
		l2 = parseLiteral("instance(dd,prohibited,sai__event(evacuate(downtown),dd),not (secure(downtown)))");
		System.out.println(l1.isLogicalConsequenceOf(l2));
		
		l1 = new VerifiableConsequenceLiteral(parseLiteral("instance(dd,prohibited,sai__event(evacuate(downtown),dd),not (secure(downtown)))"));
		l2 = parseLiteral("instance(dd,prohibited,sai__event(evacuate(downtown),dd),not (secure(downtown)))");
		System.out.println(l1.isLogicalConsequenceOf(l2));
		
		l1 = new VerifiableConsequenceLiteral(parseLiteral("teste(a)"));
		l2 = parseLiteral("teste(X)");
		System.out.println(l1.isLogicalConsequenceOf(l2));
		
	}
}