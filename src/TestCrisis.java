


public class TestCrisis {


	public static void main(String[] args) {


/*
		InputStream is;

		try {

			SaiEngine engine = new SaiEngine();



			is = new FileInputStream("/home/maiquel/Dropbox/ufsc/doutorado/emse/crisis/app_paper/normative_running.sai");			
			ANTLRInputStream input = new ANTLRInputStream(is);			
			sai_normativeLexer lexer = new sai_normativeLexer(input);		
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			sai_normativeParser parser = new sai_normativeParser(tokens);
			ParseTree tree = parser.sai();

			ParseTreeWalker walker = new ParseTreeWalker(); // create standard walker			
			sai_normativeListenerImpl extractor = new sai_normativeListenerImpl(engine.getProgram());
			walker.walk(extractor, tree); // initiate walk of tree with listener			


			
			is = new FileInputStream("/home/maiquel/Dropbox/ufsc/doutorado/emse/crisis/app_paper/constitutive_running.sai");
			input = new ANTLRInputStream(is);
			sai_constitutiveLexer constLexer = new sai_constitutiveLexer(input);		
			tokens = new CommonTokenStream(constLexer);
			sai_constitutiveParser constParser = new sai_constitutiveParser(tokens);
			tree = constParser.constitutive_spec();

			walker = new ParseTreeWalker(); // create standard walker
			sai_constitutiveListenerImpl constExtractor = new sai_constitutiveListenerImpl(engine.getProgram());
			walker.walk(constExtractor, tree); // initiate walk of tree with listener
			System.out.println(engine.getProgram().toString());
			//adding a first environmental property
			engine.addEnvironmentalProperty(parseLiteral("nbInhabit(downtown,100)"));
			//engine.addEnvironmentalEvent(parseLiteral("checkin(table_pcc)"), createAtom("bob"));
			//engine.addEnvironmentalEvent(parseLiteral("checkin(table_logistic)"), createAtom("tom"));
			//engine.addEnvironmentalEvent(parseLiteral("checkin(table_fire_brigade)"), createAtom("jim"));

	
	
			//suggestion for running:
			//putTangiProd(table1,tangiProd1,15,20)[sai__agent(bob)]
			
			//1. sigmaE(checkin(table_cpp)[sai__agent(bob)])
			//   sigmaE(checkin(table_logistic)[sai__agent(tom)])
			//   sigmaE(checkin(table_fire_brigade)[sai__agent(jim)])
			//2. security_phase(downtown,preventive) - environmental property of downtown being in security phase
			//3. sigmaE(putTangiProd(table1,tangiProd1,15,20)[sai__agent(bob)])
			//   sigmaE(putTangible(_,tangibleObject4,15,20)[sai__agent(jim)])			
			Scanner s = new Scanner(System.in);		
			while(true){
				String command = s.next();
				if(command.substring(0,6).equals("sigmaA")){
					Literal agent = parseLiteral(command);
					engine.addEnvironmentalAgent((Literal) agent.getTerm(0));
				}
				else
					if(command.substring(0,6).equals("sigmaE")){
						Literal event = parseLiteral(command);
						Literal eventId = (Literal) event.getTerm(0);						
						engine.addEnvironmentalEvent(eventId, (Atom) eventId.getAnnot("sai__agent").getTerm(0));
					}
					else
						if(command.substring(0,7).equals("retract")){
							Literal cmd = parseLiteral(command);
							engine.replaceEnvironmentalProperty((Literal) cmd.getTerm(0), parseLiteral("retract"));
						}
					
					else{

						engine.addEnvironmentalProperty(parseLiteral(command));
					}

			}


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



*/
	}

}
