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
 * This class represents system performance data and extends the BaseIotData class.
 * It includes attributes for CPU utilization, disk utilization, and memory utilization.
 * It implements the Serializable interface to allow for object serialization.
 */
public class SystemPerformanceData extends BaseIotData implements Serializable
{
	// static
	
	
	// private var's
	private float cpuUtil  = ConfigConst.DEFAULT_VAL;
	private float diskUtil = ConfigConst.DEFAULT_VAL;
	private float memUtil  = ConfigConst.DEFAULT_VAL;
	
    
	// constructors
	/**
	 * Default constructor that calls the constructor of the base class and sets the name
	 * of the data to SYS_PERF_DATA.
	 */
	public SystemPerformanceData()
	{
		super();
		
		super.setName(ConfigConst.SYS_PERF_DATA);
	}
	
	
	// public methods
	/**
	 * Getter method to retrieve the CPU utilization.
	 * @return float The CPU utilization value.
	 */
	public float getCpuUtilization()
	{
		return this.cpuUtil;
	}
	
	/**
	 * Getter method to retrieve the disk utilization.
	 * @return float The disk utilization value.
	 */
	public float getDiskUtilization()
	{
		return this.diskUtil;
	}
	
	/**
	 * Getter method to retrieve the memory utilization.
	 * @return float The memory utilization value.
	 */
	public float getMemoryUtilization()
	{
		return this.memUtil;
	}
	
	/**
	 * Setter method to set the CPU utilization value.
	 * @param val The value to set for CPU utilization.
	 */
	public void setCpuUtilization(float val)
	{
		this.cpuUtil = val;
	}

	/**
	 * Setter method to set the disk utilization value.
	 * @param val The value to set for disk utilization.
	 */
	public void setDiskUtilization(float val)
	{
		this.diskUtil = val;
	}

	/**
	 * Setter method to set the memory utilization value.
	 * @param val The value to set for memory utilization.
	 */
	public void setMemoryUtilization(float val)
	{
		this.memUtil = val;
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
		sb.append(ConfigConst.CPU_UTIL_PROP).append('=').append(this.getCpuUtilization()).append(',');
		sb.append(ConfigConst.DISK_UTIL_PROP).append('=').append(this.getDiskUtilization()).append(',');
		sb.append(ConfigConst.MEM_UTIL_PROP).append('=').append(this.getMemoryUtilization());
		
		return sb.toString();
	}
	
	
	// protected methods
	
	/* (non-Javadoc)
	 * @see programmingtheiot.data.BaseIotData#handleUpdateData(programmingtheiot.data.BaseIotData)
	 */
	/**
	 * Handles the update of data for this instance by extracting the values from the provided
	 * BaseIotData object if it is an instance of SystemPerformanceData.
	 * @param data The BaseIotData object to use for updating.
	 */
	protected void handleUpdateData(BaseIotData data)
	{
		if (data instanceof SystemPerformanceData)
		{
			SystemPerformanceData sData = (SystemPerformanceData) data;
			this.setCpuUtilization(sData.getCpuUtilization());
			this.setDiskUtilization(sData.getDiskUtilization());
			this.setMemoryUtilization(sData.getMemoryUtilization());
		}
	}
	
}
