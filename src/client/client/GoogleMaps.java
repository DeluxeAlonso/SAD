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

import javax.swing.*;
import java.awt.*;
import util.Constants;

public class GoogleMaps {
    
    public GoogleMaps(Solution solution){
        Node[][] nodes = solution.getNodes();
        final Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame("Vista en Mapa");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
"   <script type='text/javascript'>\n" +
"     function initialize() {\n" +
"  var directionsDisplay = new google.maps.DirectionsRenderer();\n" +
"  var directionsService = new google.maps.DirectionsService();\n" +
"  var myLatlng = new google.maps.LatLng(-11.9959406,-77.0816826);\n" +
"  var mapOptions = {\n" +
"    zoom: 4,\n" +
"    center: myLatlng\n" +
"  }\n" +
"  var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);\n" +
" var newLatlng = new google.maps.LatLng("+Constants.WAREHOUSE_LATITUDE+","+Constants.WAREHOUSE_LONGITUDE+"); "+
"      var marker = new google.maps.Marker({\n" +
"      position: newLatlng,\n" +
"      map: map,\n" +
"      icon: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png',\n" +
"      title: 'Centro de distribucion'\n" +
"  });\n";
    for(int i=0;i<nodes.length;i++){
        for(int j=0;j<nodes[i].length;j++){
html += " var newLatlng = new google.maps.LatLng("+nodes[i][j].getY()+","+nodes[i][j].getX()+"); "+
"      var marker = new google.maps.Marker({\n" +
"      position: newLatlng,\n" +
"      map: map,\n" +
//"      title: "+nodes[i][j].getPartialOrder().getPedido().getCliente().getNombre()+"\n" +
"  });\n";
        }
    }
html += "" +
"var request = {\n" +
"    origin: new google.maps.LatLng(1,1),\n" +
"    destination: new google.maps.LatLng(2,2),\n" +
"    travelMode: google.maps.TravelMode.DRIVING\n" +
"  };\n" +
"  directionsService.route(request, function(result, status) {\n" +
"    if (status == google.maps.DirectionsStatus.OK) {\n" +
"      directionsDisplay.setDirections(result);\n" +
"    }\n" +
"  });" +
"}\n" +
"\n" +
"google.maps.event.addDomListener(window, 'load', initialize);\n" +
"   </script>\n" +
"</head>\n" +
"<body>\n" +
"<div id=\"map-canvas\"></div>\n" +
"</body>\n" +
"</html>";
        browser.loadHTML(html);
        
    }
}
