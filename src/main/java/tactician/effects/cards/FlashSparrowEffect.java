package tactician.effects.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import tactician.actions.PlaySoundAction;

public class FlashSparrowEffect extends AbstractGameEffect {
	private float x;
	private float y;
	private final float endX;
	private final float endY;
	private final float scaleMultiplier;
	private final String soundKey;
	private final float volume;
	private boolean fired;
	private TextureAtlas.AtlasRegion img;

	public FlashSparrowEffect(float x, float y, String soundKey, float volume, Color color) {
		this.img = ImageMaster.DAGGER_STREAK;
		x -= (120.0F * Settings.scale);
		y -= (-80.0F * Settings.scale);
		this.endX = x - this.img.packedWidth / 2.0F;
		this.endY = y - this.img.packedHeight / 2.0F;
		this.x += this.endX + MathUtils.random(-550.0F, -450.0F) * Settings.scale;
		this.y = this.endY + MathUtils.random(380.0F, 320.0F) * Settings.scale;
		this.soundKey = soundKey;
		this.volume = volume;
		this.startingDuration = 0.3F;
		this.duration = 0.3F;
		this.scaleMultiplier = MathUtils.random(0.05F, 0.2F);
		this.rotation = 150.0F;
		this.color = color;
	}

	public void update() {
		if (!fired) {
			AbstractDungeon.actionManager.addToTop(new PlaySoundAction(this.soundKey, volume));
			fired = true;
		}
		this.duration -= Gdx.graphics.getDeltaTime();
		this.x = Interpolation.swingIn.apply(this.endX, this.x, this.duration * 3.33F);
		this.y = Interpolation.swingIn.apply(this.endY, this.y, this.duration * 3.33F);
		if (this.duration < 0.0F) {
			this.fired = false;
			this.isDone = true;
			this.duration = 0.0F;
		}
		this.color.a = 1.0F - (this.duration / 2);
		this.scale = this.duration * Settings.scale + this.scaleMultiplier;
	}

	public void render(SpriteBatch sb) {
		sb.setColor(this.color);
		sb.draw(this.img, this.x, this.y, this.img.packedWidth / 2.0F, this.img.packedHeight / 2.0F, this.img.packedWidth, this.img.packedHeight, this.scale, this.scale * 1.5F, this.rotation);
		sb.setBlendFunction(770, 1);
		sb.draw(this.img, this.x, this.y, this.img.packedWidth / 2.0F, this.img.packedHeight / 2.0F, this.img.packedWidth, this.img.packedHeight, this.scale * 0.75F, this.scale * 0.75F, this.rotation);
		sb.setBlendFunction(770, 771);
	}

	public void dispose() {}
}