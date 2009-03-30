/* DistanceData.java - distance data storage.
 *
 * Copyright (c) 2009 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wigraph;

/** Distance data storage
 */
public class DistanceData {

    public float average;
    public float min;
    public float max;

    DistanceData(float _average,float _min,float _max) {
        average = _average;
        min = _min;
        max = _max;
    }

}
