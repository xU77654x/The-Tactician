package tactician.effects.cards.fire;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.LightFlareParticleEffect;

public class ArcfireIgniteEffect extends AbstractGameEffect {
	private static final int COUNT = 25;
	private final float x;
	private final float y;

	public ArcfireIgniteEffect(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void update() {
		for (int i = 0; i < 25; i++) {
			AbstractDungeon.effectsQueue.add(new ArcfireBurstParticleEffect(this.x, this.y));
			AbstractDungeon.effectsQueue.add(new LightFlareParticleEffect(this.x, this.y, Color.ORANGE));
		}
		this.isDone = true;
	}

	public void render(SpriteBatch sb) {}

	public void dispose() {}
}