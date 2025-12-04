package tactician.effects.cards.fire;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.ExhaustEmberEffect;
import com.megacrit.cardcrawl.vfx.scene.TorchParticleXLEffect;

public class BolganoneEffect extends AbstractGameEffect {
	private float x;
	private float y;
	private static final float X_RADIUS = 200.0F * Settings.scale;
	private static final float Y_RADIUS = 250.0F * Settings.scale;
	private Vector2 v = new Vector2(0.0F, 0.0F);

	public BolganoneEffect(float x, float y) {
		this.duration = 0.5F;
		this.x = x;
		this.y = y;
		this.renderBehind = false;
	}

	@Override
	public void update() {
		float tmp = Interpolation.fade.apply(-209.0F, 30.0F, this.duration / 0.5F) * 0.017453292F;
		this.v.x = MathUtils.cos(tmp) * X_RADIUS;
		this.v.y = -MathUtils.sin(tmp) * Y_RADIUS;
		AbstractDungeon.effectsQueue.add(new TorchParticleXLEffect(this.x + this.v.x + MathUtils.random(-30.0F, 30.0F) * Settings.scale, this.y + this.v.y + MathUtils.random(-10.0F, 10.0F) * Settings.scale));
		AbstractDungeon.effectsQueue.add(new TorchParticleXLEffect(this.x + this.v.x + MathUtils.random(-30.0F, 30.0F) * Settings.scale, this.y + this.v.y + MathUtils.random(-10.0F, 10.0F) * Settings.scale));
		AbstractDungeon.effectsQueue.add(new TorchParticleXLEffect(this.x + this.v.x + MathUtils.random(-30.0F, 30.0F) * Settings.scale, this.y + this.v.y + MathUtils.random(-10.0F, 10.0F) * Settings.scale));
		AbstractDungeon.effectsQueue.add(new ExhaustEmberEffect(this.x + this.v.x, this.y + this.v.y));
		this.duration -= Gdx.graphics.getDeltaTime();
		if (this.duration < 0.0F) { this.isDone = true; }
	}

	@Override
	public void render(SpriteBatch sb) {}

	@Override
	public void dispose() {}
}