package tactician.effects.cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.AnimatedSlashEffect;
import tactician.actions.PlaySoundAction;

public class TacticianSwordLanceEffect extends AbstractGameEffect {
	private final float x;
	private final float y;
	private final String soundKey;
	private final float volume;
	private final float angle;
	private final float velocityX;
	private final float velocityY;
	private final float scale;
	private final Color color;

	public TacticianSwordLanceEffect(float x, float y, String soundKey, float volume, float angle, float velocityX, float velocityY, float scale, Color color) {
		this.x = x;
		this.y = y;
		this.soundKey = soundKey;
		this.volume = volume;
		this.angle = angle;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.scale = scale;
		this.color = color;
		this.startingDuration = 0.075F;
		this.duration = this.startingDuration;
	}

	@Override
	public void update() {
		AbstractDungeon.actionManager.addToTop(new PlaySoundAction(this.soundKey, volume));
		AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(this.x, this.y - 30.0F * Settings.scale, velocityX, velocityY, angle, scale, color, Color.LIGHT_GRAY));
		for (int i = 0; i < 3; i++) { AbstractDungeon.effectsQueue.add(new UpgradeShineParticleEffect(this.x + MathUtils.random(-40.0F, 40.0F) * Settings.scale, this.y + MathUtils.random(-40.0F, 40.0F) * Settings.scale)); }
		this.isDone = true;
	}

	@Override
	public void render(SpriteBatch sb) {}

	@Override
	public void dispose() {}
}