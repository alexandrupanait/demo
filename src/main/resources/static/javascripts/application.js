// Place your application-specific JavaScript functions and classes here
// This file is automatically included by javascript_include_tag :defaults
function PopupCenter(pageURL, title,w,h) {        
		window.name="mainWindow";
        var left = (screen.width/2)-(w/2);
        var top = (screen.height/2)-(h/2);
        var popup = window.open (pageURL, title, 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, copyhistory=no, width='+w+', height='+h+', top='+top+', left='+left);        
        popup.focus();
}

function showImage(id, parent){
	elem = document.getElementById(id);	
	parent_elem = document.getElementById(parent);	
	var top = 0;	
	while(parent_elem!=null){
		top+=parent_elem.offsetTop;
		parent_elem = parent_elem.offsetParent;		
	}	
	elem.style.visibility="visible";
	elem.style.top = (top)+"px";		
}

function hideImage(id,parent){
	elem = document.getElementById(id);
	parent_elem = document.getElementById(parent);	
	elem.style.visibility="hidden";		
}

function writeEnglishURL(){
	currentURL = window.location.toString();
	if(currentURL.indexOf('?')>0 && currentURL.indexOf('language')>0)
		languageURL = currentURL.replace("_ro","_en")
	else if (currentURL.indexOf('?')>0)
		languageURL = currentURL+"&language=_en"
	else
		languageURL = currentURL+"?language=_en"		 
	document.write('<a href="'+languageURL+'">english</a>')	
}

function writeRomanianURL(){
	currentURL = window.location.toString();
	if(currentURL.indexOf('?')>0 && currentURL.indexOf('language')>0)
		languageURL = currentURL.replace("_en","_ro")
	else if (currentURL.indexOf('?')>0)
		languageURL = currentURL+"&language=_ro"
	else
		languageURL = currentURL+"?language=_ro"		 
	document.write('<a href="'+languageURL+'">romana</a>')
}

function submitOK(id){	
	passwd = document.getElementById(id)	
	passwd.value= hexMD5(passwd.value);
	return true;
}

