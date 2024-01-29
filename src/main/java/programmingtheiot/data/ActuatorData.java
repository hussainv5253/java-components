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
* This class represents Actuator data in the context of the Programming the Internet of Things project.
* It extends the BaseIotData class and provides methods to get and set properties specific to ActuatorData,
* such as command, value, state data, and response flag. It also overrides the handleUpdateData method
* to update ActuatorData-specific properties when receiving data updates. The class includes a toString
* method for generating a string representation of the instance in CSV format.
*/
public class ActuatorData extends BaseIotData implements Serializable
{
	// static
	
	
	// private var's

	private int     command      = ConfigConst.DEFAULT_COMMAND;
	private float   value        = ConfigConst.DEFAULT_VAL;
	private boolean isResponse   = false;
	private String  stateData    = "";
    
    
	// constructors
	
	/**
	 * Default.
	 * 
	 */
	public ActuatorData()
	{
		super();
	}
	
	
	// public methods
	/**
	 * Getter for the command.
	 *
	 * @return int The command value.
	 */
	public int getCommand()
	{
		return this.command;
	}
	/**
	 * Getter for the value.
	 *
	 * @return float The value.
	 */
	public float getValue()
	{
		return this.value;
	}
	/**
	 * Getter for the state data.
	 *
	 * @return String The state data.
	 */
	public String getStateData()
	{
		return this.stateData;
	}
	/**
	 * Checks if the response flag is enabled.
	 *
	 * @return boolean True if the response flag is enabled, false otherwise.
	 */
	public boolean isResponseFlagEnabled()
	{
		return this.isResponse;
	}
	/**
	 * Sets the response flag to true.
	 */
	public void setAsResponse()
	{
		this.isResponse = true;
	}
	/**
	 * Setter for the command.
	 *
	 * @param command The command value to set.
	 */
	public void setCommand(int command)
	{
		this.command = command;
	}
	/**
	 * Setter for the value.
	 *
	 * @param val The value to set.
	 */
	public void setValue(float val)
	{
		this.value = val;
	}
	/**
	 * Setter for the state data.
	 *
	 * @param stateData The state data to set.
	 */
	public void setStateData(String stateData)
	{
		this.stateData = stateData;
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
		sb.append(ConfigConst.COMMAND_PROP).append('=').append(this.getCommand()).append(',');
		sb.append(ConfigConst.IS_RESPONSE_PROP).append('=').append(this.isResponseFlagEnabled()).append(',');
		sb.append(ConfigConst.VALUE_PROP).append('=').append(this.getValue());
		
		return sb.toString();
	}
	
	
	// protected methods
	
	/* (non-Javadoc)
	 * @see programmingtheiot.data.BaseIotData#handleUpdateData(programmingtheiot.data.BaseIotData)
	 */
	/**
	 * Handles updating data for ActuatorData instances based on the provided BaseIotData.
	 * Overrides the base class method to update ActuatorData-specific properties.
	 *
	 * @param data The BaseIotData instance to copy into the internal class-scoped variables.
	 */
	protected void handleUpdateData(BaseIotData data)
	{
		if (data instanceof ActuatorData)
		{
			ActuatorData aData = (ActuatorData) data;
			this.setValue(aData.getValue());
			this.setCommand(aData.getCommand());
			this.setStateData(aData.getStateData());
			
			if (aData.isResponseFlagEnabled()) {
				this.isResponse = true;
			}
		}
	}
	
}
