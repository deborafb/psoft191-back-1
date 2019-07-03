package projeto.rest.response;

import lombok.Data;

@Data
public class DisciplinaResponse {
	
	private long id;
	private String nome;
	
	public DisciplinaResponse(long id, String nome) {
		this.id = id;	    
		this.nome = this.nomeFormatacao(nome); 
	}
	
	public String nomeFormatacao(String nome) {
		String saida = "";
		String[] nomeSaida = nome.split(" ");
	    for(int i = 0; i < nomeSaida.length; i ++) {
	    	saida += nomeSaida[i].substring(0, 1) + nomeSaida[i].substring(1, nomeSaida[i].length()).toLowerCase() + " ";	    	
	    }
	    saida = saida.trim();
	    if (saida.substring(saida.length()-2, saida.length()).equals("ii")) {
	    	saida = saida.substring(0, saida.length()-2);
	    	saida += "II";
	    } else if (saida.substring(saida.length()-1, saida.length()).equals("i")) {
	    	saida = saida.substring(0, saida.length()-1);
	    	saida += "I";
	    }
	    return saida;	
	}
}
