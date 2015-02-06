package org.sports.gate.model;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;

import java.util.ArrayList;
import java.util.List;

import org.sports.ontology.model.ResultRelation;

public class DocumentResults {
	private List<ResultRelation> resultRelations;
	private Document doc;

	public DocumentResults(Document doc) {
		this.doc = doc;
		this.setResultRelations(new ArrayList<ResultRelation>());
	}
	
	private void addFoundResult(ResultRelation relation) {
		if (!this.resultRelations.contains(relation)) {
			this.getResultRelations().add(relation);
		}
	}
	
	public ResultRelation extractLocationResult(Annotation result) {
		String resultStr = result.getFeatures().get("result").toString();
		
		List<String> competitors = new ArrayList<String>();
		competitors.add(result.getFeatures().get("first_competitor").toString());		
		String location = result.getFeatures().get("location") != null ? result.getFeatures().get("location").toString() : "";		
		
		ResultRelation relation = new ResultRelation("location");
		relation.setCompetitors(competitors);
		relation.setResult(resultStr);
		relation.setLocation(location);
		
		return relation;
	}	


	public ResultRelation extractSampleResult(Annotation result) {
		String resultStr = result.getFeatures().get("result").toString();
		
		List<String> competitors = new ArrayList<String>();
		competitors.add(result.getFeatures().get("first_competitor").toString());
		if (result.getFeatures().get("second_competitor") != null) {
			competitors.add(result.getFeatures().get("second_competitor").toString());
		}
		String location = result.getFeatures().get("location") != null ? result.getFeatures().get("location").toString() : "";
		
		ResultRelation relation = new ResultRelation("sample");
		relation.setCompetitors(competitors);
		relation.setResult(resultStr);
		relation.setLocation(location);
		
		return relation;
	}
	
	public void extractResults() {
		AnnotationSet foundResults = doc.getAnnotations().get("FoundResult");
//		AnnotationSet quotedText = doc.getAnnotations().get("QuotedText");
//		AnnotationSet results = doc.getAnnotations().get("Result");

		for (Annotation result : foundResults) {
			String kind = result.getFeatures().get("kind").toString();
			ResultRelation relation;
			
			switch (kind) {
			case "sample":
				relation = extractSampleResult(result);
				this.addFoundResult(relation);
				break;
			case "location":
				relation = extractLocationResult(result);
				this.addFoundResult(relation);
				break;
			default:
				//this.addFoundResult(competitors, resultStr);
			}			
		}
	}

	public List<ResultRelation> getResultRelations() {
		return resultRelations;
	}

	public void setResultRelations(List<ResultRelation> resultRelations) {
		this.resultRelations = resultRelations;
	}
}
