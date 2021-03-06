package org.wahlzeit.model;

@PatternInstance(
	patternName = "Value Object",
	participants = {
		"ValueObject"	
	}
)
public class SphericCoordinate extends AbstractCoordinate{
	
	private double latitude;
	private double longitude;
	private double radius;

	
	public static SphericCoordinate makeSphericCoordinate(double latitude, double longitude, double radius) {
		SphericCoordinate temp = new SphericCoordinate(latitude, longitude, radius);
		for(int i = 0; i < CoordinateListSpher.size(); i++) {
			if(temp.isEqual(CoordinateListSpher.get(i))) {
				return CoordinateListSpher.get(i).asSphericCoordinate();
			}
		}
		CoordinateListSpher.add(temp);
		return temp;
	}
	
	/**
	 * constructor, takes x, y, z values as parameters 
	 * @param latitude
	 * @param longitude
	 * @param radius
	 * @pre radius, latitude and longitude are all valid values
	 * @post SphericCoordinte object is correct created
	 */
	protected SphericCoordinate(double latitude, double longitude, double radius) {
		//Precondition
		
		if(latitude >= Double.MAX_VALUE || longitude >= Double.MAX_VALUE || radius >= Double.MAX_VALUE) {
			throw new IllegalArgumentException("The given values aren't vaild!");
		}
				
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
		
		//Postcondition
		assert latitude == this.latitude : "couldn't write varibale x";
		assert longitude == this.longitude : "couldn't write varibale y";
		assert radius == this.radius : "couldn't write varibale z";
		//class invariant
		assertClassInvariants();
	}
	
	/**
	 *  method to convert this coordinate to a cartesian coordinate
	 *  @return the cartesian representation of this point
	 *  @post result != null
	 */
	@Override
	public CartesianCoordinate asCartesianCoordinate() {
		//class invariant
		assertClassInvariants();
		//no Precondition, because no input is given to that function
		
		CartesianCoordinate result = doAsCartesianCoordinate();
		
		//Postcondition
		if(result == null) {
			throw new IllegalStateException("Cound not creat new CartesianCoordinate object!");
		}
		//class invariant
		assertClassInvariants();
		
		return result;
	}
	
	/**
	 * method that does the actual work in converting the spheric representation to a cartesian one
	 * @return the cartesian representation of this point
	 */
	protected CartesianCoordinate doAsCartesianCoordinate() {
		double z = this.radius * Math.cos(this.longitude);
		double y = this.radius * Math.sin(this.longitude) * Math.sin(this.latitude);
		double x = this.radius * Math.sin(this.longitude) * Math.cos(this.latitude);
		CartesianCoordinate result =  CartesianCoordinate.makeCartesianCoordinate(x, y, z);
		return result;
	}

	/**
	 *  method to calculate the cartesian distance between given and and own coordinates
	 * @param coords
	 * @return the calculated distance
	 * @pre coords != null
	 * @post result >= 0
	 */
	@Override
	public double getCartesianDistance(Coordinate coords) {
		//class invariant
		assertClassInvariants();
		//Precondition
		if(coords == null){
			throw new IllegalArgumentException("Null was given as an parameter!");
		}
		
		CartesianCoordinate cartCoords = this.asCartesianCoordinate();
		double result = cartCoords.getCartesianDistance(cartCoords);
		
		//Postcondition
		if(result < 0) {
			throw new IllegalStateException("Distance wasn't calculated correctly!");
		}
		//class invariant
		assertClassInvariants();
		return result;
	}

	/**
	 *  method to convert this coordinate to a spheric coordinate
	 *  @return the spheric representation of this point
	 */
	@Override
	public SphericCoordinate asSphericCoordinate() {
		//no Precondition, because no input is given to this method 
		//no Postcondition, because object state isn't changed
		return this;
	}

	/**
	 *  method to calculate the spheric distance between given and and own coordinates
	 * @param coords
	 * @return the calculated distance,
	 * @pre coords != null
	 * @post result >= 0
	 */
	@Override
	public double getSphericDistance(Coordinate coords) {
		//class invariant
		assertClassInvariants();
		//Precondition
		if(coords == null){
			throw new IllegalArgumentException("Null was given as an parameter!");
		}
				
		double result = doGetSphericDistance(coords);
		
		//Postcondition
		if(result < 0) {
			throw new IllegalStateException("Distance wasn't calculated correctly!");
		}
		//class invariant
		assertClassInvariants();
		return result;
	}
	
	/**
	 *  to actual work for getSphericDistance is done here
	 * @param coords
	 * @return
	 */
	protected double doGetSphericDistance(Coordinate coords) {
		SphericCoordinate spherCoords;
		if( coords == null ){
			return -1.0;
		} else if( coords instanceof CartesianCoordinate ) {
			spherCoords = coords.asSphericCoordinate();
		} else {
			spherCoords = (SphericCoordinate) coords;
		}
		double deltaAngle = Math.acos(Math.sin(this.latitude) * Math.sin(spherCoords.latitude) +
				Math.cos(this.latitude) * Math.cos(spherCoords.latitude * Math.cos(Math.abs(this.longitude - spherCoords.longitude))));
		double result = this.radius * deltaAngle;
		
		return result;
	}

	/**
	 * method to check if the given coordinate object is the same as the referenced coordinate object
	 * @param coords
	 * @return true, if both coordinate objects are the same, false otherwise
	 */
	@Override
	public boolean isEqual(Coordinate coords) {
		//no Precondition, because every input can be given to this method 
		if(coords == null) {
			return false;
		}
		SphericCoordinate spherCoords = coords.asSphericCoordinate();
		if( coords == this ){
			return true;
		} else if( (Math.abs(spherCoords.latitude - this.latitude) < 0.00000001) && (Math.abs(spherCoords.longitude - this.longitude) < 0.00000001) && (Math.abs(spherCoords.radius - this.radius) < 0.00000001) ) {
			return true;
		} else {
			return false;
		}
		//no Postcondition, because state of object wasn't changed
	}

	/**
	 * getter method for radius value 
	 * @return radius
	 */
	public double getRadius() {
		//no Precondition, because no input is given to this method 
		//no Postcondition, because object state isn't changed
		return radius;
	}

	/**
	 * getter method for longitude value 
	 * @return longitude
	 */
	public double getLongitude() {
		//no Precondition, because no input is given to this method 
		//no Postcondition, because object state isn't changed
		return longitude;
	}

	/**
	 * getter method for latitude value 
	 * @return latitude
	 */
	public double getLatitude() {
		//no Precondition, because no input is given to this method 
		//no Postcondition, because object state isn't changed
		return latitude;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(radius);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	/**
	 * Method that checks the internal attributes of an SphericCoordinate Object for plausibility 
	 */
	@Override
	protected void assertClassInvariants() {
		if(this.radius == Double.NaN || this.radius < 0 || this.radius > Double.POSITIVE_INFINITY) {
			throw new IllegalArgumentException("No valid value for radius!");
		}
		
		if(this.latitude == Double.NaN || this.latitude < 0 || this.latitude >= 360) {
			throw new IllegalArgumentException("No valid value for latitude!");
		}
		
		if(this.longitude == Double.NaN || this.longitude < 0 || this.longitude >= 180) {
			throw new IllegalArgumentException("No valid value for longitude!");
		}
	}

}
