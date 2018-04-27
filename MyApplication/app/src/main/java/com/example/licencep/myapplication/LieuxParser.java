package com.example.licencep.myapplication;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class LieuxParser extends DefaultHandler{

    private ArrayList<LieuxAVisiter> list;
    private XMLReader xr;

    public LieuxParser() throws Exception {
        URL urlbase = new URL("http://cedric.cnam.fr/~farinone/USRS27/lieuxParis.xml");
        InputSource is = new InputSource(urlbase.openStream());
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();

        xr = sp.getXMLReader();
        xr.setContentHandler(this);
        xr.parse(is);
    }

    public ArrayList<LieuxAVisiter> getList() {
        return this.list;
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        list = new ArrayList<LieuxAVisiter>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if(localName.equals("lieu")) {
            LieuxAVisiter lav = new LieuxAVisiter();
            lav.setNom(attributes.getValue("name"));
            lav.setNumero(attributes.getValue("number"));

            list.add(lav);
        }
    }
}
