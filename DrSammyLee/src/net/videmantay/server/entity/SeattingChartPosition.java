package net.videmantay.server.entity;

import net.videmantay.shared.FurnitureType;

public class SeattingChartPosition {
	
	private double x;
	private double y;
	private FurnitureType type;
	private Long studentId;
	
	
	public SeattingChartPosition(){
		
		
	}


	public SeattingChartPosition(double x, double y, FurnitureType type) {
		super();
		this.x = x;
		this.y = y;
		this.type = type;
	}


	public double getX() {
		return this.x;
	}


	public double getY() {
		return this.y;
	}


	public FurnitureType getType() {
		return this.type;
	}


	public void setX(double x) {
		this.x = x;
	}


	public void setY(double y) {
		this.y = y;
	}


	public void setType(FurnitureType type) {
		this.type = type;
	}
	
	
	

}
