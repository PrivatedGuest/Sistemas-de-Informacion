
public class Obtener_eventos {

	String id_espectaculo;
	String id_recinto;
	String fecha;
	String estado;
	String E_bebe;
	String E_Inf;
	String E_Adul;
	String E_Para;
	String E_Jub;
	
	public Obtener_eventos(String a,String b,String c,String d,String e,String f,String g,String h,String i) {
		this.id_espectaculo=a;
		this.id_recinto=b;
		this.fecha=c;
		this.estado=d;
		this.E_bebe=e;
		this.E_Inf=f;
		this.E_Adul=g;
		this.E_Para=h;
		this.E_Jub=i;
	}
	
	public String getId_espectaculo() {
		return id_espectaculo;
	}
	public String getId_recinto() {
		return id_recinto;
	}
	public String getFecha() {
		return fecha;
	}
	public String getEstado() {
		return estado;
	}
	public String getE_bebe() {
		return E_bebe;
	}
	public String getE_Inf() {
		return E_Inf;
	}
	public String getE_Adul() {
		return E_Adul;
	}
	public String getE_Para() {
		return E_Para;
	}
	public String getE_Jub() {
		return E_Jub;
	}
	
}
