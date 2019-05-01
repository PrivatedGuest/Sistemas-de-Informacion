
public class Obtener_espectaculos {

	String id_espectaculo;
	String tipo;
	String descripcion;
	String participante;

	public Obtener_espectaculos(String a, String b, String c, String d) {
		this.id_espectaculo=a;
		this.tipo=b;
		this.descripcion=c;
		this.participante=d;		
	}

	public String getId_espectaculo() {
		return id_espectaculo;
	}

	public String getTipo() {
		return tipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getParticipante() {
		return participante;
	}

	
	
	
	
}