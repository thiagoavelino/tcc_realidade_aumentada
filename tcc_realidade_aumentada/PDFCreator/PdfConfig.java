package PDFCreator;

import java.io.Serializable;

class PdfConfig implements Serializable{
	private String materia;
	private String professor;
	private String assunto;
	
	public PdfConfig(String prof, String mat, String ass){
		this.materia = mat;
		this.professor=prof;
		this.assunto = ass;
	}
	
	public String getMateria() {
		return materia;
	}
	public void setMateria(String materia) {
		this.materia = materia;
	}
	public String getProfessor() {
		return professor;
	}
	public void setProfessor(String professor) {
		this.professor = professor;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
}

