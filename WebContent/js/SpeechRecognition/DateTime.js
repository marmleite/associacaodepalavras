class DateTime {

    constructor(dateTime) {
        if (dateTime !== null) {
            this.dateTime = dateTime;
        } else {
            this.dateTime = new Date();
        }
    }

    format(attrFormat) {
        this.attrFormat = attrFormat;
    }

    get() {
        var strDateTime = '';
        if (this.attrFormat === null || this.attrFormat === undefined) {
            this.format('y-m-d h:i:s');
        }
        for (var i = 0; i < this.attrFormat.length; i++) {
            switch (this.attrFormat[i]) {
                case 'y':
                case 'Y':
                    strDateTime += this.dateTime.getFullYear().toString().padStart(4, 0);
                    break;
                case 'm':
                case 'M':
                    strDateTime += (this.dateTime.getMonth() + 1).toString().padStart(2, 0);
                    break;
                case 'd':
                case 'D':
                    strDateTime += this.dateTime.getDate().toString().padStart(2, 0);
                    break;
                case 'H':
                case 'h':
                    strDateTime += this.dateTime.getHours().toString().padStart(2, 0);
                    break;
                case 'i':
                case 'I':
                    strDateTime += this.dateTime.getMinutes().toString().padStart(2, 0);
                    break;
                case 's':
                case 'S':
                    strDateTime += this.dateTime.getSeconds().toString().padStart(2, 0);
                    break;
                default:
                    strDateTime += this.attrFormat[i];
                    break;
            }
        }

        return strDateTime;
    }
	
	getTime(){
		return this.dateTime.getTime();
	}
	
}


