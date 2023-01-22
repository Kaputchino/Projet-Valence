    fetch('NUPESColor.json')
        .then(function (response) {
            return response.json();
        })
        .then(function (data) {
            appendData(data);
        })
        .catch(function (err) {
            console.log('error: ' + err);
        });

    function appendData(data) {
        var listDiv = document.getElementById('listeParti');
        var ul=document.createElement('ul');
        for (var i = 0; i < data.list.length; ++i) {
              var li=document.createElement('li');
              li.innerHTML = data.list[i].parti;   // Use innerHTML to set the text
              ul.appendChild(li);                                 
        }
        listDiv.appendChild(ul);    // Note here
    }