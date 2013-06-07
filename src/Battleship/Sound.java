package Battleship;

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
			bombSounds[0] = Applet.newAudioClip(new File("sounds/boom.wav").toURL());
			bombSounds[1] = Applet.newAudioClip(new File("sounds/blooey.wav").toURL());
			bombSounds[2] = Applet.newAudioClip(new File("sounds/bomb.wav").toURL());
			bombSounds[3] = Applet.newAudioClip(new File("sounds/explosion.wav").toURL());
			bombSounds[4] = Applet.newAudioClip(new File("sounds/thunder.wav").toURL());
			yourTurn = Applet.newAudioClip(new File("sounds/yourTurn.wav").toURL());
			wait = Applet.newAudioClip(new File("sounds/wait.wav").toURL());
			splash = Applet.newAudioClip(new File("sounds/splash.wav").toURL());
			sonar = Applet.newAudioClip(new File("sounds/sonar.wav").toURL());
			start = Applet.newAudioClip(new File("sounds/start.wav").toURL());
			victorious = Applet.newAudioClip(new File("sounds/victorious.wav").toURL());
			loser = Applet.newAudioClip(new File("sounds/loser.wav").toURL());
			lostShip = Applet.newAudioClip(new File("sounds/lostship.wav").toURL());
		}
		catch(MalformedURLException mue){}
	}

	public static void playHit()	{	bombSounds[random.nextInt(5)].play();	}
}