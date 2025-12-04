package tactician.effects.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class RareCardBorderEffect extends AbstractGameEffect {
	private boolean fired;
	private final Color color;
	private float timer = 0.0F;

	public RareCardBorderEffect(Color color) {
		this.color = color;
		this.duration = 1.0F;
	}

	@Override
	public void update() {
		this.timer -= Gdx.graphics.getDeltaTime();
		if (this.timer < 0.0F) {
			this.timer += 0.05F;
		}
		if (this.timer < 0.045F) { this.color.a = Interpolation.fade.apply(0.0F, 1.0F, (1.0F - this.timer) * 10.0F); }
		else { this.color.a = Interpolation.pow2Out.apply(0.0F, 1.0F, this.timer); }
	}

	@Override
	public void render(SpriteBatch sb) { sb.setColor(this.color); }

	@Override
	public void dispose() {}
}