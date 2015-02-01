package org.sports.gate.model;

import gate.Document;

import java.util.ArrayList;
import java.util.List;

public class DocumentResults {
	private List<ResultRelation> resultRelations;
	private Document doc;
	
	public DocumentResults(Document doc) {
		this.doc = doc;
		this.resultRelations = new ArrayList<ResultRelation>();
	}
}
