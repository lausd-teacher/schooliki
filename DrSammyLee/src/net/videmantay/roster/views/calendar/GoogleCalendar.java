package net.videmantay.roster.views.calendar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.IsProperties;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import static com.google.gwt.query.client.GQuery.$$;

import gwt.material.design.client.ui.MaterialLoader;
import net.videmantay.roster.ClientFactory;
import net.videmantay.roster.json.CalendarItemJson;
import net.videmantay.roster.json.CalendarListJson;
import net.videmantay.shared.util.URLS;

public class GoogleCalendar extends Composite {

	private static GoogleCalendarUiBinder uiBinder = GWT.create(GoogleCalendarUiBinder.class);

	interface GoogleCalendarUiBinder extends UiBinder<Widget, GoogleCalendar> {
	}
	
	@UiField
	IFrameElement calendarFrame;
	
	ClientFactory factory;
	
	private CalendarListJson calendars = JavaScriptObject.createObject().cast();

	public GoogleCalendar(ClientFactory factory) {
		initWidget(uiBinder.createAndBindUi(this));
		this.factory = factory;
		String accessToken = factory.getAccessToken();
		
		if(accessToken != null){
		
			MaterialLoader.showLoading(true);
			GQuery.ajax(URLS.GOOGLE_CALENDAR_LIST_ENDPOINT,
					Ajax.createSettings().setType("GET").setHeaders($$("Authorization: Bearer "+ accessToken)))
					.done(new Function() {
						@Override
						public void f() {
							calendars = JsonUtils.safeEval(this.arguments(0).toString());
							MaterialLoader.showLoading(false);
							setGoogleCalendar();
							
						}
					}).progress(new Function() {
						@Override
						public void f() {
							MaterialLoader.showLoading(true);
						}
					}).fail(new Function() {
						@Override
						public void f() {
							MaterialLoader.showLoading(false);
							Window.alert("Error connecting to the Server, Please try again later");
						}
					});
		}

	}
	
	private void setGoogleCalendar(){
		
		CalendarItemJson calendarItem = calendars.getCalendars().get(0);
		String calendarId = calendarItem.getId();
		calendarId = calendarId.replace("@", "%40");
		String timeZone = calendarItem.getTimeZone();
		calendarFrame.setSrc("https://calendar.google.com/calendar/embed?src="+calendarId+"&ctz="+timeZone);
		
	}

}
