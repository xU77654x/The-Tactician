package tactician.effects.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import tactician.actions.PlaySoundAction;

public class TacticianWindEffect extends AbstractGameEffect {
	private float x;
	private float y;
	private final String soundKey;
	private final float volume;
	private final Color color;
	private float scale;
	private float speed;
	private final float speedStart;
	private final float speedTarget;
	private boolean fired;
	private final TextureAtlas.AtlasRegion img;

	public TacticianWindEffect(float x, float y, String soundKey, float volume, Color color, float scale) {
		this.img = ImageMaster.BLUR_WAVE;
		this.x = x - this.img.packedWidth / 2.0F;
		this.y = y - this.img.packedHeight / 2.0F;
		this.soundKey = soundKey;
		this.volume = volume;
		this.color = color;
		this.color.a = 1.0F;
		this.scale = scale;
		this.duration = 1.0F;
		this.rotation = MathUtils.random(-6.0F, 6.0F);
		this.renderBehind = false;
		this.speedStart = 3333.33F;
		this.speedTarget = 3333.33F * Settings.scale;
		this.speed = this.speedStart;
	}

	public void update() {
		if (!fired && this.soundKey != null) {
			fired = true;
			AbstractDungeon.actionManager.addToTop(new PlaySoundAction(this.soundKey, volume));
		}
		Vector2 tmp = new Vector2(MathUtils.cosDeg(this.rotation), MathUtils.sinDeg(this.rotation));
		tmp.x *= this.speed * Gdx.graphics.getDeltaTime();
		tmp.y *= this.speed * Gdx.graphics.getDeltaTime();
		this.x += tmp.x;
		this.y += tmp.y;
		this.scale *= 1.0F + Gdx.graphics.getDeltaTime() * 2.0F;
		this.duration -= Gdx.graphics.getDeltaTime();
		if (this.duration < 0.0F) {
			this.fired = false;
			this.isDone = true;
		}
	}

	public void render(SpriteBatch sb) {
		sb.setBlendFunction(770, 1);
		sb.setColor(this.color);
		sb.draw(this.img, this.x, this.y, this.img.packedWidth / 2.0F, this.img.packedHeight / 2.0F, this.img.packedWidth, this.img.packedHeight * 12, this.scale * 2, this.scale * 2, this.rotation + 270 + MathUtils.random(-3.0F, 3.0F));
		sb.setBlendFunction(770, 771);
	}

	public void dispose() {}
}