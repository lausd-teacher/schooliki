package net.videmantay.roster.views.assignment;

import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.view.client.AsyncDataProvider;

import net.videmantay.roster.json.GradedWorkJson;

public class AssignmentGrid extends DataGrid<GradedWorkJson> {

	public AssignmentGrid(AsyncDataProvider<GradedWorkJson> providesKey) {
		super(0, providesKey);
	}
}
