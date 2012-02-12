package com.chips.dataclient;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.os.AsyncTask;
import android.os.SystemClock;

import com.chips.xmlhandler.SAXHandler;

public abstract class XMLDataClient extends Observable {
    protected XMLDataClient() {
        /* Get a SAXParser from the SAXPArserFactory. */
        spf = SAXParserFactory.newInstance();
        try {
            /* Get the XMLReader of the SAXParser we created. */
            sp = spf.newSAXParser();
            xr = sp.getXMLReader();
            xmlURL = getXMLURL();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    
    protected abstract URL getXMLURL() throws MalformedURLException;
    
    private class GetDataAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            reloadData();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            getDataTask = null;
            clientNotifyObservers();
        }
    }
    
    protected abstract void reloadData();
    
    // Force client to reload the data asynchronously
    public void refreshClient() {
        long now = SystemClock.uptimeMillis();
        if (getDataTask == null && (true || (now > nextRunTime || now < lastRunTime))) {
            // 2nd check is in case the clock's been reset.
            lastRunTime = SystemClock.uptimeMillis();
            nextRunTime = lastRunTime + 1000 * 60 * 5; // 5 minutes
            getDataTask = new GetDataAsyncTask();
            getDataTask.execute();
        }
    }
    
    protected void parse(SAXHandler<?> h) {
        xr.setContentHandler(h);
        try {
            xr.parse(new InputSource(xmlURL.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
    
    protected void clientNotifyObservers() {
        setChanged();
        notifyObservers();
    }
    
    // Parsing
    private XMLReader xr;
    private SAXParser sp;
    private SAXParserFactory spf;
    protected URL xmlURL;
    
    // Asynchronous task
    private GetDataAsyncTask getDataTask;
    private long lastRunTime = 0;
    private long nextRunTime = 0;
}
