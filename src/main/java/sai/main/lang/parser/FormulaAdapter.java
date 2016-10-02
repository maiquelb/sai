package sai.main.lang.parser;

import jason.asSyntax.LogicalFormula;
import jason.asSyntax.parser.ParseException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import sai.main.lang.parser.sai_constitutiveParser.Sf_formulaContext;
import sai.main.lang.parser.sai_constitutiveParser.Sff_and_exprContext;
import sai.main.lang.parser.sai_constitutiveParser.Sff_or_exprContext;
import sai.main.lang.semantics.InstProgram;
import sai.main.lang.semantics.statusFunction.StateStatusFunction;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import static jason.asSyntax.ASSyntax.parseFormula;

public class FormulaAdapter {
	
	private static String formula;
	
	
	
	public static String adaptFormula1(String lformula) throws IOException{
		InputStream is =  new ByteArrayInputStream(lformula.concat(".").getBytes());
		ANTLRInputStream input = new ANTLRInputStream(is);
		sai_constitutiveLexer constLexer = new sai_constitutiveLexer(input);		
		CommonTokenStream tokens = new CommonTokenStream(constLexer);
		sai_constitutiveParser constParser = new sai_constitutiveParser(tokens);
		ParseTree tree = constParser.sf_formula();
		//ParseTreeWalker walker = new ParseTreeWalker(); // create standard walker
		FormulaAdapter f = new FormulaAdapter();
		VisitorTermM v = f.new VisitorTermM(); 
		v.visitSf_formula((Sf_formulaContext) tree);
		
		
		
		return formula;
	}
	
	public static String adaptFormula1(LogicalFormula lformula) throws IOException{
		InputStream is =  new ByteArrayInputStream(lformula.toString().getBytes());
		ANTLRInputStream input = new ANTLRInputStream(is);
		sai_constitutiveLexer constLexer = new sai_constitutiveLexer(input);		
		CommonTokenStream tokens = new CommonTokenStream(constLexer);
		sai_constitutiveParser constParser = new sai_constitutiveParser(tokens);
		ParseTree tree = constParser.sf_formula();
		//ParseTreeWalker walker = new ParseTreeWalker(); // create standard walker
		FormulaAdapter f = new FormulaAdapter();
		VisitorTermM v = f.new VisitorTermM(); 
		v.visitSf_formula((Sf_formulaContext) tree);
				
		return formula;
	}
	
	
	public static String adaptFormula(LogicalFormula lformula, InstProgram instProgram) throws IOException{
		InputStream is =  new ByteArrayInputStream(lformula.toString().getBytes());
		ANTLRInputStream input = new ANTLRInputStream(is);
		sai_constitutiveLexer constLexer = new sai_constitutiveLexer(input);		
		CommonTokenStream tokens = new CommonTokenStream(constLexer);
		sai_constitutiveParser constParser = new sai_constitutiveParser(tokens);
		ParseTree tree = constParser.sf_formula();
		//ParseTreeWalker walker = new ParseTreeWalker(); // create standard walker
		FormulaAdapter f = new FormulaAdapter();
		VisitorTermM_InstProgram v = f.new VisitorTermM_InstProgram(); 
		v.visitSf_formula((Sf_formulaContext) tree, instProgram);
				
		return formula;
	}
	
	public static String adaptFormula(String lformula, InstProgram instProgram) throws IOException, ParseException{						
		return FormulaAdapter.adaptFormula(parseFormula(lformula), instProgram);
	}
	
	
	
	private class VisitorTermM extends  sai_constitutiveBaseVisitor<Void> {
		@Override
		public Void visitSf_formula(Sf_formulaContext ctx) {
			formula = "";
			visitSff_or_expr(ctx.sff_or_expr());
			return null;
		}
		
		
		@Override
		public Void visitSff_or_expr(Sff_or_exprContext ctx) {	
			visitSff_and_expr(ctx.sff_and_expr());
			if(ctx.sff_or_expr()!=null){
				formula = formula.concat("|");
				visitSff_or_expr(ctx.sff_or_expr());
			}
			return null;
		}
		
