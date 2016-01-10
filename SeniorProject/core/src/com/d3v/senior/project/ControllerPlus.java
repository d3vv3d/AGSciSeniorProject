package com.d3v.senior.project;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;

/**
 * Created by Dev on 9/13/15.
 */
public class ControllerPlus extends ControllerAdapter
{
    boolean prevState0= false;
    boolean prevState1 = false;
    boolean prevState2 = false;
    boolean prevState3 = false;
    boolean prevState5 = false;
    boolean prevState4 = false;
    boolean prevState6 = false;
    boolean prevState7 = false;
    boolean prevState9 = false;
    boolean prevState12 = false;
    public boolean buttonJustPressed(Controller controller, int buttonIndex)
    {
        switch (buttonIndex)
        {
            case 0: if (controller.getButton(0))
                {
                    prevState0 = true;
                }
                if (prevState0)
                {
                    if (!controller.getButton(0))
                    {
                        prevState0 = false;
                        return true;
                    }
                }
                break;
            case 1: if (controller.getButton(1))
                    {
                        prevState1 = true;
                    }
                    if (prevState1)
                    {
                        if (!controller.getButton(1))
                        {
                            prevState1 = false;
                            return true;
                        }
                    }
                break;
            case 2: if (controller.getButton(2))
            {
                prevState2 = true;
            }
                if (prevState2)
                {
                    if (!controller.getButton(2))
                    {
                        prevState2 = false;
                        return true;
                    }
                }
                break;
            case 3: if (controller.getButton(3))
            {
                prevState3 = true;
            }
                if (prevState3)
                {
                    if (!controller.getButton(0))
                    {
                        prevState3 = false;
                        return true;
                    }
                }
                break;
            case 4: if (controller.getButton(4))
            {
                prevState4 = true;
            }
                if (prevState4)
                {
                    if (!controller.getButton(4))
                    {
                        prevState4 = false;
                        return true;
                    }
                }
                break;
            case 5: if (controller.getButton(5))
            {
                prevState5 = true;
            }
                if (prevState5)
                {
                    if (!controller.getButton(5))
                    {
                        prevState5 = false;
                        return true;
                    }
                }
                break;
            case 6: if (controller.getButton(6))
            {
                prevState6 = true;
            }
                if (prevState6)
                {
                    if (!controller.getButton(6))
                    {
                        prevState6 = false;
                        return true;
                    }
                }
                break;
            case 7: if (controller.getButton(7))
            {
                prevState7 = true;
            }
                if (prevState7)
                {
                    if (!controller.getButton(7))
                    {
                        prevState7 = false;
                        return true;
                    }
                }
                break;
            case 9: if (controller.getButton(9))
            {
                prevState9 = true;
            }
                if (prevState9)
                {
                    if (!controller.getButton(9))
                    {
                        prevState9 = false;
                        return true;
                    }
                }
                break;
            case 12: if (controller.getButton(12))
                    {
                        prevState12 = true;
                    }
                    if (prevState12)
                    {
                        if (!controller.getButton(12))
                        {
                            prevState12 = false;
                            return true;
                        }
                    }
                    break;
        }
        return false;
    }

    //Analog stick only left Up and Down
    boolean prevStateUp = false;
    boolean prevStateDown = false;
    public boolean axisReleased(Controller controller, int axis, float direction) {
        if (direction < 0) {
            if (!prevStateUp) {
                if (controller.getAxis(axis) >= direction) {
                    prevStateUp = true;
                    return true;
                }
            } else if (prevStateUp) {
                if (controller.getAxis(axis) <= direction)
                    prevStateUp = false;
            }
        }
        else if (direction > 0) {
            if (!prevStateDown) {
                if (controller.getAxis(axis) <= direction) {
                    prevStateDown = true;
                    return true;
                }
            } else if (prevStateDown) {
                if (controller.getAxis(axis) >= direction)
                    prevStateDown = false;
            }
        }
        return false;
    }
}
