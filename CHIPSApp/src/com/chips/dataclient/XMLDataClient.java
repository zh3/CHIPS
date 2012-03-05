package com.chips.dataclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Queue;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.os.AsyncTask;
import android.util.Log;

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
        
        dataLoadedOnce = false;
        taskList = new LinkedList<GetDataAsyncTask>();
    }
    
    private URL getXMLURL() throws MalformedURLException {
        return (URL == null) ? null : new URL(URL);
    }
    
    public void setURL(String baseURL, String argumentString) {
        ArrayList<String> argList = new ArrayList<String>();
        argList.add(argumentString);
        setURL(baseURL, argList);
    }
    
    public void setURL(String baseURL, List<String> arguments) {
        try {
            URL = baseURL.trim();
            if (URL.length() > 0 && URL.charAt(URL.length() - 1) != '/') {
                URL = URL + "/";
            }
            
            for (String arg : arguments) {
                URL += URLEncoder.encode(arg.trim(), "UTF-8") + "/";
            }
            
            xmlURL = getXMLURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
    public boolean hasLoadedOnce() {
        return dataLoadedOnce;
    }
    
    private class GetDataAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            reloadData();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            getDataTask = taskList.poll();
            if (getDataTask != null) getDataTask.execute();
            
            dataLoadedOnce = true;
            clientNotifyObservers();
        }
    }
    
    /**
     * Action to be performed in the background by the client. Should reload
     * the XML data and parse into an object representation or state flags.
     */
    protected abstract void reloadData();
    
    // Force client to reload the data asynchronously
    public void asynchronousLoadClientData() {
        //long now = SystemClock.uptimeMillis();
        
        if (getDataTask == null && URL != null/* && (now > nextRunTime || now < lastRunTime)*/) {
            // 2nd check is in case the clock's been reset.
            //lastRunTime = SystemClock.uptimeMillis();
            //nextRunTime = lastRunTime + 1000 * 60 * 5; // 5 minutes
            getDataTask = new GetDataAsyncTask();
            getDataTask.execute();
        } else if (URL != null) {
            // Add task to Queue
            taskList.add(new GetDataAsyncTask());
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
    
    public void logURL() {
        Log.d("XML client URL was: ", URL);
    }
    
    public void synchronousLoadClientData() {
        reloadData();
        clientNotifyObservers();
    }
    
    // Parsing
    private XMLReader xr;
    private SAXParser sp;
    private SAXParserFactory spf;
    protected String URL;
    protected URL xmlURL;
    private boolean dataLoadedOnce;
    // Asynchronous task
    private GetDataAsyncTask getDataTask;
    private Queue<GetDataAsyncTask> taskList;
    //private long lastRunTime = 0;
    //private long nextRunTime = 0;
}
