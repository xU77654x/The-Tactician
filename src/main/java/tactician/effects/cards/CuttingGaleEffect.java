package tactician.effects.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.WindyParticleEffect;
import tactician.actions.PlaySoundAction;

public class CuttingGaleEffect extends AbstractGameEffect {
	private final float x;
	private final float y;
	private final String soundKey;
	private final float volume;
	private boolean fired;
	private final Color windColor;
	private int count = 0;
	private float timer = 0.0F;

	public CuttingGaleEffect(float x, float y, String soundKey, float volume) {
		this.x = x;
		this.y = y;
		this.windColor = new Color(0.8F, 1.0F, 0.85F, 0.9F);
		this.color = new Color(0.5F, 1.0F, 0.67F, 1.0F);
		this.soundKey = soundKey;
		this.volume = volume;
		// this.duration = 1.5F;
	}

	@Override
	public void update() {
		if (!fired) {
			AbstractDungeon.actionManager.addToTop(new PlaySoundAction(this.soundKey, volume));
			fired = true;
		}
		this.timer -= Gdx.graphics.getDeltaTime();
		if (this.timer < 0.0F) {
			this.timer += 0.05F;
			if (this.count == 0) { AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(this.windColor.cpy())); }
			AbstractDungeon.effectsQueue.add(new WindyParticleEffect(this.windColor.cpy(), false));
			this.count++;
			if (this.count == 10) {
				fired = false;
				this.isDone = true;
			}
		}
		if (this.timer < 0.045F) { this.color.a = Interpolation.fade.apply(0.0F, 1.0F, (1.0F - this.timer) * 10.0F); }
		else { this.color.a = Interpolation.pow2Out.apply(0.0F, 1.0F, this.timer); }
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setColor(this.color);
		sb.draw(ImageMaster.INTENT_MAGIC_L, this.x - 256F, this.y - 256F, 128.0F * this.scale * 4, 128.0F * this.scale * 4, 0.0F,0.0F, 1F, 1F);
	}

	@Override
	public void dispose() {}
}