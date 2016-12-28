package com.shang.layoutformat;

import com.intellij.openapi.vfs.VirtualFile;
import org.apache.http.util.TextUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Created by wangyan-pd on 2016/8/22.
 */
public class HardStringsReplaceFromatter {
    private static HardStringsReplaceFromatter instance;
    private File xmlFILE;

    public static HardStringsReplaceFromatter getInstance() {
        if(instance == null)
            instance = new HardStringsReplaceFromatter();
        return instance;
    }
    private static LinkedHashMap<String,String> stringKeyValuesMap = new LinkedHashMap<String,String>();
//    public HashMap<String,String>  getStringKeyValuesMap(){
//        return stringKeyValuesMap;
//    }
    public void initKeyValuesMap(VirtualFile file) {
        stringKeyValuesMap.clear();
        index = 0;
        String xmlFile = "";
        if(file.getPath().endsWith("/layout")){
            xmlFile = file.getPath().replace("/layout","");
        }else if(file.getPath().endsWith(".xml") && file.getPath().contains("/layout")){
            int index = file.getPath().lastIndexOf("/layout");
            xmlFile =  file.getPath().substring(0,index);
        }
        String Values = xmlFile +File.separator +"values";
        String Strings = "strings.xml";
        xmlFILE = new File(Values,Strings);
        if(!new File(Values).exists())
            new File(Values).mkdirs();
        if(!xmlFILE.exists()){
            try {
                xmlFILE.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
            return;
        }
        try {
            initMapUseSax(xmlFILE);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initMapUseSax(File xmlFILE) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        // 2.创建解析器
        SAXParser parser = factory.newSAXParser();
        // 3.获取需要解析的文档，生成解析器,最后解析文档
        SaxHandler dh = new SaxHandler();
        parser.parse(xmlFILE, dh);
    }

    private  static int index = 0;

    public String obtainStringKeyByValue(String value) {
        if(stringKeyValuesMap.values().contains(value))
            return getKeyByValue(value);
        //当不存在时，新建一个key，放到map中去
        index++;
        String currentIndex = "stringValue"+index;
        while (stringKeyValuesMap.containsKey(currentIndex)){
            index++;
            currentIndex = "stringValue"+index;
        }
        stringKeyValuesMap.put(currentIndex,value);
        return currentIndex;

    }

    private String getKeyByValue(String value) {
        Set<String> keySet = stringKeyValuesMap.keySet();
        for(String key :keySet){
            if(stringKeyValuesMap.get(key).equals(value))
                return key;
        }
        return "";
    }

    public void saveMap2ValuesStrings() {
        if(stringKeyValuesMap.size() == 0)
            return;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(xmlFILE,false);//覆盖文件
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream,"UTF-8");
            String strings = covertStringKeyValuesMap2Xml(stringKeyValuesMap);
            outputStreamWriter.write(strings);
            outputStreamWriter.flush();
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String covertStringKeyValuesMap2Xml(LinkedHashMap<String, String> stringKeyValuesMap) {
        StringBuffer xml =new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n");
        for(String key :stringKeyValuesMap.keySet()){
            xml.append("\t<string name=\"").append(key).append("\">").append(stringKeyValuesMap.get(key)).append("</string>\n");
        }
        xml.append("</resources>");
        return xml.toString();
    }

    static class SaxHandler extends DefaultHandler {

        private String currentKey;

        /* 此方法有三个参数
                   arg0是传回来的字符数组，其包含元素内容
                   arg1和arg2分别是数组的开始位置和结束位置 */
        @Override
        public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
            String content = new String(arg0, arg1, arg2);
            System.out.println(content);
            super.characters(arg0, arg1, arg2);
            if(!TextUtils.isEmpty(currentKey))
                stringKeyValuesMap.put(currentKey,content);
            currentKey = "";
        }

        @Override
        public void endDocument() throws SAXException {
            System.out.println("\n…………结束解析文档…………");
            super.endDocument();
        }

        /* arg0是名称空间
           arg1是包含名称空间的标签，如果没有名称空间，则为空
           arg2是不包含名称空间的标签 */
        @Override
        public void endElement(String arg0, String arg1, String arg2)
                throws SAXException {
            System.out.println("结束解析元素  " + arg2);
            super.endElement(arg0, arg1, arg2);
        }

        @Override
        public void startDocument() throws SAXException {
            System.out.println("…………开始解析文档…………\n");
            super.startDocument();
        }

        /*arg0是名称空间
          arg1是包含名称空间的标签，如果没有名称空间，则为空
          arg2是不包含名称空间的标签
          arg3很明显是属性的集合 */
        @Override
        public void startElement(String arg0, String arg1, String arg2,
                                 Attributes arg3) throws SAXException {
            System.out.println("开始解析元素 " + arg2);
            if (arg3 != null) {
                for (int i = 0; i < arg3.getLength(); i++) {
                    // getQName()是获取属性名称，
                    System.out.print(arg3.getQName(i) + "=\"" + arg3.getValue(i) + "\"");
                    if(arg3.getQName(i).equals("name"))
                        currentKey = arg3.getValue(i);
                }
            }
            System.out.print(arg2 + ":");
            super.startElement(arg0, arg1, arg2, arg3);
        }
    }
}
