package bean;

import dao.DAO;

/*
 * Talvez seja melhor fazer na integração com R futuramente, mas fica aqui para fins de entendimento
 */
public class DetectorMentiras {

	/*
	 * Constante de taxa de aprendizado. Provavelmente virará uma função
	 */
	private static final double ALFA = 0.3;

	/*
	 * Estrutura da Rede Neural
	 */
	//obj de coneção com o bd
	DAO dao; 
	
	// Camadas de neuronios[Soma das Entradas][resultado da saida]
	private double[][] camadaEntradas = new double[300][2];
	private double[][] camadas1 = new double[200][2];
	private double[][] camadas2 = new double[100][2];
	private double[][] saidas = new double[3][2];
	// camadas de pesos:
	private double[] pesosEntradaTempo = new double[100];
	private double[] pesosEntradaErros = new double[100];
	private double[] pesosEntradaRepeticao = new double[100];
	private double[][] pesos1 = new double[300][200];
	private double[][] pesos2 = new double[200][100];
	private double[][] pesosSaida = new double[100][3];
	// camada de pesos do bias (=1)
	private double[][] bias1 = new double[300][200];
	private double[][] bias2 = new double[200][100];
	private double[][] bias3 = new double[100][3];
	Historia historiaEscolhida;
	
	

	public DetectorMentiras(){
		/*
		 * inicializando variaveis
		 */
		this.dao = new DAO();
		dao.buscaPesos();
		// camadas de pesos:
		this.pesosEntradaTempo = this.dao. pesosEntradaTempo;
		this.pesosEntradaErros = this.dao.pesosEntradaErros;
		this.pesosEntradaRepeticao = this.dao.pesosEntradaRepeticao;
		this.pesos1 = this.dao.pesos1;
		this.pesos2 = this.dao.pesos2;
		this.pesosSaida = this.dao.pesosSaida;
		// camada de pesos do bias (=1)
		this.bias1 = this.dao.bias1;
		this.bias2 = this.dao.bias2;
		this.bias3 = this.dao.bias3;
		historiaEscolhida = new Historia();
	}
	
	/*
	 * funcao que recebe a soma das entradas do neuronio e as normaliza para um
	 * valor entre 0 e 1 (sida do neuronio)
	 */
	public double calculaSaida(double entrada) {
		return 1 / (1 + Math.pow(Math.E, -entrada));
	}

	/*
	 * correcaodo teste (feedfoward)
	 */	
	public void corrigir(Teste teste) {
		historiaEscolhida = new Historia();
		int res;
		
		// calculando a camadas de entrada
		for (Resposta resposta : teste.getRespostas()) {
			this.camadaEntradas[resposta.getPosicao()][0] = (resposta.getTempoReacao()/100.00) * this.pesosEntradaTempo[resposta.getPosicao()];		
			this.camadaEntradas[resposta.getPosicao()][1] = this.calculaSaida(this.camadaEntradas[resposta.getPosicao()][0]);
	
			this.camadaEntradas[resposta.getPosicao()+100][0] = (resposta.getErro()?1.0:0.0) * this.pesosEntradaErros[resposta.getPosicao()];		
			this.camadaEntradas[resposta.getPosicao()+100][1] = this.calculaSaida(this.camadaEntradas[resposta.getPosicao()+100][0]);

			this.camadaEntradas[resposta.getPosicao()+200][0] = (resposta.getErroRepeticao()?1.0:0.0) * this.pesosEntradaRepeticao[resposta.getPosicao()];		
			this.camadaEntradas[resposta.getPosicao()+200][1] = this.calculaSaida(this.camadaEntradas[resposta.getPosicao()+200][0]);
		}

		// para a camada1
		for (int i = 0; i < 200; i++) {
			for (int y = 0; y < 300; y++) {
				this.camadas1[i][0] += (this.camadaEntradas[y][1] * this.pesos1[y][i]) + this.bias1[y][i];
			}
			this.camadas1[i][1] = this.calculaSaida(this.camadas1[i][0]);
		}
		
		// para a camada2
		for (int i = 0; i < 100; i++) {
			for (int y = 0; y < 200; y++) {
				this.camadas2[i][0] += (this.camadas1[y][1] * this.pesos2[y][i]) + this.bias2[y][i];
			}
			this.camadas2[i][1] = this.calculaSaida(this.camadas2[i][0]);

		}
		// para as saidas
		double[] resultado = new double[3];

		for (int i = 0; i < 3; i++) {
			for (int y = 0; y < 100; y++) {
				this.saidas[i][0] += (this.camadas2[y][1] * this.pesosSaida[y][i]) + this.bias3[y][i];
			}
			this.saidas[i][1] = this.calculaSaida(this.camadas2[i][0]);
			resultado[i] = this.saidas[i][1];

		}
		
		res = 1;
		for (int i = 1; i < 3; i++) {
			if(resultado[i] > resultado[i-1])
				res = i+1;
		}
		
		
		historiaEscolhida.setId(res);
		teste.setHistoriaEscolhida(historiaEscolhida);
	}

