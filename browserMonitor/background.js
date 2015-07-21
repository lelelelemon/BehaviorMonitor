var articleData = {};
function checkForValidUrl(tabId, changeInfo, tab) {
		chrome.pageAction.show(tabId);
		articleData.url = tab.url;
		articleData.visit = new Date();
};

chrome.tabs.onUpdated.addListener(checkForValidUrl);
articleData.firstAccess = "获取中...";	
if(!articleData.error){
	$.ajax({
		url: "http://localhost:8888/highlight.php",
		cache: false,
		type: "POST",
		data: JSON.stringify({url:articleData.url}),
		dataType: "json"
	}).done(function(msg) {
		if(msg.error){
			articleData.firstAccess = msg.error;
		} else {
			articleData.firstAccess = msg.firstAccess;
		}
	}).fail(function(jqXHR, textStatus) {
		articleData.firstAccess = textStatus;
	});
}