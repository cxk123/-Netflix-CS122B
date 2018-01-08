//
//$(document).ready(function() {
//	
//	var dropdown = $('<div id="autocompleteDropdown"></div>');
//	
//	$('body').append(dropdown);
//	
//	$('#autocompleteDropdown').hide();
//	
//	var pos = $('#searchBar').offset();
//    var height = $('#searchBar').height();
//    var width = $('#searchBar').width();
//    
//    $('#autocompleteDropdown').css({
//        minWidth: width + 5 + 'px',
//        top: pos.top + height + 10 + 'px',
//        left: pos.left + 'px'
//    });
//	
//	$('#searchBar').focus(function() {
//		$('#autocompleteDropdown').fadeIn();
//	});
//	
//	$('#searchBar').blur(function() {
//		$('#autocompleteDropdown').fadeOut();
//	});
//	
//});
//
//function lookup(text) 
//{
//	if (text.length == 0) 
//	{
//		$('#autocompleteDropdown').fadeOut();
//	} 
//	else 
//	{
//		$.get("FabFlixSearchBar", {searchText: text}, function(list) {
//			
//			$('#autocompleteDropdown').html(list);
//			$('#autocompleteDropdown').fadeIn();
//		});
//	}
//};
	var xmlHttp;

	function getMoreContents(){
		//get user's input
		var content=document.getElementById("keyword").value;
		if(content==""){
			clearContent();
			return;
		}
		
		//send user's input to server
		
		xmlHttp=createXMLHttp();
		
		
		var url="search?keyword="+escape(content);

		xmlHttp.open("GET",url,true);
	
		xmlHttp.onreadystatechange=callback;
		xmlHttp.send(null); 
	}
	
	function createXMLHttp(){
		var xmlHttp;
		if(window.XMLHttpRequest){
			xmlHttp=new XMLHttpRequest();
		}
		if(window.ActiveXObject){
			xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
			if(!xmlHttp){
				xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
			}
		}
		return xmlHttp;
	}
	
	function callback(){
		if(xmlHttp.readyState==4){
			if(xmlHttp.status==200){
				var result=xmlHttp.responseText;
				var json=eval("("+result+")");
				//then we can display dynamicly display those data				
				//alert(json);
				setContent(json);
			}
		}
	}
	
	//set how to display content
	function setContent(contents){
		//fist, we should know the length of data, so we can generate <tr>
		clearContent();
		setLocation();
		var size=contents.length;
		for(var i=0;i<size;i++){
			var nextNode=contents[i];
			var tr=document.createElement("tr");
			var td=document.createElement("td");
			td.setAttribute("border","0");
			td.setAttribute("bgcolor","#FFFAFA");
			td.onmouseover=function(){
				this.className='mouseOver';
			};
			td.onmouseout=function(){
				this.className='mouseOut';
			};
			td.onmousedown=function(){
				document.getElementById("keyword").value=this.innerText;
			};
			/* td.onclick=function(){
				alert('Hello');
				document.getElementById("keyword").value="xxx";
			}; */
			var text=document.createTextNode(nextNode);
			td.appendChild(text);
			tr.appendChild(td);
			document.getElementById("content_table_body").appendChild(tr);
		}
	}
	
	function clearContent(){
		var contentTableBody=document.getElementById("content_table_body");
		var size=contentTableBody.childNodes.length;
		for(var i=size-1;i>=0;i--){
			contentTableBody.removeChild(contentTableBody.childNodes[i]);
		}
		document.getElementById("popDiv").style.border="none";
	}
	
	function keywordBlur(){
		clearContent();
	}
	
	function setLocation(){
		var content=document.getElementById("keyword");
		var width=content.offsetWidth;
		var left=content["offsetLeft"];
		var top=content["offsetTop"]+content.offsetHight;
		//obtain div
		var popDiv=document.getElementById("popDiv");
		popDiv.style.border="black qpx solid";
		popDiv.style.left=left+"px";
		popDiv.style.top=top+"px";
		popDiv.style.width=width+"px";
		document.getElementById("content_table").style.width=width+"px";
	}