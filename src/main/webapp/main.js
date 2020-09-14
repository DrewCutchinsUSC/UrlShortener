let urlResult = document.getElementById("url-result")
let urlInput = document.getElementById("url-form").urlInput

let linkBase = "http://localhost:8080/link/"

function submitUrl(){
    let url = urlInput.value

    if(!isValidHttpUrl(url)){
        urlResult.innerHTML = "Please enter a valid url!"
        return false;
    }

    let xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", "api/url", true);
    xmlhttp.onreadystatechange = function () { //Call a function when the state changes.
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            displayResult(JSON.parse(xmlhttp.responseText).endpoint);               
        }
    };

    xmlhttp.send(JSON.stringify({
        "url": url
    })); 

    return false;
}

function displayResult(endpoint){
    let link = linkBase + endpoint

    let linkElement = document.createElement('a');
    linkElement.setAttribute('href', link);
    linkElement.innerHTML = link;

    urlResult.innerHTML = '';
    urlResult.appendChild(linkElement);
}

function isValidHttpUrl(str) {
    let url;
  
    try {
      url = new URL(str);
    } catch (_) {
      return false;  
    }
  
    return url.protocol === "http:" || url.protocol === "https:";
}