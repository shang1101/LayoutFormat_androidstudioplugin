package com.shang.layoutformat;

import junit.framework.TestCase;

public class FormatterTest extends TestCase {

    private String xml1;
    private String xml2;
    private String xml3;
    private String xml4;
    private String xml5;
    private String xml6;


    @Override protected void setUp() throws Exception {
        super.setUp();
        xml1 = "<Test android:text=\"123\"\nandroid:id=\"456\"/>";
        xml2 = "<Test android:text=\"123\"\nandroid:id=\"456\" />";
        xml3 = "<Test android:text=\"123\"\nandroid:id=\"456\">";
        xml4 = "<Test android:text=\"123\"\nandroid:id=\"456\" >";
        xml5 = "<XXX android:layout_height=\"fill_parent\" string=\"fill_parent should be written to match_parent\"/>";
        xml6 = "<Button\n" +
                "        android:layout_width=\"match_parent\"\n" +
                "        android:text=\"Hello World, SampleActivity\"/><Button\n" +
                "        android:layout_width=\"match_parent\"\n" +
                "        android:text=\"Hello World, SampleActivity\"/>";
    }


    public void testRetrofit() {
        xml1 = Formatter.retrofit(xml1);
        xml2 = Formatter.retrofit(xml2);
        xml3 = Formatter.retrofit(xml3);
        xml4 = Formatter.retrofit(xml4);
        xml5 = Formatter.retrofit(xml5);
        xml6 = Formatter.retrofit(xml6);
        System.out.println(xml1);
        System.out.println(xml2);
        System.out.println(xml3);
        System.out.println(xml4);
        System.out.println(xml5);
        System.out.println(xml6);
        assertEquals(xml1, "<Test android:text=\"123\"\nandroid:id=\"456\"\n/>");
        assertEquals(xml2, "<Test android:text=\"123\"\nandroid:id=\"456\"\n/>");
        assertEquals(xml3, "<Test android:text=\"123\"\nandroid:id=\"456\"\n>");
        assertEquals(xml4, "<Test android:text=\"123\"\nandroid:id=\"456\"\n>");
        assertEquals(xml5, "<XXX android:layout_height=\"match_parent\" " +
                "string=\"fill_parent should be written to match_parent\"\n/>");
        assertEquals(Formatter.retrofit("android:singleLine=\"true\""), "android:maxLines=\"1\"");
    }


    public void testRepair() {
        xml1 = Formatter.retrofit(xml1);
        xml2 = Formatter.retrofit(xml2);
        xml3 = Formatter.retrofit(xml3);
        xml4 = Formatter.retrofit(xml4);
        xml5 = Formatter.retrofit(xml5);
        xml6 = Formatter.retrofit(xml6);

        xml1 = Formatter.repair(xml1);
        xml2 = Formatter.repair(xml2);
        xml3 = Formatter.repair(xml3);
        xml4 = Formatter.repair(xml4);
        xml5 = Formatter.repair(xml5);
        xml6 = Formatter.repair(xml6);
        System.out.println(xml1);
        System.out.println(xml2);
        System.out.println(xml3);
        System.out.println(xml4);
        System.out.println(xml6);
        assertEquals(xml1, "<Test android:text=\"123\"\nandroid:id=\"456\"/>");
        assertEquals(xml2, "<Test android:text=\"123\"\nandroid:id=\"456\"/>");
        assertEquals(xml3, "<Test android:text=\"123\"\nandroid:id=\"456\">");
        assertEquals(xml4, "<Test android:text=\"123\"\nandroid:id=\"456\">");
        assertEquals(xml5, "<XXX android:layout_height=\"match_parent\" " +
                "string=\"fill_parent should be written to match_parent\"/>");
    }
}
