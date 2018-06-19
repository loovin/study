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
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.QueryParser.Operator;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

/**
 * 通过解析输入的查询串，生成对应的query
 * 
 * @author Administrator
 *
 */
public class Search_QueryParser {
	private static String[] ids = { "1", "2", "3", "4", "5", "6" };
	private String[] emails = { "aa@tsb.com", "bb@tsb.com", "cc@qq.com", "dd@sina.com", "ee@qq.com", "ff@tsb.com" };
	private String[] content = { "welcome to tsb, i like book", "you are my son, i like pingpang",
			"i am you father, i like game", "do you want to die, i like football", "i like china",
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

	public Search_QueryParser() {
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
	 * 模糊查询
	 */
	public void search() {
		IndexSearcher searcher = getSearcher();

		try {
			//设置默认filed为content
			QueryParser parser = new QueryParser(Version.LUCENE_35, "content", new StandardAnalyzer(Version.LUCENE_35));
			//默认操作符（空格）为and，默认为or
			//parser.setDefaultOperator(Operator.AND);
			//是否允许首字母用 * （由于效率低，默认是不允许的）
			parser.setAllowLeadingWildcard(true);
			
			//包含want
			Query query = parser.parse("want");
			//包含 china 或者 football（空格默认为or）
			query = parser.parse("football die");
			
			query = parser.parse("football AND china");
			
			query = parser.parse("name:jake");
			
			query = parser.parse("name:j* AND (content:game OR content:(team *ootball))");
			
			/*
			 *  （- 表示NOT） ，（ + 表示 必须，可理解为AND）
			 *  NOT name:ja* AND (game team)
			 */
			query = parser.parse("- name:ja* + (game team)");

			/**
			 * 匹配区间,从..到..,TO必须大写
			 * 不能匹配数字类型
			 */
			query = parser.parse("id:[2 TO 5]");
			/**
			 * 区间匹配：数字类型不能被匹配到
			 */
			//query = parser.parse("attaches:[2 TO 5]");
			/**
			 * 闭区间匹配
			 */
			query = parser.parse("id:{2 TO 5}");

			/**
			 * 完全匹配
			 */
			query = parser.parse("\"i like game\"");
			/**
			 * 在you与i 之间有一个词
			 */
			query = parser.parse("\"you i\"~1");


			TopDocs topDocs = searcher.search(query, 10);
			System.out.println("一共查询到了记录数：" + topDocs.totalHits);

			for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
				Document doc = searcher.doc(scoreDoc.doc);
				System.out.println(
						doc.get("id") + "\t" + scoreDoc.score + "\t" + doc.get("name") + "\t" + doc.get("content"));
			}

			searcher.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Search_QueryParser indexUtil = new Search_QueryParser();
		indexUtil.index();

		indexUtil.search();
	}

}
