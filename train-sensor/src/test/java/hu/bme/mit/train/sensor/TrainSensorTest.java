package hu.bme.mit.train.sensor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;

import static org.mockito.Mockito.*;

public class TrainSensorTest {

	
	private TrainUser user;
	
	private TrainController controller;
	private TrainSensor sensor;
	
    @Before
    public void before() {
    	user = mock(TrainUser.class);
    	controller = mock(TrainController.class);
    	sensor = new TrainSensorImpl(controller, user);
        controller.setSpeedLimit(120);
        controller.setJoystickPosition(120);
        controller.followSpeed();
    }

    @Test
    public void speedLimitLessThan0() {
        sensor.overrideSpeedLimit(-1);
        
        when(user.getAlarmState()).thenReturn(true);
        verify(user,times(1)).setAlarmState(true);
    }
    @Test
    public void speedLimitGreaterThan500() {
    	sensor.overrideSpeedLimit(501);    	
        when(user.getAlarmState()).thenReturn(true);
        verify(user,times(1)).setAlarmState(true);
    }
    @Test
    public void speedLimitTooSmall() {    
    	sensor.overrideSpeedLimit(50);     	
    	verify(controller,times(1)).getReferenceSpeed();    	
    	when(user.getAlarmState()).thenReturn(true);
    	
    	
    	
    }
    @Test
    public void speedLimitIsFine() {
    	sensor.overrideSpeedLimit(70);
    	
        when(user.getAlarmState()).thenReturn(false);
        verify(user,times(0)).setAlarmState(true);
        verify(user,times(0)).setAlarmState(false);
    }
}
