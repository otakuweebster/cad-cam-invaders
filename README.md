![CADCAM ALIENS](https://i.imgur.com/19m19et.png)
# THE INVASION OF THE CADCAM ALIENS
An all Java-created game to demonstrate different capabilities that the JAVAFX dependencies can create and alter different assets alike. Based from an collaborative
project for COSC190.

**NOTE**: In under no circumstances you can use this to substitute as your homework file. It is against your university/post-secondary's academic integrity and 
we do not take any responsibility for your suspension. THIS IS MERELY JUST A PROJECT THAT HAS BEEN DECIDED TO BE CODED AS A HOBBY UPON SUBMISSION. 

## ABOUT THE GAME
The Earth is being invaded by one of CST's most deadliest and ravenous creatures in the galaxy, **CAD-CAM ALIENS**. Equipped with the knowledge of controlling
a launcher remotely, you are tasked to shoot down their ships before they reach Earth. 

## HOW IT WORKS
- Use your keyboard's left or right button to move left/right, and up button to shoot pellets. **HOWEVER**, you cant shoot pellets rapidly. It reloads every
  second to charge itself up, so be careful!
- As the level progress, the difficulty and the number of ufos increases. Be alert!
- Scores are as follow:
  - 6 points for hitting a ship
  - -1 point for bullets fired. (Think of it as a wasted resources for using those bullets)
- If any ufo reaches the earth, **EARTH HAS BEEN INVADED**

## THE INTERNALS
Interally, this game has been heavily coded in Java, more specifically through JAVAFX. However, threading is also a big part of the game as creating multiple threads instances
is more effective than using timeline when moving the ships down.

For the splashscreen and pre and post game animation, all of it are coded with Timelines, SequentialTimeline, and FadeTransition through the help of AnimationUtils to
allow the given ImageView object to go left and right continously until to a certain point.

For the game itself, it uses Java's Threading System to allow multiple threads of barrage to work continuosly while the JavaFX's thread is running in the background.

## Acknowledgement
Thanks to Levi Krozser for coding the partial portion of the Invaders Class. Truly helped me in understanding how thread can make or break our game.
Thanks to Bryce Barrie for the project. It was truly fun!
Thanks to Kale Oleksuik for some assistance and support for the game. You rock!


