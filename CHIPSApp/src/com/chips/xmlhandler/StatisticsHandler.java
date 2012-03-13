package com.chips.xmlhandler;

import java.util.Arrays;
import java.util.EnumMap;

import org.xml.sax.SAXException;

import com.chips.xmlhandler.TagStateEnum.StatisticsTagState;

public class StatisticsHandler extends SAXHandler<StatisticsTagState> {
    private static final int DEFAULT_HEIGHT = -1;
    private static final int DEFAULT_WEIGHT = -1;
    
    public StatisticsHandler() {
        buffers = new EnumMap<StatisticsTagState, StringBuilder>(
                StatisticsTagState.class);
        values = Arrays.asList(StatisticsTagState.values());
        
        height = DEFAULT_HEIGHT;
        weight = DEFAULT_WEIGHT;
    }
    
    @Override
    protected StatisticsTagState valueOf(String s) {
        return StatisticsTagState.valueOf(normalize(s));
    }

    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if (localName.equals("stats")) {
            height = Integer.parseInt(
                    getString(StatisticsTagState.HEIGHT).trim());
            weight = Integer.parseInt(
                    getString(StatisticsTagState.WEIGHT).trim());

            clearTagBuffers();
        }
    }
    
    public int getHeight() {
        return height;
    }
    public int getWeight() {
        return weight;
    }

    private int height;
    private int weight;
}
