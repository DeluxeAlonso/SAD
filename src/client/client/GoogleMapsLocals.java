package client.client;

import algorithm.Node;
import algorithm.Solution;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEvent;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventListener;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventType;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import entity.Cliente;
import entity.Local;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import util.Constants;

public class GoogleMapsLocals {
    
    public GoogleMapsLocals(ArrayList<Local> locals){
        final Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame("Vista en mapa de los locales");
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(900, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //browser.loadURL("./map.html");
        String html = "<!DOCTYPE html>\n" +
"<html>\n" +
"<head>\n" +
"   <meta name='viewport' content=\"initial-scale=1.0, user-scalable=no\"/>\n" +
"   <style type='text/css'>\n" +
"       html { height: 100% }\n" +
"       body { height: 100%; margin: 0; padding: 0 }\n" +
"       #map-canvas { height: 100% }\n" +
"   </style>\n" +
"   <script type='text/javascript' src='https://maps.googleapis.com/maps/api/js?v=3.exp'></script>\n" +
"   <script type='text/javascript' src='https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.js'></script>\n" +
"   <script type='text/javascript'>\n" +
"  var waypts = [];\n" +
"  var splitWayptsArray = [];\n";
        /*
    for(int i=0;i<clients.size();i++){
html += " var waypts"+i+" = [];\n" +
"         splitWayptsArray.push(waypts"+i+");\n";
    }
                */
html += "function initialize() {\n" +
"  var directionsDisplay = new google.maps.DirectionsRenderer();\n" +
"  var directionsService = new google.maps.DirectionsService();\n" +
"  var limaLatlng = new google.maps.LatLng(-11.9416154,-77.0930122);\n" +
"  var mapOptions = {\n" +
"    zoom: 8,\n" +
"    center: limaLatlng\n" +
"  }\n" +
"  var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);\n" +
"directionsDisplay.setMap(map);" +
" var distributionCenter = new google.maps.LatLng("+Constants.WAREHOUSE_LATITUDE+","+Constants.WAREHOUSE_LONGITUDE+"); "+
" var marker = new google.maps.Marker({\n" +
"      position: distributionCenter,\n" +
"      map: map,\n" +
"      icon: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png',\n" +
"      title: 'Centro de distribucion'\n" +
" });\n" +
"waypts.push(distributionCenter);\n";
    for(int i=0;i<locals.size();i++){
html += " var local = new google.maps.LatLng("+locals.get(i).getLatitud()+","+locals.get(i).getLongitud()+"); "+
" var marker = new google.maps.Marker({\n" +
"      position: local,\n" +
"      map: map,\n" +
"      title: '"+locals.get(i).getNombre()+"'\n" +
" });\n"; 
    }
html += "}\n" +
"google.maps.event.addDomListener(window, 'load', initialize);\n" +          
"</script>\n" +
"</head>\n" +
"<body>\n" +
"<div id=\"map-canvas\"></div>\n" +
"</body>\n" +
"</html>";
        browser.loadHTML(html);
    }
}
