package net.videmantay.server.entity;

import java.io.Serializable;

public class Station implements Serializable{
	public String title;
	public String color;
	public String description;
	public String x;
	public String y ;
	public String width;
	public String height;
	public String rotate; 
	public String image;
	public int number;
	public String zIndex;
	public StationType type;
	
	enum StationType{CARPET_L, CARPET_S, KIDNEY,SQUARE,CIRCLE}
}
