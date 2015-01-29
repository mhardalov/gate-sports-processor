package org.sports.gate.model;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;
import gate.util.InvalidOffsetException;

import java.util.ArrayList;
import java.util.List;

public class DocumentQuotes {
	private List<PersonQuotes> personQuotes;
	private Document doc;

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

	private String extractQuote(AnnotationSet quotedText, Annotation personSay)
			throws InvalidOffsetException {
		AnnotationSet quote = quotedText.getContained(personSay.getStartNode()
				.getOffset(), personSay.getEndNode().getOffset());

		return doc
				.getContent()
				.getContent(quote.firstNode().getOffset(),
						quote.lastNode().getOffset()).toString();
	}

	private String extractPerson(AnnotationSet quotes, Annotation personSay)
			throws InvalidOffsetException {
		String personString = "";

		AnnotationSet containedQuotes = quotes
				.getContained(personSay.getStartNode().getOffset(), personSay
						.getEndNode().getOffset());

		AnnotationSet persons = doc.getAnnotations().get("Person");

		AnnotationSet person = persons.getContained(containedQuotes.firstNode()
				.getOffset(), containedQuotes.lastNode().getOffset());

		if (person.size() == 0) {
			AnnotationSet entities = doc.getAnnotations().get("EntityAnnotation");
			
			person = entities.getContained(containedQuotes.firstNode()
					.getOffset(), containedQuotes.lastNode().getOffset());
		}

		if (person.size() > 0) {
			personString = doc
					.getContent()
					.getContent(person.firstNode().getOffset(),
							person.lastNode().getOffset()).toString();
		}

		return personString;
	}

	public List<PersonQuotes> getPersonQuotes() {
		return personQuotes;
	}

	public void setPersonQuotes(List<PersonQuotes> personQuotes) {
		this.personQuotes = personQuotes;
	}

	public DocumentQuotes(Document doc) {
		this.doc = doc;
		this.personQuotes = new ArrayList<PersonQuotes>();
	}

	public void extractQuotes() throws InvalidOffsetException {
		AnnotationSet personSays = doc.getAnnotations().get("PersonSays");
		AnnotationSet quotedText = doc.getAnnotations().get("QuotedText");
		AnnotationSet quotes = doc.getAnnotations().get("Quote");

		for (Annotation personSay : personSays) {
			String quote = this.extractQuote(quotedText, personSay);
			String person = this.extractPerson(quotes, personSay);

			this.addPersonQuote(person, quote);
		}
	}
}
