package client.delivery;

import algorithm.Node;
import algorithm.Solution;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import util.Constants;

public class GoogleMaps{
    private Image icon = null;
    public GoogleMaps(Solution solution){
        Node[][] nodes = solution.getNodes();
        final Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);
        JFrame frame = new JFrame("Vista en Mapa");
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(1000, 700);
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
"  var splitWayptsArray = [];\n" +
"  var productsArray = [];\n" +
"  var clientsByRouteArray = [];";
    for(int i=0;i<nodes.length;i++){
html += " var waypts"+i+" = [];" +
"         var clientByRoute"+i+" = [];\n" +
"         splitWayptsArray.push(waypts"+i+");" +
"         clientsByRouteArray.push(clientByRoute"+i+");\n";
    }
html += "function initialize() {\n" +
"  var table = document.getElementById('info');\n" +
"  var selectRoute = document.getElementById('selectRoute');\n" +
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
" });\n";
    for(int i=0;i<nodes.length;i++){
        for(int j=0;j<nodes[i].length;j++){
html += " var newLatlng = new google.maps.LatLng("+nodes[i][j].getY()+","+nodes[i][j].getX()+");\n" +
"waypts"+i+".push({location:newLatlng,stopover:true});\n" +
"clientByRoute"+i+".push({local:'"+nodes[i][j].getPartialOrder().getPedido().getLocal().getNombre()+"',product:'"+nodes[i][j].getProduct().getNombre()+"',cantPallets:'"+nodes[i][j].getDemand()+"'});\n";
        }
    }
html += "createRoute(waypts0,clientByRoute0);" +
"function createRoute(waypts,clientByRoute){\n" +
"  var request = {\n" +
"    origin: new google.maps.LatLng("+Constants.WAREHOUSE_LATITUDE+","+Constants.WAREHOUSE_LONGITUDE+"),\n" +
"    destination: new google.maps.LatLng("+Constants.WAREHOUSE_LATITUDE+","+Constants.WAREHOUSE_LONGITUDE+"),\n" +
"    waypoints: waypts,\n" +
"    travelMode: google.maps.TravelMode.DRIVING\n" +
"  };\n" +
"  directionsService.route(request, function(result, status) {\n" +
"    if (status == google.maps.DirectionsStatus.OK) {\n" +
"      directionsDisplay.setDirections(result);\n" +
"    }\n" +
"  });\n" +
"  $('#info').html('');\n" +
"  $('#info').append(\"<tr style='background-color:#FFFFFF;'><th>Punto</th><th>Local</th><th>Producto</th><th>Cant. Pallets</th></tr><tr style='background-color:#FB7468;'><td>A</td><td>Centro de distribución</td><td>-</td><td>-</td></tr>\");\n" +
"  i=0;" +
"  for(i;i<clientByRoute.length;i++){\n" +
"    $('#info').append(\"<tr style='background-color:#87CB47;'><td>\"+String.fromCharCode(66+i)+\"</td><td>\"+clientByRoute[i].local+\"</td><td>\"+clientByRoute[i].product+\"</td><td>\"+clientByRoute[i].cantPallets+\"</td></tr>\");\n" +
"  }\n" +
"  $('#info').append(\"<tr style='background-color:#FB7468;'><td>\"+String.fromCharCode(66+i)+\"</td><td>Centro de distribución</td><td>-</td><td>-</td></tr>\");\n" +
"}\n" +

"$('select').change(function(e){\n" +
"   e.preventDefault();\n" +
"   createRoute(splitWayptsArray[$('select option:selected').val()],clientsByRouteArray[$('select option:selected').val()]);\n" +
"});\n" +
"  map.controls[google.maps.ControlPosition.TOP_RIGHT].push(table);\n" + 
"  map.controls[google.maps.ControlPosition.TOP_RIGHT].push(selectRoute);\n" + 

"}\n" +
"\n" +
"google.maps.event.addDomListener(window, 'load', initialize);\n" +
            
"   </script>\n" +
"</head>\n" +
"<body>\n" +
"<select id='selectRoute'>\n";
for(int i=0; i<nodes.length;i++){
    html += "<option value="+i+">Ruta del camión "+(i+1)+"</option>\n";
}
html += "</select>\n" +
"<table id='info' border='1'>\n" +
"</table>\n" +
"<div id=\"map-canvas\"></div>\n" +
"</body>\n" +
"</html>";
        browser.loadHTML(html);
    }
}
