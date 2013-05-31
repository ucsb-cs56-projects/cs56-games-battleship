package edu.ucsb.cs56.S13.WeiGuo.battleship.model;


import java.io.*;
import java.applet.*;
import java.net.*;
import java.util.*;

/**
 * Sound.java: Manages the random sounds, and simplifies the game code.
 *
 * @author       William Dubel, Robert Ledesma
 * @version      1.0     June 29, 2001
 */

public class Sound
{
	private static Random random = new Random();
	static AudioClip yourTurn, wait, splash, sonar, start, victorious, loser, lostShip;

	private static AudioClip [] bombSounds = new AudioClip[5];
	static
	{
		try
		{
			bombSounds[0] = Applet.newAudioClip(new File("edu/ucsb/cs56/S13/WeiGuo/battleship/view/sounds/boom.wav").toURL());
			bombSounds[1] = Applet.newAudioClip(new File("edu/ucsb/cs56/S13/WeiGuo/battleship/view/sounds/blooey.wav").toURL());
			bombSounds[2] = Applet.newAudioClip(new File("edu/ucsb/cs56/S13/WeiGuo/battleship/view/sounds/bomb.wav").toURL());
			bombSounds[3] = Applet.newAudioClip(new File("edu/ucsb/cs56/S13/WeiGuo/battleship/view/sounds/explosion.wav").toURL());
			bombSounds[4] = Applet.newAudioClip(new File("edu/ucsb/cs56/S13/WeiGuo/battleship/view/sounds/thunder.wav").toURL());
			yourTurn = Applet.newAudioClip(new File("edu/ucsb/cs56/S13/WeiGuo/battleship/view/sounds/yourTurn.wav").toURL());
			wait = Applet.newAudioClip(new File("edu/ucsb/cs56/S13/WeiGuo/battleship/view/sounds/wait.wav").toURL());
			splash = Applet.newAudioClip(new File("edu/ucsb/cs56/S13/WeiGuo/battleship/view/sounds/splash.wav").toURL());
			sonar = Applet.newAudioClip(new File("edu/ucsb/cs56/S13/WeiGuo/battleship/view/sounds/sonar.wav").toURL());
			start = Applet.newAudioClip(new File("edu/ucsb/cs56/S13/WeiGuo/battleship/view/sounds/start.wav").toURL());
			victorious = Applet.newAudioClip(new File("edu/ucsb/cs56/S13/WeiGuo/battleship/view/sounds/victorious.wav").toURL());
			loser = Applet.newAudioClip(new File("edu/ucsb/cs56/S13/WeiGuo/battleship/view/sounds/loser.wav").toURL());
			lostShip = Applet.newAudioClip(new File("edu/ucsb/cs56/S13/WeiGuo/battleship/view/sounds/lostship.wav").toURL());
		}
		catch(MalformedURLException mue){}
	}

	public static void playHit()	{	bombSounds[random.nextInt(5)].play();	}
}