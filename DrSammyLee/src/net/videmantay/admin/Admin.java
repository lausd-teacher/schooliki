package net.videmantay.admin;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;

import net.videmantay.admin.places.MainAdminPlace;


public class Admin implements EntryPoint {
	
	private MainAdminPlace adminPlace = new MainAdminPlace("admin");
	private SimplePanel appWidget = new SimplePanel();

	@Override
	public void onModuleLoad() {
		
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		final PlaceController controller = clientFactory.getPlaceController();

		EventBus bus = clientFactory.getEventBus();
		ActivityMapper activityMapper = new MyActivityMapper(clientFactory);
		ActivityManager activityManager = new ActivityManager(activityMapper, bus);
		activityManager.setDisplay(appWidget);

		MyHistoryMapper historyMapper = GWT.create(MyHistoryMapper.class);
		final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(controller, bus, adminPlace);

		RootPanel.get().add(appWidget);

		
//		Window.addWindowClosingHandler(new ClosingHandler(){
//	     	@Override
//			public void onWindowClosing(ClosingEvent event) {
//	     		controller.goTo(new RosterHomePlace("rosters"));
//			}
//		});
		
		historyHandler.handleCurrentHistory();
	}

}
