package tactician.music;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
/*
public class CustomMusic {
	private Music music;
	private float loopStart;
	private float loopEnd;

	public CustomMusic(String path, float loopStart, float loopEnd) {
		this.music = Gdx.audio.newMusic(Gdx.files.internal(path));
		this.loopStart = loopStart;
		this.loopEnd = loopEnd;
		this.music.setLooping(false);
	}

	public void play() {
		music.play();
	}

	public void update() {
		if (music.isPlaying()) {
			if (music.getPosition() >= loopEnd) {
				music.setPosition(loopStart);
			}
		}
	}

	public void stop() {
		music.stop();
	}
} */