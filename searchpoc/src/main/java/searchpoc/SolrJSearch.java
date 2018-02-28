package searchpoc;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class SolrJSearch {

	public void search(SolrClient client) throws SolrServerException, IOException {
		
        SolrQuery query = new SolrQuery();
        query.set("defType", "edismax");
        query.setQuery("city:Delhi");
        query.addField("*");  
        query.set("bq", "city:abc^2");
//        query.addFilterQuery("city:Delhi");
//        query.setFields("id","first name","last name","phone","city");
//        query.setStart(0);
//        query.set("defType", "edismax");
        System.out.println(query);
		QueryResponse response = client.query(query);
		SolrDocumentList results = response.getResults();
		System.out.println();
		int i = 1;
		for (SolrDocument solrDocument : results) {
			
			System.out.println(solrDocument.toString());
			i++;
		}
		
		
	}
}
