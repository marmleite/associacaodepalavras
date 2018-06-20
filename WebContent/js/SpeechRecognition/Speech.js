class Speech{
	
	constructor(){
		speechInstance = this;
		this.isRecording = false;
		this.audioTranscription = '';
		this.lastRecording = '';
		this.lastInitTimeSpeech = null;
		
		var speechApi = window.SpeechRecognition || window.webkitSpeechRecognition;

        this.receiveAudio = new speechApi();
		console.log(this.receiveAudio);

        speechInstance.receiveAudio.continuous = true;
        speechInstance.receiveAudio.interimResults = false;
        speechInstance.receiveAudio.lang = 'pt-BR';
		this.timeAudios = [];
				
		//Evento lançado quando a gravação é finalizada
		speechInstance.receiveAudio.onaudioend = function(event){
			console.log('onaudioend');
		}
		
		//Evento lançado o que foi dito não é reconhecido
		speechInstance.receiveAudio.onnomatch = function(event){
			console.log('onnomatch');
			console.log(event);
		}				
		
		speechInstance.receiveAudio.onsoundend = function(event){
			console.log('onsoundend');
		}				
		
		speechInstance.receiveAudio.onerror = function(event){
			console.log('onerror');
			console.log(event.error);
		}
		
	}
	
	launchMessageError(event){
		console.log('launchMessageError');
		console.log(event.error);
		if(event.error == 'network'){
			$('#titleError').text('Ops!! Encontramos um erro');
			$('#messageError').text('Falha na rede! Por favor verifique sua internet para continuar!');
			//footerError
			$('#modalError').modal();
		}else if(event.error == 'no-speech'){
			$('#titleError').text('Ops!! Encontramos um erro');
			$('#messageError').text('Nenhuma fala foi detectada');
			//footerError
			$('#modalError').modal();
		}
	}
	
	init(){
		this.receiveAudio.start();
	}
	
	pause(){
		this.receiveAudio.stop();	
	}
	
	setEvent(event, callback)
	{
		var events = [
			'onaudiostart', 
			'onaudioend', 
			'onend', 
			'onerror', 
			'onnomatch', 
			'onsoundstart', 
			'onsoundend', 
			'onspeechstart',
			'onspeechend', 
			'onresult',
			'onstart'
		];
		if(events.indexOf(event) > -1){
			console.log('evento consolidado : ' + event);
			this.receiveAudio[event] = callback;
			return;
		}
	}
	
	setContinuous(continuous){
		this.receiveAudio.continuous = continuous;
	}
	
	setInterimResults(interimResults){
		this.receiveAudio.interimResults = interimResults;
	}
	
	
}


