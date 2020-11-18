/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.xml.bind.JAXBContext
 *  javax.xml.bind.JAXBException
 *  javax.xml.bind.Unmarshaller
 */
package wordeotest;

import java.net.URL;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import wordeotest.conSql;
import wordeotest.xmlContent;
import wordeotest.xmlReader;

public class subDefine {
    public static List<Float> startsub;
    public static List<Float> duration;
    public static List<String> contentSub;
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd;

    static {
        rnd = new SecureRandom();
    }

    public List<xmlContent> orderSub(String url) throws Exception {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new URL("http://video.google.com/timedtext?lang=en&v=" + url).openStream());
            JAXBContext jaxbContext = JAXBContext.newInstance((Class[])new Class[]{xmlReader.class});
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            xmlReader reader = (xmlReader)jaxbUnmarshaller.unmarshal((Node)doc);
            return reader.getAll();
        }
        catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; ++i) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    public subDefine(String url) throws Exception {
        Connection con = null;
        Statement preInsertTable = null;
        PreparedStatement insertList = null;
        this.orderSub(url);
        String tableName = subDefine.randomString(12);
        con = conSql.getSql();
        String toSQL = "INSERT INTO sublist(id,tablename,url) VALUES(NULL,?,?)";
        for (int s = 0; s < 1; ++s) {
            try {
                try {
                    insertList = con.prepareStatement(toSQL);
                    insertList.setString(1, tableName);
                    insertList.setString(2, url);
                    insertList.executeUpdate();
                }
                catch (SQLException e) {
                    System.out.println(e.getMessage());
                    if (insertList != null) {
                        insertList.close();
                    }
                    if (con == null) continue;
                    con.close();
                    continue;
                }
            }
            catch (Throwable throwable) {
                if (insertList != null) {
                    insertList.close();
                }
                if (con != null) {
                    con.close();
                }
                throw throwable;
            }
            if (insertList != null) {
                insertList.close();
            }
            if (con == null) continue;
            con.close();
        }
        String insertTableSQL = "INSERT INTO @1(rownum,start,duration,content,url) VALUES(NULL,?,?,?,?)";
        insertTableSQL = insertTableSQL.replaceFirst("@1", "bigdata");
        String[] content = new String[this.orderSub(url).size()];
        for (int i = 0; i < this.orderSub(url).size(); ++i) {
            content[i] = this.orderSub(url).get(i).getText();
            content[i] = content[i].replaceAll("&amp;#39", "'");
            content[i] = content[i].replaceAll(";", "");
            content[i] = content[i].replaceAll("&amp", "");
            content[i] = content[i].replaceAll("&#39;", "'");
            content[i] = content[i].replaceAll("&#39", "'");
            content[i] = content[i].replaceAll("&quot;", "\"");
            content[i] = content[i].replaceAll("&quot", "\"");
            content[i] = content[i].replaceAll("quot", "");
            content[i] = content[i].replaceAll("\n", " ");
            content[i] = content[i].replaceAll("\r", " ");
            content[i] = content[i].replaceAll("gtgt", "");
            char lastres = content[i].charAt(content[i].length() - 1);
            if (lastres == ',') {
                content[i] = content[i].substring(0, content[i].length() - 1);
            }
            if (lastres == '-') {
                content[i] = content[i].substring(0, content[i].length() - 2);
            }
            if (lastres == ':') {
                content[i] = content[i].substring(0, content[i].length() - 1);
            }
            try {
                try {
                    con = conSql.getSql();
                    preInsertTable = con.prepareStatement(insertTableSQL);
                    preInsertTable.setFloat(1, this.orderSub(url).get(i).getStart());
                    preInsertTable.setFloat(2, this.orderSub(url).get(i).getDur());
                    preInsertTable.setString(3, content[i]);
                    preInsertTable.setString(4, url);
                    preInsertTable.executeUpdate();
                }
                catch (SQLException ee) {
                    System.out.println(ee.getMessage());
                    if (preInsertTable != null) {
                        preInsertTable.close();
                    }
                    if (con == null) continue;
                    con.close();
                    continue;
                }
            }
            catch (Throwable throwable) {
                if (preInsertTable != null) {
                    preInsertTable.close();
                }
                if (con != null) {
                    con.close();
                }
                throw throwable;
            }
            if (preInsertTable != null) {
                preInsertTable.close();
            }
            if (con == null) continue;
            con.close();
        }
    }
}

