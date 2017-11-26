package org.wahlzeit.model;

public abstract class AbstractCoordinate implements Coordinate{
	
	/**
	 *  method to calculate the cartesian distance between a given point a the
	 *  point on which this method is called
	 *  @param coords
	 *  @return cartesian distance
	 */
	@Override
	public double getDistance(Coordinate coords) {
		return getCartesianDistance(coords);
	}
	
	/**
	 *  method to forward the java.lang.object method equals() to the implemented method isEqual()
	 *  @param obj
	 *  @return true, if given object is the same as referenced object, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		} else if(obj == this){
			return true;
		} else if(obj instanceof CartesianCoordinate) {
			return isEqual((Coordinate) obj);
		} else if(obj instanceof SphericCoordinate) {
			return isEqual((Coordinate) obj);
		} else {
			return false;
		}
	}
}
