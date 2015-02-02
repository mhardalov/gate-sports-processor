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
import org.sports.gate.model.PersonQuotes;
import org.sports.gate.model.ResultRelation;

public class Application {

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
//	private static PrintWriter writer;

	// private static void annotate(Corpus corpus) throws
	// InvalidOffsetException,
	// IOException {
	// writer = new PrintWriter(outputSerializationDir + "quotes.txt", "UTF-8");
	//
	// for (int i = 0; i < corpus.size(); i++) {
	// Document doc = corpus.get(i);
	// AnnotationSet quotes = doc.getAnnotations().get("QuotedText");
	// AnnotationSet tokens = doc.getAnnotations().get("Token");
	//
	// for (Annotation quote : quotes) {
	// AnnotationSet containedTokens = tokens.getContained(quote
	// .getStartNode().getOffset(), quote.getEndNode()
	// .getOffset());
	// String quoteStr = doc
	// .getContent()
	// .getContent(quote.getStartNode().getOffset(),
	// quote.getEndNode().getOffset()).toString();
	//
	// if (containedTokens.size() < 5) {
	// System.out.println(quoteStr);
	// continue;
	// }
	//
	// writer.println(doc.getContent().getContent(
	// quote.getStartNode().getOffset(),
	// quote.getEndNode().getOffset()));
	//
	// PrintWriter writer = new PrintWriter(outputSerializationDir +
	// "myFile.txt", "UTF-8");
	// writer.write(doc.getContent().getContent(
	// quote.getStartNode().getOffset(),
	// quote.getEndNode().getOffset()).toString());
	// writer.write("\n");
	// writer.close();
	// }
	//
	// // AnnotationSet quotes = doc.getAnnotations().get("Quote");
	// // AnnotationSet personSays =
	// // doc.getAnnotations().get("PersonSays");
	// // AnnotationSet quoteSentenses = doc.getAnnotations().get(
	// // "QuoteSentense");
	// // AnnotationSet persons = doc.getAnnotations().get("Person");
	// //
	// // for (Annotation psays : personSays) {
	// // AnnotationSet containedQuotes = quotes.getContained(psays
	// // .getStartNode().getOffset(), psays.getEndNode()
	// // .getOffset());
	// //
	// // // AnnotationSet containedPersons - contain
	// // System.out.println(doc.getContent().getContent(
	// // containedQuotes.firstNode().getOffset(),
	// // containedQuotes.lastNode().getOffset()));
	// // }
	// }
	// }
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

	public static void main(String[] args) throws Exception {
		// prepare the library and clean up the config files
		Gate.setGateHome(new File(gateHome));
		Gate.setPluginsHome(new File(gatePluginsHome));
		Gate.init();
		Gate.initConfigData();

		// throws swing exception
		// show the main window
		SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
				MainFrame.getInstance().setVisible(true);
			}
		});

		// create a corpus
		Corpus corpus = Factory.newCorpus(corpusName);

		for (String str : args) {
			Document doc = Factory.newDocument(str);
			corpus.add(doc);
		}

		// load an application from a gapp file
		ConditionalSerialAnalyserController myapp = (ConditionalSerialAnalyserController) PersistenceManager
				.loadObjectFromFile(new File(applicationFile));

		// set a corpus for the app
		myapp.setCorpus(corpus);

		// execute the application
		myapp.execute();

		//annotate(corpus);
		annotateResults(corpus);
	}

}
