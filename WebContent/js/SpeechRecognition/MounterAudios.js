class MounterAudios{

	
	constructor()
	{
		//0 = não iniciado
		//1 = iniciado
		//2 = liberado para envio
		//3 = encerrado parcial
		//4 = encerrado completo
		this.status = 0;
		this.maxResponseTimeFunction = null;
		this.repetition = false;
		
		//tempo máximo para resposta(em milisegundos)
		this.maxResponseTime = 5000;
		var callback = function(response){	
			console.log(response);
			mounterInstance.setJsonPage(response);
		};
		
        var sender = new Sender('palavras','GET');
        sender.send(callback);
		this.counter = 0;
		this.size = 0;
		this.timeAudios = [];
		
	}
	
	setJsonPage(jsonPage){
		speechInstance.init();
		this.jsonPage = jsonPage;
		this.size = this.jsonPage.palavras.length;
		this.mountNext();
	}
	
	getCurrentIdResponse(){
		return this.jsonPage.palavras[this.counter].id;
	}
	
	getIdObject(){
		return this.jsonPage.id;
	}
	
	isReady(){
		if(this.jsonPage !== null){
			return true;
		}
		return false;
	}
	
	isFinished(){
		if(this.size <= this.counter){
			if(this.repetition == true){
				console.log('finalizou');
				console.log(this.counter);
				console.log(this.jsonPage.palavras);
				return true;
			}
			this.repetition = true;
			this.counter = 0;
			return false;
		}
		return false;	
	}
	
	getCurrentInitTime(){
		
	}
	
	mountNext()
	{
		if(this.isFinished()){
			$('#imageRecording').hide();
			$('#loaderResponse').show();
//			$('#mensagemLoaderResponse').show();
			var callback = function(response){
				$('#loaderResponse').hide();
				$('#resultado').show();
				console.log(response);
				$('#strResult').text(response);
			};
			
			
			var sender = new Sender("testes?idTeste=" + mounterInstance.getIdObject(),'POST');
			sender.send();
			
			//Buscar resultado
			return;
		}
		this.status = 1;
		$('#response').html('<audio id="audio" onended="storeEndAudio();" ><source src="audios/' +  this.jsonPage.palavras[this.counter++].caminhoAudio + '" ></source> Your browser does not support the audio element</audio>');
		speechInstance.pause();
		document.getElementById('audio').play();
	}
	
	storeEndAudio(){
		$('#imageRecording').show();
		this.status = 2;
		this.timeAudios[this.counter] = new DateTime(null);
		try{
			speechInstance.init();	
		}catch(error){
			console.log(error);
		}
//		var atualIdAudio = this.getCurrentIdResponse();
		this.maxResponseTimeFunction = setTimeout(function(){
//			if(atualIdAudio != mounterInstance.getCurrentIdResponse()){
//				alert('id passado' + atualIdAudio);
//				alert('id atual' + mounterInstance.getCurrentIdResponse());
//				return;
//			}
			$('#imageRecording').hide();
//			console.log('passou tempo máximo');
			mounterInstance.onResult();
		}, this.maxResponseTime);
		console.log('Fim do audio   -   ' + this.timeAudios[this.counter].getTime());
		console.log(this.timeAudios[this.counter].get());
	}
	
	getCurrentEndTimeAudio(){
		return this.timeAudios[this.counter];
	}
	
	onResult(event = null){
		clearTimeout(this.maxResponseTimeFunction);
		if(mounterInstance.status != 2){
			console.log(mounterInstance.status);
			return;
		}
		speechInstance.status = 3;
		if(event != null){
			console.log('onresult');
			var internDate = new DateTime(null);
			console.log(internDate.getTime());
			var interimTranscript = '';
			var dateTime = (new DateTime(new Date())).get();
			
//			for (var cont = event.resultIndex; cont < event.results.length; cont++) {
//				if (event.results[cont].isFinal) {
//					speechInstance.audioTranscription += event.results[cont][0].transcript;
//				} else {
//					interimTranscript += event.results[cont][0].transcript;
//				}
//				speechInstance.lastRecording = event.results[cont][0].transcript;
//				
//				var result = speechInstance.audioTranscription || interimTranscript;
//			}
			
			for (var cont = event.resultIndex; cont < event.results.length; cont++) {
				if (event.results[cont].isFinal) {
					speechInstance.audioTranscription += event.results[cont][0].transcript;
				}
				speechInstance.lastRecording = event.results[cont][0].transcript;

				var result = speechInstance.lastRecording;
				console.log("result - mounter- event");
				console.log(event);
			}
			
			
			
		}else{
			result = '';
		}

		try {
			var callback = function(response) {
//				console.log(response);
				mounterInstance.mountNext();
			};

			var sender = new Sender('respostas','POST');
			sender.addData('idTeste', mounterInstance.getIdObject());
         	sender.addData('posicao', mounterInstance.counter);
         	sender.addData('idPalavra', mounterInstance.getCurrentIdResponse()-1);
         	sender.addData('resposta', speechInstance.lastRecording);
         	sender.addData('tempo', dateTime);
         	if(speechInstance.lastInitTimeSpeech == null){
    			sender.addData('tempoReacao', 0);
         	}else{
    			sender.addData('tempoReacao', speechInstance.lastInitTimeSpeech.getTime() - mounterInstance.getCurrentEndTimeAudio().getTime());         		
         	}
			
			if(event == null){
				sender.addData('tempoDeResposta', 0);
			}else{
				sender.addData('tempoDeResposta', speechInstance.lastInitTimeSpeech
						.getTime()
						- mounterInstance.getCurrentEndTimeAudio().getTime());
				console.log('Fim do áudio :   '
						+ mounterInstance.getCurrentEndTimeAudio().getTime());
				console.log('Ínicio da fala :   '
						+ speechInstance.lastInitTimeSpeech.getTime());

				var teste = new DateTime(
						new Date(
								mounterInstance.getCurrentEndTimeAudio().getTime()
										+ (mounterInstance.getCurrentEndTimeAudio()
												.getTime() - speechInstance.lastInitTimeSpeech
												.getTime())));
				console.log('Tempo de resposta ---  ' + teste.get());

			}
			sender.send(callback);
			speechInstance.lastInitTimeSpeech = null;
			speechInstance.lastRecording = null;
		} catch (error) {
			console.log(error);
		}
	}	
}