		@Override
		public Void visitSff_and_expr(Sff_and_exprContext ctx) {
			if(ctx.sff_rel().arithm_term().arithm_factor().sff_atom().NEGATION()!=null)
				formula = formula.concat("not ");
			if(ctx.sff_rel().arithm_term().arithm_factor().sff_atom().sff_or_expr()!=null){
				formula = formula.concat("(");
				visitSff_or_expr(ctx.sff_rel().arithm_term().arithm_factor().sff_atom().sff_or_expr());
				formula = formula.concat(")");
			}
			else{				
				if((ctx.sff_rel().arithm_term().arithm_factor().sff_atom().pred()!=null)&&(ctx.sff_rel().arithm_term().arithm_factor().sff_atom().pred().ATOM().getText().equals("sai__is"))){
					formula = formula.concat("sai__is(" + ctx.sff_rel().arithm_term().arithm_factor().sff_atom().pred().list_of_pred_terms().pred_terms().pred_term(0).getText() + "," + 
							                              ctx.sff_rel().arithm_term().arithm_factor().sff_atom().pred().list_of_pred_terms().pred_terms().pred_term(1).getText() + ",_)");
				}
				else
					if(ctx.sff_rel().arithm_term().arithm_factor().INTMOD()!=null){
						formula = formula +  "((" + ctx.sff_rel().arithm_term().arithm_factor().sff_atom().getText() + ")mod(" + ctx.sff_rel().arithm_term().arithm_factor().arithm_factor().getText() + "))" + ctx.sff_rel().TK_REL_OP().getText() + ctx.sff_rel().sff_rel().getText();
					}
					else{
						formula = formula.concat(ctx.sff_rel().getText());
					}
			}
			if(ctx.sff_and_expr()!=null){
				formula = formula.concat("&");
				visitSff_and_expr(ctx.sff_and_expr());
			}			
			return null;
		}		
	}
	
	
	

	/*A visitor that considers the institutional program*/
	private class VisitorTermM_InstProgram extends  sai_constitutiveBaseVisitor<Void> {
		InstProgram instProgram;
		
		public Void visitSf_formula(Sf_formulaContext ctx, InstProgram instProgram) {
			this.instProgram = instProgram;
			formula = "";
			visitSff_or_expr(ctx.sff_or_expr());
			return null;
		}
		
		
		@Override
		public Void visitSff_or_expr(Sff_or_exprContext ctx) {	
			visitSff_and_expr(ctx.sff_and_expr());
			if(ctx.sff_or_expr()!=null){
				formula = formula.concat("|");
				visitSff_or_expr(ctx.sff_or_expr());
			}
			return null;
		}
		
		@Override
		public Void visitSff_and_expr(Sff_and_exprContext ctx) {
			if(ctx.sff_rel().arithm_term().arithm_factor().sff_atom().NEGATION()!=null)
				formula = formula.concat("not ");
			if(ctx.sff_rel().arithm_term().arithm_factor().sff_atom().sff_or_expr()!=null){
				formula = formula.concat("(");
				visitSff_or_expr(ctx.sff_rel().arithm_term().arithm_factor().sff_atom().sff_or_expr());
				formula = formula.concat(")");
			}
			else{				
				if((ctx.sff_rel().arithm_term().arithm_factor().sff_atom().pred()!=null)&&(ctx.sff_rel().arithm_term().arithm_factor().sff_atom().pred().ATOM().getText().equals("sai__is"))){
					formula = formula.concat("sai__is(" + ctx.sff_rel().arithm_term().arithm_factor().sff_atom().pred().list_of_pred_terms().pred_terms().pred_term(0).getText() + "," + 
							                              ctx.sff_rel().arithm_term().arithm_factor().sff_atom().pred().list_of_pred_terms().pred_terms().pred_term(1).getText() + ",_)");
				}
				else
					if(ctx.sff_rel().arithm_term().arithm_factor().INTMOD()!=null){
						formula = formula +  "((" + ctx.sff_rel().arithm_term().arithm_factor().sff_atom().getText() + ")mod(" + ctx.sff_rel().arithm_term().arithm_factor().arithm_factor().getText() + "))" + ctx.sff_rel().TK_REL_OP().getText() + ctx.sff_rel().sff_rel().getText();
					}
					else
						//if the element of the formula is a state-sf
						if(ctx.sff_rel().arithm_term().arithm_factor().sff_atom().pred()!=null&&instProgram.getStatusFunctionByName(ctx.sff_rel().arithm_term().arithm_factor().sff_atom().pred().getText())!=null&&instProgram.getStatusFunctionByName(ctx.sff_rel().arithm_term().arithm_factor().sff_atom().pred().getText())instanceof StateStatusFunction){
							formula = formula.concat("sai__is(_,"+ctx.sff_rel().getText()+",_)");
						}
					else{
						formula = formula.concat(ctx.sff_rel().getText());
					}
			}
			if(ctx.sff_and_expr()!=null){
				formula = formula.concat("&");
				visitSff_and_expr(ctx.sff_and_expr());
			}			
			return null;
		}		
	}
}
