
public class Obtener_gradas {

	String id_espectaculo;
	String id_recinto;
	String fecha;
	String id_grada;
	String Max_preser;
	String p_bb;
	String p_inf;
	String p_adul;
	String p_parad;
	String p_jub;
	
	public Obtener_gradas(String a,String b,String c,String d,String e,String f,String g,String h,String i,String j) {
		this.id_espectaculo=a;
		this.id_recinto=b;
		this.id_grada=c;
		this.fecha=d;
		this.Max_preser=e;
		this.p_bb=f;
		this.p_inf=g;
		this.p_adul=h;
		this.p_parad=i;
		this.p_jub=j;
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
	public String getId_grada() {
		return id_grada;
	}
	public String getMax_preser() {
		return Max_preser;
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
