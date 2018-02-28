package searchpoc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;



public class ResultSetToXML {
	
	public static void main(String[] args) throws ParserConfigurationException, SQLException, TransformerConfigurationException, TransformerException {
		
		JDBC jdbc  = new JDBC();
		ResultSet resultSet = jdbc.getResultSet();
		Document document = toDocument(resultSet);
		String documentAsXml = getDocumentAsXml(document);
		System.out.println("s " + documentAsXml);
	}
	
	public static Document toDocument(ResultSet rs)
			   throws ParserConfigurationException, SQLException
			{
			   DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			   DocumentBuilder builder        = factory.newDocumentBuilder();
			   Document doc                   = builder.newDocument();

			   Element results = doc.createElement("add");
			   doc.appendChild(results);

			   ResultSetMetaData rsmd = rs.getMetaData();
			   int colCount           = rsmd.getColumnCount();

			   while (rs.next())
			   {
			      Element row = doc.createElement("doc");
			      results.appendChild(row);

			      for (int i = 1; i <= colCount; i++)
			      {
			         String columnName = rsmd.getColumnName(i);
			         Object value      = rs.getObject(i);

				Element node = doc.createElement(columnName);
				try {
					node.appendChild(doc.createTextNode(value.toString()));
				} 
				catch (NullPointerException e) {
					System.out.println("null ptr");
				}
				row.appendChild(node);
			}
			   }
			   return doc;
			}
	
	
	public static String getDocumentAsXml(Document doc)
		      throws TransformerConfigurationException, TransformerException {
		    DOMSource domSource = new DOMSource(doc);
		    TransformerFactory tf = TransformerFactory.newInstance();
		    Transformer transformer = tf.newTransformer();
		    //transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes");
		    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		    transformer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
		    // we want to pretty format the XML output
		    // note : this is broken in jdk1.5 beta!
		    transformer.setOutputProperty
		       ("{http://xml.apache.org/xslt}indent-amount", "4");
		    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		    //
		    java.io.StringWriter sw = new java.io.StringWriter();
		    StreamResult sr = new StreamResult(sw);
		    transformer.transform(domSource, sr);
		    return sw.toString();
		 }

}
