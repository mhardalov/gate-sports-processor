package org.sports.gate.model;

import java.util.ArrayList;
import java.util.List;

public class ResultRelation {

	private String result;
	private List<String> competitors;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<String> getCompetitors() {
		return competitors;
	}

	public void setCompetitors(List<String> competitors) {
		this.competitors = competitors;
	}
	
	public ResultRelation() {
		this.result = "";
		this.competitors = new ArrayList<String>();
	}
}
