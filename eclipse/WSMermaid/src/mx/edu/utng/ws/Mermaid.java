package mx.edu.utng.ws;

public class Mermaid {
	private int id;
	private String name;
	private String age;
	private String description;
	private String type;
	private String sing;
	private String color;
	
	
	public Mermaid(int id, String name, String age, String description, String type, String sing, String color) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.description = description;
		this.type = type;
		this.sing = sing;
		this.color = color;
	}
	
	public Mermaid() {
		this(0,"","","","","","");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSing() {
		return sing;
	}

	public void setSing(String sing) {
		this.sing = sing;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Mermaid [id=" + id + ", name=" + name + ", age=" + age + ", description=" + description + ", type="
				+ type + ", sing=" + sing + ", color=" + color + "]";
	}
	
	
	
	  
	

}
