var mounterInstance = null;
var speechInstance = null;
var idHistoria;

function storeEndAudio() {
	mounterInstance.storeEndAudio();
}

window.addEventListener('DOMContentLoaded', function() {

	if (window.SpeechRecognition || window.webkitSpeechRecognition) {
		speechInstance = new Speech();
	}

});

function testarAutoFalante() {

}

function carregarMounter() {

	// onaudiosstart
	var auxiliar = speechInstance.receiveAudio.onaudiostart = function(event) {
		console.log('onaudiostart');
		var internDateStart = new DateTime(null);
		console.log(internDateStart);
		console.log(internDateStart.getTime());
	};
	speechInstance.setEvent('onaudiostart', auxiliar);

	// onstart
	auxiliar = function(event) {
		speechInstance.isRecording = true;
	};
	speechInstance.setEvent('onstart', auxiliar);

	// onend
	auxiliar = function(event) {
		console.log('onend');
		if (mounterInstance.isFinished() !== true) {
			speechInstance.receiveAudio.start();
			return;
		}
		speechInstance.isRecording = false;
	};
	speechInstance.setEvent('onend', auxiliar);

	// onerror
	auxiliar = function(event) {
		console.log('onerror');
		console.log(event.error);
		if (mounterInstance.isFinished() !== true) {
			try{
				speechInstance.receiveAudio.stop();
				speechInstance.receiveAudio.start();	
			}catch(error){
				console.log(error);
			}
			return;
		}
	};
	speechInstance.setEvent('onerror', auxiliar);

	// onspeechstart
	auxiliar = function(event) {
		speechInstance.lastInitTimeSpeech = new DateTime(null);
		console.log('Início da fala  :   '
				+ speechInstance.lastInitTimeSpeech.getTime());
	}
	speechInstance.setEvent('onspeechstart', auxiliar);

	// onresult
	auxiliar = function(event) {
		$('#imageRecording').hide();
		mounterInstance.onResult(event);
	};
	speechInstance.setEvent('onresult', auxiliar);

	mounterInstance = new MounterAudios();
}

function redirecionarParaDetector(){
	$('#testePerifericos').css('display', 'none');
	$('#inicioDetector').modal();
//	$('#instrucoesTeste').show();
}

function iniciarDetector(){
	var historia;
	var t= Math.floor(Math.random() * 10);
	
	if(t<3){ 
		historia = "videos/espiao_x264.mp4"; 
		idHistoria=1;
	}else{
		if(t<6){ 
			historia = "videos/ladrao_x264.mp4";
			idHistoria=2;
		} else{
			historia = "videos/testemunha_x264.mp4";
			idHistoria=3;
		}
	}

	$('#testeMentira').css('display', 'block');
	$('#videoEstoria').attr('src', historia);
	$('#containerPrincipal').hide();
	$('#fundoVideo').show();
	document.getElementById('videoEstoria').play();
}

function iniciarPalavras(){
	const http = new XMLHttpRequest();
	http.open("GET", "testes?idHistoria="+idHistoria,true);
	http.send();
	$('#fundoVideo').hide();
	$('#containerPrincipal').show();
	setTimeout(function(){
		carregarMounter();
	}, 1000);
}
