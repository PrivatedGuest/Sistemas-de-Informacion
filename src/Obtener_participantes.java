
public class Obtener_participantes {

	String participante;
	String id_espectaculo;
	String fecha;
	String id_recinto;
	String tipo;
	
	public Obtener_participantes(String a,String b,String c,String d,String e) {
		this.participante=a;
		this.id_espectaculo=b;
		this.fecha=c;
		this.id_recinto=d;
		this.tipo=e;
	}
	
	public String getParticipante() {
		return participante;
	}
	public String getId_espectaculo() {
		return id_espectaculo;
	}
	public String getFecha() {
		return fecha;
	}
	public String getId_recinto() {
		return id_recinto;
	}
	public String getTipo() {
		return tipo;
	}
	
}
