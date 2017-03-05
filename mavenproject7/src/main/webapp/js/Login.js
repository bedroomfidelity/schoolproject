/**
 * Created by Khanh on 3/4/2017.
 */
function initRequest() {
    if (window.XMLHttpRequest) {
        if (navigator.userAgent.indexOf('MSIE') != -1) {
            isIE = true;
        }
        return new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        isIE = true;
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
}
function eventHandler(method,actionname){
    var url = "api/user/" + actionname;
    console.log(url);
    req = initRequest();
    req.open(method, url, true);
    req.onreadystatechange = callback;
    req.send(null);
}
function callback(){
    if (req.readyState == 4) {
        if (req.status == 200) {

            parseMessages(req.responseXML);
        }
    }

}