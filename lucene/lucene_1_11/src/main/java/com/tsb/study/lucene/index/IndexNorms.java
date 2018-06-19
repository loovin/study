package com.tsb.study.lucene.index;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
/**
 * 建立索引时进行加权操作
 * @author Administrator
 *
 */
public class IndexNorms {
	private String[] ids = { "1", "2", "3", "4", "5", "6" };
	private String[] emails = { "aa@tsb.com", "bb@tsb.com", "cc@qq.com", "dd@sina.com", "ee@qq.com", "ff@tsb.com" };
	private String[] content = { 
			"welcome to tsb, i like book", 
			"you are my son, i like pingpang", 
			"i am you father, i like game", 
			"do you want to die, i like footbool",
			"i like china", 
			"you are so naive, i like team" };
	private int[] attches = { 2, 3, 1, 4, 5, 5 };
	private String[] names = { "shangsan", "lisi", "john", "jetty", "mike", "jake" };
	private Map<String,Float> scores = new HashMap<String,Float>();

	private Directory directory = null;

	public IndexNorms() {
		try {
			scores.put("tsb.com", 2.0f);
			scores.put("qq.com", 1.5f);
			directory = FSDirectory.open(new File("D:\\lucene\\index\\indexutil"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteIndex() {
		System.out.println("===============清空全部索引===============");
		IndexWriter writer = null;

		try {
			writer = new IndexWriter(directory,
					new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));
			writer.deleteAll();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != writer) {
				try {
					writer.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void index() {
		System.out.println("============重新构建索引===========");
		IndexWriter writer = null;
		try {
			writer = new IndexWriter(directory,
					new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));
			Document doc = null;
			for (int i = 0; i < ids.length; i++) {
				doc = new Document();
				doc.add(new Field("id", ids[i], Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
				doc.add(new Field("email", emails[i], Field.Store.YES, Field.Index.NOT_ANALYZED));
				doc.add(new Field("content", content[i], Field.Store.NO, Field.Index.ANALYZED));
				doc.add(new Field("name", names[i], Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
				
				String et = emails[i].substring(emails[i].indexOf("@")+1);
				//System.out.println(et);
				if(scores.containsKey(et)){
					doc.setBoost(scores.get(et));
				}else{
					doc.setBoost(0.5f);
				}
				
				writer.addDocument(doc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != writer) {
				try {
					writer.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void query() {
		IndexReader reader = null;
		try {
			reader = IndexReader.open(directory);
			IndexSearcher searcher = new IndexSearcher(reader);
			
			TermQuery termQuery = new TermQuery(new Term("content","like"));
			TopDocs topDocs = searcher.search(termQuery, 10);
			for(ScoreDoc scoreDoc:topDocs.scoreDocs){
				Document doc = searcher.doc(scoreDoc.doc);
				System.out.println(doc.get("id")+"------------"+doc.get("email"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

	public static void main(String[] args) {
		IndexNorms indexUtil = new IndexNorms();
		indexUtil.deleteIndex();
		indexUtil.index();
		indexUtil.query();
	}

}
