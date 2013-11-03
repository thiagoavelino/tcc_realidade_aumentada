package PDFCreator;

/***
 * Classe que armazena os dados a serem inseridos no pdf.
 * A imagem sera guardada em um diret�rio tempor�rio para n�o ocupar tanta mem�ria.
 * @author FARINHAKI
 *
 */
class ImStr{
	private String text;
	private String image;
	private String description;

	public ImStr(String tx, String imageName, String desc){
		this.text = tx;
		this.image = imageName;
		this.description = desc;
	}
	
	public String getText(){
		return this.text;
	}
	public String getImage(){
		return this.image;
	}
	public String getDescription(){
		return this.description;
	}
}

