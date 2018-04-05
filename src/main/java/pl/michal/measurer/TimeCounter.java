package pl.michal.measurer;

import lombok.extern.log4j.Log4j;
import org.springframework.util.StopWatch;

@Log4j
public class TimeCounter {

    private static final String SEPARATOR = ":";

    private StopWatch stopWatch;

    private Double maxTime;

    public TimeCounter(Double maxTime){
        this.maxTime = maxTime;
    }

    public void start(String taskName){
        this.stopWatch = new StopWatch();
        this.stopWatch.start(taskName);
    }

    public void stop(){
        stopWatch.stop();
        StopWatch.TaskInfo taskInfo = stopWatch.getLastTaskInfo();
        if(taskInfo.getTimeSeconds() > maxTime) {
            StringBuilder sb = new StringBuilder();
            sb.append(taskInfo.getTaskName());
            sb.append(SEPARATOR);
            sb.append(taskInfo.getTimeSeconds());
            log.info(sb.toString());
        }
    }

}
