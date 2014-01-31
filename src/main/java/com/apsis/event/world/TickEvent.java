
package com.apsis.event.world;

import com.apsis.event.Event;

/**
 * @author Cal Stephens
 */
public class TickEvent extends Event{

	public final long lastTickStartTime;
	public final long lastTickEndTime;
	public final long lastTickDuration;
	public final long tickStartTime;
	
	public TickEvent(long lastTickStartTime, long lastTickEndTime, long lastTickDuration, long tickStartTime){
		this.lastTickDuration = lastTickDuration;
		this.lastTickEndTime = lastTickEndTime;
		this.lastTickStartTime = lastTickStartTime;
		this.tickStartTime = tickStartTime;
	}
	
}