	/*
	 * função que recebe as respostas, corrige e treina a rede neural
	 */
	public void corrigirTreinar(Teste teste) throws Exception {

		/*
		 * inicialização de variaveis locais 
		 */	
		double[] esperado = new double[3];
		double delta[] = new double[300];
		double erroLocal[] = new double[300];
		double erroTotal;
		/*
		 * inicio do backpropagation
		 */				
		// correção para pegar os valores dos neuronios e a saida		
		this.corrigir(teste);	
		
		for (int y=1; y<=3; y++) {
			esperado[y-1] = teste.getHistoriaEscolhida().getId() == y ? 1 : 0;
		}
		
		// calculo do erro total
		erroTotal = Math.pow((esperado[0] - this.saidas[0][1]), 2) + Math.pow((esperado[1] - this.saidas[1][1]), 2)
				+ Math.pow((esperado[1] - this.saidas[2][1]), 2) / 3;

		/*
		 * Backpropagation para a camada de saida/pesosSaida
		 */
		for (int i = 0; i < 3; i++) {
			delta[i] = (this.saidas[i][1] - esperado[i]) * (this.saidas[i][1] * (1 - this.saidas[i][1]));
			for (int y = 0; y < 100; y++) {
				this.dao.pesosSaida[y][i] = this.pesosSaida[y][i] - (this.pesosSaida[y][i] * delta[i] * ALFA);
				this.dao.bias3[y][i] = this.bias3[y][i] - (1 * delta[i] * ALFA);
			}
		}

		/*
		 * Bp para a camadas2 /pesos2
		 */

		// derivada do erro para esta camada:
		for (int i = 0; i < 100; i++) {
			for (int y = 0; y < 3; y++) {
				erroLocal[i] += (delta[i] * this.pesos2[i][y]);
			}
		}

		// atribuição de valores
		for (int i = 0; i < 100; i++) {
			delta[i] = (erroLocal[i]) * (this.camadas2[i][1] * (1 - this.camadas2[i][1]));
			for (int y = 0; y < 200; y++) {
				this.dao.pesos2[y][i] = this.pesos2[y][i] - (this.pesos2[y][i] * delta[i] * ALFA);
				this.dao.bias2[y][i] = this.bias2[y][i] - (1 * delta[i] * ALFA);
			}
		}

		/*
		 * Bp para a camada1 /pesos1
		 */
		// derivada do erro para esta camada:
		erroLocal = new double[300];
		for (int i = 0; i < 200; i++) {
			for (int y = 0; y < 100; y++) {
				erroLocal[i] += (delta[i] * this.pesos2[i][y]);
			}
		}

		// atribuição de valores
		for (int i = 0; i < 200; i++) {
			delta[i] = erroLocal[i] * (this.camadas1[i][1] * (1 - this.camadas1[i][1]));
			for (int y = 0; y < 300; y++) {
				this.dao.pesos1[y][i] = this.pesos1[y][i] - (this.pesos1[y][i] * delta[i] * ALFA);
				this.dao.bias1[y][i] = this.bias1[y][i] - (1 * delta[i] * ALFA);
			}
		}
		/*
		 * camada de entrada, Pesos de entrada
		 */
		// Bp para a entrada
		erroLocal = new double[300];
		for (int i = 0; i < 300; i++) {
			for (int y = 0; y < 200; y++) {
				erroLocal[i] += (delta[i] * this.pesos1[i][y]);
			}
		}

		// atribuição de valores
		for (int i = 0; i < 100; i++) {
			delta[i] = erroLocal[i] * (this.camadaEntradas[i][1] * (1 - this.camadaEntradas[i][1]));

			this.dao.pesosEntradaTempo[i] = this.pesosEntradaTempo[i] - (this.pesosEntradaTempo[i] * delta[i] * ALFA);
			this.dao.pesosEntradaRepeticao[i] = this.pesosEntradaRepeticao[i]- (this.pesosEntradaRepeticao[i] * delta[i] * ALFA);
			this.dao.pesosEntradaErros[i] = this.pesosEntradaErros[i]- (this.pesosEntradaErros[i] * delta[i] * ALFA);
		}

		// atualizando os pesos, bias e dados da rede
		this.dao.atualizarPesos();
		this.dao.insereHistoricoRede(erroTotal, teste.getHistoriaEscolhida().getId());

	}

}