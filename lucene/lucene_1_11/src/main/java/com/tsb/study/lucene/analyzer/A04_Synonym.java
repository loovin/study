package com.tsb.study.lucene.analyzer;

import com.chenlb.mmseg4j.Dictionary;
import com.chenlb.mmseg4j.MaxWordSeg;
import com.chenlb.mmseg4j.analysis.MMSegTokenizer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
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
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 同义词
 */
public class A04_Synonym extends Analyzer{
    public TokenStream tokenStream(String s, Reader reader) {
        Dictionary dictionary = Dictionary.getInstance("E:\\test");
        return new SynonymTokenFilter(new MMSegTokenizer(new MaxWordSeg(dictionary),reader));
    }

    class SynonymTokenFilter extends TokenFilter{
        private CharTermAttribute cta = null;
        private PositionIncrementAttribute pia = null;
        private AttributeSource.State current = null;
        private Stack<String> sames = null;

        protected SynonymTokenFilter(TokenStream input) {
            super(input);
            cta = this.addAttribute(CharTermAttribute.class);
            pia = this.addAttribute(PositionIncrementAttribute.class);
            sames = new Stack<String>();
        }

        public boolean incrementToken() throws IOException {
            while (sames.size() > 0){
                String str = sames.pop();
                //还原状态
                //this.restoreState(current);
                cta.setEmpty();
                cta.append(str);
                pia.setPositionIncrement(0);
                return true;
            }

            if(!this.input.incrementToken()){
                return false;
            }

            if(getSynonym(cta.toString())){
                //如果有同义词,则将当前状态先保存
                //current = this.captureState();
            }

            return true;
        }

        private boolean getSynonym(String name){
            Map<String,String[]> maps = new HashMap<String, String[]>();
            maps.put("中国",new String[]{"天朝","大唐朝"});
            maps.put("我",new String[]{"吾","咱"});
            String[] sws = maps.get(name);
            if(sws != null){
                for(String str:sws){
                    sames.push(str);
                }
                return true;
            }
            return false;
        }
    }

    static String text = "我来自中国湖南长沙大通";

    private static void display(){
        try {
            Analyzer analyzer = new A04_Synonym();
            A01_Hello.displayAllTokenInfo(text,analyzer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void search(){
        try {
            Directory directory = new RAMDirectory();
            IndexWriter indexWriter = new IndexWriter(directory,new IndexWriterConfig(Version.LUCENE_35,new A04_Synonym()));
            Document document = new Document();
            document.add(new Field("content",text,Field.Store.YES,Field.Index.ANALYZED));
            indexWriter.addDocument(document);
            indexWriter.close();

            IndexSearcher searcher = new IndexSearcher(IndexReader.open(directory));
            TopDocs tds = searcher.search(new TermQuery(new Term("content","大唐朝")),10);
            for(ScoreDoc sd : tds.scoreDocs){
                Document doc = searcher.doc(sd.doc);
                System.out.println(doc.get("content"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String s[]){
        display();
        System.out.println("-------------------start search----------------");
        search();
    }
}
