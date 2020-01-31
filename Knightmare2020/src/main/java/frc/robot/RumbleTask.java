/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.TimerTask;
import java.util.Timer;
import java.lang.Thread;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

/**
 * Add your docs here.
 */
public class RumbleTask extends TimerTask {

    private Timer timer = new Timer();

    private final long RUMBLE_PUSLE_TIME = 100;
    private final double RUMBLE_STRENGTH = 1.0;
    private final long RUMBLE_SPIN_DOWN_TIME = 200;

    private XboxController m_controller;
    private RumbleType[] m_sides;
    private int m_num;

    public RumbleTask( XboxController controller, RumbleType[] sides, int num ){
        m_controller = controller;
        m_sides = sides;
        m_num = num;
    }
    
    private void rumble( XboxController controller, RumbleType[] sides, double strength ) {
        for( RumbleType s: sides ){
            controller.setRumble(s, strength ); 
        }
    } 

    public void rumblePulse( XboxController controller, RumbleType[] sides, int num ){
        
    }

    private void task_wait( long wait_time_ms ){
        try {
            Thread.sleep( wait_time_ms );
        }
        catch ( Exception e )
        {
            // Active wait
            // Start the clock
            long rumble_start_time = System.nanoTime();

            // Wait for wait_time_ms in nanoseconds
            while ( System.nanoTime() - rumble_start_time < wait_time_ms * 1000000 )
            { /* wait */ }
        }
    }

    /*
     * This method is called automatically by Timer.schedule()
     */
    @Override
    public void run()
    {
        // Grab num from outer scope
        int n = m_num;

        while ( n > 0 )
        {
            // Turn on the rumble
            rumble( m_controller, m_sides, 1 );

            // Wait for rumble_pulse_time
            task_wait( RUMBLE_PUSLE_TIME );

            // Turn off the rumble
            rumble( m_controller, m_sides, 0 );

            // If this is the last pulse, do not pause for next pulse
            if ( n <= 1 )
                break;

            // Allow motor to spin down
            task_wait( RUMBLE_SPIN_DOWN_TIME );

            n--;
        }

        // Destroy the thread
        timer.cancel();
        timer.purge();
    }


// // Start the pulses immediately
//     timer.schedule( task, 0 );

}
