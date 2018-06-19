package com.tsb.study.lucene.index;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class IndexUpdateDelete {
	private String[] ids = { "1", "2", "3", "4", "5", "6" };
	private String[] emails = { "aa@tsb.com", "bb@tsb.com", "cc@qq.com", "dd@sina.com", "ee@tsb.com", "ff@tsb.com" };
	private String[] content = { "welcome to tsb", "you are my son", "i am you father", "do you want to die",
			"i like china", "you are so naive" };
	private int[] attches = { 2, 3, 1, 4, 5, 5 };
	private String[] names = { "shangsan", "lisi", "john", "jetty", "mike", "jake" };

	private Directory directory = null;

	public IndexUpdateDelete() {
		try {
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

	/**
	 * 非deleteAll删除的文件并不会被完全删除，而是在回收站里，存放在.del文件里； 可以恢复
	 */
	public void deleteIndex_1() {
		System.out.println("==============删除部分文档=========");
		IndexWriter writer = null;

		try {
			writer = new IndexWriter(directory,
					new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));

			writer.deleteDocuments(new Term("id", "1"));

			QueryParser queryParser = new QueryParser(Version.LUCENE_35, "content",
					new StandardAnalyzer(Version.LUCENE_35));
			Query query = queryParser.parse("you father");
			writer.deleteDocuments(query);
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
	 * 将已删除的文档从回收站清空
	 */
	public void forceMergeDeletes() {
		IndexWriter writer = null;
		System.out.println("================将已删除的文档从回收站清空=============");
		try {
			writer = new IndexWriter(directory,
					new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));

			writer.forceMergeDeletes();
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

	public void un_delete() {
		IndexReader reader = null;
		try {
			reader = IndexReader.open(directory, false);
			reader.undeleteAll();
			System.out.println("============恢复所有删除的文件===========");
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
			System.out.println("有效文档" + reader.numDocs());
			System.out.println("被删除的文档：" + reader.numDeletedDocs());
			System.out.println("文档总数：" + reader.maxDoc());
			System.out.println("========================");

			if (true)
				return;

			IndexSearcher searcher = new IndexSearcher(reader);
			QueryParser queryParser = new QueryParser(Version.LUCENE_35, "content",
					new StandardAnalyzer(Version.LUCENE_35));
			Query query = queryParser.parse("you father");
			TopDocs topDocs = searcher.search(query, 10);
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (ScoreDoc scoreDoc : scoreDocs) {
				Document doc = searcher.doc(scoreDoc.doc);
				System.out.println(doc.get("id"));
			}
			System.out.println("========================");
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
	
	/**
	 * 更新其实是先删除，再添加
	 */
	public void update(){
		System.out.println("==============更新索引===========");
		IndexWriter writer = null;
		try {
			writer = new IndexWriter(directory,
					new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));
			
			Document doc = new Document();
			doc.add(new Field("id", "111", Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
			doc.add(new Field("email", "111@111", Field.Store.YES, Field.Index.NOT_ANALYZED));
			doc.add(new Field("content", "updated 1111", Field.Store.NO, Field.Index.ANALYZED));
			doc.add(new Field("name", "1111111", Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
			
			writer.updateDocument(new Term("id","1"), doc);
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

	public static void main(String[] args) {
		IndexUpdateDelete indexUtil = new IndexUpdateDelete();
		indexUtil.deleteIndex();
		indexUtil.index();
		indexUtil.deleteIndex_1();
		indexUtil.query();
		indexUtil.un_delete();
		indexUtil.query();
		indexUtil.deleteIndex_1();
		indexUtil.forceMergeDeletes();
		indexUtil.un_delete();
		indexUtil.query();
		indexUtil.deleteIndex();
		indexUtil.index();
		indexUtil.update();
		indexUtil.query();
	}

}
