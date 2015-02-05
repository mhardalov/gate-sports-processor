package org.sports.gate;

import gate.Corpus;
import gate.Document;
import gate.Factory;
import gate.Gate;
import gate.creole.ConditionalSerialAnalyserController;
import gate.gui.MainFrame;
import gate.util.InvalidOffsetException;
import gate.util.persistence.PersistenceManager;

import java.io.File;
import java.util.List;

import javax.swing.SwingUtilities;

import org.sports.gate.model.DocumentQuotes;
import org.sports.gate.model.DocumentResults;
import org.sports.gate.model.DocumentModel;
import org.sports.gate.model.PersonQuotes;
import org.sports.gate.model.ResultRelation;

public class GateSportsApplication {

	// configuration data
	static String docsFolder = "resources/gate/sports_terms/docs_test";
	static String corpusName = "Sports articles";
	static String applicationFile = "/home/momchil/Projects/spring-demo/gate-sports-processor/src/main/resources/gate/SportsAnalysis.xgapp";
	static String ontologyFile = "src/main/resources/gate/sports_terms/ontology/disease_organ.owl";
	static String outputSerializationDir = "/home/momchil/Projects/spring-demo/gate-sports-processor/src/main/resources/gate/sports_terms/output_files/";
	static String datastoreDir = "/home/momchil/Projects/spring-demo/gate-sports-processor/src/main/resources/gate/sports_terms/datastore";
	// change the values of the following two variables with the corresponding
	// paths in your own system
	final static String gateHome = "/home/momchil/GATE_Developer_8.0/";
	final static String gatePluginsHome = "/home/momchil/GATE_Developer_8.0/plugins/";

	public static void annotate(Corpus corpus) throws InvalidOffsetException {
		for (int i = 0; i < corpus.size(); i++) {
			Document doc = corpus.get(i);
			DocumentQuotes docQuotes = new DocumentQuotes(doc);
			docQuotes.extractQuotes();
			List<PersonQuotes> personQuotes = docQuotes.getPersonQuotes();

			for (PersonQuotes quotes : personQuotes) {
				System.out.println("Person" + quotes.getPerson() + " said:");
				System.out.print("Quotes:");
				for (String quote : quotes.getQuotes()) {
					System.out.print(quote + ",");
				}

				System.out.println("");
			}
		}
	}
	
	public static void annotateResults(Corpus corpus) {
		
		for (int i = 0; i < corpus.size(); i++) {
			Document doc = corpus.get(i);
			DocumentResults docResults = new DocumentResults(doc);
			docResults.extractResults();
			List<ResultRelation> resultRelation = docResults.getResultRelations();

			for (ResultRelation relation : resultRelation) {
				System.out.println("Result Found:");
				for (String competitor : relation.getCompetitors()) {
					System.out.print(competitor + " - ");
				}
				System.out.print(relation.getResult());				

				System.out.println("");
			}
		}
	}
	
	static {
		// prepare the library and clean up the config files
		Gate.setGateHome(new File(gateHome));
		Gate.setPluginsHome(new File(gatePluginsHome));
		try {
			Gate.init();
			Gate.initConfigData();
			
			// throws swing exception
			// show the main window
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					MainFrame.getInstance().setVisible(false);
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public static void annotate(DocumentModel document) throws Exception {

		// create a corpus
		Corpus corpus = Factory.newCorpus(corpusName);
		Document doc = Factory.newDocument(document.getContent());
		corpus.add(doc);

//		for (String str : documents) {
//			Document doc = Factory.newDocument(str);
//			corpus.add(doc);
//		}

		// load an application from a gapp file
		ConditionalSerialAnalyserController myapp = (ConditionalSerialAnalyserController) PersistenceManager
				.loadObjectFromFile(new File(applicationFile));

		// set a corpus for the app
		myapp.setCorpus(corpus);

		// execute the application
		myapp.execute();

		annotate(corpus);
		annotateResults(corpus);
	}

}
