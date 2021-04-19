package com.mapbox.maps

import android.content.Context
import android.util.AttributeSet

/**
 * Defines configuration [MapInitOptions] for a [MapboxMap]. These options can be used when adding a
 * map to your application programmatically (as opposed to via XML). If you are using a MapFragment,
 * you can pass these options in using the static factory method newInstance(MapboxMapOptions).
 * If you are using a [MapView], you can pass these options in using the constructor
 * MapView(Context, MapboxMapOptions). If you add a map using XML, then you can apply these options
 * using custom XML tags.
 *
 * @property context the context of the application.
 * @property resourceOptions Resource options when using a MapView. Access token required when using a Mapbox service. Please see [https://www.mapbox.com/help/create-api-access-token/](https://www.mapbox.com/help/create-api-access-token/) to learn how to create one.More information in this guide [https://www.mapbox.com/help/first-steps-android-sdk/#access-tokens](https://www.mapbox.com/help/first-steps-android-sdk/#access-tokens).
 * @property mapOptions Describes the map options value when using a MapView.
 * @property initialCameraOptions The Initial Camera options when creating a MapView.
 * @property textureView Flag indicating to use a TextureView as render surface for the MapView. Default is false.
 * @property attrs The [AttributeSet] object that init the MapView.
 */
data class MapInitOptions constructor(
  val context: Context,
  var resourceOptions: ResourceOptions = getDefaultResourceOptions(context),
  var mapOptions: MapOptions = getDefaultMapOptions(context),
  var initialCameraOptions: CameraOptions? = null,
  var textureView: Boolean = false,
  var attrs: AttributeSet? = null
) {

  /**
   * Static methods
   */
  companion object {
    /**
     * Get a default [ResourceOptions] with token from default [CredentialsManager]
     * @property context the context of the application.
     */
    fun getDefaultResourceOptions(context: Context): ResourceOptions = ResourceOptions.Builder()
      .accessToken(CredentialsManager.default.getAccessToken(context))
      .cachePath("${context.filesDir.absolutePath}/$DATABASE_NAME")
      .assetPath(context.filesDir.absolutePath)
      .cacheSize(50_000_000) // 50 mb
      .build()

    /**
     * Get a default [MapOptions] with reasterization mode [GlyphsRasterizationMode#ALL_GLYPHS_RASTERIZED_LOCALLY]
     * @property context the context of the application.
     */
    fun getDefaultMapOptions(context: Context): MapOptions = MapOptions.Builder()
      .glyphsRasterizationOptions(
        GlyphsRasterizationOptions.Builder()
          .rasterizationMode(GlyphsRasterizationMode.ALL_GLYPHS_RASTERIZED_LOCALLY)
          .build()
      ).pixelRatio(context.resources.displayMetrics.density)
      .build()
  }
}