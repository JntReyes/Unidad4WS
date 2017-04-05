package mx.edu.utng.ws;

public class AreaGradoExtracurricular {
	private int cveAreaGradoExtracurricular;
	private String descripcion;
	private String activo;
	
	
	public AreaGradoExtracurricular(int cveAreaGradoExtracurricular, String descripcion, String activo) {
		super();
		this.cveAreaGradoExtracurricular = cveAreaGradoExtracurricular;
		this.descripcion = descripcion;
		this.activo = activo;
	}
	
	public AreaGradoExtracurricular() {
		this(0,"","");
	}

	public int getCveAreaGradoExtracurricular() {
		return cveAreaGradoExtracurricular;
	}

	public void setCveAreaGradoExtracurricular(int cveAreaGradoExtracurricular) {
		this.cveAreaGradoExtracurricular = cveAreaGradoExtracurricular;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "AreaGradoExtracurricular [cveAreaGradoExtracurricular=" + cveAreaGradoExtracurricular + ", descripcion="
				+ descripcion + ", activo=" + activo + "]";
	}
	
	
	
	

}
