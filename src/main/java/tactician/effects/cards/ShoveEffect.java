package tactician.effects.cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.StarBounceEffect;
import tactician.actions.PlaySoundAction;

public class ShoveEffect extends AbstractGameEffect {
	private final float x;
	private final float y;
	private final String soundKey;
	private final float volume;
	private boolean fired;

	public ShoveEffect(float x, float y, String soundKey, float volume) {
		this.x = x;
		this.y = y;
		this.soundKey = soundKey;
		this.volume = volume;
	}

	@Override
	public void update() {
		for (int i = 0; i < 9; i++) { AbstractDungeon.actionManager.addToTop(new VFXAction(new StarBounceEffect(this.x, this.y))); }
		AbstractDungeon.actionManager.addToTop(new PlaySoundAction(this.soundKey, volume));
		this.isDone = true;
	}

	@Override
	public void render(SpriteBatch sb) {}

	@Override
	public void dispose() {}
}