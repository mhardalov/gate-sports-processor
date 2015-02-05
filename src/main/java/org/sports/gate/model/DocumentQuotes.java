package org.sports.gate.model;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;

import java.util.ArrayList;
import java.util.List;

import org.sports.ontology.model.PersonQuotes;

public class DocumentQuotes {
	private List<PersonQuotes> personQuotes;
	private Document doc;

	public DocumentQuotes(Document doc) {
		this.doc = doc;
		this.personQuotes = new ArrayList<PersonQuotes>();
	}

	private PersonQuotes getByPerson(String person) {
		PersonQuotes result = null;

		if (person != "") {
			for (PersonQuotes quotes : this.personQuotes) {
				String quotesPerson = quotes.getPerson().toLowerCase();
				String lowerPerson = person.toLowerCase();

				// If it's entity. Example Person John Smith equals "said Smith"
				if (quotesPerson.contains(lowerPerson)) {
					result = quotes;
					break;
				}
			}

			if (result == null) {
				result = new PersonQuotes();
				result.setPerson(person);
				this.personQuotes.add(result);
			}
		}

		return result;
	}

	private void addPersonQuote(String person, String quote) {
		PersonQuotes personQuotes = this.getByPerson(person);

		if (personQuotes != null) {
			personQuotes.addQuote(quote);
		}
	}

	private String extractQuote(Annotation personSay) {

		return (String) personSay.getFeatures().get("quote");
	}

	private String extractPerson(Annotation personSay) {
		return (String) personSay.getFeatures().get("person");
	}

	public List<PersonQuotes> getPersonQuotes() {
		return personQuotes;
	}

	public void setPersonQuotes(List<PersonQuotes> personQuotes) {
		this.personQuotes = personQuotes;
	}

	public void extractQuotes() {
		AnnotationSet personSays = doc.getAnnotations().get("PersonSays");

		for (Annotation personSay : personSays) {
			String quote = this.extractQuote(personSay);
			String person = this.extractPerson(personSay);

			this.addPersonQuote(person, quote);
		}
	}
}
