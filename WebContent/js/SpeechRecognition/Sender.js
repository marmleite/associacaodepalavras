class Sender {

    constructor(url, type = 'POST')
    {
        if (type !== 'POST' && type !== 'GET') {
            throw 'Request ' + type + ' is not defined.';
        }
        if (url === null || url === undefined) {
            throw 'Url can not be empty.';
        }
        this.url = url;
        this.type = type;
        this.counter = 0;
        this.data = null;
    }

    addData(key, value)
    {
        if (this.data !== null) {
            this.data += '&' + key + '=' + value;
            return;
        }
        this.data = key + '=' + value;
    }

    getData() {
        for (var objectData in this.data) {
            objectData.name;
        }
    }

    send(callback)
    {
        $.ajax({
            type: this.type,
            url: this.url,
            data: this.data
//        }).done(function (e) {
//			console.log(e);
//            alert(e);
//        })
      }).done(callback)
		.fail(function(jqXHR, textStatus, msg){
			alert(msg);	
		});
    }
}


