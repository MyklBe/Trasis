package pl.michal.measurer;

import lombok.Cleanup;
import lombok.val;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeMeasurement {


    /*
    This pointcut is telling that measurement is running for all methods executing in runtime.
    First * means that we allow methods with any returning type
    Second * means that we take any methods, from any class, from any package. In one word, it's inspecting whole project.
    '(..)' means that we accept all methods with any arguments.
    */
    @Pointcut("execution(* *(..))")
    private void allMethods() {}

    @Around("allMethods() && @annotation(measurement)")
    public Object profile(ProceedingJoinPoint proceedingJoinPoint, Measurement measurement) throws Throwable {

        @Cleanup("stop") val timeCounter = new TimeCounter(measurement.maxTime());
        timeCounter.start(proceedingJoinPoint.toShortString());

        return proceedingJoinPoint.proceed();
    }
}
