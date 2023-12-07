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
 * information, action command, state data and a list of the following
 * data items:
 * <p>SystemPerformanceData
 * <p>SensorData
 * 
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
	
	public boolean addSystemPerformanceData(SystemPerformanceData data)
    {
        if (data != null) {
            return this.sysPerfDataList.add(data);
        }
        return false;
    }
	
	public boolean addSensorData(SensorData data)
    {
        if (data != null) {
            return this.sensorDataList.add(data);
        }
        return false;
    }


    public int getCommand()
    {
        return this.command;
    }

    public List<SensorData> getSensorDataList()
    {
        return this.sensorDataList;
    }

    public List<SystemPerformanceData> getSystemPerformanceDataList()
    {
        return this.sysPerfDataList;
    }

    public void setCommand(int actionCmd)
    {
        this.command = actionCmd;
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
