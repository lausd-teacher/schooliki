package gwtquery.plugins.ui.interactions;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayInteger;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;

import gwtquery.plugins.ui.Ui;
import gwtquery.plugins.ui.UiPlugin;
import gwtquery.plugins.ui.UiWidget;
import gwtquery.plugins.ui.WidgetOptions;
import gwtquery.plugins.ui.interactions.Draggable.Options;

/**
 * Implements jQuery-UI's Resizable
 * 
 * @see <a href="http://docs.jquery.com/UI/Resizable">jQuery-UI's Resizable</a>
 * 
 * @author Philippe Laflamme
 */
public class Resizable extends UiWidget<Resizable, Resizable.Options> {

  public static class Options extends WidgetOptions<Options> {

    protected Options() {

    }

    public static native final Options create()
    /*-{
      return {};
    }-*/;

    public native final Options containment(String containment)
    /*-{
      this["containment"] = containment;
      return this;
    }-*/;

    public native final Options containment(Element containment)
    /*-{
      this["containment"] = containment;
      return this;
    }-*/;

    public native final Options containment(JsArrayInteger containment)
    /*-{
      this["containment"] = containment;
      return this;
    }-*/;

    public final Options containment(GQuery gquery) {
      this.containment(gquery.get(0));
      return this;
    }
    
    
    ///Events//////////
    public final native Options stop(Function f)/*-{
   	this["stop"] = function(event, ui){
   		 f.@com.google.gwt.query.client.Function::f(Lcom/google/gwt/user/client/Event;[Ljava/lang/Object;)(event, [ui]);
   	}
   		return this;
   }-*/;
  }

  public static class Event extends JavaScriptObject {

	    public static final String start = "resizestart";

	    public static final String stop = "resizestop";

	    protected Event() {

	    }
	  }

  /**
   * Used to register the plugin.
   */
  private static class ResizablePlugin implements UiPlugin<Resizable> {

    public Resizable init(Ui ui, WidgetOptions<?> options) {
      return new Resizable(ui, (Resizable.Options) options.cast());
    }

  }

  public static final Class<Resizable> Resizable = Resizable.class;

  static {
    registerPlugin(Resizable.class, new ResizablePlugin());
  }

  public Resizable(Ui ui, Resizable.Options options) {
    super(ui, "resizable", options);
  }
}