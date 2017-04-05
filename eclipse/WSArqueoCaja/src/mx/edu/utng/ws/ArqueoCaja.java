package mx.edu.utng.ws;

public class ArqueoCaja {
	private int cveArqueo;
	private int cveUnidadAcademica;
	private String fechaArqueo;
	private String observaciones;
	private float total;
	private String personal;
	
	
	public ArqueoCaja(int cveArqueo, int cveUnidadAcademica, String fechaArqueo, String observaciones, float total,
			String personal) {
		super();
		this.cveArqueo = cveArqueo;
		this.cveUnidadAcademica = cveUnidadAcademica;
		this.fechaArqueo = fechaArqueo;
		this.observaciones = observaciones;
		this.total = total;
		this.personal = personal;
	}
	
	public ArqueoCaja() {
		this(0,0,"","",0.0F,"");
	}

	public int getCveArqueo() {
		return cveArqueo;
	}

	public void setCveArqueo(int cveArqueo) {
		this.cveArqueo = cveArqueo;
	}

	public int getCveUnidadAcademica() {
		return cveUnidadAcademica;
	}

	public void setCveUnidadAcademica(int cveUnidadAcademica) {
		this.cveUnidadAcademica = cveUnidadAcademica;
	}

	public String getFechaArqueo() {
		return fechaArqueo;
	}

	public void setFechaArqueo(String fechaArqueo) {
		this.fechaArqueo = fechaArqueo;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public String getPersonal() {
		return personal;
	}

	public void setPersonal(String personal) {
		this.personal = personal;
	}

	@Override
	public String toString() {
		return "ArqueoCaja [cveArqueo=" + cveArqueo + ", cveUnidadAcademica=" + cveUnidadAcademica + ", fechaArqueo="
				+ fechaArqueo + ", observaciones=" + observaciones + ", total=" + total + ", personal=" + personal
				+ "]";
	}
	
	
}
