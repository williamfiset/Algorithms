/**
 * This file shows you how to find the distance between two geographic coordinates.
 *
 * <p>Time Complexity: O(1)
 *
 * @author Micah Stairs
 */
package com.williamfiset.algorithms.geometry;

import static java.lang.Math.*;

public class LongitudeLatitudeGeographicDistance {

  // Compute the distance between geographic coordinates in units
  // of its radii (multiply by 6371km for Earth)
  public static double dist(double lat1, double lon1, double lat2, double lon2) {
    double dLat = toRadians(lat2 - lat1);
    double dLon = toRadians(lon2 - lon1);
    double a =
        sin(dLat / 2.0) * sin(dLat / 2.0)
            + cos(toRadians(lat1)) * cos(toRadians(lat2)) * sin(dLon / 2.0) * sin(dLon / 2.0);
    return 2.0 * atan2(sqrt(a), sqrt(1 - a));
  }
}
