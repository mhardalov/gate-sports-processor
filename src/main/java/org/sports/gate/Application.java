package org.sports.gate;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Corpus;
import gate.Document;
import gate.Factory;
import gate.Gate;
import gate.creole.ConditionalSerialAnalyserController;
import gate.gui.MainFrame;
import gate.util.InvalidOffsetException;
import gate.util.persistence.PersistenceManager;

import java.io.File;

public class Application {

	// configuration data
	static String docsFolder = "resources/gate/sports_terms/docs_test";
	static String corpusName = "Sports articles";
	static String applicationFile = "src/main/resources/gate/SportsTermsProcessor.xgapp";
	static String ontologyFile = "src/main/resources/gate/sports_terms/ontology/disease_organ.owl";
	static String ontologyFile1 = "src/main/resources/gate/sports_terms/ontology/disease_organ1.owl";
	static String outputSerializationDir = "src/main/resources/gate/sports_terms/output_files/";
	// change the values of the following two variables with the corresponding
	// paths in your own system
	final static String gateHome = "/home/momchil/GATE_Developer_8.0/";
	final static String gatePluginsHome = "/home/momchil/GATE_Developer_8.0/plugins/";

	private static void annotate(Corpus corpus) throws InvalidOffsetException {
		for (int i = 0; i < corpus.size(); i++) {
			Document doc = corpus.get(i);
			AnnotationSet anns = doc.getAnnotations()
					.get("Quote");
			AnnotationSet anns2 = doc.getAnnotations().get("Person");
			for (Annotation a : anns) {
				System.out.println("rel: "						
						+ doc.getContent().getContent(
								a.getStartNode().getOffset(),
								a.getEndNode().getOffset()));
				
				AnnotationSet anns3 = anns2.getContained(a.getStartNode().getOffset(),
						a.getEndNode().getOffset());
				
				for (Annotation a1 : anns3) {
					System.out.println("rel: "						
							+ doc.getContent().getContent(
									a1.getStartNode().getOffset(),
									a1.getEndNode().getOffset()));
				}
			}
		}
	}
	
	private static void stems(Corpus corpus) throws InvalidOffsetException {
		for (int i = 0; i < corpus.size(); i++) {
			Document doc = corpus.get(i);
			AnnotationSet anns = doc.getAnnotations()
					.get("Token");
			
			for (Annotation a : anns) {
				String stem = a.getFeatures().get("stem").toString();
				System.out.println(stem);
				
				
			}
		}
	}

	public static void main(String[] args) throws Exception {
		// prepare the library and clean up the config files
		Gate.setGateHome(new File(gateHome));
		Gate.setPluginsHome(new File(gatePluginsHome));
		Gate.init();
		Gate.initConfigData();		

		// make invisiblevisible
		MainFrame.getInstance().setVisible(true);

		// create a corpus
		Corpus corpus = Factory.newCorpus(corpusName);

		// add documents to the corpus
		// File inputDir = new File(docsFolder);
		// for (File file : inputDir.listFiles()) {
		// // create a new document
		// Document doc = Factory.newDocument(file.toURI().toURL());
		// corpus.add(doc);
		// }

		Document doc = Factory
				.newDocument("Президентът на българския футболен съюз Борислав Михайлов изтъкна причините, "
						+ "поради които Изпълкомът реши да освободи националния селекционер Любослав Пенев от "
						+ "заемания от него пост. Също така босът на БФС призова медиите да не нападат "
						+ "четвъртите в света, които участват в управлението на родния футбол. "
						+ "\"Анализирахме доста ситуацията в националния отбор. "
						+ "Всичките три години на Любо Пенев, в които постигна 9 победи, 7 равни и 8 загуби. "
						+ "Колегите бяха на мнение, особено през последната година, че не се представяме добре. "
						+ "Още след четвъртия кръг отборът почти е изгубил шансове да се класира и то при "
						+ "положение че се класират първите два отбора в групата. "
						+ "Издръжката на националния отбор е 2 750 000 лева. "
						+ "Условията са уникални, на нивото на големите отбори. "
						+ "Със сигурност най-големите виновници са футболистите. "
						+ "Но треньорът трябва да им въздейства по някакъв начин. "
						+ "Играчите го подведоха. Някои от футболистите се самозабравиха. "
						+ "Приятели сме с Любо, играли сме заедно. Разделяме се като приятели. "
						+ "Футболистите подведоха треньора. Много от тях не знаят какво е да си "
						+ "играч на националния отбор. Питах ги какъв им е проблемът, а те ми казват, че няма проблем. "
						+ "Значи има голям проблем. Ако се бяха класирали, щяха да си разделят 2,5 млн. евро. "
						+ "Как повече да ги мотивираме?\", каза Боби, след което призова медиите да не критикуват "
						+ "\"четвъртите в света\", които са част от управлението на българския футбол. "
						+ "\"Искам да престанете да се упражнявате върху играчите от 1994 г. "
						+ "Няма по-добра федерация от тази. Престанете да се упражнявате върху тези хора, "
						+ "защото те са дали много\", допълни Михайлов, след което отново се върна на темата "
						+ "\"Любо Пенев\". \"Дали ще плащаме неустойка, дали няма, тепърва ще говорим с него. "
						+ "Със сигурност Пенев отлепи ютията, но е факт, че сме четвърти. "
						+ "Много ме е яд за раздялата с него. Вкара дисциплина\", добави босът на футболния съюз.");

		corpus.add(doc);

		// load an application from a gapp file
		ConditionalSerialAnalyserController myapp = (ConditionalSerialAnalyserController) PersistenceManager
				.loadObjectFromFile(new File(applicationFile));

		// set a corpus for the app
		myapp.setCorpus(corpus);

		// execute the application
		myapp.execute();

		annotate(corpus);
		stems(corpus);
	}

}
