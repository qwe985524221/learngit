package com.zyj.test.quartztest;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RAMJob implements Job{
	private static int i=0;
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("定时任务启动 "+RAMJob.i);
		RAMJob.i++;
		if(RAMJob.i>2) {
			try {
				arg0.getScheduler().clear();
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	
}
