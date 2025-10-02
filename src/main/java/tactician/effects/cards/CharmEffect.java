package tactician.effects.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import tactician.actions.PlaySoundAction;

public class CharmEffect extends AbstractGameEffect {
	private final String soundKey;
	private final float volume;
	private final float base_a;
	private float fadeOutStart;

	public CharmEffect(String soundKey, float volume) {
		this.soundKey = soundKey;
		this.volume = volume;
		this.duration = 1.5F;
		this.startingDuration = 1.5F;
		this.fadeOutStart = 0.5F;
		this.color = Color.YELLOW.cpy();
		this.base_a = 0.775F;
	}

	public void update() {
		if (this.duration == this.startingDuration) { AbstractDungeon.actionManager.addToTop(new PlaySoundAction(soundKey, volume)); }
		this.duration -= Gdx.graphics.getDeltaTime();
		if (this.duration > startingDuration) { this.color.a = Interpolation.pow5In.apply(startingDuration, 0.0F, (this.duration - this.startingDuration) / this.startingDuration); }
		else { this.color.a = Interpolation.exp10In.apply(0.0F, startingDuration, base_a * (this.duration / (this.startingDuration * 1.05F))); }
		if (this.duration < 0.0F) {
			this.color.a = 0.0F;
			this.isDone = true;
		}
	}

	public void render(SpriteBatch sb) {
		sb.setColor(this.color);
		sb.setBlendFunction(770, 1);
		sb.draw(ImageMaster.SPOTLIGHT_VFX, 125F, 0.0F, AbstractDungeon.player.drawX + AbstractDungeon.player.hb_w, Settings.HEIGHT);
		sb.setBlendFunction(770, 771);
	}

	public void dispose() {}
}