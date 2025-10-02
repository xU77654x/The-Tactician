package tactician.effects.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.SwirlyBloodEffect;
import tactician.actions.PlaySoundAction;

public class TacticianDarkEffect extends AbstractGameEffect {
	private final float x;
	private final float y;
	private final String soundKey;
	private final float volume;
	private float swirlCount;
	private boolean fired;
	private boolean flipHorizontal;
	private boolean flipVertical;
	private float scaleY;
	private float aV;

	public TacticianDarkEffect(float x, float y, String soundKey, float volume, float imgScale, float swirlCount) {
		this.x = x;
		this.y = y;
		this.color = Color.PURPLE.cpy();
		this.soundKey = soundKey;
		this.volume = volume;
		this.swirlCount = swirlCount;
		this.duration = 0.50F;
		this.startingDuration = 0.50F;
		this.renderBehind = false;
		this.scale = Settings.scale * imgScale;
		this.rotation = MathUtils.random(-8.0F, 8.0F);
		this.flipHorizontal = MathUtils.randomBoolean();
		this.flipVertical = MathUtils.randomBoolean();
		this.aV = MathUtils.random(-100.0F, 100.0F);
	}

	@Override
	public void update() {
		if (!fired) {
			AbstractDungeon.actionManager.addToTop(new PlaySoundAction(this.soundKey, volume));
			fired = true;
		}
		if (swirlCount > 0) {
			AbstractDungeon.actionManager.addToTop(new VFXAction(new SwirlyBloodEffect(this.x, this.y)));
			swirlCount--;
		}
		this.rotation += Gdx.graphics.getDeltaTime() * this.aV;
		this.scale = Interpolation.pow4Out.apply(5.0F, 1.0F, this.duration * 4.0F) * Settings.scale;
		this.scaleY = Interpolation.bounceOut.apply(0.2F, 2.0F, this.duration * 4.0F) * Settings.scale;
		this.color.a = 1.0F;
		this.duration -= Gdx.graphics.getDeltaTime();
		if (this.duration < 0.0F) {
			fired = false;
			this.isDone = true;
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setColor(this.color);
		sb.setBlendFunction(770, 1);
		sb.draw(ImageMaster.DARK_ORB_ACTIVATE_VFX, this.x - 70.0F, this.y - 70.0F, 70.0F, 70.0F, 140.0F, 140.0F, this.scale, this.scaleY, this.rotation, 0, 0, 140, 140, this.flipHorizontal, this.flipVertical);
		sb.setBlendFunction(770, 771);
	}

	@Override
	public void dispose() {}
}