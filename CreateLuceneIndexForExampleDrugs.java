

/**
 * See http://www.onjava.com/pub/a/onjava/2003/01/15/lucene.html?page=1
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
public class CreateLuceneIndexForExampleDrugs {

	/**
	 * Create a Lucene index containing some sample drug names
	 * The field names need to include the values of the fieldName attribute
	 * of the metaField elements and the lookupField element in the 
	 * LookupDescriptorFile for this dictionary.
	 * For example, if the LookupDescriptorFile being used is LookupDesc.xml
	 * and if it defines the following four elements for this dictionary:<br>
	 *   lookupField fieldName="first_word"<br>
	 *   metaField fieldName="code"<br>
	 * 	 metaField fieldName="preferred_designation"<br>
	 * 	 metaField fieldName="other_designation"<br>
	 * then the lucene index needs to include four fields with those names.
	 * 
	 * @param args unused/ignored
	 */
	@SuppressWarnings("deprecation")
    public static void main(String args[]) throws Exception {

		// Name of the lucene index directory to be created 
		File indexDir = new File("drug_index");//C:/temp/lucene/" + "drug-index";
		
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_31);
		boolean createFlag = true;

		IndexWriter writer = new IndexWriter(FSDirectory.open(indexDir), analyzer, createFlag, IndexWriter.MaxFieldLength.LIMITED);

		BufferedReader br = new BufferedReader(new FileReader("drugs.csv"));
		String line = br.readLine();
    int linelength = line.split(",").length;
    System.out.println(linelength);
    line = br.readLine();
    int id = 1;
    while(line!=null){
        
        String[] temp = line.split(",");
        if (temp.length!=linelength){
            System.out.println(line);
            line = br.readLine();
            continue;
        }
        String drugname = temp[2].trim().replaceAll("\"", "");
        String genericName = temp[19].replaceAll("\"", "");
        System.out.println(drugname);
		
			Document document = new Document();
			document.add(new Field("UNIQUE_DOCUMENT_IDENTIFIER_FIELD", String.valueOf(id), Field.Store.YES,
					Field.Index.NO));
			document.add(new Field("code", "C"+String.valueOf(id), Field.Store.YES,
					Field.Index.NO));
			document.add(new Field("codeTokenized", "C"+String.valueOf(id), Field.Store.YES,
					Field.Index.ANALYZED));
			document.add(new Field("first_word", drugname.split(" ")[0], Field.Store.YES,
					Field.Index.ANALYZED));
			document.add(new Field("preferred_designation", genericName, Field.Store.YES,
					Field.Index.ANALYZED));
			document.add(new Field("other_designation", drugname, Field.Store.YES,
		          Field.Index.ANALYZED));
			id++;
			line = br.readLine();
			writer.addDocument(document);
    }
		writer.close();
		System.out.println("Wrote lucene index: " + writer);
		br.close();
	}
	
}


