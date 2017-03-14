package gwtquery.plugins.ui.interactions;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.query.client.Function;

import gwtquery.plugins.ui.Ui;
import gwtquery.plugins.ui.UiPlugin;
import gwtquery.plugins.ui.UiWidget;
import gwtquery.plugins.ui.WidgetOptions;

/**
 * Implements jQuery-UI's Sortable
 * 
 * @see <a href="http://docs.jquery.com/UI/Sortable">jQuery-UI's Sortable</a>
 * 
 * @author Philippe Laflamme
 */
public class Sortable extends UiWidget<Sortable, Sortable.Options> {

  public static class Options extends WidgetOptions<Options> {

    protected Options() {

    }

    public static native final Options create()
    /*-{
      return {};
    }-*/;
 
    public static native final Options containment(String s)/*-{
  		this["containment"] = s;
  		return this;
  	}-*/;
    
    public static native final Options change(Function f)/*-{
    	this["change"] = function(event, ui){
   		 f.@com.google.gwt.query.client.Function::f(Lcom/google/gwt/user/client/Event;[Ljava/lang/Object;)(event, [ui]);
   	}
   		return this;
    }-*/;
  }

 //EVENTS
  public static class Event extends JavaScriptObject{
	  protected Event(){}
	  public final static String change = "sortchange";
  }

  /**
   * Used to register the plugin.
   */
  private static class SortablePlugin implements UiPlugin<Sortable> {

    public Sortable init(Ui ui, WidgetOptions<?> options) {
      return new Sortable(ui, (Sortable.Options) options.cast());
    }

  }

  public static final Class<Sortable> Sortable = Sortable.class;

  static {
    registerPlugin(Sortable.class, new SortablePlugin());
  }

  public Sortable(Ui ui, Sortable.Options options) {
    super(ui, "sortable", options);
  }
}