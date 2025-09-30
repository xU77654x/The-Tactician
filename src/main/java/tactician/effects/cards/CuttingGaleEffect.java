package tactician.effects.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.WindyParticleEffect;
import tactician.actions.PlaySoundAction;

import static com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE;

public class CuttingGaleEffect extends AbstractGameEffect {
	private float x;
	private float y;
	private String soundKey;
	private float volume;
	private boolean fired;
	private int count = 0;
	private float timer = 0.0F;

	public CuttingGaleEffect(float x, float y, String soundKey, float volume) {
		this.x = x;
		this.y = y;
		this.color = new Color(0.8F, 1.0F, 0.85F, 0.9F);
		this.soundKey = soundKey;
		this.volume = volume;
		this.duration = 1.5F;
	}

	public void update() {
		if (!fired) {
			AbstractDungeon.actionManager.addToTop(new PlaySoundAction(this.soundKey, volume));
			// AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.x, this.y, NONE)); // TODO: Implement Axe and Lance effects this way?
			fired = true;
		}
		this.timer -= Gdx.graphics.getDeltaTime();
		if (this.timer < 0.0F) {
			this.timer += 0.05F;
			if (this.count == 0) { AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(this.color.cpy())); }
			AbstractDungeon.effectsQueue.add(new WindyParticleEffect(this.color, false));
			this.count++;
			if (this.count == 10) {
				fired = false;
				this.isDone = true;
			}
		}
	}

	public void render(SpriteBatch sb) {
		sb.setColor(0.5F, 1.0F, 1.0F, 1.0F);
		sb.draw(ImageMaster.INTENT_MAGIC_L, this.x - 256F, this.y - 256F, 128.0F * this.scale * 4, 128.0F * this.scale * 4, 0.0F,0.0F, 1F, 1F);
	}
	// TODO: This sprite needs to fade in and fade out.

	public void dispose() {}
}