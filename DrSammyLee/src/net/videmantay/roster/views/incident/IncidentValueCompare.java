package net.videmantay.roster.views.incident;

import java.util.Comparator;

import net.videmantay.roster.json.IncidentJson;

public class IncidentValueCompare implements Comparator<IncidentJson> {

	@Override
	public int compare(IncidentJson o1, IncidentJson o2) {
		return o1.getPoints() -o2.getPoints();
	}

}
