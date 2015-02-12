package org.sports.gate;

import gate.Corpus;
import gate.Document;
import gate.Factory;
import gate.Gate;
import gate.creole.ConditionalSerialAnalyserController;
import gate.gui.MainFrame;
import gate.util.persistence.PersistenceManager;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.classification.mahout.algorithms.SGDClassification;
import org.classification.mahout.gate.LoadDocuments;
import org.sports.gate.model.DocumentQuotes;
import org.sports.gate.model.DocumentResults;
import org.sports.ontology.OntologyHandler;
import org.sports.ontology.model.DocumentModel;
import org.sports.ontology.model.PersonQuotes;
import org.sports.ontology.model.ResultRelation;

import com.hp.hpl.jena.rdf.model.Resource;

public class GateSportsApplication {

	// configuration data
	static String docsFolder = "resources/gate/sports_terms/docs_test";
	static String corpusName = "Sports articles";
	static String applicationFile = "/home/momchil/Projects/spring-demo/gate-sports-processor/src/main/resources/gate/SportsAnalysis.xgapp";
	static String ontologyFile = "/home/momchil/Projects/spring-demo/gate-sports-processor/src/main/resources/gate/sports_terms/ontology/sports.owl";
	static String outputSerializationDir = "/home/momchil/Projects/spring-demo/gate-sports-processor/src/main/resources/gate/sports_terms/output_files/";
	static String datastoreDir = "/home/momchil/Projects/spring-demo/gate-sports-processor/src/main/resources/gate/sports_terms/datastore";
	// change the values of the following two variables with the corresponding
	// paths in your own system
	final static String gateHome = "/home/momchil/GATE_Developer_8.0/";
	final static String gatePluginsHome = "/home/momchil/GATE_Developer_8.0/plugins/";
	static ConditionalSerialAnalyserController myapp;
	static Corpus corpus;

	static OntologyHandler handler = new OntologyHandler();	
	static SGDClassification classification = new SGDClassification();

	static {
		// prepare the library and clean up the config files
		Gate.setGateHome(new File(gateHome));
		Gate.setPluginsHome(new File(gatePluginsHome));
		try {
			Gate.init();
			Gate.initConfigData();
			MainFrame.getInstance().setVisible(false);

			// load an application from a gapp file
			myapp = (ConditionalSerialAnalyserController) PersistenceManager
					.loadObjectFromFile(new File(applicationFile));

			// create a corpus
			corpus = Factory.newCorpus(corpusName);

			handler.open(ontologyFile);
			LoadDocuments documents = new LoadDocuments();

			classification.setOpinions(documents.getOpinions());
			classification.train();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static DocumentQuotes annotateQuotes(Document doc) throws IOException {

		DocumentQuotes docQuotes = new DocumentQuotes(doc);
		docQuotes.extractQuotes(classification);

		return docQuotes;
	}

	public static DocumentResults annotateResults(Document doc) {

		DocumentResults docResults = new DocumentResults(doc);
		docResults.extractResults();

		return docResults;
	}

	private static void addDocToOntology(DocumentModel document,
			DocumentQuotes quotes, DocumentResults results) {

		Resource docResource = handler.registerDocument(document);

		for (PersonQuotes quoteModel : quotes.getPersonQuotes()) {
			handler.addPersonQuote(quoteModel, docResource);
		}

		for (ResultRelation relationModel : results.getResultRelations()) {
			handler.addResultRelation(relationModel, docResource);
		}
	}

	public static void annotate(List<DocumentModel> documents) throws Exception {

		for (DocumentModel document : documents) {
			Document doc = Factory.newDocument(document.getContent());
			corpus.add(doc);
		}

		// set a corpus for the app
		myapp.setCorpus(corpus);

		// execute the application
		myapp.execute();

		int index = 0;
		for (Document doc : corpus) {

			DocumentQuotes quotes = annotateQuotes(doc);
			DocumentResults results = annotateResults(doc);

			if (quotes.getPersonQuotes().size() > 0
					|| results.getResultRelations().size() > 0)
				addDocToOntology(documents.get(index), quotes, results);

			index++;
		}

		// remove the document from the corpus again
		corpus.clear();

		handler.save(ontologyFile);
	}
}
