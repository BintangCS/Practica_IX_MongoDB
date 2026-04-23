package model;

import org.bson.Document;

public class Cocktail {
    private String name;
    private String category;
    private String alcoholic;
    private String glass;
    private String instructions;

    public Cocktail(String name, String category, String alcoholic, String glass, String instructions) {
        this.name = name;
        this.category = category;
        this.alcoholic = alcoholic;
        this.glass = glass;
        this.instructions = instructions;
    }

    public Document toDocument() {
        return new Document("name", name)
                .append("category", category)
                .append("isAlcoholic", alcoholic.equalsIgnoreCase("Alcoholic"))
                .append("glassType", glass)
                .append("instructions", instructions);
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAlcoholic() {
		return alcoholic;
	}

	public void setAlcoholic(String alcoholic) {
		this.alcoholic = alcoholic;
	}

	public String getGlass() {
		return glass;
	}

	public void setGlass(String glass) {
		this.glass = glass;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
    
    
}