document.addEventListener('DOMContentLoaded', function () {
	var data = chrome.extension.getBackgroundPage().articleData;
	var URL = chrome.extension.getBackgroundPage().URL;
	if(data.error){
		$("#message").text(data.error);
		$("#content").hide();
	}else{
		$("#message").hide();
		$("#content-title").text(URL);
		$("#content-author").text(data.author);
		$("#content-date").text(data.postDate);
		$("#content-first-access").text(data.firstAccess);
	}
});
