
public class Obtener_localidades {

	String id_localidade;
	String estado;
	String id_recinto;
	String id_grada;
	String p_bb;
	String p_inf;
	String p_adul;
	String p_parad;
	String p_jub;
	public Obtener_localidades(String id_localidade,String estado, String id_recinto, String id_grada,
			 String p_bb, String p_inf, String p_adul, String p_parad, String p_jub) {
		this.id_localidade = id_localidade;
		this.estado=estado;
		this.id_recinto = id_recinto;
		this.id_grada = id_grada;
		this.p_bb = p_bb;
		this.p_inf = p_inf;
		this.p_adul = p_adul;
		this.p_parad = p_parad;
		this.p_jub = p_jub;
	}
	public String getId_localidade() {
		return id_localidade;
	}
	public String get_estado() {
		return this.estado;
	}
	public String getId_recinto() {
		return id_recinto;
	}
	public String getId_grada() {
		return id_grada;
	}
	public String getP_bb() {
		return p_bb;
	}
	public String getP_inf() {
		return p_inf;
	}
	public String getP_adul() {
		return p_adul;
	}
	public String getP_parad() {
		return p_parad;
	}
	public String getP_jub() {
		return p_jub;
	}
	
	
}
