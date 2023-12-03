package net.grinecraft.etwig.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "portfolio")
public class Portfolio {
	
	public Portfolio(int portfolioID, String name, String color, String abbreviation) {
		super();
		this.portfolioID = portfolioID;
		this.name = name;
		this.color = color;
		this.abbreviation = abbreviation;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
	private int portfolioID;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "color")
	private String color;
	
	@Column(name = "abbreviation")
	private String abbreviation;

	public int getPortfolioID() {
		return portfolioID;
	}

	public void setPortfolioID(int portfolioID) {
		this.portfolioID = portfolioID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	@Override
	public String toString() {
		return "Portfolio [portfolioID=" + portfolioID + ", name=" + name + ", color=" + color + ", abbreviation=" + abbreviation + "]";
	}	
}
