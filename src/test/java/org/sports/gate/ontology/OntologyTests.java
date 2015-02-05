package org.sports.gate.ontology;

import java.util.Calendar;

import org.junit.Test;
import org.sports.gate.model.DocumentModel;
import org.sports.gate.model.PersonQuotes;
import org.sports.gate.model.ResultRelation;

import com.hp.hpl.jena.rdf.model.Resource;

public class OntologyTests {
	
	@Test
	public void ontologyTest() {
		PersonQuotes quotes = new PersonQuotes();
		quotes.setPerson("John Smith");
		quotes.addQuote("I'm the best.");
		quotes.addQuote("Ontology testing with some quotes by me.");
		quotes.addQuote("Third sentsence for today.");
		
		ResultRelation relation = new ResultRelation();
		relation.setResult("7:2");
		relation.getCompetitors().add("Levski");
		relation.getCompetitors().add("CSKA");
		
		DocumentModel document = new DocumentModel();
		document.setContent("Empty");
		document.setUrl("http://somewhere/JohnSmith");
		document.setDate(Calendar.getInstance().getTime());
		
		OntologyHandler handler = new OntologyHandler();
		Resource resource = handler.registerDocument(document);
		handler.addPersonQuote(quotes, resource);
		handler.addResultRelation(relation, resource);
		
		quotes = new PersonQuotes();
		quotes.setPerson("Tom Johnes");		
		quotes.addQuote("Second Quote by me.");
		quotes.addQuote("Come on do it!");
		
		relation = new ResultRelation();
		relation.setResult("10s");
		relation.getCompetitors().add("Bolt");	
		document = new DocumentModel();
		document.setContent("Empty");
		document.setUrl("http://somewhere/TomJohnes");
		document.setDate(Calendar.getInstance().getTime());
		
		resource = handler.registerDocument(document);
		handler.addPersonQuote(quotes, resource);
		handler.addResultRelation(relation, resource);
		
		handler.print();
		
		handler.query("http://somewhere/TomJohnes");
	}
}
