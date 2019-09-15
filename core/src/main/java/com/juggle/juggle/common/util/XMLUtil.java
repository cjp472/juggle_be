package com.juggle.juggle.common.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*XML工具类*/
@Component
public class XMLUtil {
    private XStream inclueUnderlineXstream = new XStream(new DomDriver(null,new XmlFriendlyNameCoder("_-", "_")));

    public Map<String, String> parseXml(String msg) throws Exception {
        Map<String, String> map = new HashMap<>();
        InputStream inputStream = new ByteArrayInputStream(msg.getBytes("UTF-8"));
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();
        List<Element> elementList = root.elements();
        for (Element e : elementList){
            map.put(e.getName(), e.getText());
        }
        inputStream.close();
        return map;
    }

    private XStream xstream = new XStream(new XppDriver(new NoNameCoder()) {

        @Override
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                boolean cdata = true;
                @Override
                @SuppressWarnings("rawtypes")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }
                @Override
                public String encodeNode(String name) {
                    return name;
                }
                @Override
                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });

    public XStream getXstreamInclueUnderline() {
        return inclueUnderlineXstream;
    }

    public XStream xstream() {
        return xstream;
    }
}