package tactician.effects.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import tactician.actions.PlaySoundAction;
import tactician.effects.PlayVoiceEffect;

public class LunaEffect extends AbstractGameEffect {
	private float x;
	private float y;
	private TextureAtlas.AtlasRegion img;
	private Color altColor;
	public LunaEffect() {
		this.img = ImageMaster.CRYSTAL_IMPACT;
		this.x = AbstractDungeon.player.hb.cX - this.img.packedWidth / 2.0F;
		this.y = AbstractDungeon.player.hb.cY - this.img.packedHeight / 2.0F;
		this.startingDuration = 1.25F;
		this.duration = this.startingDuration;
		this.scale = Settings.scale;
		this.color = Color.BLUE.cpy();
		this.altColor = Color.CYAN.cpy();
		this.color.a = 0.0F;
		this.renderBehind = false;
	}

	public void update() {
		this.duration -= Gdx.graphics.getDeltaTime();
		if (this.duration > this.startingDuration / 2.0F) { this.color.a = Interpolation.fade.apply(1.0F, 0.01F, this.duration - this.startingDuration / 2.0F) * Settings.scale; }
		else { this.color.a = Interpolation.fade.apply(0.01F, 1.0F, this.duration / this.startingDuration / 2.0F) * Settings.scale; }
		this.scale = Interpolation.pow5In.apply(2.4F, 0.3F, this.duration / this.startingDuration) * Settings.scale;
		if (this.duration < 0.0F) { this.isDone = true; }
	}

	public void render(SpriteBatch sb) {
		sb.setBlendFunction(770, 1);
		this.altColor.a = this.color.a;
		sb.setColor(this.altColor);
		sb.draw(this.img, this.x - 10, this.y + 8, this.img.packedWidth / 2.0F, this.img.packedHeight / 2.0F, this.img.packedWidth, this.img.packedHeight, this.scale * 1.1F, this.scale * 1.1F, 0.0F);
		sb.setColor(this.color);
		sb.draw(this.img, this.x - 10, this.y + 8, this.img.packedWidth / 2.0F, this.img.packedHeight / 2.0F, this.img.packedWidth, this.img.packedHeight, this.scale * 0.9F, this.scale * 0.9F, 0.0F);
		sb.setBlendFunction(770, 771);
	}

	public void dispose() {}
}