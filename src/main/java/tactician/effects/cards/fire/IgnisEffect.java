package tactician.effects.cards.fire;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.ExhaustEmberEffect;
import tactician.actions.PlaySoundAction;

public class IgnisEffect extends AbstractGameEffect {
	float x;
	float y;
	private final String soundKey;
	private final float volume;

	public IgnisEffect(AbstractCreature creature, String soundKey, float volume) {
		this.x = creature.hb.cX;
		this.y = creature.hb.cY;
		this.soundKey = soundKey;
		this.volume = volume;
	}

	public void update() {
		AbstractDungeon.actionManager.addToTop(new PlaySoundAction(this.soundKey, volume));
		int i;
		for (i = 0; i < 75; i++) { AbstractDungeon.effectsQueue.add(new IgnisParticleEffect(this.x, this.y)); }
		for (i = 0; i < 20; i++) { AbstractDungeon.effectsQueue.add(new ExhaustEmberEffect(this.x, this.y)); }
		this.isDone = true;
	}

	public void render(SpriteBatch sb) {}

	public void dispose() {}
}