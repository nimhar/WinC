/*
 * Decompiled with CFR 0.150.
 */
package wordeotest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import wordeotest.conSql;

public class logic {
    int count = 0;
    private static ArrayList<Integer> start;
    private static ArrayList<Integer> dur;
    private static ArrayList<String> text;
    private static ArrayList<String> url;

    public ArrayList<Integer> getStart() {
        return start;
    }

    private static void setStart(float st) {
        start.add(Math.round(st));
    }

    public ArrayList<Integer> getDur() {
        return dur;
    }

    private static void setDur(float dr) {
        dur.add(Math.round(dr));
    }

    public ArrayList<String> getText() {
        return text;
    }

    private static void setText(String txt) {
        text.add(txt);
    }

    public ArrayList<String> getUrl() {
        return url;
    }

    private static void setUrl(String ur) {
        url.add(ur);
    }

    public void scanIt(String text) throws SQLException {
        String sql = "SELECT * FROM @1 WHERE MATCH(content) AGAINST(? IN BOOLEAN MODE)";
        sql = sql.replaceFirst("@1", "bigdata");
        PreparedStatement pst = conSql.getSql().prepareStatement(sql);
        pst.setString(1, String.valueOf(text) + "*");
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            System.out.println(String.valueOf(rs.getFloat("start")) + " " + rs.getFloat("duration"));
            logic.setStart(rs.getFloat("start"));
            logic.setDur(rs.getFloat("duration"));
            logic.setText(rs.getString("content"));
            logic.setUrl(rs.getString("url"));
        }
    }
}

