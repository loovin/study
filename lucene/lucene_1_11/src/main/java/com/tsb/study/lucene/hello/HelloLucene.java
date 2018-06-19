package com.tsb.study.lucene.hello;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class HelloLucene {
	
	/**
	 * 建立索引
	 * @throws IOException 
	 */
	public static void index() throws IOException{
		//1、创建Directory
		//Directory directory = new RAMDirectory();
		Directory directory = FSDirectory.open(new File("D:\\lucene\\index\\hello"));
		
		//2、创建IndexWriter
		IndexWriterConfig iwConfig = new IndexWriterConfig(Version.LUCENE_35,new StandardAnalyzer(Version.LUCENE_35));
		IndexWriter indexWriter = null;
		try {
			indexWriter = new IndexWriter(directory, iwConfig);
			
			Document doc = null;
			File f = new File("D:\\lucene\\hello");
			for(File file:f.listFiles()){
				//3、创建Document
				doc = new Document();
				//4、为Document创建Field
				doc.add(new Field("content", new FileReader(file)));
				doc.add(new Field("filename",file.getName(),Field.Store.YES,Field.Index.NOT_ANALYZED));
				doc.add(new Field("path",file.getPath(),Field.Store.YES,Field.Index.NOT_ANALYZED));
				
				//5、通过IndexWriter添加文档到索引中
				indexWriter.addDocument(doc);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(null != indexWriter){
				try {
					indexWriter.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public static void search(){
		try {
			//1、创建Directory
			Directory directory = FSDirectory.open(new File("D:\\lucene\\index\\hello"));
			
			//2、创建IndexReader
			IndexReader reader = IndexReader.open(directory);
			System.out.println(reader.numDocs());
			System.out.println(reader.maxDoc());
			
			//3、根据IndexReader创建IndexSearch
			IndexSearcher searcher = new IndexSearcher(reader);
			
			//4、创建搜索Query
			QueryParser parser = new QueryParser(Version.LUCENE_35, "content", new StandardAnalyzer(Version.LUCENE_35));
			Query query = parser.parse("OTHERWISE");//搜索包含  OTHERWISE 的文档
			//5、根据search搜索，并返回TopDocs
			TopDocs topDocs = searcher.search(query, 10);
			//6、根据TopDocs获取StoreDoc对象
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for(ScoreDoc scoreDoc:scoreDocs){
				Document doc = searcher.doc(scoreDoc.doc);
				System.out.println(doc.get("filename")+"-------------"+doc.get("path"));
			}
			//7、根据search和storedoc获取Document对象
			//8、根据Document对象获取需要的值
			
			searcher.close();
			reader.close();
			directory.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
		try {
			//index();
			search();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
