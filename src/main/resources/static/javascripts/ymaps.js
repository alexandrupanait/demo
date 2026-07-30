
function generateMap(){
	// Create a map object  
	var map = new YMap(document.getElementById('map'));
	
	// Add map type control  
	map.addTypeControl();
	// Add map zoom (long) control  
	map.addZoomLong();
	
	// Add the Pan Control  
	map.addPanControl();
	
	// Set map type to either of: YAHOO_MAP_SAT, YAHOO_MAP_HYB, YAHOO_MAP_REG  
	map.setMapType(YAHOO_MAP_REG);
	
	map.addMarker(new YGeoPoint(44.418532, 26.117282), "");
	
	// Display the map centered on a geocoded location  
	map.drawZoomAndCenter(new YGeoPoint(44.418532, 26.117282), 3);
}