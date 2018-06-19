package com.tsb.study.lucene.realtimesearch;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;

/**
 * 1、NRTManager管理indexwriter
 * 2、NRTManager.getSearcherManager 获取searcherManager
 * 3、所有的索引更新操作均交给NRTManager, 如nrtManager.deleteDocuments
 * 4、nrtManager.maybeReopen(true);通过索引变更, 或者new NRTManagerReopenThread(nrtManager,50,25);时将最大reopen时间变小
 * 
 * @author Administrator
 *
 */
public class NRT02_NRTManager {
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
	private IndexWriter writer = null;
	private SearcherManager searcherManager = null;
	private NRTManager nrtManager = null;

	public NRT02_NRTManager() {
		try {
			directory = new RAMDirectory();
			writer = new IndexWriter(directory,
					new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));

			index();

			nrtManager = new NRTManager(writer, new SearcherWarmer() {
				public void warm(IndexSearcher s) throws IOException {
					System.out.println("----------------重新打开searcher-----------------");
				}
			});

			NRTManagerReopenThread reopenThread = new NRTManagerReopenThread(nrtManager,2,0.025);
			reopenThread.setDaemon(true);
			reopenThread.setName("NRTManager reopen Thread");
			reopenThread.start();

			searcherManager = nrtManager.getSearcherManager(true);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void index() {
		try {
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
		}

	}

	/**
	 * 模糊查询
	 */
	public void search() {
		IndexSearcher searcher = searcherManager.acquire();
		try {
			System.out.println("========================================");
			TermQuery query = new TermQuery(new Term("content","like"));
			TopDocs topDocs = searcher.search(query, 10);
			for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
				Document doc = searcher.doc(scoreDoc.doc);
				System.out.println(
						doc.get("id") + "\t" + scoreDoc.score + "\t" + doc.get("name") + "\t" + doc.get("content"));
			}

			searcher.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				searcherManager.release(searcher);//一定要释放
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void delete_2() {
		System.out.println("==============删除部分文档=========");
		try {
			nrtManager.deleteDocuments(new Term("id", "2"));
			commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

//		try {
//			nrtManager.maybeReopen(true);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

//		try {
//			searcherManager.maybeReopen();//这里通知索引变更，打开一个新的IndexSearcher
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	private void commit(){
		try {
			writer.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void searchFor(){
		for(int i=0;i<5;i++){
			search();
			try {
				Thread.sleep(1000);
				if(i==1){
					delete_2();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		NRT02_NRTManager indexUtil = new NRT02_NRTManager();
		indexUtil.searchFor();
	}

}
