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

public class TacticianAxeEffect extends AbstractGameEffect {
	private final float x;
	private final float y;
	private final String soundKey;
	private final float volume;
	private final Color color;
	private final float size;
	private boolean fired;
	private final TextureAtlas.AtlasRegion img;

	public TacticianAxeEffect(float x, float y, String soundKey, float volume, Color color, float size) {
		this.img = ImageMaster.VERTICAL_IMPACT;
		this.x = x - (this.img.packedWidth / 2.0F);
		this.y = y - (this.img.packedHeight * 0.01F);
		this.soundKey = soundKey;
		this.volume = volume;
		this.duration = 0.625F;
		this.startingDuration = 0.625F;
		this.color = color;
		this.size = size;
		this.rotation = 45.0F;
		this.scale = Settings.scale;
		this.renderBehind = false;
	}

	@Override
	public void update() {
		this.duration -= Gdx.graphics.getDeltaTime();
		if (!fired) {
			AbstractDungeon.actionManager.addToTop(new PlaySoundAction(this.soundKey, volume));
			fired = true;
		}
		if (this.duration > 0.2F) { this.color.a = Interpolation.fade.apply(0.5F, 0.0F, (this.duration - 0.34F) * 5.0F); }
		else { this.color.a = Interpolation.fade.apply(0.0F, 0.5F, this.duration * 5.0F); }
		this.scale = Interpolation.fade.apply(Settings.scale * 1.1F, Settings.scale * 1.05F, this.duration / 0.6F) / size;

		if (this.duration < 0.0F) {
			fired = false;
			this.isDone = true;
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setColor(this.color);
		sb.setBlendFunction(770, 1);
		sb.draw(this.img, this.x + MathUtils.random(-10.0F, 10.0F) * Settings.scale, this.y, this.img.packedWidth / 2.0F, 0.0F, this.img.packedWidth, this.img.packedHeight, this.scale * 0.3F, this.scale * 0.8F, this.rotation - 18.0F);
		sb.draw(this.img, this.x + MathUtils.random(-10.0F, 10.0F) * Settings.scale, this.y, this.img.packedWidth / 2.0F, 0.0F, this.img.packedWidth, this.img.packedHeight, this.scale * 0.3F, this.scale * 0.8F, this.rotation + MathUtils.random(12.0F, 18.0F));
		sb.draw(this.img, this.x + MathUtils.random(-10.0F, 10.0F) * Settings.scale, this.y, this.img.packedWidth / 2.0F, 0.0F, this.img.packedWidth, this.img.packedHeight, this.scale * 0.4F, this.scale * 0.5F, this.rotation - MathUtils.random(-10.0F, 14.0F));
		sb.draw(this.img, this.x + MathUtils.random(-10.0F, 10.0F) * Settings.scale, this.y, this.img.packedWidth / 2.0F, 0.0F, this.img.packedWidth, this.img.packedHeight, this.scale * 0.7F, this.scale * 0.9F, this.rotation + MathUtils.random(20.0F, 28.0F));
		sb.draw(this.img, this.x + MathUtils.random(-10.0F, 10.0F) * Settings.scale, this.y, this.img.packedWidth / 2.0F, 0.0F, this.img.packedWidth, this.img.packedHeight, this.scale * 1.5F, this.scale * MathUtils.random(1.4F, 1.6F), this.rotation);
		Color c = Color.GOLD.cpy();
		c.a = this.color.a;
		sb.setColor(c);
		sb.draw(this.img, this.x + MathUtils.random(-10.0F, 10.0F) * Settings.scale, this.y, this.img.packedWidth / 2.0F, 0.0F, this.img.packedWidth, this.img.packedHeight, this.scale, this.scale * MathUtils.random(0.8F, 1.2F), this.rotation);
		sb.draw(this.img, this.x + MathUtils.random(-10.0F, 10.0F) * Settings.scale, this.y, this.img.packedWidth / 2.0F, 0.0F, this.img.packedWidth, this.img.packedHeight, this.scale, this.scale * MathUtils.random(0.4F, 0.6F), this.rotation);
		sb.draw(this.img, this.x + MathUtils.random(-10.0F, 10.0F) * Settings.scale, this.y, this.img.packedWidth / 2.0F, 0.0F, this.img.packedWidth, this.img.packedHeight, this.scale * 0.5F, this.scale * 0.7F, this.rotation + MathUtils.random(20.0F, 28.0F));
		sb.setBlendFunction(770, 771);
	}

	@Override
	public void dispose() {}
}