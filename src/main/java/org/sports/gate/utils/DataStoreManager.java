package org.sports.gate.utils;

import gate.Corpus;
import gate.DataStore;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.creole.ResourceInstantiationException;
import gate.persist.SerialDataStore;
import gate.util.Out;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class DataStoreManager {

	private String datastorePath;

	private void openDataStore() {
		try {

			File storageDir = File.createTempFile("TestPersist__",
					"__StorageDir");
			datastorePath = storageDir.toURI().toURL().toString();
			storageDir.delete();

			// create and open a serial data store
			DataStore sds = Factory.createDataStore(
					"gate.persist.SerialDataStore", datastorePath);
			Out.prln("serial datastore created...");

			// create test corpus
			Corpus corp = this.createTestCorpus();
			Corpus persistCorp = null;

			// save corpus in datastore
			// SecurityInfo is ingored for SerialDataStore - just pass null
			// a new persisent corpus is returned
			persistCorp = (Corpus) sds.adopt(corp);
			sds.sync(persistCorp);
			Out.prln("corpus saved in datastore...");

			// change corpus and sync it with the datastore
			persistCorp.setName("new name");
			persistCorp.sync();

			// load corpus from datastore using its persistent ID
			Object corpusID = persistCorp.getLRPersistenceId();
			persistCorp = null;

			FeatureMap corpFeatures = Factory.newFeatureMap();
			corpFeatures.put(DataStore.LR_ID_FEATURE_NAME, corpusID);
			corpFeatures.put(DataStore.DATASTORE_FEATURE_NAME, sds);

			// tell the factory to load the Serial Corpus with the specified ID
			// from the specified datastore
			persistCorp = (Corpus) Factory.createResource(
					"gate.corpora.SerialCorpusImpl", corpFeatures);
			Out.prln("corpus loaded from datastore...");

			// remove corpus from datastore
			//sds.delete("gate.corpora.SerialCorpusImpl", corpusID);
			Out.prln("corpus deleted from datastore...");
			persistCorp = null;

			// close data store
//			sds.close();
//			sds = null;

			// reopen it
//			sds = new SerialDataStore(this.datastorePath);
//			sds.open();

			// save, load and delete a document
			Document doc = this.createTestDocument();
			Document persistDoc = null;

			// save document in datastore
			// SecurityInfo is ingored for SerialDataStore - just pass null
			persistDoc = (Document) sds.adopt(doc);			
			sds.sync(persistDoc);
			Out.prln("document saved in datastore...");

			// change document and sync it with the datastore
			persistDoc.setName("new name");
			persistDoc.sync();

			// load document from datastore
			Object docID = persistDoc.getLRPersistenceId();
			persistDoc = null;

			FeatureMap docFeatures = Factory.newFeatureMap();
			docFeatures.put(DataStore.LR_ID_FEATURE_NAME, docID);
			docFeatures.put(DataStore.DATASTORE_FEATURE_NAME, sds);

			persistDoc = (Document) Factory.createResource(
					"gate.corpora.DocumentImpl", docFeatures);
			Out.prln("document loaded from datastore...");

			// remove document from datastore
			//sds.delete("gate.corpora.DocumentImpl", docID);
			Out.prln("document deleted from datastore...");
			persistDoc = null;

			// close data store
			//sds.close();

			// delete datastore
			//sds.delete();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Corpus createTestCorpus() throws MalformedURLException,
			ResourceInstantiationException {

		Document doc1 = Factory.newDocument(new URL(
				"http://gate.ac.uk/index.html"));
		// Document doc1 = Factory.newDocument(new
		// URL("file:///c:/temp/test.txt"));
		Document doc2 = Factory.newDocument(new URL(
				"http://gate.ac.uk/documentation.html"));

		assert doc1 != null && doc2 != null;

		// create a corpus with the above documents
		Corpus result = Factory.newCorpus("test corpus");
		assert result != null;
		result.add(doc1);
		result.add(doc2);

		return result;
	}

	public Document createTestDocument() throws MalformedURLException,
			ResourceInstantiationException {

		Document result = Factory.newDocument(new URL(
				"http://gate.ac.uk/index.html"));
		assert result != null;

		return result;
	}

	public DataStoreManager(String datastorePath) {
		this.datastorePath = datastorePath;

		this.openDataStore();
	}

}
