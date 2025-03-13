# MIR Technical Description

## MIR100
The robot model that we use is a MiR100. It is an automated robot for logistics purposes.
There is installed a module to connect a cart over it using two pins.
The Mir100 has a wifi antenna that allows the user to connect to the dashboard and the API.
In the end, the MiR100 is equipped with useful infrared laser sensors and 3D cameras to locate itself
and the obstacles around it.

## Maps and Missions
Both from the dashboard or the API, the MiR100 uses a combination of maps and missions to run tasks.
Maps are created by scanning the surrounding environment.
Missions are the series of actions, in a map, that allows the MiR to complete a specific task.
Missions are added to a queue and executed in a second moment (when the status of the MiR is "executing")

## API 
The MiR100 has an http API implemented and accessible via the wifi module.
The API requires authentication using a bearer token that can be calculated from the user credential (WIP).
API calls allow us to control the MiR in every action it can do.
In this specific moment, we still don't know how to run mission queued but only how to add them to the queue.

## Controller
A goal for our project is to add a joystick to control the robot in manual mode.
We know that to create the controller we need to implement a websocket,
but the development of this feature is still in working.

    X -> 1 forward movement
    X -> -1 backward movement
    Z -> 1 leftward movement
    Z -> -1 rightward movement

