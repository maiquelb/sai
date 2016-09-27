import java.util.ArrayList;
import java.util.LinkedList;

import sai.main.lang.parser.sai_constitutiveBaseListener;
import sai.main.lang.parser.sai_constitutiveParser.MContext;
import sai.main.lang.parser.sai_constitutiveParser.Sf_formulaContext;
import sai.main.lang.parser.sai_constitutiveParser.Sff_and_exprContext;
import sai.main.lang.parser.sai_constitutiveParser.Sff_or_exprContext;


public class TesteListner extends sai_constitutiveBaseListener{

	private ArrayList<String> terms = new ArrayList<String>();

	@Override
	public void exitM(MContext ctx) {
		// TODO Auto-generated method stub

	}

	/*

	@Override
	public void exitSff_rel(Sff_relContext ctx) {
		if(ctx.sff_rel().TK_IS()==null){
			System.out.println("nulo " +  ctx.getText());
		}else
			System.out.println("nao nulo " + ctx.arithm_term().getText() +" "+ ctx.TK_IS().getText() + " " + ctx.sff_rel().getText());

	}
	 */

	//firsfirst(x)|second(y)& a is b
	@Override
	public void exitSff_or_expr(Sff_or_exprContext ctx) {
		if(ctx.TK_OR()!=null){
			System.out.println(ctx.TK_OR().getText());
		}
		/*
		 * //se a expressao and for composta

		if(ctx.sff_and_expr().TK_AND()==null)
		   System.out.println( ctx.sff_and_expr().sff_rel().getText());
		else{
			System.out.println(ctx.sff_and_expr().sff_and_expr().getText());
			System.out.println(ctx.sff_and_expr().TK_AND());
			System.out.println(ctx.sff_and_expr().sff_rel().getText());
		}
		 */

		if(ctx.sff_and_expr().TK_AND()==null)
			System.out.println( ">> " + ctx.sff_and_expr().sff_rel().getText());
		else{
			processAnd(ctx.sff_and_expr());
		/*	//System.out.println(ctx.sff_and_expr().sff_and_expr().getText());
			System.out.println(ctx.sff_and_expr().sff_and_expr().getText());
			
			System.out.println(ctx.sff_and_expr().TK_AND());
			
			System.out.println(ctx.sff_and_expr().sff_rel().getText());*/
		}

	}

	
	private void processAnd(Sff_and_exprContext ctx){
		LinkedList<String> stack = new LinkedList<String>();
		//System.out.println( ctx.sff_rel().getText());
		stack.push(ctx.sff_rel().getText());
		if(ctx.sff_and_expr()!=null)			
		{
			//System.out.println(ctx.TK_AND().getText());
			stack.push(ctx.TK_AND().getText());
			processAnd(ctx.sff_and_expr());			
		}
		//else
			//System.out.println( ctx.sff_rel().getText());
		
		for(String s:stack){
			System.out.println(s);
		}
		
	}
	
	@Override
	public void exitSff_and_expr(Sff_and_exprContext ctx) {
		/*if(ctx.TK_AND()!=null){
			System.out.println(ctx.TK_AND().getText());
		}

		System.out.println(ctx.sff_rel().getText());
		 */
	}


	@Override
	public void exitSf_formula(Sf_formulaContext ctx) {
		//System.out.println(ctx.getText());
		//System.out.println(ctx.sff_or_expr().sff_and_expr().sff_rel().getText());
	}







}
