function onEndedTesteAudio() {
	$('#responseAudio').show(500);
}

function onOkAudio() {
	$('#perifericoAutoFalante').hide(500, function() {
		$('#perifericoMicrofone').show(500);
	});
}

function onNonOkAudio() {
	$('#dicasAudioPeriferico').show(500);
}

function onHearAgain() {
	$('#perifericoMicrofone').hide(500, function() {
		$('#perifericoAutoFalante').show(500);
	});
	$('#dicasAudioPeriferico').hide();
	$('#dicasMicrofonePeriferico').hide();
}

function testarMicrofone() {
	$('#imageRecordingTest').show(100);

	// onresult
	auxiliar = function(event) {
		console.log('onresult');

		for (var cont = event.resultIndex; cont < event.results.length; cont++) {
			if (event.results[cont].isFinal) {
				speechInstance.audioTranscription += event.results[cont][0].transcript;
			}
			speechInstance.lastRecording = event.results[cont][0].transcript;

			var result = speechInstance.lastRecording;
			alert(result);
		}

		try {
			if (result === 'cabelo') {
				$('#redirecionamento').modal();
			} else {
				$('#dicasMicrofonePeriferico').show(300);
			}
		} catch (error) {
			console.log(error);
		} finally {
			$('#imageRecordingTest').hide(100);
			speechInstance.pause();
		}
	};
	speechInstance.setEvent('onresult', auxiliar);

	// onerror
	auxiliar = function(event) {
		console.log('onerror');
		speechInstance.launchMessageError(event);
		$('#imageRecordingTest').hide();
	};
	speechInstance.setEvent('onerror', auxiliar);
	speechInstance.init();
	console.log(speechInstance);

}

function expansible(headerElement, expansibleElement) {
	expansibleElement.slideToggle(300);
}
