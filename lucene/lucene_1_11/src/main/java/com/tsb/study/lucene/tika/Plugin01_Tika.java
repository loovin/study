package com.tsb.study.lucene.tika;

import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;
import org.apache.commons.io.FilenameUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

import java.io.*;

/**
 * 对于非txt的文档，如word，pdf等，默认的parser无法解析，需要借助tika
 */
public class Plugin01_Tika {
    public static void index() {

        IndexWriter indexWriter = null;
        try {
            Directory directory = FSDirectory.open(new File("E:\\IT_Technology\\ext\\index"));

            //2、创建IndexWriter
            IndexWriterConfig iwConfig = new IndexWriterConfig(Version.LUCENE_35,new MMSegAnalyzer());
            indexWriter = new IndexWriter(directory, iwConfig);

            File f = new File("E:\\IT_Technology\\ext\\docs");
            for(File file:f.listFiles()){
                //5、通过IndexWriter添加文档到索引中
                indexWriter.addDocument(genDoc(file));

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

    private static Document genDoc(File file) throws IOException {
        Document doc = new Document();
        Metadata metadata = new Metadata();
        //doc.add(new Field("content", file2Txt_1(file), Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("content", new Tika().parse(new FileInputStream(file),metadata)));
        doc.add(new Field("title", FilenameUtils.getBaseName(file.getName()),Field.Store.YES,Field.Index.ANALYZED));
        doc.add(new Field("filename",file.getName(),Field.Store.YES,Field.Index.NOT_ANALYZED));
        doc.add(new Field("path",file.getPath(),Field.Store.YES,Field.Index.NOT_ANALYZED));
        String page = metadata.get("xmpTPg:NPages") == null ?"0":metadata.get("xmpTPg:NPages");
        doc.add(new NumericField("pageCount", Field.Store.YES,true).setIntValue(Integer.parseInt(page)));
        return doc;
    }

    /**
     * tika根据文件自动侦测合适的parser
     * 方式一
     * @param f
     * @return
     */
    public static String file2Txt_1(File f){
        InputStream is = null;
        try {
            is = new FileInputStream(f);
            Metadata metadata = new Metadata();
            Parser parser = new AutoDetectParser();
            ContentHandler handler = new BodyContentHandler();
            ParseContext context = new ParseContext();
            context.set(Parser.class,parser);
            parser.parse(is,handler,metadata,context);
            return handler.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 方式二
     * @param f
     * @return
     * @throws Exception
     */
    public static String file2Txt_2(File f) {
        Tika tika = new Tika();
        try {
            return tika.parseToString(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String s[]){
//        System.out.print(
//                file2Txt_1(new File("E:\\IT_Technology\\ext\\docs\\Flume.docx"))
//        );
//
//        System.out.print(
//                file2Txt_2(new File("E:\\IT_Technology\\ext\\docs\\Flume.docx"))
//        );
        index();
    }
}
