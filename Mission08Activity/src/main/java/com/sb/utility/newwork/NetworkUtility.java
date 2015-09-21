package com.sb.utility.newwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by student on 2015-09-15.
 */
public class NetworkUtility {
    /**
     * getUrlConnection
     * @Note : url connection
     * @return
     * @throws Exception
     *
     *
     */
    public static URLConnection getUrlConnection(String urlString)
            throws Exception {

        URL url = new URL( urlString );                 // Given url information
        URLConnection connection = url.openConnection();// Connection
        connection.setDoOutput( true );
        return connection;
    }

    /**
     * Retrieve source from url(Customized for class)
     * @param url
     * @return
     * @throws Exception
     */
    public static String getReturnString(String url) throws Exception {
        return getReturnString(getUrlConnection(url));
    }

    /**
     * getReturnString
     * @Note : Result connected
     * @param connection
     * @return
     * @throws IOException
     *
     *
     */
    public static String getReturnString(URLConnection connection)
            throws IOException {
        BufferedReader in = new BufferedReader( new InputStreamReader(
                connection.getInputStream(), "UTF-8" ) );   // If returned value is UTF-8
        StringBuffer buffer = new StringBuffer();           // @Thread stringbuffer is safe
        String decodedString;

        while( ( decodedString = in.readLine() ) != null ) {
            buffer.append( decodedString );
        }

        in.close();

        return buffer.toString();
    }
}
