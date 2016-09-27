import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import sai.main.lang.parser.FormulaVisitor;
import sai.main.lang.parser.sai_constitutiveLexer;
import sai.main.lang.parser.sai_constitutiveParser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;



public class Teste {


	public static void main(String[] args) {

		
		InputStream is;
		try {
			//is = new FileInputStream("/home/maiquel/Dropbox/ufsc/doutorado/tese/lang/constitutive_example_sem_is.sai");
			//is = new ByteArrayInputStream("while first(x)|second(y)&third(z)&fourgh(e)* a is b|five(s)".getBytes());
			//is = new ByteArrayInputStream("not a|b&(c|d&x |(a is b & t))&e".getBytes());
			is = new ByteArrayInputStream("not(sai__is(Other,mayor,_))|sai__is(Actor,mayor,_)|Actor is mayor".getBytes());

			ANTLRInputStream input = new ANTLRInputStream(is);
			sai_constitutiveLexer constLexer = new sai_constitutiveLexer(input);		
			CommonTokenStream tokens = new CommonTokenStream(constLexer);
			sai_constitutiveParser constParser = new sai_constitutiveParser(tokens);
			ParseTree tree = constParser.sf_formula();
			ParseTreeWalker walker = new ParseTreeWalker(); // create standard walker
			//TesteListner constExtractor = new TesteListner();
			//walker.walk(constExtractor, tree); // initiate walk of tree with listener
			
			FormulaVisitor visitor = new FormulaVisitor();
			visitor.visit(tree);
			System.out.println(visitor.getFormula());
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
