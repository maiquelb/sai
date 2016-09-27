import static jason.asSyntax.ASSyntax.createAtom;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import sai.main.lang.parser.sai_constitutiveLexer;
import sai.main.lang.parser.sai_constitutiveListenerImpl;
import sai.main.lang.parser.sai_constitutiveParser;
import sai.main.lang.semantics.InstProgram;
import sai.main.lang.semantics.statusFunction.AgentStatusFunction;


public class TesteAntlr {
	public static void main(String args[]) throws IOException{
		InstProgram program = new InstProgram();
		try {
			program.addStatusFunction(new AgentStatusFunction(createAtom("y")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStream is = new ByteArrayInputStream("1: x count-as y while teste(x) & x is y.".getBytes());
		ANTLRInputStream input = new ANTLRInputStream(is);			
		sai_constitutiveLexer lexer = new sai_constitutiveLexer(input);		
		CommonTokenStream tokens = new CommonTokenStream(lexer);	
		sai_constitutiveParser parser = new sai_constitutiveParser(tokens);	

		ParseTree tree = parser.const_rule();
		sai_constitutiveListenerImpl nParser = new sai_constitutiveListenerImpl(program);
		ParseTreeWalker walker = new ParseTreeWalker(); // create standard walker			
		walker.walk(nParser, tree); // initiate walk of tree with listener
	}

}
