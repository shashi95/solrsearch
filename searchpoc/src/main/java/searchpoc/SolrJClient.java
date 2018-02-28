package searchpoc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SolrJClient {

	 public static void main(String[] args) throws Exception {

	        SolrClient client = new HttpSolrClient
	                .Builder("http://localhost:8983/solr/films").build();
//	        
	       // index(client);
	        

	        new SolrJSearch().search(client);
	    }

	private static void index(SolrClient client) throws Exception, SolrServerException, IOException {
		
		List<SolrInputDocument> solrInputDocumentListFromXmlFile = 
				getSolrInputDocumentListFromXmlFile("/home/shashi/Downloads/solr-7.2.1/example/films/films-data.xml");
		client.add(solrInputDocumentListFromXmlFile);
		client.commit();
	}
	 
	 private static List<SolrInputDocument> getSolrInputDocumentListFromXmlFile(
		        String fileName) throws Exception {

		    ArrayList<SolrInputDocument> solrDocList = new ArrayList<SolrInputDocument>();

		    File fXmlFile = new File(fileName);

		    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    Document doc = dBuilder.parse(fXmlFile);
		    
		    NodeList docList = doc.getElementsByTagName("doc");

		    for (int docIdx = 0; docIdx < docList.getLength(); docIdx++) {

		        Node docNode = docList.item(docIdx);

		        if (docNode.getNodeType() == Node.ELEMENT_NODE) {

		            SolrInputDocument solrInputDoc = new SolrInputDocument();

		            Element docElement = (Element) docNode;

		            NodeList fieldsList = docElement.getChildNodes();

		            for (int fieldIdx = 0; fieldIdx < fieldsList.getLength(); fieldIdx++) {

		                Node fieldNode = fieldsList.item(fieldIdx);

		                if (fieldNode.getNodeType() == Node.ELEMENT_NODE) {

		                    Element fieldElement = (Element) fieldNode;

		                    String fieldName = fieldElement.getAttribute("name");
		                    String fieldValue = fieldElement.getTextContent();

		                    solrInputDoc.addField(fieldName, fieldValue);
		                }

		            }

//		            if(docIdx == 1){
//		            	solrInputDoc.setDocumentBoost(10);
//
//		            }
		            solrDocList.add(solrInputDoc);
		        }
		    }

		    return solrDocList;

		}
	 
}
