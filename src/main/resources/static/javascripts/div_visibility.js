var browserType;

if (document.layers) {browserType = "nn4"}
if (document.all) {browserType = "ie"}
if (window.navigator.userAgent.toLowerCase().match("gecko")) {
 browserType= "gecko"
}

function hide_element(div_id) {
  if (browserType == "gecko" )
     document.poppedLayer = 
         eval('document.getElementById(div_id)');
  else if (browserType == "ie")
     document.poppedLayer = 
        eval('document.getElementById(div_id)');
  else
     document.poppedLayer =   
        eval('document.layers[div_id]');
  document.poppedLayer.style.display = "none";
}

function show_element(div_id) {
  if (browserType == "gecko" )
     document.poppedLayer = 
         eval('document.getElementById(div_id)');
  else if (browserType == "ie")
     document.poppedLayer = 
        eval('document.getElementById(div_id)');
  else
     document.poppedLayer = 
         eval('document.layers[div_id]');
  document.poppedLayer.style.display = "inline";
}

function toggle_element_visibility(id) {  
         var state = document.getElementById(id).style.display;  
             if (state == 'block') {  
                 document.getElementById(id).style.display = 'none';  
             } else {  
                 document.getElementById(id).style.display = 'block';  
             }  
}  

function setVisibility(check_id, element_id) {
	if (document.getElementById(check_id).checked == true) {
		document.getElementById(element_id).style.display = 'block';
	} else {
		document.getElementById(element_id).style.display = 'none';
	}
}
