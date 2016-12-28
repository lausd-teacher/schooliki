/*
 * Copyright 2011 floreysoft GmbH (www.floreysoft.net)
 *
 * Written by Sergej Soller (ssoller@q-ric.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.floreysoft.gwt.picker.client.domain;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * MapsView is a subclass of View. Control the initial view for map selection using this class.
 */
public final class MapsView extends JavaScriptObject {
  protected MapsView() {}

  public ViewId getId() {
    return ViewId.findByValue(getNativeId());
  }
  private native String getNativeId() /*-{
    return this.getId();
  }-*/;

  public native void setQuery(String string) /*-{
    this.setQuery(string);
  }-*/;

  /**
   * Center the initial map view at a given coordinate.
   * The arguments are latitude and longitude values, respectively.
   *
   * @param lat The latitude
   * @param lng The longitude
   */
  public native void setCenter(double lat, double lng) /*-{
    this.setCenter(lat, lng);
  }-*/;

  /**
   * Set the initial zoom level.
   * Valid range is 0 to 21; a higher value indicates a greater zoom.
   *
   * @param zoom The zoom level
   */
  public native void setZoom(int zoom) /*-{
    this.setZoom(zoom);
  }-*/;

  /**
   * @return A new instance of MapsView
   */
  public native static MapsView create() /*-{
    return new $wnd.google.picker.MapsView();
  }-*/;
}
