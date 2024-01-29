/**
 * This class is part of the Programming the Internet of Things project.
 * 
 * It is provided as a simple shell to guide the student and assist with
 * implementation for the Programming the Internet of Things exercises,
 * and designed to be modified by the student as needed.
 */ 

package programmingtheiot.data;

import java.io.Serializable;

import programmingtheiot.common.ConfigConst;

/**
 * This class represents sensor data and extends the BaseIotData class. It implements the
 * Serializable interface to allow for object serialization.
 */
public class SensorData extends BaseIotData implements Serializable
{
	// static
	
	
	// private var's
	private float value = ConfigConst.DEFAULT_VAL;
	
    
	// constructors
	
	public SensorData()
	{
		super();
	}
	
	public SensorData(int sensorType)
	{
		super();
	}
	
	
	// public methods
	/**
	 * Getter method to retrieve the sensor data value.
	 * @return float The sensor data value.
	 */
	public float getValue()
	{
		return this.value;
	}
	
	/**
	 * Setter method to set the sensor data value.
	 * @param val The value to set for the sensor data.
	 */
	public void setValue(float val)
	{
		this.value = val;
	}
	
	/**
	 * Returns a string representation of this instance. This will invoke the base class
	 * {@link #toString()} method, then append the output from this call.
	 * 
	 * @return String The string representing this instance, returned in CSV 'key=value' format.
	 */
	public String toString()
	{
		StringBuilder sb = new StringBuilder(super.toString());
		
		sb.append(',');
		sb.append(ConfigConst.VALUE_PROP).append('=').append(this.getValue());
		
		return sb.toString();
	}
	
	
	// protected methods
	
	/* (non-Javadoc)
	 * @see programmingtheiot.data.BaseIotData#handleUpdateData(programmingtheiot.data.BaseIotData)
	 */
	/**
	 * Handles the update of data for this instance by extracting the value from the provided
	 * BaseIotData object if it is an instance of SensorData.
	 * @param data The BaseIotData object to use for updating.
	 */
	protected void handleUpdateData(BaseIotData data)
	{
		if (data instanceof SensorData)
		{
			SensorData sData = (SensorData) data;
			this.setValue(sData.getValue());
		}
	}
	
}
