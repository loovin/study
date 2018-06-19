package com.tsb.study.lucene.search;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

/**
 * 对于term查询，如果字段有分词，则精确按词匹配，如果不分词，则匹配开头字母
 * 
 * @author Administrator
 *
 */
public class Search_Term_Numeric_Range {
	private static String[] ids = { "1", "2", "3312", "4", "5", "6" };
	private String[] emails = { "aa@tsb.com", "bb@tsb.com", "cc@qq.com", "dd@sina.com", "ee@qq.com", "ff@tsb.com" };
	private String[] content = { "welcome to tsb, i like book", "you are my son, i like pingpang",
			"i am you father, i like game", "do you want to die, i like footbool", "i like china",
			"you are so naive, i like team" };
	private int[] attches = { 2, 3, 1, 4, 5, 5 };
	private String[] names = { "shangsan", "lisi", "john", "jetty", "mike", "jake" };
	private static Date[] dates = null;
	static {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		dates = new Date[ids.length];
		try {
			dates[0] = format.parse("2010-02-19");
			dates[1] = format.parse("2012-01-11");
			dates[2] = format.parse("2011-09-19");
			dates[3] = format.parse("2010-12-22");
			dates[4] = format.parse("2012-01-01");
			dates[5] = format.parse("2011-05-19");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Directory directory = null;
	private IndexReader reader = null;

	public Search_Term_Numeric_Range() {
		directory = new RAMDirectory();
	}

	public synchronized IndexSearcher getSearcher() {
		try {
			if (null == reader) {
				reader = IndexReader.open(directory);
			} else {
				IndexReader tmpReader = IndexReader.openIfChanged(reader);
				if (null != tmpReader) {
					reader.close();
					reader = tmpReader;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new IndexSearcher(reader);
	}

	public void index() {
		System.out.println("============重新构建索引===========");
		IndexWriter writer = null;
		try {
			writer = new IndexWriter(directory,
					new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));
			writer.deleteAll();

			Document doc = null;
			for (int i = 0; i < ids.length; i++) {
				doc = new Document();
				doc.add(new Field("id", ids[i], Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
				doc.add(new Field("email", emails[i], Field.Store.YES, Field.Index.NOT_ANALYZED));
				doc.add(new Field("content", content[i], Field.Store.YES, Field.Index.ANALYZED));
				doc.add(new Field("name", names[i], Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
				doc.add(new NumericField("attaches", Field.Store.YES, true).setIntValue(attches[i]));
				doc.add(new NumericField("date", Field.Store.YES, true).setLongValue(dates[i].getTime()));
				doc.setBoost(3.5f);
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

	/**
	 * 精确匹配查询，如果分词只能按单个单词查找，不分词则需精确匹配
	 */
	public void searchByTerm(String field, String value) {
		System.out.println("============== search by term:('" + field + "'='" + value + "') ===========");
		IndexSearcher searcher = getSearcher();
		Query query = new TermQuery(new Term(field, value));

		try {
			TopDocs topDocs = searcher.search(query, 10);
			System.out.println("一共查询到了记录数：" + topDocs.totalHits);

			for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
				Document doc = searcher.doc(scoreDoc.doc);
				System.out.println(doc.get("id") + "\t" + doc.get("content"));
			}

			searcher.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 精确匹配范围查询
	 * 
	 * @param field
	 * @param value
	 */
	public void searchByTermRange(String field, String start, String end) {
		System.out.println(
				"============== search by term range:(" + field + " in['" + start + "','" + end + "']) ===========");
		IndexSearcher searcher = getSearcher();
		Query query = new TermRangeQuery(field, start, end, true, true);

		try {
			TopDocs topDocs = searcher.search(query, 10);
			System.out.println("一共查询到了记录数：" + topDocs.totalHits);

			for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
				Document doc = searcher.doc(scoreDoc.doc);
				System.out.println(doc.get("id") + "\t" + doc.get("content"));
			}

			searcher.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void searchByNumericRange(String field, int start, int end) {
		System.out.println(
				"============== search by numeric range:(" + field + " in['" + start + "','" + end + "']) ===========");
		IndexSearcher searcher = getSearcher();
		Query query = NumericRangeQuery.newIntRange(field, start, end, true, true);

		try {
			TopDocs topDocs = searcher.search(query, 10);
			System.out.println("一共查询到了记录数：" + topDocs.totalHits);

			for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
				Document doc = searcher.doc(scoreDoc.doc);
				System.out.println(doc.get("id") + "\t" + doc.get("content"));
			}

			searcher.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Search_Term_Numeric_Range indexUtil = new Search_Term_Numeric_Range();
		indexUtil.index();
		
		indexUtil.searchByTerm("email", "ee@qq.com");
		indexUtil.searchByTerm("content", "you");
		indexUtil.searchByTerm("content", "you are");
		indexUtil.searchByTerm("id", "3");
		
		indexUtil.searchByTermRange("id", "3", "4");
		indexUtil.searchByTermRange("name", "k", "m");
		
		indexUtil.searchByNumericRange("attaches", 2, 4);
	}

}
