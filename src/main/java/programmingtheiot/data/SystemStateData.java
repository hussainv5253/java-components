/**
 * This class is part of the Programming the Internet of Things project.
 * 
 * It is provided as a simple shell to guide the student and assist with
 * implementation for the Programming the Internet of Things exercises,
 * and designed to be modified by the student as needed.
 */ 

package programmingtheiot.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import programmingtheiot.common.ConfigConst;

/**
 * Convenience wrapper to store system state data, including location
 * information, action command, state data, and a list of SystemPerformanceData
 * and SensorData items.
 */
public class SystemStateData extends BaseIotData implements Serializable
{
	// static
	
	
	// private var's
	private int command = ConfigConst.DEFAULT_COMMAND;
	private List<SystemPerformanceData> sysPerfDataList = null;
	private List<SensorData> sensorDataList = null;
    
    
	// constructors
	
	public SystemStateData()
	{
		super();
		
		super.setName(ConfigConst.SYS_STATE_DATA);
		
		this.sysPerfDataList = new ArrayList<>();
		this.sensorDataList  = new ArrayList<>();
	}
	
	// public methods
	/**
	 * Adds a SystemPerformanceData item to the list.
	 * @param data The SystemPerformanceData item to add.
	 * @return boolean True if the addition was successful, false otherwise.
	 */
	public boolean addSystemPerformanceData(SystemPerformanceData data)
	{
		if (data != null) {
			return this.sysPerfDataList.add(data);
		}
		return false;
	}

	/**
	 * Adds a SensorData item to the list.
	 * @param data The SensorData item to add.
	 * @return boolean True if the addition was successful, false otherwise.
	 */
	public boolean addSensorData(SensorData data)
	{
		if (data != null) {
			return this.sensorDataList.add(data);
		}
		return false;
	}
	
	/**
	 * Setter method to set the action command value.
	 * @param actionCmd The value to set for the action command.
	 */
	public void setCommand(int actionCmd)
	{
		this.command = actionCmd;
	}

	/**
	 * Getter method to retrieve the action command.
	 * @return int The action command value.
	 */
	public int getCommand()
	{
		return this.command;
	}

	/**
	 * Getter method to retrieve the list of SensorData items.
	 * @return List<SensorData> The list of SensorData items.
	 */
	public List<SensorData> getSensorDataList()
	{
		return this.sensorDataList;
	}

	/**
	 * Getter method to retrieve the list of SystemPerformanceData items.
	 * @return List<SystemPerformanceData> The list of SystemPerformanceData items.
	 */
	public List<SystemPerformanceData> getSystemPerformanceDataList()
	{
		return this.sysPerfDataList;
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
		sb.append(ConfigConst.SENSOR_DATA_LIST_PROP).append('=').append(this.getSensorDataList()).append(',');
		sb.append(ConfigConst.SYSTEM_PERF_DATA_LIST_PROP).append('=').append(this.getSystemPerformanceDataList());
		
		return sb.toString();
	}
	
	
	// protected methods
	
	/* (non-Javadoc)
	 * @see programmingtheiot.data.BaseIotData#handleUpdateData(programmingtheiot.data.BaseIotData)
	 */
	/**
	 * Handles the update of data for this instance by extracting the values from the provided
	 * BaseIotData object if it is an instance of SystemStateData.
	 * @param data The BaseIotData object to use for updating.
	 */
	protected void handleUpdateData(BaseIotData data)
	{
		if (data instanceof SystemStateData)
		{
			SystemStateData ssData = (SystemStateData) data;
			this.setCommand(ssData.getCommand());
			
			// Add sensor data from ssData to this.sensorDataList
	        List<SensorData> ssSensorDataList = ssData.getSensorDataList();
	        for (SensorData sensorData : ssSensorDataList) {
	            this.addSensorData(sensorData);
	        }
			
	        // Add SystemPerformanceData data from ssData to this.sensorDataList
	        List<SystemPerformanceData> ssSystemPerfDataList = ssData.getSystemPerformanceDataList();
	        for (SystemPerformanceData sysPerfData : ssSystemPerfDataList) {
	            this.addSystemPerformanceData(sysPerfData);
	        }	
		}
	}
	
}
