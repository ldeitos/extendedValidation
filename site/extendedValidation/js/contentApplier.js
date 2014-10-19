var defaultLanguage = "pt-BR";
var path = "template/";

function addMetaExpires() {
	var now = new Date();
	var nowPlusAWeek = new Date(now.getTime() + 604800000);
	var expireDate = nowPlusAWeek.toUTCString();
	
	var meta = document.createElement("meta");
	$(meta).attr("name", "Expires");
	$(meta).attr("http-equiv", "Expires");
	$(meta).attr("content", expireDate);
	$(meta).appendTo($("head"));
}

function importContentFiles(files, language) {	
	if(language == null) {
		language = getActualLanguage();
	}
	
	$("link[rel='import']").remove();	
	for(i in files) {	
		var filePath = path + files[i] + "_" + language + ".html";		
		
		if(existUrl(filePath)){
			importContentFile(filePath);
		} else {
			filePath = path + files[i] + "_" + defaultLanguage + ".html";
			importContentFile(filePath);
		}		
	}
}

function applyContent(element, contentSelector) {
	$("link[rel='import']").each(function(){
		var content = this.import;
	   
		if(content == null) {
			$.get(this.href, function(data){
				var div = document.createElement("div");
				$(div).html(data);
				var el = div.querySelector(contentSelector);
			
				if(el != null) {
					var toAppend = el.cloneNode(true);
					$(element).empty();
					$(element).append(toAppend);									
				}									
			});
			
		} else {
			var el = content.querySelector(contentSelector);
			
			if(el != null) {
				var toAppend = el.cloneNode(true);
				$(element).empty();
				$(element).append(toAppend);				
				return false;
			}	
		}
	});    
}

function changeLanguageContent(newLanguage) {	
	if(newLanguage == null) {
		return;
	}
	
	setActualLanguage(newLanguage);	
	location.reload();	
}

function importContentFile(filePath) {
	var link = document.createElement("link");
	$(link).attr("rel", "import");
	$(link).attr("href", filePath);
	$(link).appendTo($("head"));		
}

function existUrl(url)
{
    var http = new XMLHttpRequest();
    http.open('HEAD', url, false);
    http.send();
    return http.status!=404;
}

function activeMenu(menuClass) {
	$("[id^='menu']").each(function() {
		var elementClass = $(this).attr("id");
		
		if(elementClass.match(menuClass)) {
			$(this).addClass("active");
		} else {
			$(this).removeClass("active");
		}
	});
}

function getActualLanguage() {
	var actualLanguage = window.navigator.language || navigator.browserLanguage;
	var cookies = document.cookie;
	
	if(cookies != null && cookies.length > 0){
		cookies = cookies.split(";");
		
		for(i in cookies) {
			cookie = cookies[i].split("=");
			
			if(cookie[0].trim() == "actualLanguage") {
				actualLanguage = cookie[1].trim();
			}			
		}
	} else {
		setActualLanguage(actualLanguage);
	}
	
	return actualLanguage;
}

function setActualLanguage(language) {
	document.cookie = "actualLanguage=" + language;
}

