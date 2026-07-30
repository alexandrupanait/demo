			function initTab(){																									
					if (selectedTab == "meniuTabButton") {						
						loadTab(document.getElementById("meniuTabButton"));
					}
					else 
						if (selectedTab == "titleTabButton") {
							loadTab(document.getElementById("titleTabButton"));
						}
						else {							
							loadTab(document.getElementById("texteTabButton"));
						}
			}						
			var selectedFromRightList;						
			var selectedPosition = '<%= session[:selectedFromRightList] %>';
			function addToRightList(){
				if (selectedFromLeftList != null) {
					var id = selectedFromLeftList.id;
					id = id.substr(0,id.lastIndexOf('_'))+'_active';
					document.getElementById(id).value = 't';					
				}				
			}
			
			function addToLeftList(){
				if (selectedFromRightList != null) {
					var id = selectedFromRightList.id;
					id = id.substr(0, id.lastIndexOf('_'))+'_active';
					document.getElementById(id).value = 'f';					
				}				
			}
			
			function moveUp(){				
				if(selectedFromRightList!=null){
					var id = selectedFromRightList.id;					
					id = id.substr(0,id.lastIndexOf('_'))+'_listorder';						
					pos = document.getElementById(id).value;					
					document.getElementById('selectedFromRightList').value = pos;					
					pos = Number(pos);
					if (pos > 0) {						
						pos = pos-1;
						if(findElementWithPosition(pos)!=null)						
							findElementWithPosition(pos).value = pos+1;								
						document.getElementById(id).value = pos;												
						document.getElementById('selectedFromRightList').value = pos;
					}					
				}
			}	
			
			function moveDown(){
				if(selectedFromRightList!=null){
					var id = selectedFromRightList.id;										
					id = id.substr(0,id.lastIndexOf('_'))+'_listorder';					
					pos = document.getElementById(id).value;										
					document.getElementById('selectedFromRightList').value = pos;					
					pos = Number(pos);					
					var elemCount = getRightListElementsCount();									
					if (pos < (elemCount-1)) {																														
						pos = pos+1;
						if(findElementWithPosition(pos)!=null)
							findElementWithPosition(pos).value = pos-1;														
						document.getElementById(id).value = pos;				
						document.getElementById('selectedFromRightList').value = pos;		
					}
				}
			}		
			
			function findElementWithPosition(x){
				var div = document.getElementById("visibleElementsList");
				var toFind = "elem_"+(x)+"_listorder";								
				for(i = 0;i<div.childNodes.length ;i++){					
					if(div.childNodes[i].id==toFind){						
						return div.childNodes[i];
					}						
				}
			}
			
			function getRightListElementsCount(){
				var div = document.getElementById("visibleElementsList");
				var toRet = 0;
				for (i = 0; i < div.childNodes.length; i++) {
					var elem = div.childNodes[i].id;
					if(elem!=null && elem.indexOf("listorder")>0) toRet++;
				}				
				return toRet;
			}
			
			function loadTab(button){
				var titleButton = document.getElementById("titleTabButton");				
				var meniuButton = document.getElementById("meniuTabButton");
				var textButton = document.getElementById("texteTabButton");																
				if (button.id == 'titleTabButton') {																		
					button.style.borderStyle = 'none';
					meniuButton.style.borderStyle= 'outset';
					textButton.style.borderStyle= 'outset';
					document.getElementById('titleElementsDiv').style.display='';
					document.getElementById('meniuElementsDiv').style.display='none';
					document.getElementById('texteElementsDiv').style.display='none';
				}
				if (button.id == 'meniuTabButton') {																		
					button.style.borderStyle = 'none';
					titleButton.style.borderStyle= 'outset';
					textButton.style.borderStyle= 'outset';
					document.getElementById('titleElementsDiv').style.display='none';
					document.getElementById('meniuElementsDiv').style.display='';					
					document.getElementById('texteElementsDiv').style.display='none';					
				}
				if (button.id == 'texteTabButton') {																		
					button.style.borderStyle = 'none';
					meniuButton.style.borderStyle= 'outset';
					titleButton.style.borderStyle= 'outset';
					document.getElementById('titleElementsDiv').style.display='none';
					document.getElementById('meniuElementsDiv').style.display='none';					
					document.getElementById('texteElementsDiv').style.display='';					
				}
				}				
